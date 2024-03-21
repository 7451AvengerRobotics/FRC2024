package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexTransporter extends SubsystemBase {
    // Motor controller to control the rollers in the indexer
    private final CANSparkMax transportMotor;
    public boolean inIndex;
    private final DigitalInput indexBeamBreak;
    
    public IndexTransporter()
    {
        super();
        // Initializing the Motor
        transportMotor = new CANSparkMax(Constants.index, MotorType.kBrushless);
        indexBeamBreak = new DigitalInput(3);
        transportMotor.enableVoltageCompensation(12);

        
    }

    //Spins motor
    public void spinMotor(double power){
        transportMotor.set(power);
    }

    public boolean indexDetected(){
        return !indexBeamBreak.get();
       }

    public boolean indexNotDetected(){
        return indexBeamBreak.get();
       }
    


    public Command setIndexPower(double power){
        return runEnd(
            () -> {
                spinMotor(power);
            }, 
            () ->{
                spinMotor(0);
            });
    }

    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Feeder", this.indexDetected());
    }




}
