package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.testSparkMax;


public class tets extends Command{
    private final testSparkMax tests;
    public tets(testSparkMax tests){
        this.tests = tests;
  
      
        addRequirements(tests);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        tests.spin();
    }
    
    @Override
    public void end(boolean interrupted){
        tests.spin();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

