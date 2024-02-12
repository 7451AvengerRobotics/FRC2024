package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.IndexTransporter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LedHandler;


public class intakeToIndex extends Command{
    private final Intake intake;
    private final IndexTransporter index;
    private final LedHandler led;
    private final double intakePercent;
    private final double indexPercent;
    public intakeToIndex(Intake intake, IndexTransporter index, LedHandler led, double intakePercent, double indexPercent){
        this.intake = intake;
        this.index = index;
        this.led = led;
        this.intakePercent = intakePercent;
        this.indexPercent = indexPercent;
  
      
        addRequirements(intake);
        addRequirements(index);
        addRequirements(led);

    }

    @Override
    public void initialize(){

    }
    
    @Override 
    public void execute(){
        new SequentialCommandGroup(new ParallelCommandGroup(new intakeCommand(intake, 0.5), 
        new indexCommand(index, indexPercent)).until(index::objInIndex)
        .andThen(new setLedColorCommand(led, 0, 0, 255)));
    }
    
    @Override
    public void end(boolean interrupted){
        intake.intake(0);
        index.spinMotor(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

