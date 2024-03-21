package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;


public class autoAimCommand extends Command{
    private final CommandSwerveDrivetrain swerve;
    private final Supplier<Pose2d> currentPoseSupplier;
    public autoAimCommand(CommandSwerveDrivetrain drive, final Supplier<Pose2d> currentPoseSupplier){
        this.swerve = drive;
        this.currentPoseSupplier = currentPoseSupplier;
  
      
        addRequirements(drive);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        swerve.faceAngle(swerve.angleToSpeakerSupplier(currentPoseSupplier));

    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

