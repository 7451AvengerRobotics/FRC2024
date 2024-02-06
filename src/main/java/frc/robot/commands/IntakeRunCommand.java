package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeRunCommand extends Command {

    private final Intake rIntake;
    private final double rPower;

    public IntakeRunCommand (Intake intake, double power){
        this.rIntake = intake;
        this.rPower = power;
        addRequirements(rIntake);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        rIntake.intake(rPower);
    }

    @Override
    public void end(boolean interrupted){
        rIntake.intake(0);

    }

    @Override
    public boolean isFinished(){
        return true;
    }   
}
