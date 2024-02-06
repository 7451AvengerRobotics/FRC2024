package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Feeder extends SubsystemBase {

    private final CANSparkMax feeder;
    private final DigitalInput beamBreak;
    public boolean active;

    public Feeder(){
        super();
        feeder = new CANSparkMax(39, MotorType.kBrushless);
        beamBreak = new DigitalInput(0);
        

    }
    
    public boolean detected(){
        return !beamBreak.get();
       }
    
    public void feed(double power){
        feeder.set(power);
    }
    
    
}
