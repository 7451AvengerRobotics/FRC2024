package frc.robot.commands.PivotCommands  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pivot;


public class setPivotWithShooterMap extends Command{
    private final Pivot pivot;
    private final Limelight lime;

    public setPivotWithShooterMap(Pivot pivot, Limelight lime){
        this.pivot = pivot;
        this.lime = lime;
        addRequirements(pivot);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        if(lime.getID() == 7 || lime.getID() == 3){
             pivot.setPosition(lime.getShooterMapAngle());
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

