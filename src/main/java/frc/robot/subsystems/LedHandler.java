package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.StrobeAnimation;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedHandler extends SubsystemBase
{
    private final CANdle candle = new CANdle(5);
    // private final RainbowAnimation rainbow;
    //private final FireAnimation fire;
    private final StrobeAnimation indexstrobe;
    private final StrobeAnimation feederstrobe;
    public LedHandler() {
        super();
        
        candle.configFactoryDefault();

        candle.configStatusLedState(false);
        // configuration
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = false;
        configAll.v5Enabled = false;
        configAll.disableWhenLOS = false;
        configAll.stripType = LEDStripType.RGB;
        configAll.brightnessScalar = 0.2;
        indexstrobe = new StrobeAnimation(0, 255, 0, 0, 0.5, 200, 10);
        feederstrobe = new StrobeAnimation(255, 0, 255, 0, 0.5, 200, 10);
        // configAll.vBatOutputMode = VBatOutputMode.Modulated;
      
        candle.configAllSettings(configAll, 100);
        //fire = new FireAnimation(1, 1, 0, 1, 0.3);
       // candle.animate(fire);
        candle.setLEDs(255, 0, 255);
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
    public void setIndexStrobe(){
        candle.animate(indexstrobe);
    }
    public void setFeederStrobe(){
        candle.animate(feederstrobe);
    }
    public void clearAnimation(){
        candle.clearAnimation(0);
    }
    //  public void setRainbow(){
    //      candle.animate(rainbow);
    //  }

    //  public void setFire(){
    //     candle.animate(fire);
    //  }
    
}