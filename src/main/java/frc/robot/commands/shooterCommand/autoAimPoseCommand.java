package frc.robot.commands.shooterCommand;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Limelight;

public class autoAimPoseCommand extends Command{
       private final Swerve drive;
    private final Limelight limelight;
    public autoAimPoseCommand(Swerve drive, Limelight limelight){
        this.limelight = limelight;
        this.drive = drive;


    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        drive.setGoalHeadingToGoal();
    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
    
}
