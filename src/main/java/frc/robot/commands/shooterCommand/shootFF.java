package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TestVortex;


public class shootFF extends Command{
    private final Shooter shoot;
    private final double voltage;
    private final double acceleration;
    public shootFF(Shooter shoot, double voltage, double acceleration){
        this.shoot = shoot;
        this.voltage = voltage;
        this.acceleration = acceleration;
      
        addRequirements(shoot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        shoot.setFF(voltage, acceleration);
    }
    
    @Override
    public void end(boolean interrupted){
        shoot.setFF(voltage, acceleration);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

