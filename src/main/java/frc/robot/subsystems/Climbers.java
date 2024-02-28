//Somebody check these imports
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
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
    private final DigitalInput climber1LimitSwitch;
    private final DigitalInput climber2LimitSwitch;


    public Climbers()
    {
        super();
        climberOne = new CANSparkMax(Constants.climber1, MotorType.kBrushless);
        climberTwo = new CANSparkMax(Constants.climber2, MotorType.kBrushless);
        climber1LimitSwitch = new DigitalInput(1);
        climber2LimitSwitch = new DigitalInput(2);

        climberOneEncoder = climberOne.getEncoder();
        climberTwoEncoder = climberTwo.getEncoder();

        climberOneEncoder.setPosition(0);
        climberTwoEncoder.setPosition(0);

        
        climberOne.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 283);
        climberOne.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 0);


        climberOne.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
        climberOne.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);

        climberTwo.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0);
        climberTwo.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -290);


        climberTwo.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
        climberTwo.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
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


   @Override
    public void periodic(){
        SmartDashboard.putNumber("ClimberOnePosition", this.climberOneGetEncoderPosition());
        SmartDashboard.putNumber("ClimberTwoPosition", this.climberTwoGetEncoderPosition());
        SmartDashboard.putBoolean("ClimberOneDetected", this.climber1Detected());
        SmartDashboard.putBoolean("ClimberTwoDetected", this.climber2Detected());
    }


}