package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TestVortex;


public class shootShooter extends Command{
    private final Shooter shoot;
    private final double power;
    public shootShooter(Shooter shoot, double power){
        this.shoot = shoot;
        this.power = power;
      
        addRequirements(shoot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        shoot.shoot(power);
    }
    
    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

