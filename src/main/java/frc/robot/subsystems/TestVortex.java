package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType ;


public class TestVortex extends SubsystemBase{
   private final CANSparkFlex testVortex1;
 //  private final CANSparkFlex testVortex2;

   public TestVortex(){
        testVortex1 = new CANSparkFlex(9, MotorType.kBrushless);
        //testVortex2 = new CANSparkFlex(Constants.testVortex2ID, MotorType.kBrushless);
   }

   public void setPowerToTestVortex1(double power){
    testVortex1.set(power);
   }

   
   // public void setPowerToTestVortex2(double power){
   //  testVortex2.set(power);
   // }
    
}
