package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class testSparkMax extends SubsystemBase {
        private final CANSparkMax testSparkMax;

    public testSparkMax(){
        testSparkMax = new CANSparkMax(33, MotorType.kBrushless);

    }


    public void spin (){
        testSparkMax.set(0.1);
    }
    
}
