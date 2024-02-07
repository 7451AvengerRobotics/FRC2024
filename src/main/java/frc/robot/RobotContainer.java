package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.shooterCommand.feedCommand;
import frc.robot.commands.shooterCommand.setLedColorCommand;
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
    private final Joystick driver = new Joystick(0);
    private final Joystick buttonPanel = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    private final JoystickButton shooterTest0 = new JoystickButton(driver, PS4Controller.Button.kCircle.value);
    private final JoystickButton squareButton = new JoystickButton(driver, PS4Controller.Button.kSquare.value);
    private final JoystickButton shooterTest2 = new JoystickButton(driver, PS4Controller.Button.kTriangle.value);
    private final JoystickButton shooterTest3 = new JoystickButton(driver, PS4Controller.Button.kCross.value);

    /* Subsystems */
    // private final Swerve s_Swerve = new Swerve();
    private final Shooter shooter = new Shooter();

    // private final TestVortex vortex = new TestVortex();
    private final LedHandler LED = new LedHandler();
    private final Feeder feeder = new Feeder();

    

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

    configureButtonBindings();


    feeder.setDefaultCommand(new ParallelCommandGroup(new feedCommand(feeder, - 0.1), new setLedColorCommand(LED, 0, 0, 255)).until(feeder::detected).andThen(new setLedColorCommand(LED, 0, 255, 0)));
    shooter.setDefaultCommand(new shootFF(shooter, 1000));

    }



    


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
    

    squareButton.whileTrue(new ParallelCommandGroup(new shootFF(shooter, 1500), 
     new WaitCommand(2).andThen(new feedCommand(feeder, -0.2).andThen(new setLedColorCommand(LED, 0, 0, 255)))));
    


    }   

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        // return new exampleAuto(s_Swerve);
        return null;
    }
}


