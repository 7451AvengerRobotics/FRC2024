package frc.robot.commands.RetrieveDisc  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexTransporter;


public class indexCommand extends Command{
    private final IndexTransporter index;
    private final double percent;
    public indexCommand(IndexTransporter index, double percent){
        this.index = index;
        this.percent = percent;
  
      
        addRequirements(index);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        index.spinMotor(percent);
    }
    
    @Override
    public void end(boolean interrupted){
        index.spinMotor(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

