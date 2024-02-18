package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climbers;


public class climberOneCommand extends Command{
    private final Climbers climbers;
    private final double percent;
    public climberOneCommand(Climbers climb, double percent){
        this.climbers = climb;
        this.percent = percent;
  
      
        addRequirements(climbers);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        climbers.setClimberOne(percent);
    }
    
    @Override
    public void end(boolean interrupted){
        climbers.setClimberOne(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

