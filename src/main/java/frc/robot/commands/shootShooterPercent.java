package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TestVortex;


public class shootShooterPercent extends Command{
    private final Shooter shoot;
    private final double powerTop;
    public shootShooterPercent(Shooter shoot, double powerTop){
        this.shoot = shoot;
        this.powerTop = powerTop;
      
        addRequirements(shoot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        shoot.shoot(powerTop);
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

