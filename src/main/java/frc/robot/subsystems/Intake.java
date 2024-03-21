package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final CANSparkMax intake;
    private final RelativeEncoder encoder;

    public Intake(){
        super();
        intake = new CANSparkMax(Constants.intake, MotorType.kBrushless);
        encoder = intake.getEncoder();
        intake.enableVoltageCompensation(12);
    }

    public void intake(double power){
        intake.set(power);
    }
    
    public double getEncoderPosition(){
        return encoder.getPosition();
    }

    public Command setintakePower(double power){
        return runEnd(
            () -> {
                intake(power);
            }, 
            () -> {
                intake(0);
            });
    }




}
