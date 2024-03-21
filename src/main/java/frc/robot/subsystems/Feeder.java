package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {

    private final CANSparkMax feeder;
    private final DigitalInput beamBreak;
    public boolean active;

    public Feeder(){
        super();
        feeder = new CANSparkMax(Constants.feeder, MotorType.kBrushless);
        beamBreak = new DigitalInput(0);
        feeder.enableVoltageCompensation(12);
        

    }
    
    public boolean detected(){
        return !beamBreak.get();
       }

    public boolean notDetected(){
        return beamBreak.get();
       }
    
    public void feed(double power){
        feeder.set(power);
    }

    public Command setFeederPower(double power){
        return runEnd(
            () -> {
                feed(power);
            },
            () -> {
                feed(0);
            }
            );
    }


    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Feeder", this.detected());
    }
    

}
