package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexTransporter extends SubsystemBase {
    // Motor controller to control the rollers in the indexer
    private final CANSparkMax transportMotor;
    public boolean inIndex;
    
    public IndexTransporter()
    {
        super();
        // Initializing the Motor
        transportMotor = new CANSparkMax(Constants.index, MotorType.kBrushless);
        
    }

    //Spins motor
    public void spinMotor(double power){
        transportMotor.set(power);
    }
    
}
