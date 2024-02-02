package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TestVortex;


public class shooterIndex extends Command{
    private final Shooter shoot;
    private final double percent;
    public shooterIndex(Shooter shoot, double percent){
        this.shoot = shoot;
        this.percent = percent;
  
      
        addRequirements(shoot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        shoot.feed(percent);
        

    }
    
    @Override
    public void end(boolean interrupted){
        shoot.feed(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

