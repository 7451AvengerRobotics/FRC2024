package frc.robot.subsystems;

import static edu.wpi.first.units.MutableMeasure.mutable;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.MutableMeasure;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Config;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {


    //private final CANSparkMax m_index;
    private final CANSparkFlex shooterTop;
    private final CANSparkFlex shooterBottom;
    private final SimpleMotorFeedforward controlTop;
    private final SimpleMotorFeedforward controlBot;
    private final MutableMeasure<Voltage> m_appliedVoltage = mutable(Volts.of(0));
    private final MutableMeasure<Angle> m_distance = mutable(Rotations.of(0));
    private final MutableMeasure<Velocity<Angle>> m_velocity = mutable(RotationsPerSecond.of(0));
    private SysIdRoutine routine;




    private double topkS = 0;
    private double topkV = 0;
    private double topkA = 0;

    private double botKS = 0;
    private double botKv = 0;
    private double botKa = 0;


    
    public Shooter(){
        super();
        
        shooterTop =  new CANSparkFlex(Constants.shooterTop, MotorType.kBrushless);
        shooterBottom =  new CANSparkFlex(Constants.shooterBottom, MotorType.kBrushless);
        //m_index = new CANSparkMax(Constants.m_index, MotorType.kBrushless);
        shooterBottom.restoreFactoryDefaults();
        shooterBottom.setInverted(false);
        shooterTop.setInverted(true);
        controlTop = new SimpleMotorFeedforward(topkS, topkV, topkA);
        controlBot = new SimpleMotorFeedforward(botKS, botKv, botKa);


        // shooterTop.setOpenLoopRampRate(1);
        // shooterBottom.setOpenLoopRampRate(1);

        setSysIdRoutine(shooterTop, "top-motor");
 
    }

    public void shoot(double power){
        shooterTop.set(power);
        shooterBottom.set(power);
       
    }



    public void setBottom(double power){
        shooterBottom.set(power);
    }

    public void setTop(double power){
        shooterTop.set(power);
    }

    public double RPSToMPS(double wheelRPS, double circumference){
        double wheelMPS = wheelRPS * circumference;
        return wheelMPS;
    }

    public void setFF(double velocity){
        shooterBottom.setVoltage(controlTop.calculate(velocity));
        shooterTop.setVoltage(controlBot.calculate(velocity));
    }



    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return routine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return routine.dynamic(direction);
    }

    public void setSysIdRoutine(CANSparkFlex shooterTop2, String motorName) {
    RelativeEncoder encoder = shooterTop2.getEncoder();
    routine = new SysIdRoutine(new Config(), new SysIdRoutine.Mechanism(
        (Measure<Voltage> voltage) -> shooterTop2.set(voltage.in(Volts) / RobotController.getBatteryVoltage()),
        log -> {
          log.motor(motorName)
              .voltage(
                  m_appliedVoltage.mut_replace(
                      shooterTop2.get() * RobotController.getBatteryVoltage(), Volts))
              .angularPosition(m_distance.mut_replace(encoder.getPosition(), Rotations))
              .angularVelocity(m_velocity.mut_replace(encoder.getVelocity(), RotationsPerSecond));
        }, this));
  }

  public void invertMotors(boolean invert) {
    if (invert != shooterTop.getInverted())
      shooterTop.setInverted(invert);
  }

  public void stopMotors() {
    shooterTop.set(0);
  }




}
