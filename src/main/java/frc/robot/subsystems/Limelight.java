package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
    double goalHeightInches = 58.33;

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
        double targetOffsetAngle_Vertical = ty.getDouble(0.0);
    
        // how many degrees back is your limelight rotated from perfectly vertical?
    
        // distance from the center of the Limelight lens to the floor
    
        // distance from the target to the floor
    
        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
    
        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
        return distanceFromLimelightToGoalInches;
      }

    public double getRotationAdjust() {
        double tx = table.getEntry("tx").getDouble(0);
        double heading_error = -tx;
        if (tx > 1.0) {
          steering_adjust = Kp * heading_error - min_command;
        } else if (tx < 1.0) {
          steering_adjust = Kp * heading_error + min_command;
        }
        return steering_adjust;
      }


     

    @Override
    public void periodic(){
        SmartDashboard.putNumber("XPos", getXPos());
        SmartDashboard.putNumber("YPos", getYPos());
        SmartDashboard.putNumber("Area", getArea());
        SmartDashboard.putNumber("April Tag", getID());
        SmartDashboard.putNumber("Distance", getDistance());

    }

    
 
}
