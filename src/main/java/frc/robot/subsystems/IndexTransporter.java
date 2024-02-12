package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexTransporter extends SubsystemBase {
    // Motor controller to control the rollers in the indexer
    private final CANSparkMax transportMotor;
    private final DigitalInput beamBreak;
    public boolean inIndex;
    
    public IndexTransporter()
    {
        super();
        // Initializing the Motor
        transportMotor = new CANSparkMax(Constants.transportMotor, MotorType.kBrushless);
        beamBreak = new DigitalInput(1);
        
    }

    //Spins motor
    public void spinMotor(double power){
        transportMotor.set(power);
    }

    public boolean objInIndex(){
        return beamBreak.get();
    }
    
}
