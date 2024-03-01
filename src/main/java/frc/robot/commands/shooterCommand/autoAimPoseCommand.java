package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;

public class autoAimPoseCommand extends Command{
    private final Swerve drive;
    public autoAimPoseCommand(Swerve drive){
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
