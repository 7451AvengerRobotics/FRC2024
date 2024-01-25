package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexTransporter extends SubsystemBase {
    // Motor controller to control the rollers in the indexer
    private final CANSparkMax transportMotor;
    private static boolean isOn;
    public IndexTransporter()
    {
        super();
        // Initializing the Motor
        transportMotor = new CANSparkMax(Constants.transportMotor, MotorType.kBrushed);
    }

    //Spins motor
    public void spinTransportMotor(double power){
        transportMotor.set(power);
        isOn=true;
    }
    //Stops Motor
    public void stopTransportMotor(){
        transportMotor.set(0.0);
        isOn=false;
    }

    public void toggleTransportMotor(double power){
        if(isOn){
            this.spinTransportMotor(power);
        }else{
            this.stopTransportMotor();
        }
    }

    
    
}
