package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.Swerve;
import frc.robot.commands.setClimberPos;
import frc.robot.commands.ClimberCommand.climberCommand;
import frc.robot.commands.ClimberCommand.climberManualCommand;
import frc.robot.commands.ClimberCommand.climberOneCommand;
import frc.robot.commands.ElevatorCommand.elevatorPositionCommand;
import frc.robot.commands.Misc.setLedColorCommand;
import frc.robot.commands.PivotCommands.pivotWithLimelight;
import frc.robot.commands.PivotCommands.setPivotPosition;
import frc.robot.commands.RetrieveDisc.allFeed;
import frc.robot.commands.RetrieveDisc.indexCommand;
import frc.robot.commands.shooterCommand.feedCommand;
import frc.robot.commands.shooterCommand.shootFF;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;
import frc.robot.subsystems.Swerve.Telemetry;
import frc.robot.subsystems.Swerve.generated.TunerConstants;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    
    /* Controllers */
    private final Joystick buttonPanel = new Joystick(0);

    private final CommandPS4Controller joystick = new CommandPS4Controller(1);
    /* Drive Controls */

    private final Pose2d targetPose = new Pose2d(10, 5, Rotation2d.fromDegrees(180));



   private final SendableChooser<Command> autoChooser;


    /* Subsystems */    
    private final Shooter shooter = new Shooter();
    private final IndexTransporter index = new IndexTransporter();
    private final Intake intake = new Intake();
    private final Feeder feed = new Feeder();
    private final ElevatorSub elevator = new ElevatorSub();
    private final Climbers climbers = new Climbers();
    private final Pivot pivot = new Pivot();
    public final Swerve swerve = new Swerve();
    private final Limelight limelight = new Limelight();
    private final LedHandler led = new LedHandler();


  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  // My joystick
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);



    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        NamedCommands.registerCommand("shootFirst", new WaitCommand(1.2).andThen(
            new feedCommand(feed, -0.7)).andThen(
                new setLedColorCommand(led, 0, 0, 255)).withTimeout(2));
        NamedCommands.registerCommand("shoot",
            new feedCommand(feed, -0.75).andThen(
                new setLedColorCommand(led, 0, 0, 255)).withTimeout(0.1));
        NamedCommands.registerCommand("fullIntake", new allFeed(feed, intake, index, -0.5, -0.5, -0.2).until(feed::detected));
        NamedCommands.registerCommand("pivot", new setPivotPosition(pivot, 9.0).withTimeout(0.5));

        PathPlannerPath ampFromMid = PathPlannerPath.fromPathFile("ampFromMid");
        PathPlannerPath speakerFromMid = PathPlannerPath.fromPathFile("sourceFromMid");

        PathConstraints constraints = new PathConstraints(
        3.0, 4.0,
            Units.degreesToRadians(540), Units.degreesToRadians(720));

        Command ampMid = AutoBuilder.pathfindThenFollowPath(
            ampFromMid,
            constraints,
            3.0 // Rotation delay distance in meters. This is how far the robot should travel before attempting to rotate.
        );

        Command speakerMid = AutoBuilder.pathfindThenFollowPath(
            speakerFromMid,
            constraints,
            3.0 // Rotation delay distance in meters. This is how far the robot should travel before attempting to rotate.
        );

    joystick.square().onTrue(ampMid);
    joystick.cross().onTrue(speakerMid);

    configureButtonBindings();

    led.setDefaultCommand(new setLedColorCommand(led, 0, 0, 255));

    shooter.setDefaultCommand(new shootFF(shooter, 5000, feed));

    autoChooser = AutoBuilder.buildAutoChooser();

    SmartDashboard.putData("Auto Chooser", autoChooser);

    }

    

    


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
    drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    joystick.R1().whileTrue( drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed*0.3) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed*0.3) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate*0.3) // Drive counterclockwise with negative X (left)
        ));    

    joystick.triangle().whileTrue(drivetrain.applyRequest(() -> brake));
    joystick.circle().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));

    // reset the field-centric heading on left bumper press
    joystick.L1().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);


        JoystickButton w = new JoystickButton(buttonPanel, Constants.w);
        JoystickButton a = new JoystickButton(buttonPanel, Constants.a);
        JoystickButton s = new JoystickButton(buttonPanel, Constants.s);
        JoystickButton d = new JoystickButton(buttonPanel, Constants.d);
        JoystickButton intakeButton = new JoystickButton(buttonPanel, Constants.one); 
        JoystickButton outtakeButton = new JoystickButton(buttonPanel, Constants.two); 
        JoystickButton eject = new JoystickButton(buttonPanel, Constants.three);
        JoystickButton four = new JoystickButton(buttonPanel, Constants.four);
        JoystickButton five = new JoystickButton(buttonPanel, Constants.five);
        JoystickButton six = new JoystickButton(buttonPanel, Constants.six);
        JoystickButton seven = new JoystickButton(buttonPanel, Constants.seven);
        JoystickButton eight = new JoystickButton(buttonPanel, Constants.eight);

        


        


    //Amp 
      w.onTrue(new ParallelCommandGroup(new setPivotPosition(pivot, 38), 
                new elevatorPositionCommand(elevator, - 99.145751953125, 406393.53125)).raceWith(new WaitCommand(1)));


    //Shoot

        d.whileTrue(new shootFF(shooter, 6000, feed).raceWith(
            new WaitCommand(0.3)).andThen(
                new ParallelCommandGroup(
                        new feedCommand(feed, -1), 
                        new indexCommand(index, -0.5))).andThen(
                        new setLedColorCommand(led, 0, 0, 255)));

  //Reset
        s.onTrue(new ParallelCommandGroup(new setPivotPosition(pivot, 2), 
            new elevatorPositionCommand(elevator, 0, 0),
            new setClimberPos(climbers, 0, 0)));

    //Stage
        a.onTrue(( 
            new climberCommand(climbers, 1)
                .until(climbers::climbersDetected))
                    .andThen(new setPivotPosition(pivot, 10).raceWith(new WaitCommand(1))));
    
        intakeButton.onTrue(new ParallelCommandGroup(
            new setPivotPosition(pivot, 3), 
            new allFeed(feed, intake, index, -0.4, -0.4, -0.3)).until(feed::detected).andThen(
                new setLedColorCommand(led, 0, 255, 0)).andThen(
                    new feedCommand(feed, 0.1).raceWith(new WaitCommand(0.2))));

        
         outtakeButton.onTrue(new ParallelCommandGroup(
            new allFeed(feed, intake, index, 0.5, 0.5, 0.2).andThen(
                new setLedColorCommand(led, 0, 0, 255))));

        eject.whileTrue(new setLedColorCommand(led, 255, 0, 0).andThen(
            new ParallelCommandGroup(new setPivotPosition(pivot, 40), 
            new feedCommand(feed, 0.2)).andThen(
                new setLedColorCommand(led, 0, 0, 255))));

        four.whileTrue(new ParallelCommandGroup(
                new shootFF(shooter, 2000, feed), 
                new feedCommand(feed, -1)));
                
        five.whileTrue(new climberManualCommand(climbers, 0.8));
        six.whileTrue(new climberOneCommand(climbers, -0.1));

        seven.whileTrue(new ParallelCommandGroup( new shootFF(shooter, 6000, feed), 
        new setPivotPosition(pivot, 8.9)).raceWith(
            new WaitCommand(0.5)).andThen(
                new ParallelCommandGroup(
                        new feedCommand(feed, -1), 
                        new indexCommand(index, -0.5))).andThen(
                        new setLedColorCommand(led, 0, 0, 255)));

         eight.onTrue(new ParallelCommandGroup(new setPivotPosition(pivot, 47),
            new setClimberPos(climbers, 176.06430053710938, -173.01708984375),
            new elevatorPositionCommand(elevator, -135, 555390).raceWith(
                new WaitCommand(1))));
        
        
            
     }      



    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
       // An ExampleCommand will run in autonomous
        // return new exampleAuto(s_Swerve);
         return autoChooser.getSelected();
    }
}