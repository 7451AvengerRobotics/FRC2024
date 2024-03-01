//Somebody check these imports
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climbers extends SubsystemBase 
{
    //Initializes Variables
    private final CANSparkMax climberOne;
    private final CANSparkMax climberTwo;
    private final RelativeEncoder climberOneEncoder;
    private final RelativeEncoder climberTwoEncoder;
    private final SparkPIDController controller1;
    private final SparkPIDController controller2;
    private final DigitalInput climber1LimitSwitch;
    private final DigitalInput climber2LimitSwitch;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public double kP2, kI2, kD2, kIz2, kFF2, kMaxOutput2, kMinOutput2;

    public Climbers()
    {
        super();
        climberOne = new CANSparkMax(Constants.climber1, MotorType.kBrushless);
        climberTwo = new CANSparkMax(Constants.climber2, MotorType.kBrushless);

        climberOne.setIdleMode(IdleMode.kBrake);
        climberTwo.setIdleMode(IdleMode.kBrake);

        climber1LimitSwitch = new DigitalInput(1);
        climber2LimitSwitch = new DigitalInput(2);

        controller1 = climberOne.getPIDController();
        controller2 = climberTwo.getPIDController();

        climberOneEncoder = climberOne.getEncoder();
        climberTwoEncoder = climberTwo.getEncoder();

        climberOneEncoder.setPosition(0);
        climberTwoEncoder.setPosition(0);

        
        climberOne.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 283);
        climberOne.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 0);


        climberOne.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        climberOne.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);

        climberTwo.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0);
        climberTwo.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -280);


        climberTwo.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
        climberTwo.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

        kP = .01; 
        kI = 0;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 0.5; 
        kMinOutput = -0.5;

        kP2 = .01; 
        kI2 = 0; 
        kD2 = 0; 
        kIz2 = 0; 
        kFF2 = 0; 
        kMaxOutput2 = 1; 
        kMinOutput2 = -1;

        controller1.setP(kP);
        controller1.setI(kI);
        controller1.setD(kD);
        controller1.setIZone(kIz);
        controller1.setFF(kFF);
        controller1.setOutputRange(kMinOutput, kMaxOutput);

        controller2.setP(kP2);
        controller2.setI(kI2);
        controller2.setD(kD2);
        controller2.setIZone(kIz2);
        controller2.setFF(kFF2);
        controller2.setOutputRange(kMinOutput2, kMaxOutput2);
    }

    //Sets power of the Elevator 
    public void setPower(double power)
    {
        climberOne.set(-power);
        climberTwo.set(power);
    }
    public boolean climber2Detected(){
        return !climber2LimitSwitch.get();
       }

    public boolean climber1Detected(){
        return !climber1LimitSwitch.get();
    }

    public boolean climbersDetected(){
        return (!climber1LimitSwitch.get() && !climber2LimitSwitch.get());
    }

    public void setClimberOne(double power){
        climberOne.set(power);
    }

    public void setClibmerTwo(double power){
        climberTwo.set(power);
    }
    public double climberTwoGetEncoderPosition(){
        return climberTwoEncoder.getPosition();
    }

      public double climberOneGetEncoderPosition(){
        return climberOneEncoder.getPosition();
    }

    public void setClimberPosition(double clim1, double climb2){
            controller1.setReference(clim1, CANSparkMax.ControlType.kPosition);
            controller2.setReference(climb2, CANSparkMax.ControlType.kPosition);
    }


   @Override
    public void periodic(){
        SmartDashboard.putNumber("ClimberOnePosition", this.climberOneGetEncoderPosition());
        SmartDashboard.putNumber("ClimberTwoPosition", this.climberTwoGetEncoderPosition());
        SmartDashboard.putBoolean("ClimberOneDetected", this.climber1Detected());
        SmartDashboard.putBoolean("ClimberTwoDetected", this.climber2Detected());
    }


}