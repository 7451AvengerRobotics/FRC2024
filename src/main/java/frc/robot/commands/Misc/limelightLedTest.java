package frc.robot.commands.Misc;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.Limelight; 



public class limelightLedTest extends Command{
    private final Limelight lime;
    private final LedHandler led;

    public limelightLedTest(Limelight lime,  LedHandler led){
        this.lime = lime;
        this.led = led;
        addRequirements(lime);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        if(lime.getID() == 1)
        {
            if(lime.getXPos()>2){
                led.setColor(255,0,0);
            } else{
                led.setBlue();
            }
            
        }
        else if(lime.getID() == 2)
        {
            led.setGreen();
        
        } 
    }
    
    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}