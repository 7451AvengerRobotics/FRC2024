package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;

public class feedCommand extends Command{
       private final Feeder feeder;
    private final double percent;
    public feedCommand(Feeder feed, double percent){
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
