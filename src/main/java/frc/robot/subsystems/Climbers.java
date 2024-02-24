//Somebody check these imports
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

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

    public Climbers()
    {
        super();
        climberOne = new CANSparkMax(Constants.climber1, MotorType.kBrushless);
        climberTwo = new CANSparkMax(Constants.climber2, MotorType.kBrushless);
        climberOneEncoder = climberOne.getEncoder();
        climberTwoEncoder = climberTwo.getEncoder();

        climberOneEncoder.setPosition(0);
        climberTwoEncoder.setPosition(0);

        
        climberOne.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0);
        climberOne.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -315);


        climberOne.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        climberOne.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

        climberTwo.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 290);
        climberTwo.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 0);


        climberTwo.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
        climberTwo.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    }

    //Sets power of the Elevator 
    public void setPower(double power)
    {
        climberOne.set(-power);
        climberTwo.set(power);
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
    }


}