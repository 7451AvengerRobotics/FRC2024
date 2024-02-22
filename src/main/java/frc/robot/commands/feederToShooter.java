package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Shooter;


public class feederToShooter extends Command{
    private final Feeder feeder;
    private final LedHandler led;
    private final double feederPercent;
    public feederToShooter(Feeder feeder, Shooter shooter, LedHandler led, double feederPercent, double velocity){
        this.feeder = feeder;
        this.led = led;
        this.feederPercent = feederPercent;
  
      
        addRequirements(feeder);
        addRequirements(shooter);
        addRequirements(led);

    }

    @Override  
    public void initialize(){

    }
    
    @Override 
    public void execute(){
        Commands.sequence(new feedCommand(feeder, feederPercent).until(feeder::detected)
        .andThen(new setLedColorCommand(led, 0, 255, 0)));
    }
    
    @Override
    public void end(boolean interrupted){
        feeder.feed(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

