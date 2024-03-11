package frc.robot.subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import edu.wpi.first.math.geometry.Pose2d;


public class Eyes extends SubsystemBase {

    public LimelightHelpers limelight;
    public double tx;
    public double ty;
    public double ta;
    public double tID;
  
    public Eyes() {}


    public void updateData() {
        //TODO: This must be tuned to specific robot
        tx = LimelightHelpers.getTX("limelight");
        ty = LimelightHelpers.getTY("limelight");
        ta = LimelightHelpers.getTA("limelight");
        tID = LimelightHelpers.getFiducialID("limelight");

        SmartDashboard.putNumber("AprilTagX", tx);
        SmartDashboard.putNumber("AprilTagY", ty);
        SmartDashboard.putNumber("AprilTagA", ta);
        SmartDashboard.putNumber("AprilTagID", tID);

    }

    public double[] getDataPackage() {

        double[] data = {
            tx,
            ty,
            ta,
            tID
        };

        return data;
    }

    public static Pose2d getRobotPose() {

        Pose2d pose;
        //TODO: This must be tuned to specific robot
        pose = LimelightHelpers.getBotPose2d_wpiBlue("limelight");
        return pose;

    }

}
