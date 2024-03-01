package frc.robot.commands.PivotCommands  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;


public class setPivotPosition extends Command{
    private final Pivot pivot;
    private final double position;
    public setPivotPosition(Pivot pivot, double position){
        this.pivot = pivot;
        this.position = position;
        addRequirements(pivot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        pivot.setPosition(position);
    }
    
    @Override
    public void end(boolean interrupted){
       
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

