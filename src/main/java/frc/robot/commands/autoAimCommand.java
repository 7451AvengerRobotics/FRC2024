// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.Feeder;
// import frc.robot.subsystems.IndexTransporter;
// import frc.robot.subsystems.Intake;


// public class autoAimCommand extends Command{
//     private final CommandSwerveDrivetrain swerve;
//     private final 
//     public autoAimCommand(Feeder feed, Intake intake, IndexTransporter index, double intakePercent, double indexPercent, double feedPercent){
//         this.feed = feed;
//         this.intake = intake;
//         this.index = index;
//         this.intakePercent = intakePercent;
//         this.indexPercent = indexPercent;
//         this.feedPercent = feedPercent;
  
      
//         addRequirements(feed);

//     }

//     @Override
//     public void initialize(){
//     }
    
//     @Override 
//     public void execute(){
//         feed.feed(feedPercent);
//         intake.intake(intakePercent);
//         index.spinMotor(indexPercent);


//     }
    
//     @Override
//     public void end(boolean interrupted){
//         feed.feed(0);
//         intake.intake(0);
//         index.spinMotor(0);
//     }

//     @Override
//     public boolean isFinished(){
//         return false;
//     }
// }

