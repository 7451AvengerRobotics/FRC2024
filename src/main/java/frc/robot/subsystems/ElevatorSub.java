//Somebody check these imports
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSub extends SubsystemBase 
{

    //Initializes Variables
    private final CANSparkMax ele1;
    private final RelativeEncoder encoderEle1;
    private final SparkPIDController controller1;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;


    public ElevatorSub()
    {
        super();
        ele1 = new CANSparkMax(Constants.mElevator1, MotorType.kBrushless);

        encoderEle1 = ele1.getEncoder();
        controller1 = ele1.getPIDController();
    

        encoderEle1.setPosition(0);
        ele1.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, -1);
        ele1.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -135);


        ele1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        ele1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);


        ele1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        ele1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
 
        kP = 1; 
        kI = 0;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;


        controller1.setP(kP);
        controller1.setI(kI);
        controller1.setD(kD);
        controller1.setIZone(kIz);
        controller1.setFF(kFF);
        controller1.setOutputRange(kMinOutput, kMaxOutput);





    }

 

    //Sets power of the Elevator 
    public void setPower(double power)
    {
        ele1.set(-power);
    }

    public void setElevatorOne(double power){
        ele1.set(-power);

    }
    public double getEncoderEle1Position(){
       return encoderEle1.getPosition();
    }


   





    public void setElevatorPosition(double ele1Pos){
        controller1.setReference(ele1Pos, CANSparkMax.ControlType.kPosition);
    }

     public Command resetElevatorPosition(){
        return runOnce(
            () -> {
                setElevatorPosition(0);
            });
    }

    public Command setElePosCMD(double position){
        return runOnce(
            () -> setElevatorPosition(position)
        );
    }

 
}