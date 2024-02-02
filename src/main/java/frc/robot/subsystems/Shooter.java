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
import edu.wpi.first.wpilibj.DigitalInput;
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
    private final CANSparkMax feeder;
    private final SimpleMotorFeedforward controlTop;
    private final SimpleMotorFeedforward controlBot;
    private final MutableMeasure<Voltage> m_appliedVoltage = mutable(Volts.of(0));
    private final MutableMeasure<Angle> m_distance = mutable(Rotations.of(0));
    private final MutableMeasure<Velocity<Angle>> m_velocity = mutable(RotationsPerSecond.of(0));
    private SysIdRoutine routine;




    private double topkS = -0.015666;
    private double topkV = 0.0018151;
    private double topkA = 0.00033602;

    private double botKS = 0.090092;
    private double botKv = 0.0017978;
    private double botKa = 0.00011993;
    private final DigitalInput beamBreak;
     private boolean active;



    
    public Shooter(){
        super();
        
        shooterTop =  new CANSparkFlex(Constants.shooterTop, MotorType.kBrushless);
        shooterBottom =  new CANSparkFlex(Constants.shooterBottom, MotorType.kBrushless);

        feeder = new CANSparkMax(41, MotorType.kBrushless);
        
        feeder.setInverted(true);

        shooterBottom.setInverted(false);
        shooterTop.setInverted(true);
        controlTop = new SimpleMotorFeedforward(topkS, topkV, topkA);
        controlBot = new SimpleMotorFeedforward(botKS, botKv, botKa);
        
        beamBreak = new DigitalInput(0);

        setSysIdRoutine(shooterBottom, "bot-motor");
 
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
        shooterBottom.setVoltage(controlBot.calculate(velocity));
        shooterTop.setVoltage(controlTop.calculate(velocity));
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
    if (invert != shooterBottom.getInverted())
      shooterBottom.setInverted(invert);
  }

  public void stopMotors() {
    shooterBottom.set(0);
  }

  public void feed(double power){
    feeder.set(power);
  }

  public boolean breaked(){
    return beamBreak.get();
   }

  @Override
   public void periodic(){
    SmartDashboard.putBoolean("Limit Switch State", this.breaked());
   }





}
