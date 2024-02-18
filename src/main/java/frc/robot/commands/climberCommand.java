package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climbers;


public class climberCommand extends Command{
    private final Climbers climbers;
    private final double percent;
    public climberCommand(Climbers climb, double percent){
        this.climbers = climb;
        this.percent = percent;
  
      
        addRequirements(climbers);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        climbers.setPower(percent);
    }
    
    @Override
    public void end(boolean interrupted){
        climbers.setPower(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

