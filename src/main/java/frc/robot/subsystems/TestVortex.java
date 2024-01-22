package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType ;


public class TestVortex extends SubsystemBase{
   private final CANSparkFlex testVortex1;
   private final CANSparkFlex testVortex2;
   private final DigitalInput limitSwitch;
   private boolean active;


   public TestVortex(){
    super();
        testVortex1 = new CANSparkFlex(9, MotorType.kBrushless);
        testVortex2 = new CANSparkFlex(19, MotorType.kBrushless);
        limitSwitch = new DigitalInput(Constants.limitSwitchPort);
   }

   public void setPower(double power){
    testVortex1.set(-power);
    testVortex2.set(power);
   }

   public boolean detected(){
    return limitSwitch.get();
   }

  @Override
   public void periodic(){
    SmartDashboard.putBoolean("Limit Switch State", this.detected());
   }
   

   
   // public void setPowerToTestVortex2(double power){
   //  testVortex2.set(power);
   // }
    
}
