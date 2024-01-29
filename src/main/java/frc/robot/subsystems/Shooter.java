package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {


    //private final CANSparkMax m_index;
    private final CANSparkFlex shooterTop;
    private final CANSparkFlex shooterBottom;
    SparkPIDController shooterTopController; 
    SparkPIDController shooterBottomController; 

    private double kP;
    private double kI;
    private double kD;
    private double kFF;
    private double kIZone, kMaxOutput, kMinOutput, maxRpm, maxAccel;


    
    public Shooter(){
        super();
        
        shooterTop = new CANSparkFlex(Constants.shooterTop, MotorType.kBrushless);
        shooterBottom = new CANSparkFlex(Constants.shooterBottom, MotorType.kBrushless);
        //m_index = new CANSparkMax(Constants.m_index, MotorType.kBrushless);
        shooterBottom.restoreFactoryDefaults();
        shooterBottom.setInverted(false);
        shooterTop.setInverted(true);
        shooterTopController = shooterTop.getPIDController();
        shooterBottomController = shooterBottom.getPIDController();
        // shooterTop.setOpenLoopRampRate(1);
        // shooterBottom.setOpenLoopRampRate(1);
        kP = 0.00005;
        kI = 0;
        kD = 0;
        kFF = 0.00015;
        kIZone = 0;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRpm = 6000;

        shooterBottomController.setP(kP);
        shooterTopController.setP(kP);
        shooterBottomController.setD(kD);
        shooterTopController.setD(kD);
        shooterBottomController.setI(kI);
        shooterTopController.setI(kI);
        shooterBottomController.setFF(kFF);
        shooterTopController.setFF(kFF);
        shooterBottomController.setIZone(kP);
        shooterTopController.setIZone(kP);

        shooterBottomController.setOutputRange(kMinOutput, kMaxOutput);
        shooterTopController.setOutputRange(kMinOutput, kMaxOutput);

        shooterBottomController.setSmartMotionMaxAccel(1000, 0);
        shooterTopController.setSmartMotionMaxAccel(1000, 0);
        

        shooterBottomController.setSmartMotionMinOutputVelocity(500, 0);
        shooterTopController.setSmartMotionMinOutputVelocity(500, 0);
    



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

    public void setVelocity(double velocity) {
        shooterTopController.setReference(velocity, CANSparkFlex.ControlType.kSmartVelocity);
        shooterBottomController.setReference(velocity, CANSparkFlex.ControlType.kSmartVelocity);
    }




}
