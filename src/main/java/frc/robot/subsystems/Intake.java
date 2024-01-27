package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final CANSparkMax intake;


    public Intake(){
        super();
        intake = new CANSparkMax(Constants.intakeMotorCAN, MotorType.kBrushless);

    }

    public void runMotor(double power){
        intake.set(power);
    }

    public void stopMotor(){
        intake.set(0);
    }

    
    
    
}
