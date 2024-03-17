package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSub;    


public class elevatorPositionCommand extends Command{
    private final ElevatorSub elevator;
    private final double ele2Pos;
    public elevatorPositionCommand(ElevatorSub elevator, double ele1Pos, double ele2Pos){
        this.elevator = elevator;
        this.ele2Pos = ele2Pos;
  
      
        addRequirements(elevator);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        elevator.setElevatorPosition(ele2Pos);

    }
    
    @Override
    public void end(boolean interrupted){
     
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

