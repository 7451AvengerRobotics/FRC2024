package frc.robot.commands.LedCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;

public class ledAnimationCommand extends Command {

    private final LedHandler led;
  

    public ledAnimationCommand(LedHandler led){
        this.led = led;
   

        addRequirements(led);
    }

    @Override
    public void initialize(){
        led.clearAnimation();
    }

    @Override
    public void execute(){
        led.setIndexStrobe();
    }

    @Override
    public void end(boolean interrupted){
        led.clearAnimation();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    
}
