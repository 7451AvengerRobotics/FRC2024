package frc.robot.commands.LedCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Limelight;

public class limelightAlignedLEDCommand extends Command{
   private LedHandler led;
   private Limelight limelight;
    public limelightAlignedLEDCommand(LedHandler led, Limelight limelight){
        this.led = led;
        this.limelight = limelight;

        addRequirements(led, limelight);
    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        if (limelight.isAimedAtSpeaker()){
            led.setAlignedStrobe();
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
