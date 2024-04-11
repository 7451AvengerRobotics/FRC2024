package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ElevatorCommand.elevatorPositionCommand;
import frc.robot.commands.LedCommands.ledAnimationCommand;
import frc.robot.commands.LedCommands.limelightAlignedLEDCommand;
import frc.robot.commands.LedCommands.setLedColorCommand;
import frc.robot.commands.PivotCommands.setPivotPosition;
import frc.robot.commands.PivotCommands.setPivotWithShooterMap;
import frc.robot.commands.RetrieveDisc.allFeed;
import frc.robot.commands.RetrieveDisc.indexCommand;
import frc.robot.commands.shooterCommand.feedCommand;
import frc.robot.commands.shooterCommand.shootFF;
import frc.robot.commands.shooterCommand.shootPercentage;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;
import frc.robot.subsystems.Swerve.Telemetry;
import frc.robot.subsystems.Swerve.generated.TunerConstants;
import frc.util.InterpolatingTreeMap;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    
    /* Controllers */
    private final Joystick buttonPanel = new Joystick(0);

    private final CommandPS4Controller joystick = new CommandPS4Controller(1);
    /* Drive Controls */
    



   private final SendableChooser<Command> autoChooser;

    private InterpolatingTreeMap<Double, Double> shooterAngleMap = new InterpolatingTreeMap<>() {{
     put(44.55, 0.0);
     put(45.5, 0.0);
     put(46.0, 0.0);
     put(57.0, 0.0);
     put(58.54, 3.0);
     put(68.79, 4.6);
    }};

    /*  Home Data
     *  put(49.19, 0.0);
        put(61.64, 3.0);
        put(75.99, 4.5);
        put(78.7, 6.1);f
        put(83.6, 5.8);
        put(97.32, 6.25); 
        put(97.8, 7.35); 
        put(108.34, 8.0); 
        put(111.6, 8.35);
        put(114.95, 8.5); 
        put(129.32, 9.0); 
     */
