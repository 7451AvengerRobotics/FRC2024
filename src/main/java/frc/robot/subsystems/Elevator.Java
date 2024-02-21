//Somebody check these imports
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase 
{
    //Initializes Variables
    private final CANSparkMax ele1;
    private final CANSparkMax ele2;
    public Elevator()
    {
        super();
        ele1 = new CANSparkMax(Constants.mElevator1, MotorType.kBrushless);
        ele2 = new CANSparkMax(Constants.mElevator2, MotorType.kBrushless);
    }

    //Sets power of the Elevator 
    public void setPower(double power)
    {
        ele1.set(-power);
        ele2.set(power);
    }

    public void setElevatorOne(double power){
        ele1.set(-power);
    }

    public void setElevatorTwo(double power){
        ele2.set(power);
    }





}