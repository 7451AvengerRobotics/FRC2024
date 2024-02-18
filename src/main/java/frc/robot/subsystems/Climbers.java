//Somebody check these imports
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climbers extends SubsystemBase 
{
    //Initializes Variables
    private final CANSparkMax climberOne;
    private final CANSparkMax climberTwo;
    public Climbers()
    {
        super();
        climberOne = new CANSparkMax(Constants.climber1, MotorType.kBrushless);
        climberTwo = new CANSparkMax(Constants.climber2, MotorType.kBrushless);
    }

    //Sets power of the Elevator 
    public void setPower(double power)
    {
        climberOne.set(-power);
        climberTwo.set(power);
    }

    public void setClimberOne(double power){
        climberOne.set(-power);
    }

    public void setClibmerTwo(double power){
        climberTwo.set(power);
    }





}