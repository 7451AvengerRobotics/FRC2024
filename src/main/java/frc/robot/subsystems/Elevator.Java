package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class Elevator extends SubsystemBase 
{
    //Initializes Variables
    private final CANSparkMax mElevator1;
    private final CANSparkMax mElevator2;
    public Elevator()
    {
        super();
        mElevator1 = new CANSparkMax(Constants.mElevator1, MotorType.kBrushless);
        mElevator2 = new CANSparkMax(Constants.mElevator2, MotorType.kBrushless);
        mElevator2.follow(mElevator2);
    }

    //Sets power of the Elevator 
    public void setPower(double power)
    {
        mElevator1.set(-power);
        mElevator2.set(power);
    }

    public void setElevatorOne(double power){
        mElevator1.set(power);
    }

    public void setElevatorTwo(double power){
        mElevator2.set(power);
    }





}

