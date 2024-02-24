package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final CANSparkMax intake;
    private final RelativeEncoder encoder;

    public Intake(){
        super();
        intake = new CANSparkMax(Constants.intake, MotorType.kBrushless);
        encoder = intake.getEncoder();
    }

    public void intake(double power){
        intake.set(power);
    }
    
    public double getEncoderPosition(){
        return encoder.getPosition();
    }


}
