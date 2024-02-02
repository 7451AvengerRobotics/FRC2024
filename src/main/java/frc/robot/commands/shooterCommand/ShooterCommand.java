package frc.robot.commands.shooterCommand;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends SequentialCommandGroup{
    public ShooterCommand(Shooter shooter, double rpm, double percent){
        addCommands(
            new shootFF(shooter, rpm),
            new WaitCommand(5),
            new feedCommand(shooter, percent));

        

    }

    
}
