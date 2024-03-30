package frc.robot.commands.ClimberCommand  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climbers;


public class climberAutoCommand extends Command{
    private final Climbers climbers;
    private final double percent;
    public climberAutoCommand(Climbers climb, double percent){
        this.climbers = climb;
        this.percent = percent;
  
      
        addRequirements(climbers);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
            climbers.setClibmerTwo(percent + 0.2);
            climbers.setClimberOne(-percent + 0.2);


        if (climbers.climber1Detected()){
            climbers.setClimberOne(0);
        }
        
        if (climbers.climber2Detected()){
            climbers.setClibmerTwo(0);
        }
    }
    
    @Override
    public void end(boolean interrupted){
        climbers.setPower(0, 0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}