package frc.robot.commands.RetrieveDisc  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;


public class intakeCommand extends Command{
    private final Intake intake;
    private final double percent;
    public intakeCommand(Intake intake, double percent){
        this.intake = intake;
        this.percent = percent;
  
      
        addRequirements(intake);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        intake.intake(percent);
    }
    
    @Override
    public void end(boolean interrupted){
        intake.intake(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

