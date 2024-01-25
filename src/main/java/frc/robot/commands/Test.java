package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedHandler;
import frc.robot.subsystems.TestVortex;


public class Test extends Command{
    private final TestVortex test;
    private final double power;
    private final LedHandler led;
    public Test(TestVortex test,  LedHandler led, double power){
        this.test = test;
        this.power = power;
        this.led = led;
        addRequirements(test);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        test.setPower(power);
        led.setGreen();
        if (!(test.detected())) {
            test.setPower(0); 
            led.setBlue();
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

