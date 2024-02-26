package frc.robot.commands.shooterCommand;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Limelight;

public class autoAimCommand extends Command{
       private final Swerve drive;
    private final Limelight limelight;
    public autoAimCommand(Swerve drive, Limelight limelight){
        this.limelight = limelight;
        this.drive = drive;


    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
 if (limelight.getTV() == 1.0) {
        // Get X and Y coordinates of the target
        double targetX = limelight.getXPos();
        double targetY = limelight.getYPos();

        // Calculate the robot's rotation angle to face the target
        double robotAngle = drive.getYawDegrees();
        double targetAngle = Math.atan2(targetY - Constants.Positions.speakerBlueY, targetX) - Math.PI / 2;
        double rotation = targetAngle - robotAngle;

        // Rotate the robot towards the target
        drive.drive(new Translation2d(0, 0), rotation, false, false);
    } else {
        // If no target is detected, stop the robot
        drive.drive(new Translation2d(0, 0), 0, false, false);
    }    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
    
}
