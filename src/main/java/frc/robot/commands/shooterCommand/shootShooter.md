package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TestVortex;


public class shootShooter extends Command{
    private final Shooter shoot;
    private final double powerTop;
    public shootShooter(Shooter shoot, double powerTop){
        this.shoot = shoot;
        this.powerTop = powerTop;
      
        addRequirements(shoot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        shoot.setVelocity(powerTop);
    }
    
    @Override
    public void end(boolean interrupted){
        shoot.setVelocity(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

