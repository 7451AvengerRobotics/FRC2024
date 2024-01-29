package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
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
    private final JoystickButton shooterTest1 = new JoystickButton(driver, PS4Controller.Button.kSquare.value);
    private final JoystickButton shooterTest2 = new JoystickButton(driver, PS4Controller.Button.kTriangle.value);

    /* Subsystems */
    // private final Swerve s_Swerve = new Swerve();
    private final Shooter shooter = new Shooter();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // s_Swerve.setDefaultCommand(
        //     new TeleopSwerve(
        //         s_Swerve, 
        //         () -> -driver.getRawAxis(translationAxis), 
        //         () -> -driver.getRawAxis(strafeAxis), 
        //         () -> -driver.getRawAxis(rotationAxis), 
        //         () -> robotCentric.getAsBoolean()
        //     )
        // );

        // Configure the button bindings

       // led.setRainbow();
      //shooter.setDefaultCommand(new shootShooter(shooter,0));  
      shooterTest0.whileTrue(new shootShooter(shooter, 1000));
    //   shooterTest1.whileTrue(new shootShooter(shooter, 0.5));
    //   shooterTest2.whileTruex    (new shootShooter(shooter, 1));

       // vortex1.setDefaultCommand(new Test(vortex1, led, 0.1));
    //    shooterTest0.whileTrue(new )
    //     vortex1.setDefaultCommand(new testAim(vortex1, limelight));
    //     configureButtonBindings();

    }



    


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        // zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));
 
        //test.whileTrue(new RunCommand(() -> vortex2.  setPowerToTestVortex1(1)));


        // JoystickButton test1 = new JoystickButton(buttonPanel, 2);
        // test1.whileTrue(new Test(vortex1, .4));
        //testVortex2Button.onTrue(new InstantCommand(() -> vortex2.setPowerToTestVortex2(0.3)));

        
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


