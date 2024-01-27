package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {


    private final CANSparkMax m_index;
    private final CANSparkFlex m_shooter1;
    private final CANSparkFlex m_shooter2;


    
    public Shooter(){
        super();
        
        m_shooter1 = new CANSparkFlex(Constants.m_shooter1, MotorType.kBrushed);
        m_shooter2 = new CANSparkFlex(Constants.m_shooter2, MotorType.kBrushed);
        m_index = new CANSparkMax(Constants.m_index, MotorType.kBrushed);


    }





}
