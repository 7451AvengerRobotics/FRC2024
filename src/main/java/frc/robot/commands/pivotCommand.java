package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;


public class pivotCommand extends Command{
    private final Pivot pivot;
    private final double percent;
    public pivotCommand(Pivot pivot, double percent){
        this.pivot = pivot;
        this.percent = percent;
  
      
        addRequirements(pivot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        pivot.setPower(percent);
    }
    
    @Override
    public void end(boolean interrupted){
        pivot.setPower(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

