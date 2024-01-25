public class Elevator extends SubsystemBase 
{
    //Initializes Variables
    private final CANSparkMax motor1;
    private final CANSparkMax motor2;
    private boolean motorOn;
    public Elevator
    {
        //Allows Elevator to inherit properties of SubsystemBase (Vineeth, Check me on this idk if its explained right)
        super();
        motor1 = new CANSparkMax(Constants.intakeMotorCAN, MotorType.kBrushless);
        motor2 = new CANSparkMax(Constants.intakeMotorCAN, MotorType.kBrushless);
        motorOn = false;
    }

    //Sets power of the Elevator 
    public void moveElevator(double power)
    {
        motor1.set(power);
        motor2.set(power);
    }
    // Sets power of Elevator to zero
    public void stopElevator()
    {
        motor1.set(0);
        motor2.set(0);
    }
    //Toggles the motor, if the function is called once, then it runs. If its called twice, it stops and so on. 
    public void toggleElevator(double power)
    {
        if (motorOn)
        {
            this.stopElevator;
        }
        else
        {
            this.moveElevator(power);
        }
    }



}