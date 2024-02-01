package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.RainbowAnimation;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedHandler extends SubsystemBase
{
    private final CANdle candle = new CANdle(5);
    private final RainbowAnimation rainbow;
    public LedHandler() {
        super();
        
        candle.configFactoryDefault();

        candle.configStatusLedState(false);
        // configuration
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = true;
        configAll.v5Enabled = false;
        configAll.disableWhenLOS = true;
        configAll.stripType = LEDStripType.RGB;
        configAll.brightnessScalar = 1;
        // configAll.vBatOutputMode = VBatOutputMode.Modulated;
        candle.configAllSettings(configAll, 100);
      
        rainbow = new RainbowAnimation(1, 0.5, 1);
        
    }

    // changing colors
    public void setColor(int r, int g, int b)
    {
        candle.setLEDs(r, g, b);
    }

    public void setBlue()
    {
        setColor(0, 0, 255);
    }
    
    public void setGreen()
    {
        setColor(0, 255, 0);
    }

     public void setRainbow(){
         candle.animate(rainbow);
     }

    
}