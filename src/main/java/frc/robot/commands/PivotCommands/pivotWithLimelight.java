package frc.robot.commands.PivotCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pivot;

public class pivotWithLimelight extends Command {

    private final Pivot pivot;
    private final Limelight lime;
    public pivotWithLimelight(Pivot pivot, Limelight lime){
        this.lime = lime;
        this.pivot = pivot;
        addRequirements(pivot);
        addRequirements(lime);
    
    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){

        double position;

        if (lime.getArea() != 0) {
            
            position = -14.7 * lime.getArea() + 12.25;

        }

        else {
            position = 3;
        }

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
