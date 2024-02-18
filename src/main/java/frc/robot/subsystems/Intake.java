package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final CANSparkMax intake;

    public Intake(){
        super();
        intake = new CANSparkMax(Constants.intake, MotorType.kBrushless);

    }

    public void intake(double power){
        intake.set(power);
    }
    
    
}
