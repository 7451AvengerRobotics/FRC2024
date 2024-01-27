package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {


    private final CANSparkMax m_index;
    private final CANSparkFlex shooterTop;
    private final CANSparkFlex shooterBottom;


    
    public Shooter(){
        super();
        
        shooterTop = new CANSparkFlex(Constants.shooterTop, MotorType.kBrushless);
        shooterBottom = new CANSparkFlex(Constants.shooterBottom, MotorType.kBrushless);
        m_index = new CANSparkMax(Constants.m_index, MotorType.kBrushless);

        shooterBottom.setInverted(true);

    }

    public void shoot(double power){
        shooterTop.set(power);
        shooterBottom.set(power);
    }



}
