package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;    


public class elevatorTest extends Command{
    private final Elevator elevator;
    private final double power;
    public elevatorTest(Elevator elevator, double power){
        this.elevator = elevator;
        this.power = power;
  
      
        addRequirements(elevator);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        elevator.setPower(power);

    }
    
    @Override
    public void end(boolean interrupted){
        elevator.setPower(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

