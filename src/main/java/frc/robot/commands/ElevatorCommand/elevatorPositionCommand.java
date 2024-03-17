package frc.robot.commands.ElevatorCommand;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSub;    


public class elevatorPositionCommand extends Command{
    private final ElevatorSub elevator;
    private final double ele1Pos;
    public elevatorPositionCommand(ElevatorSub elevator, double ele1Pos, double ele2Pos){
        this.elevator = elevator;
        this.ele1Pos = ele1Pos;
  
      
        addRequirements(elevator);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        elevator.setElevatorPosition(ele1Pos);

    }
    
    @Override
    public void end(boolean interrupted){
     
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

