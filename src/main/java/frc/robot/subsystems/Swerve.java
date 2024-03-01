package frc.robot.subsystems;

import java.util.Optional;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.Robot;
import frc.robot.SwerveModule;

public class Swerve extends SubsystemBase {
    public boolean onRedAlliance;
    public Robot thisRobot;
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    public Pigeon2 gyro;
    public SwerveDrivePoseEstimator m_poseEstimator;
    public Eyes eyes;
    public PIDController rotPidController = new PIDController(6, 1,0.2f);

    private DoubleArrayPublisher moduleStatePublisher = NetworkTableInstance.getDefault()
            .getDoubleArrayTopic("/ModuleStates").publish();

    public Rotation2d goalHeading = new Rotation2d();

      AprilTagFieldLayout aprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();



    public Swerve() {
        gyro = new Pigeon2(Constants.Swerve.pigeonID, "7451CANivore");
        gyro.getConfigurator().apply(new Pigeon2Configuration());
        gyro.setYaw(0);
        eyes = new Eyes();
        rotPidController.enableContinuousInput(-Math.PI, Math.PI);
        rotPidController.setIZone(3);

        // m_poseEstimator =
        //   new SwerveDrivePoseEstimator(
        //      Constants.Swerve.swerveKinematics,
        //      gyro.getRotation2d(),
        //      getModulePositions(),
        //      new Pose2d(),
        //      VecBuilder.fill(0.1, 0.1, 0.1),
        //      VecBuilder.fill(1.5, 1.5, 1.5)
        //   );

        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Swerve.Mod0.constants),
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };

        Timer.delay(1.0);
        resetModulesToAbsolute();

        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getGyroYaw(), getModulePositions());

        AutoBuilder.configureHolonomic(
                this::getPose, // Robot pose supplier
                this::setPose, // Method to reset odometry (will be called if your auto has a starting pose)
                this::getChassisSpeed, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                this::setChassisSpeed, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
                new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
                        new PIDConstants(3.3, 0.0, 0.0), // Translation PID constants
                        new PIDConstants(1, 0.0, 0.0), // Rotation PID constants
                        5, // Max module speed, in m/s
                        0.39,
                        new ReplanningConfig() // Drive base radius in meters. Distance from robot center to furthest module.
                        //new ReplanningConfig(false, false) // Default path replanning config. See the API for the options here
                ),
                () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                    var alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) {
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                this // Reference to this subsystem to set requirements
        );
    }

    public ChassisSpeeds getChassisSpeed() {

        return Constants.Swerve.swerveKinematics.toChassisSpeeds(getModuleStates());

    }

    public void setChassisSpeed(ChassisSpeeds chassisSpeed) {

        SmartDashboard.putNumber("SetChassisSpeedX", chassisSpeed.vxMetersPerSecond);
        SmartDashboard.putNumber("SetChassisSpeedY", chassisSpeed.vyMetersPerSecond);
        SmartDashboard.putNumber("SetChassisSpeedOmega", chassisSpeed.omegaRadiansPerSecond);
        

        SwerveModuleState[] desiredStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(chassisSpeed);
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        moduleStatePublisher.set(new double[] {
            desiredStates[0].angle.getDegrees(), desiredStates[0].speedMetersPerSecond,
            desiredStates[1].angle.getDegrees(), desiredStates[1].speedMetersPerSecond,
            desiredStates[2].angle.getDegrees(), desiredStates[2].speedMetersPerSecond,
            desiredStates[3].angle.getDegrees(), desiredStates[3].speedMetersPerSecond,
        });

        setModuleStates(desiredStates);
    }
        

    public void drive(Translation2d translation, double rotation, boolean fieldcentric,  boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldcentric ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getHeading()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }    

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }


    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void setPose(Pose2d pose) {
        swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), pose);
    }

    public Rotation2d getHeading(){
        return getPose().getRotation();
    }

    public void setHeading(Rotation2d heading){
        swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), new Pose2d(getPose().getTranslation(), heading));
    }

    public void zeroHeading(){
        swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), new Pose2d(getPose().getTranslation(), new Rotation2d()));
    }

    public Rotation2d getGyroYaw() {
        return Rotation2d.fromDegrees(gyro.getYaw().getValue());
    }

        public double getYawDegrees() {
        return gyro.getYaw().getValue();
    }

      public double getGyroRoll() {
        return gyro.getRoll().getValue();
    }

          public double getGyroPitch() {
        return gyro.getPitch().getValue();
    }

    public void resetModulesToAbsolute(){
        for(SwerveModule mod : mSwerveMods){
            mod.resetToAbsolute();
        }
    }

    public void aimAtPoint(Translation2d point) {
        Translation2d currentPos = getPose().getTranslation();
        Translation2d deltaPos = currentPos.minus(point);
        goalHeading = deltaPos.getAngle();
      }

    public void updateAlliance() {
        Optional<Alliance> ally = DriverStation.getAlliance();
            if (ally.isPresent()) {
                if (ally.get() == Alliance.Red) {
                    onRedAlliance = true;
                }
            if (ally.get() == Alliance.Blue) {
                onRedAlliance = false;
        }
        }
    }
    
    public void setGoalHeadingToGoal(){
        if(onRedAlliance){
            aimAtPoint(Constants.goalPositions.redGoalPos);
        }else{
            aimAtPoint(Constants.goalPositions.blueGoalPos);
     }
      }

      public void driveClosedLoopHeading(Translation2d translation) {

        double rot = rotPidController.calculate(gyro.getRotation2d().getRadians(),
            goalHeading.getRadians());
    
        drive(
            translation,
            rot,
            true,
            false);
      }

      public void aimAtGoal(){
        setGoalHeadingToGoal();
        driveClosedLoopHeading(new Translation2d());
      }

    @Override
    public void periodic(){
        swerveOdometry.update(getGyroYaw(), getModulePositions());

        //  if (LimelightHelpers.getTV("limelight") == true) {
        //     m_poseEstimator.addVisionMeasurement(eyes.getRobotPose(), Timer.getFPGATimestamp() - (LimelightHelpers.getLatency_Pipeline("limelight")/1000.0) - (LimelightHelpers.getLatency_Capture("limelight")/1000.0));

        //  }
        for(SwerveModule mod : mSwerveMods){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " CANcoder", mod.getCANcoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Angle", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);    
            }
        
    }
}