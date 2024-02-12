package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeRunCommand extends Command {

    private final Intake intake;
    private final double power;

    public IntakeRunCommand (Intake intake, double power){
        this.intake = intake;
        this.power = power;
        addRequirements(intake);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        intake.intake(power);
    }

    @Override
    public void end(boolean interrupted){
        intake.intake(0);

    }

    @Override
    public boolean isFinished(){
        return true;
    }   
}
