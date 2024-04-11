package frc.robot.commands.PivotCommands  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pivot;


public class setPivotPositionwithRegression extends Command{
    private final Pivot pivot;
    private final Limelight lime;

    public setPivotPositionwithRegression(Pivot pivot, Limelight lime){
        this.pivot = pivot;
        this.lime = lime;
        addRequirements(pivot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        if(lime.getID() == 7 || lime.getID() == 4){
             pivot.setPosition(lime.setPivotAngleRegression());
        }
    
    }
    
    @Override
    public void end(boolean interrupted){
       
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

