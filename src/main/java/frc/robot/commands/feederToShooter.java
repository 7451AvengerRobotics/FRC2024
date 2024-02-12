package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.IndexTransporter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Shooter;


public class feederToShooter extends Command{
    private final Feeder feeder;
    private final Shooter shooter;
    private final LedHandler led;
    private final double feederPercent;
    private final double velocity;
    public feederToShooter(Feeder feeder, Shooter shooter, LedHandler led, double feederPercent, double velocity){
        this.feeder = feeder;
        this.shooter = shooter;
        this.led = led;
        this.feederPercent = feederPercent;
        this.velocity = velocity;
  
      
        addRequirements(feeder);
        addRequirements(shooter);
        addRequirements(led);

    }

    @Override  
    public void initialize(){

    }
    
    @Override 
    public void execute(){
        new feedCommand(feeder, feederPercent).until(feeder::detected)
        .andThen(new setLedColorCommand(led, 0, 255, 0));
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

