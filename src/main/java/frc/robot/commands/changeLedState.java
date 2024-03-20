package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.IndexTransporter;
import frc.robot.subsystems.LedHandler;

public class changeLedState extends Command{
   private LedHandler led;
   private IndexTransporter index;
   private Feeder feed;
    public changeLedState(LedHandler led, IndexTransporter index, Feeder feed){
        this.led = led;
        this.index = index;
        this.feed = feed;

        addRequirements(led);
    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        if(index.indexDetected()){
            led.setIndexStrobe();
        } else if(feed.detected()){
          led.setIndexStrobe();
        } else{
            led.clearAnimation();
            led.setBlue();
        }
    }
    
    @Override
    public void end(boolean interrupted){
        led.clearAnimation();
        led.setBlue();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}



