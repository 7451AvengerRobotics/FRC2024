package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;


public class Superstructure extends SubsystemBase{
    private Climbers climbers;
    private ElevatorSub elevator;
    private Eyes eyes;
    private Feeder feeder;
    private IndexTransporter index;
    private Intake intake;
    private LedHandler led;
    private Pivot pivot;
    private Shooter shooter;
    private CommandSwerveDrivetrain swerve;
    
    private boolean inShooter = false;
    private boolean inIndex = false;
    private boolean leftClimberDetected = false;
    private boolean rightClimberDetected = false;
    
    public Superstructure(Climbers climber, ElevatorSub elevator, Eyes eyes, Feeder feeder, IndexTransporter index, Intake intake, LedHandler led, Pivot pivot, Shooter shooter){
        this.climbers = climber;
        this.elevator = elevator;
        this.eyes = eyes;
        this.feeder = feeder;
        this.index = index;
        this.intake = intake;
        this.led = led;
        this.pivot = pivot;
        this.shooter = shooter;
    }



    public Command allIntake(double intakePow, double indexPow, double feederPow){
        var intakeCMD = intake.setintakePower(feederPow);
        var indexCMD = index.setIndexPower(indexPow);
        var feederCMD = feeder.setFeederPower(feederPow);

        return Commands.parallel(intakeCMD, indexCMD, feederCMD);
    }

      public Command allOuttake(double intakePow, double indexPow, double feederPow){
        var intakeCMD = intake.setintakePower(-feederPow);
        var indexCMD = index.setIndexPower(-indexPow);
        var feederCMD = feeder.setFeederPower(-feederPow);

        return Commands.parallel(intakeCMD, indexCMD, feederCMD);
    }

    public Command shootAMP(){
        var pivotCMD = pivot.setPivotPosition(0);
        var elevatorCMD = elevator.setElePosCMD(0);
        var feederCMD = feeder.setFeederPower(0);
        return Commands.sequence(
            Commands.parallel(
               pivotCMD, elevatorCMD 
            ),
            feederCMD
        );
    }

    public Command shootShooter(){
        var feedCMD = feeder.setFeederPower(0);

        return feedCMD;
    }

      public Command resetCommand(){
        var elevatorCMD = elevator.setElePosCMD(0);
        var pivotCMD = pivot.setPivotPosition(0);
        var climberCMD = climbers.setClimberPos(0, 0);
        double gyroHeight = swerve.getPigeon2().getGravityVectorZ().getValueAsDouble();

            if(gyroHeight > 0.5 || gyroHeight < -0.5){
                return Commands.none();
            } else{
                return Commands.sequence(elevatorCMD, pivotCMD, climberCMD);
            }
        }

    public Command setUpHang(){
        var elevatorCMD = elevator.setElePosCMD(0);
        var pivotCMD = pivot.setPivotPosition(0);
        var climberCMD = climbers.setClimberPos(0, 0);

        return Commands.parallel(elevatorCMD, pivotCMD, climberCMD);
    }

    // public Command letsHang(){
        


    // }

}
