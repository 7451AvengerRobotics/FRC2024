package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;


public class shootFF extends Command{
    private final Shooter shoot;
    private final double rpm;

    public shootFF(Shooter shoot, double rpm, Feeder feed){
        this.shoot = shoot;
        this.rpm = rpm;
  
      
        

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        shoot.setFF(rpm);
    

    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}