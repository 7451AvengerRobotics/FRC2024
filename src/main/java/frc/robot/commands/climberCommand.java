// package frc.robot.commands  ;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.Climbers;
// import frc.robot.subsystems.Swerve;


// public class climberCommand extends Command{
//     private final Climbers climbers;
//     private final double percent;
//     private final Swerve drive;
//     public climberCommand(Climbers climb, Swerve drive, double percent){
//         this.climbers = climb;
//         this.percent = percent;
//         this.drive = drive;
  
      
//         addRequirements(climbers);

//     }

//     @Override
//     public void initialize(){
//     }
    
//     @Override 
//     public void execute(){
//         if (drive.getGyroPitch() > 5){
//             climbers.setClibmerTwo(percent + 0.2);
//             climbers.setClimberOne(-percent + 0.2);
//         } else if (drive.getGyroPitch() < - 5){
//             climbers.setClibmerTwo(percent - 0.2);
//             climbers.setClimberOne(-percent - 0.2);
//         }else{ 
//             climbers.setPower(percent);
//         }

//         if (climbers.climber1Detected()){
//             climbers.setClimberOne(0);
//         }
        
//         if (climbers.climber2Detected()){
//             climbers.setClibmerTwo(0);
//         }
//     }
    
//     @Override
//     public void end(boolean interrupted){
//         climbers.setPower(0);
//     }

//     @Override
//     public boolean isFinished(){
//         return false;
//     }
// }

