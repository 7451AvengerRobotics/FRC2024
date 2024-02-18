package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {

    private final CANSparkFlex pivot;
    private final RelativeEncoder encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    private SparkPIDController m_pidController;

    public Pivot() {
        
        pivot  = new CANSparkFlex(Constants.pivot, MotorType.kBrushless);

        encoder = pivot.getEncoder();

        m_pidController = pivot.getPIDController();

        // pivot.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0);
        // pivot.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 0);


        // pivot.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        // pivot.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

        kP = 0.00001; 
        kI = 0;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 0.5; 
        kMinOutput = -0.5;

        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);


    }

    public double getEncoderPosition(){
        return encoder.getPosition();
    }

    public void setPosition(double position){
        m_pidController.setReference(position, CANSparkMax.ControlType.kPosition);
    }


    public void setPower(double power){
        pivot.set(power);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("EncoderPosition", encoder.getPosition());
    }
    
}
