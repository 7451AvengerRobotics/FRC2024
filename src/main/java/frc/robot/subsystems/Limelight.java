package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.RobotContainer;
import frc.util.InterpolatingTreeMap;

public class Limelight extends SubsystemBase {
    NetworkTable table;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry tv;
    NetworkTableEntry tid;
    double aprilTag;
    double Kp = 0.02; // Proportional control constant
    double min_command = 0.15; // Minimum amount to slightly move
    double steering_adjust;
    double limelightMountAngleDegrees = 24.0; 
    double limelightLensHeightInches = 15.0 + (3/16); 
    double heightOfGoal = 58.33;
    double limelight_kP = 0.015;
 private InterpolatingTreeMap<Double, Double> shooterAngleMap;
    public Limelight(){
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        tid = table.getEntry("tid");
        table.getEntry("pipeline").setNumber(0);
        
    shooterAngleMap = new InterpolatingTreeMap<>() {{
     put(44.55, 0.0);
     put(45.5, 0.0);
     put(58.54, 3.0);
     put(68.79, 4.6);
     put(69.72, 5.2);
     put(79.27, 6.6);
     put(83.5, 7.2);
     put(94.83, 8.4);
     put(105.41, 9.46);
     put(108.6, 9.6);
     put(119.86, 9.8);
     put(128.1, 10.8);
     put(138.36, 11.15);
     put(149.82, 11.4);
     put(158.2, 11.555);
    }};


    }



    public double getXPos(){
        return tx.getDouble(0.0);
    }

public double limelight_aim_proportional()
  {    
    // kP (constant of proportionality)
    // this is a hand-tuned number that determines the aggressiveness of our proportional control loop
    // if it is too high, the robot will oscillate.
    // if it is too low, the robot will never reach its target
    // if the robot never turns in the correct direction, kP should be inverted.
    double kP = .01;

    // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of 
    // your limelight 3 feed, tx should return roughly 31 degrees.
    double targetingAngularVelocity = LimelightHelpers.getTX("limelight") * kP;

    // convert to radians per second for our drive method
    targetingAngularVelocity *= RobotContainer.getMaxAngularRate();

    //invert since tx is positive when the target is to the right of the crosshair
    targetingAngularVelocity *= -1.0;

    return targetingAngularVelocity;
  }

    public double getTV(){
        return tv.getDouble(0.0);
    }

    public double getYPos(){
        return ty.getDouble(0.0);
    }

    public double getArea(){
       return ta.getDouble(0.0);
    }

    public double getID(){
        return tid.getDouble(0);
    }

  
    public double getDistance(){
        
        double distance = 0.0;

        double targetOffsetAngle_Vertical = Rotation2d.fromDegrees(limelightMountAngleDegrees + ty.getDouble(0.0)).getRadians();

        distance = (heightOfGoal-limelightLensHeightInches) / Math.tan(targetOffsetAngle_Vertical);
  
        return distance;
      }
    
      public double aimToTag(){
  
          double heading_error = -getXPos();
          double steering_adjust = heading_error * limelight_kP;

          return steering_adjust; 
      }

      public boolean isAimedAtSpeaker(){
        double m_tx = Math.abs(getXPos());
        return m_tx > 0.0 && m_tx < 0.1;
      }

      public double getShooterMapAngle(){
        return shooterAngleMap.get(getDistance());
      }
     

    @Override
    public void periodic(){
        SmartDashboard.putNumber("XPos", getXPos());
        SmartDashboard.putNumber("YPos", getYPos());
        SmartDashboard.putNumber("Area", getArea());
        SmartDashboard.putNumber("April Tag", getID());
        SmartDashboard.putNumber("Distance", getDistance());
        SmartDashboard.putBoolean("is Aligned", isAimedAtSpeaker());
        SmartDashboard.putNumber("ShooterMap", getShooterMapAngle());
    }

    
 
}
