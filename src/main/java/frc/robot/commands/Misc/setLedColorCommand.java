package frc.robot.commands.Misc;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;

public class setLedColorCommand extends Command {

    private final LedHandler led;
    private final int rVal;
    private final int gVal;
    private final int bVal;

    public setLedColorCommand(LedHandler led, int red, int green, int blue){
        this.led = led;
        this.rVal = red;
        this.gVal = green;
        this.bVal = blue;
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        led.setColor(rVal, gVal, bVal);
    }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    
}
