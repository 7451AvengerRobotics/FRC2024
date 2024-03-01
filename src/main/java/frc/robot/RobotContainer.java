package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.ClimberCommand.climberCommand;
import frc.robot.commands.ClimberCommand.climberOneCommand;
import frc.robot.commands.ClimberCommand.climberTwoCommand;
import frc.robot.commands.ClimberCommand.setClimberPos;
import frc.robot.commands.ElevatorCommand.elevatorPositionCommand;
import frc.robot.commands.ElevatorCommand.elevatorTest;
import frc.robot.commands.Misc.limelightLedTest;
import frc.robot.commands.Misc.setLedColorCommand;
import frc.robot.commands.PivotCommands.pivotCommand;
import frc.robot.commands.PivotCommands.setPivotPosition;
import frc.robot.commands.RetrieveDisc.allFeed;
import frc.robot.commands.RetrieveDisc.indexCommand;
import frc.robot.commands.RetrieveDisc.intakeCommand;
import frc.robot.commands.shooterCommand.autoAimCommand;
import frc.robot.commands.shooterCommand.feedCommand;
import frc.robot.commands.shooterCommand.shootFF;
import frc.robot.commands.shooterCommand.shootPercentage;
import frc.robot.subsystems.*;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    
    /* Controllers */
    private final Joystick buttonPanel = new Joystick(0);

    private final PS4Controller controller = new PS4Controller(1) ;
    /* Drive Controls */
    private final int translationAxis = PS4Controller.Axis.kLeftY.value;
    private final int strafeAxis = PS4Controller.Axis.kLeftX.value;
    private final int rotationAxis = PS4Controller.Axis.kRightX.value;

    private final SendableChooser<Command> autoChooser;

   //private final SendableChooser<Command> autoChooser;

    /* Driver Buttons */
    // private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    // private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    private final JoystickButton squareButton = new JoystickButton(controller, PS4Controller.Button.kSquare.value);
    private final JoystickButton circleButton = new JoystickButton(controller, PS4Controller.Button.kCircle.value);
    private final JoystickButton robotCentric = new JoystickButton(controller, PS4Controller.Button.kPS.value);
    private final JoystickButton resetGyro = new JoystickButton(controller, PS4Controller.Button.kTouchpad.value);
    private final JoystickButton triangleButton = new JoystickButton(controller, PS4Controller.Button.kTriangle.value);
    private final JoystickButton crossButton = new JoystickButton(controller, PS4Controller.Button.kCross.value);
    private final JoystickButton r1Button = new JoystickButton(controller, PS4Controller.Button.kR1.value);
    private final JoystickButton r2Button = new JoystickButton(controller, PS4Controller.Button.kR2.value);



    /* Subsystems */    
    private final Shooter shooter = new Shooter();
    private final IndexTransporter index = new IndexTransporter();
    private final Intake intake = new Intake();
    private final Feeder feed = new Feeder();
    private final ElevatorSub elevator = new ElevatorSub();
    private final Climbers climbers = new Climbers();
    private final Pivot pivot = new Pivot();
    public final Swerve swerve = new Swerve();
    private final Limelight limelight = new Limelight();
    private final LedHandler led = new LedHandler();
    private int increment = 1;


    

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

    NamedCommands.registerCommand("shoot", 
                        new ParallelCommandGroup(new shootFF(shooter, 6000), 
                    new WaitCommand(1.5).andThen(
                        new feedCommand(feed, -0.7))).withTimeout(1)
                            );

    NamedCommands.registerCommand("fullIntake", 
                        new allFeed(feed, intake, index, -0.5, -0.5, -0.1).until(feed::detected));

    NamedCommands.registerCommand("ramp", new shootFF(shooter, 6000));

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    


    configureButtonBindings();

    swerve.setDefaultCommand(
        new TeleopSwerve(
            swerve, 
            () -> -(controller.getRawAxis(translationAxis))*0.8, 
            () -> -(controller.getRawAxis(strafeAxis))*0.8,
            () -> -(controller.getRawAxis(rotationAxis)*-0.8),
            () -> robotCentric.getAsBoolean()
        )
    );
    //    intake.setDefaultCommand(new intakeCommand(intake, -0.7).until(
    //             feed::detected));

    //     index.setDefaultCommand(new indexCommand(index, -0.7).until(
    //             feed::detected));

    //   feed.setDefaultCommand(new feedCommand(feed, -0.1).alongWith(
    //         new setLedColorCommand(led, 0, 0, 255)).until(
    //             feed::detected).andThen(new setLedColorCommand(led, 0, 255, 0)));
       shooter.setDefaultCommand(new shootFF(shooter, 4500));


    //autoChooser = AutoBuilder.buildAutoChooser();


    //SmartDashboard.putData("Auto Chooser", autoChooser);

    }

    

    


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

        JoystickButton w = new JoystickButton(buttonPanel, Constants.w);
        JoystickButton a = new JoystickButton(buttonPanel, Constants.a);
        JoystickButton s = new JoystickButton(buttonPanel, Constants.s);
        JoystickButton d = new JoystickButton(buttonPanel, Constants.d);
        JoystickButton intakeButton = new JoystickButton(buttonPanel, Constants.one); 
        JoystickButton outtakeButton = new JoystickButton(buttonPanel, Constants.two); 
        JoystickButton eject = new JoystickButton(buttonPanel, Constants.three);
        JoystickButton four = new JoystickButton(buttonPanel, Constants.four);
        JoystickButton five = new JoystickButton(buttonPanel, Constants.five);
        JoystickButton six = new JoystickButton(buttonPanel, Constants.six);
        JoystickButton seven = new JoystickButton(buttonPanel, Constants.seven);
        JoystickButton eight = new JoystickButton(buttonPanel, Constants.eight);

        six.whileTrue(new InstantCommand(() -> swerve.aimAtGoal()));

        seven.onTrue(new setPivotPosition(pivot, 9.5));
     
        


    //Amp 
      w.onTrue(new ParallelCommandGroup(new setPivotPosition(pivot, 40), new elevatorPositionCommand(elevator, - 99.145751953125, 406393.53125)));


    //Shoot
      d.whileTrue(new ParallelCommandGroup(new shootFF(shooter, 6000), 
                    new WaitCommand(1.5).andThen(
                        new feedCommand(feed, -0.7))).andThen(
                            new setLedColorCommand(led, 0, 0, 255)));

    //Reset
      s.onTrue(new ParallelCommandGroup(new setPivotPosition(pivot, 2), new elevatorPositionCommand(elevator, 0, 0)));

    //Stage
      a.onTrue(( 
      new climberCommand(climbers, swerve, 0.8)
                .until(climbers::climbersDetected))
                    .andThen(new feedCommand(feed, 0.7).raceWith(new WaitCommand(1))));
    
      

        /* Driver Buttons */

        


         seven.onTrue(new setPivotPosition(pivot, 8));
         eight.onTrue(new ParallelCommandGroup(new setPivotPosition(pivot, 47), 
        new elevatorPositionCommand(elevator, -135, 555390)).raceWith(new WaitCommand(1)));
     }      



    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
       // An ExampleCommand will run in autonomous
        // return new exampleAuto(s_Swerve);
        return autoChooser.getSelected();
        //return null;
    }
}