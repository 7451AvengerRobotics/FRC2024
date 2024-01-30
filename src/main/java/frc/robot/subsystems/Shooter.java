package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {


    //private final CANSparkMax m_index;
    private final CANSparkFlex shooterTop;
    private final CANSparkFlex shooterBottom;
    private final SimpleMotorFeedforward control;


    private double kS = 0.0;
    private double kV = 0.0;
    private double kA = 0.0;


    
    public Shooter(){
        super();
        
        shooterTop = new CANSparkFlex(Constants.shooterTop, MotorType.kBrushless);
        shooterBottom = new CANSparkFlex(Constants.shooterBottom, MotorType.kBrushless);
        //m_index = new CANSparkMax(Constants.m_index, MotorType.kBrushless);
        shooterBottom.restoreFactoryDefaults();
        shooterBottom.setInverted(false);
        shooterTop.setInverted(true);
        control = new SimpleMotorFeedforward(kS, kV, kA);
        // shooterTop.setOpenLoopRampRate(1);
        // shooterBottom.setOpenLoopRampRate(1);
 
    }

    public void shoot(double power){
        shooterTop.set(power);
        shooterBottom.set(power);
       
    }



    public void setBottom(double power){
        shooterBottom.set(power);
    }

    public void setTop(double power){
        shooterTop.set(power);
    }

    public double RPSToMPS(double wheelRPS, double circumference){
        double wheelMPS = wheelRPS * circumference;
        return wheelMPS;
    }

    public void setFF(double velocity, double acceleration){
        shooterBottom.setVoltage(control.calculate(velocity, acceleration));
        shooterTop.setVoltage(control.calculate(velocity, acceleration));
    }




}
