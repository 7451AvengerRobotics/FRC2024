package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climbers;    


public class setClimberPos extends Command{
    private final Climbers climber;
    private final double climber1Pos;
    private final double climber2Pos;
    public setClimberPos(Climbers climber, double climber1Pos, double climber2Pos){
        this.climber = climber;
        this.climber1Pos = climber1Pos;
        this.climber2Pos = climber2Pos;
  
      
        addRequirements(climber);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        climber.setClimberPosition(climber1Pos, climber2Pos);

    }
    
    @Override
    public void end(boolean interrupted){
     
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