/* Carrolton Data
 *         put(40.0, 0.0);
        put(48.19, 0.0);//
        put(49.0, 0.0);
        put(63.7, 3.0); //range workde from 63.7-69
        put(89.96, 7.0);  //    
 */

    /* Subsystems */    
    private final Shooter shooter = new Shooter();
    private final IndexTransporter index = new IndexTransporter();
    private final Intake intake = new Intake();
    private final Feeder feed = new Feeder();
    private final ElevatorSub elevator = new ElevatorSub();
    private final Pivot pivot = new Pivot();
    private final Limelight limelight = new Limelight();
    private final LedHandler led = new LedHandler();




  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private static double MaxAngularRate = 3 * Math.PI; // 3/4 of a rotation per second max angular velocity

      public static double getMaxAngularRate() {
        return MaxAngularRate;
      }


  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.12).withRotationalDeadband(MaxAngularRate * 0.12) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);



    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        NamedCommands.registerCommand("shootFirst", new WaitCommand(0.7).andThen(
                new ParallelCommandGroup(
                        new feedCommand(feed, -1), 
                        new indexCommand(index, -0.5)).withTimeout(0.2)));
        
        NamedCommands.registerCommand("shootFirst", new WaitCommand(0.7).andThen(
                new ParallelCommandGroup(
                        new feedCommand(feed, -1), 
                        new indexCommand(index, -0.5)).withTimeout(0.2)));
        
        NamedCommands.registerCommand("ramp", new shootFF(shooter, 6000));

        //NamedCommands.registerCommand("shoot",
        //new setPivotWithShooterMap(pivot, limelight).withTimeout(0.2).andThen(new allFeed(feed, intake, index, 0, -1, -1)).withTimeout(0.6));
        
        NamedCommands.registerCommand("shoot",
                new ParallelCommandGroup(
                        new feedCommand(feed, -1), 
                        new indexCommand(index, -0.5)).withTimeout(0.2));
                //Maybe run Index
            
                NamedCommands.registerCommand("shootFirst", new WaitCommand(0.7).andThen(
                new ParallelCommandGroup(
                        new feedCommand(feed, -1), 
                        new indexCommand(index, -0.5)).withTimeout(0.2)));
        NamedCommands.registerCommand("shoot1", new ParallelCommandGroup(
            new shootFF(shooter, 6000)).withTimeout(0.75).andThen(new ParallelCommandGroup(
                            new feedCommand(feed, -1), 
                            new indexCommand(index, -0.5)).withTimeout(0.2)));
        NamedCommands.registerCommand("fullIntake", new ParallelCommandGroup(
                                            new setPivotPosition(pivot, 2.25).withTimeout(0.1), 
                                                    new allFeed(feed, intake, index,  -1, -0.3, -0.2))
                                                        .until(feed::detected));

        NamedCommands.registerCommand("pivot", new setPivotWithShooterMap(pivot,  limelight).withTimeout(0.2));
        NamedCommands.registerCommand("pivot1", new setPivotPosition(pivot, 11.2).withTimeout(0.3));
        NamedCommands.registerCommand("pivot2", new setPivotPosition(pivot, 10.64).withTimeout(0.3));
        NamedCommands.registerCommand("pivot3", new setPivotPosition(pivot, 6).withTimeout(0.3));
        NamedCommands.registerCommand("pivot4", new setPivotPosition(pivot, 9).withTimeout(0.3));
        NamedCommands.registerCommand("pivot5", new setPivotPosition(pivot, 11.15).withTimeout(0.3));
        NamedCommands.registerCommand("pivot6", new setPivotPosition(pivot,  7).withTimeout(0.2));

    configureButtonBindings();


  //  led.setDefaultCommand(
   //     new setLedColorCommand(led, 0, 255, 100).until(feed::detected).andThen(new ledAnimationCommand(led)).withTimeout(0.5).andThen(new setLedColorCommand(led, 0, 255, 0).until(feed::notDetected)));
    
    
    led.setDefaultCommand(new setLedColorCommand(led, 255, 0, 0).until(index::indexDetected).andThen
    (new ledAnimationCommand(led)).until(feed::detected).andThen(new setLedColorCommand(led, 0, 255, 0)).until(feed::notDetected));

        // shooter.setDefaultCommand(new shootPercentage(shooter, 0).until(feed::detected).andThen(new shootPercentage(shooter, 0.3)));
        //pivot.setDefaultCommand(new setPivotWithShooterMap(pivot, limelight));
         shooter.setDefaultCommand(new shootFF(shooter, 6000).until(joystick.options()).andThen(new shootFF(shooter, 0).until(feed::detected).andThen(new shootPercentage(shooter, 0.3))));
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

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
    drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    joystick.R1().whileTrue( drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed*0.3) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed*0.3) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate*0.3) // Drive counterclockwise with negative X (left)
        ));
        
    //joystick.rightTrigger().whileTrue(drivetrain.applyRequest(() -> brake));
    joystick.R2().whileTrue( drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed*0.3) // Drive forward with
    // negative Y (forward)
    .withVelocityY(-joystick.getLeftX() * MaxSpeed*0.3) // Drive left with negative X (left)
    .withRotationalRate(limelight.limelight_aim_proportional()) // Drive counterclockwise with negative X (left)
    ).andThen(new limelightAlignedLEDCommand(led, limelight))); 
 
   
        joystick.triangle().whileTrue( drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed*0.3) // Drive forward with
        // negative Y (forward)
        .withVelocityY(-joystick.getLeftX() * MaxSpeed*0.3) // Drive left with negative X (left)
        .withRotationalRate(limelight.limelight_aim_proportional()) // Drive counterclockwise with negative X (left)
        )); 
   

    // reset the field-centric heading on left bumper press
    joystick.L1().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);

    


        JoystickButton w = new JoystickButton(buttonPanel, Constants.w);
        JoystickButton a = new JoystickButton(buttonPanel, Constants.a);
        JoystickButton s = new JoystickButton(buttonPanel, Constants.s);
        JoystickButton d = new JoystickButton(buttonPanel, Constants.d);
        JoystickButton intakeButton = new JoystickButton(buttonPanel, Constants.one); 
        JoystickButton outtakeButton = new JoystickButton(buttonPanel, Constants.two); 
        JoystickButton three = new JoystickButton(buttonPanel, Constants.three);
        JoystickButton four = new JoystickButton(buttonPanel, Constants.four);
        JoystickButton five = new JoystickButton(buttonPanel, Constants.five);
        JoystickButton six = new JoystickButton(buttonPanel, Constants.six);
        JoystickButton seven = new JoystickButton(buttonPanel, Constants.seven);
        JoystickButton eight = new JoystickButton(buttonPanel, Constants.eight);

        


    //Amp 
      w.onTrue(new ParallelCommandGroup(new setPivotPosition(pivot, 38), 
                new elevatorPositionCommand(elevator, -95.145751953125), new indexCommand(index, -0.2).withTimeout(0.2)));
           
  //Reset
        s.onTrue(new ParallelCommandGroup(
            new setPivotPosition(pivot, 0), 
            new elevatorPositionCommand(elevator, 0.0),
            new allFeed(feed, intake, index, 0, 0, 0),
            new shootFF(shooter, 0)
            ));
    
        a.whileTrue(new ParallelCommandGroup(
            new feedCommand(feed, -1),
            new indexCommand(index, -0.45)
        ));

    //Shoot
    d.whileTrue(new ParallelCommandGroup(
        new setPivotPosition(pivot, 0), new shootFF(shooter, 6000)).withTimeout(0.85).andThen(new ParallelCommandGroup(
                         new feedCommand(feed, -1), 
                         new indexCommand(index, -0.5)).andThen(new setLedColorCommand(led, 255, 0, 0))));
                         
        intakeButton.onTrue(new ParallelCommandGroup(
            new setPivotPosition(pivot, 3), 
            new allFeed(feed, intake, index, -1, -0.45, -0.2)).until(feed::detected).andThen(
                    new feedCommand(feed, 0.1).raceWith(new WaitCommand(0.2))));

        
        outtakeButton.onTrue(new ParallelCommandGroup(new setLedColorCommand(led, 255, 0, 0),
            new allFeed(feed, intake, index, 0.5, 0.5, 0.2)));

        

        four.whileTrue(new ParallelCommandGroup(
                new shootFF(shooter, 2000), 
                new feedCommand(feed, -1)).andThen(new setLedColorCommand(led, 255, 0, 0)));

        six.whileTrue(new ParallelCommandGroup(
             new shootFF(shooter, 3500)).withTimeout(0.5).andThen(new ParallelCommandGroup(
                         new feedCommand(feed, -1), 
                         new indexCommand(index, -0.5))));

        three.onTrue(new shootFF(shooter, 6000));

         seven.whileTrue(new ParallelCommandGroup(
        new setPivotWithShooterMap(pivot, limelight), new shootFF(shooter, 6000)).withTimeout(0.85).andThen(new ParallelCommandGroup(
                         new feedCommand(feed, -1), 
                         new indexCommand(index, -0.5)).andThen(new setLedColorCommand(led, 255, 0, 0))));
                
        eight.onTrue(new allFeed(feed, intake, index, 0, 0, 0));
        
     }  
     






    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
       // An ExampleCommand will run in autonomous
        // return new exampleAuto(s_Swerve);
         return Commands.parallel(autoChooser.getSelected(), new shootFF(shooter, 6000));
    }

    


}