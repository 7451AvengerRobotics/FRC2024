//Somebody check these imports
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
    private boolean motorOn;
    public Elevator
    {
        //Allows Elevator to inherit properties of SubsystemBase (Vineeth, Check me on this idk if its explained right)
        super();
        mElevator1 = new CANSparkMax(Constants.mElevator1, MotorType.kBrushless);
        mElevator2 = new CANSparkMax(Constants.mElevator2, MotorType.kBrushless);
        mElevator2.follow(mElevator1);
        motorOn = false;
    }

    //Sets power of the Elevator 
    public void moveElevator(double power)
    {
        mElevator1.set(power);
        mElevator2.set(power);
    }
    // Sets power of Elevator to zero
    public void stopElevator()
    {
        mElevator1.set(0);
        mElevator2.set(0);
    }
    //Toggles the motor, if the function is called once, then it runs. If its called twice, it stops and so on. 
    public void toggleElevator(double power)
    {
        if (motorOn)
        {
            this.stopElevator;
        }
        else
        {
            this.moveElevator(power);
        }
    }



}