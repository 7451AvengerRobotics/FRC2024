//Somebody check these imports
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSub extends SubsystemBase 
{

    //Initializes Variables
    private final CANSparkMax ele1;
    private final CANSparkMax ele2;
    private final RelativeEncoder encoderEle1;
    private final RelativeEncoder encoderEle2;
    private final SparkPIDController controller1;
    private final SparkPIDController controller2;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public double kP2, kI2, kD2, kIz2, kFF2, kMaxOutput2, kMinOutput2;


    public ElevatorSub()
    {
        super();
        ele1 = new CANSparkMax(Constants.mElevator1, MotorType.kBrushless);
        ele2 = new CANSparkMax(Constants.mElevator2, MotorType.kBrushless);

        encoderEle1 = ele1.getEncoder();
        encoderEle2 = ele2.getEncoder();
        controller2 = ele2.getPIDController();
        controller1 = ele1.getPIDController();
    

        encoderEle1.setPosition(0);
        encoderEle2.setPosition(0);
        ele1.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, -1);
        ele1.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -135);


        ele1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        ele1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);



        ele2.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 555390);
        ele2.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 0);


        ele1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        ele1.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
 
        kP = 1; 
        kI = 0;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;


        kP2 = 1; 
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
        ele1.set(-power);
        ele2.set(power);
    }

    public void setElevatorOne(double power){
        ele1.set(-power);
    }

    public void setElevatorTwo(double power){
        ele2.set(power);
    }

    public double getEncoderEle1Position(){
       return encoderEle1.getPosition();
    }

    public double getEncoderEle2Position(){
        return encoderEle2.getPosition();
    }

    public void setElevatorPosition(double ele1Pos, double ele2Pos){
        controller1.setReference(ele1Pos, CANSparkMax.ControlType.kPosition);
        controller2.setReference(ele2Pos, CANSparkMax.ControlType.kPosition);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("ElevatorOnePosition", this.getEncoderEle1Position());
        SmartDashboard.putNumber("EkevatorTwoPosition", this.getEncoderEle2Position());
    }
}