package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;


public class shootPercentage extends Command{
    private final Shooter shoot;
    private final double percent;
    public shootPercentage(Shooter shoot, double percent){
        this.shoot = shoot;
        this.percent = percent;
  
      
        addRequirements(shoot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        shoot.shoot(percent);

    }
    
    @Override
    public void end(boolean interrupted){
      shoot.shoot(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

