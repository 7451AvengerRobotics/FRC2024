package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType ;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;



public class TestVortex extends SubsystemBase{
   private final CANSparkFlex testVortex1;
   //private final CANSparkFlex testVortex2;
   // private final DigitalInput limitSwitch;
   public final SparkPIDController m_pidController;
   private boolean active;
   public final RelativeEncoder m_encoder;


   public TestVortex(){
    super();
        testVortex1 = new CANSparkFlex(9, MotorType.kBrushless);
       // testVortex2 = new CANSparkFlex(19, MotorType.kBrushless);
      //   limitSwitch = new DigitalInput(Constants.limitSwitchPort);
        m_pidController = testVortex1.getPIDController();
        m_encoder = testVortex1.getEncoder();
        m_pidController.setP(0.0005);


   }

   public void setPower(double power){
    testVortex1.set(-power);
   // testVortex2.set(power);
   }

   public boolean detected(){
      return true;
   //  return limitSwitch.get();
   }

   public void setCount(double counts) {
       if (counts == 0) {
         m_pidController.setReference(0, CANSparkFlex.ControlType.kPosition);
      } else {
      m_pidController.setReference(counts , CANSparkFlex.ControlType.kPosition);
    }
   }

  @Override
   public void periodic(){
    SmartDashboard.putBoolean("Limit Switch State", this.detected());
   }
   


    
}
