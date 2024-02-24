package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.climberCommand;
import frc.robot.commands.climberOneCommand;
import frc.robot.commands.climberTwoCommand;
import frc.robot.commands.indexCommand;
import frc.robot.commands.intakeCommand;
import frc.robot.commands.pivotCommand;
import frc.robot.commands.setPivotPosition;
import frc.robot.commands.shooterCommand.feedCommand;
import frc.robot.commands.shooterCommand.shootFF;
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

    /* Driver Buttons */
    // private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    // private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    private final JoystickButton squareButton = new JoystickButton(controller, PS4Controller.Button.kSquare.value);
    private final JoystickButton circleButton = new JoystickButton(controller, PS4Controller.Button.kCircle.value);
    private final JoystickButton triangleButton = new JoystickButton(controller, PS4Controller.Button.kTriangle.value);
    private final JoystickButton crossButton = new JoystickButton(controller, PS4Controller.Button.kCross.value);
    private final JoystickButton r1Button = new JoystickButton(controller, PS4Controller.Button.kR1.value);
    private final JoystickButton r2Button = new JoystickButton(controller, PS4Controller.Button.kR2.value);



    /* Subsystems */    
    private final Shooter shooter = new Shooter();
    private final IndexTransporter index = new IndexTransporter();
    private final Intake intake = new Intake();
    private final Feeder feed = new Feeder();
    // private final Elevator elevator = new Elevator();
    private final Climbers climbers = new Climbers();
    private final Pivot pivot = new Pivot();
    private final Swerve swerve = new Swerve();

    

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

    configureButtonBindings();

    swerve.setDefaultCommand(
        new TeleopSwerve(
            swerve, 
            () -> -(controller.getRawAxis(translationAxis))*1, 
            () -> -(controller.getRawAxis(strafeAxis))*-1,
            () -> -(controller.getRawAxis(rotationAxis)*-1)
        )
    );

     intake.setDefaultCommand(new intakeCommand(intake, -0.5));
     index.setDefaultCommand(new indexCommand(index, -0.4));
     feed.setDefaultCommand(new feedCommand(feed, -0.1).until(feed::detected));   
     shooter.setDefaultCommand(new shootFF(shooter, 3000));


    autoChooser = AutoBuilder.buildAutoChooser();



    SmartDashboard.putData("Auto Chooser", autoChooser);

    }

    

    


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

        JoystickButton midCone = new JoystickButton(buttonPanel, Constants.w);
        JoystickButton midCube = new JoystickButton(buttonPanel, Constants.a);
        JoystickButton pivotButton = new JoystickButton(buttonPanel, Constants.s);
        JoystickButton highCone = new JoystickButton(buttonPanel, Constants.d);
        JoystickButton ampButton = new JoystickButton(buttonPanel, 1); 
        JoystickButton resetPivot = new JoystickButton(buttonPanel, 2); 

        
        midCone.whileTrue(new climberCommand(climbers,swerve, 0.3));
        midCube.whileTrue(new climberOneCommand(climbers, 0.3));
        highCone.whileTrue(new climberTwoCommand(climbers,0.3));
        pivotButton.onTrue(new setPivotPosition(pivot, 10));
        ampButton.onTrue(new setPivotPosition(pivot, 49));
        resetPivot.onTrue(new setPivotPosition(pivot, 5));
        /* Driver Buttons */
        
        //circleButton.whileTrue(Commands.parallel((new shootFF(shooter, 5500))).andThen(new feedCommand(feed, 1)));
        squareButton.whileTrue(new climberCommand(climbers, swerve, 0.3));
        // triangleButton.whileTrue(new climberOneCommand(climbers, 0.3)); //moves down
        // crossButton.whileTrue(new climberTwoCommand(climbers, 0.5));//moves down
        // r1Button.whileTrue(new climberOneCommand(climbers, -0.3)); // move up
        // r2Button.whileTrue(new climberTwoCommand(climbers, -0.5)); //move up
        triangleButton.onTrue(new setPivotPosition(pivot, 49));
        circleButton.whileTrue(new ParallelCommandGroup(new shootFF(shooter, 6000), 
    new WaitCommand(2).andThen(new feedCommand(feed, -0.4))));

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


