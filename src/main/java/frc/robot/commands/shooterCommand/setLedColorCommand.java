package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;

public class setLedColorCommand extends Command {

    private final LedHandler led;
    private final int rVal;
    private final int gVal;
    private final int bVal;

    public setLedColorCommand(LedHandler led, int red, int blue, int green){
        this.led = led;
        this.rVal = red;
        this.gVal = green;
        this.bVal = blue;
    }

    @Override
    public void execute(){
        led.setColor(rVal, gVal, bVal);
    }

    
}
