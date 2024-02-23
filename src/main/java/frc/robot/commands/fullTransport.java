package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.IndexTransporter;
import frc.robot.subsystems.Intake;


public class fullTransport extends Command{
    private final Intake intake;
    private final IndexTransporter index;
    private final double intakePercent;
    private final double indexPercent;
    private final Feeder feed;
    public fullTransport(Intake intake, IndexTransporter index, Feeder feed, double intakePercent, double indexPercent){
        this.intake = intake;
        this.index = index;
        this.feed = feed;
        this.intakePercent = intakePercent;
        this.indexPercent = indexPercent;
  
      
        addRequirements(intake);
        addRequirements(index);
        addRequirements(feed);

    }

    @Override
    public void initialize(){

    }
    
    @Override 
    public void execute(){
        
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

