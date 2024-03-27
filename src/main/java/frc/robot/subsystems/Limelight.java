package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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
    double heightOfGoal = 57.13;
    double limelight_kP = 0.015;

    public Limelight(){
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        tid = table.getEntry("tid");
        table.getEntry("pipeline").setNumber(0);


    }



    public double getXPos(){
        return tx.getDouble(0.0);
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


     

    @Override
    public void periodic(){
        SmartDashboard.putNumber("XPos", getXPos());
        SmartDashboard.putNumber("YPos", getYPos());
        SmartDashboard.putNumber("Area", getArea());
        SmartDashboard.putNumber("April Tag", getID());
        SmartDashboard.putNumber("Distance", getDistance());
        SmartDashboard.putBoolean("is Aligned", isAimedAtSpeaker());

    }

    
 
}
