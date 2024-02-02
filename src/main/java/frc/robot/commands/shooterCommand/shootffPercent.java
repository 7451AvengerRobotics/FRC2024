package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TestVortex;


public class shootffPercent extends Command{
    private final Shooter shoot;
    private final double powerTop;
    private final double rpmFF;
    public shootffPercent(Shooter shoot, double powerTop, double rpmFF){
        this.shoot = shoot;
        this.powerTop = powerTop;
        this.rpmFF = rpmFF;
        addRequirements(shoot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        shoot.setBottom(powerTop);
        shoot.setFF(rpmFF);
        
    }
    
    @Override
    public void end(boolean interrupted){
        shoot.setBottom(0);
        shoot.setFF(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

