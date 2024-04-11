package frc.robot.subsystems;


import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Pivot extends SubsystemBase {


   private final TalonFX pivot;
   private PositionDutyCycle positionRequest;
   private DutyCycleOut percentRequest;


   public Pivot() {
      
       pivot = new TalonFX(Constants.pivot);


       positionRequest = new PositionDutyCycle(0).withEnableFOC(false);
      
       percentRequest = new DutyCycleOut(0).withEnableFOC(false);
      


     
       TalonFXConfiguration pivotconfig = new TalonFXConfiguration();


       pivotconfig.CurrentLimits.StatorCurrentLimitEnable = true;
       pivotconfig.CurrentLimits.StatorCurrentLimit = 40; //amps
       pivotconfig.Slot0.kP = 0.45; // Need to Tune
       pivotconfig.Slot0.kI =0.01;

       pivotconfig.Slot0.kS = 0.005;
      pivotconfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
       pivotconfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -48; 
       pivotconfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
       pivotconfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0; 
       pivotconfig.MotorOutput.PeakForwardDutyCycle = 0.5;
       pivotconfig.MotorOutput.PeakReverseDutyCycle = -0.5;
       pivot.getConfigurator().apply(pivotconfig);
       pivot.getConfigurator().setPosition(0);




      




   }


   public double getEncoderPosition(){
       return pivot.getPosition().getValueAsDouble();
   }


   public void setPosition(double position){
       pivot.setControl(positionRequest.withPosition(position));
   }




   public void setPower(double power){
       pivot.setControl(percentRequest.withOutput(power));
   }






   @Override
   public void periodic(){
       SmartDashboard.putNumber("PivotEncoderPosition", this.getEncoderPosition());
   }
}



