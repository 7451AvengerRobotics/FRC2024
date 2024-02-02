package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class feedCommand extends Command{
       private final Shooter feeder;
    private final double percent;
    public feedCommand(Shooter feed, double percent){
        this.feeder = feed;
        this.percent = percent;
  
      
        addRequirements(feeder);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        feeder.feed(percent);
        

    }
    
    @Override
    public void end(boolean interrupted){
        feeder.feed(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
    
}
