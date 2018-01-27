package com.arinerron.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
public abstract class OpMode extends com.qualcomm.robotcore.eventloop.opmode.OpMode {
    public static String TAG = "OpMode";

    private HardwareMap map = null;
    private String name = "OpMode";
    private Robot robot = null;
    private ElapsedTime timer = new ElapsedTime();

    public OpMode() {

    }

    public void startTimer(double secs) {
        timer.reset();
    }

    public double getTimer() {
        return timer.seconds();
    }

    public boolean isTimerDone(double secs) {
        return this.getTimer() >= secs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract void run();
    public abstract void repeat();

    @Override
    public void init() {
        this.map = hardwareMap;
        this.robot = new Robot(this);

        if(this.getRobot().getColorSensor() != null)
            this.getRobot().getColorSensor().enableLed(true);
    }

    @Override
    public void loop() {
        this.repeat(); // DO NOT block!
    }

    @Override
    public void stop() {}

    @Override
    public void start() {
        this.robot = new Robot(this);
        new Thread(new Runnable() { public void run() {
            OpMode.this.run();
        }}).start();
    }

    public HardwareMap getHardwareMap() {
        return this.map;
    }

    public void write(String data) {
        this.write("INFO", data);
    }

    public void write(String tag, String data) {
        Log.write(tag, data);
        telemetry.addData(Constants.ROBOT_NAME, "\n" + Log.getString());
        telemetry.update();
    }

    public void write(String data, boolean asdf) {
        telemetry.addData(Constants.ROBOT_NAME, data);
        telemetry.update();
    }

    public Robot getRobot() {
        return this.robot;
    }

    public DcMotor getMotor(String name) {
        return this.getHardwareMap().dcMotor.get(name);
    }

    public Servo getServo(String name) {
        return this.getHardwareMap().servo.get(name);
    }

    public ColorSensor getColorSensor(String name) {
        return this.getHardwareMap().colorSensor.get(name);
    }

    public GyroSensor getGyroSensor(String name) {
        return this.getHardwareMap().gyroSensor.get(name);
    }

    public boolean isColor(double r, double g, double b, String name) {
        name = name.toLowerCase(); // get the lower case version of the name

        switch(name) {
            case "red":
                if(r >= Constants.COLOR_THRESHOLD)
                    return true; // it is red
                break;
            case "green":
                if(g >= Constants.COLOR_THRESHOLD)
                    return true; // it is green
                break;
            case "blue":
                if(b >= Constants.COLOR_THRESHOLD)
                    return true; // it is blue
                break;
            case "white":
                if(r > Constants.COLOR_THRESHOLD && g > Constants.COLOR_THRESHOLD && b > Constants.COLOR_THRESHOLD)
                    return true;
                break;
            case "black":
                if(r < Constants.COLOR_THRESHOLD && g < Constants.COLOR_THRESHOLD && b < Constants.COLOR_THRESHOLD)
                    return true;
                break;
            default:
                // status("Unknown color (" + r + ", " + g + ", " + b + ")."); // unknown color; will get annoying if it is spamming console.
                return false;
        }

        return false; // unknown color
    }

    private final double[] straight /*0*/ = {1, 0.9 /*0.2*/, 0.7, 0.3}, ff /*1*/ = {0.6, 0.5/*0.6*/, 0.3, 0.6}, ninety /*-1*/ = {0.3, 0.15/*0.9*/, 0, 1};
    public Direction dir = Direction.STRAIGHT;

    public void check() {
        if(this.getRobot().getServo1() != null && Double.isNaN(this.getRobot().getServo1().getPosition()))
            this.getRobot().getServo1().setPosition(straight[0]);
        if(this.getRobot().getServo2() != null && Double.isNaN(this.getRobot().getServo2().getPosition()))
            this.getRobot().getServo2().setPosition(straight[1]);
        if(this.getRobot().getServo3() != null && Double.isNaN(this.getRobot().getServo3().getPosition()))
            this.getRobot().getServo3().setPosition(straight[2]);
        if(this.getRobot().getServo4() != null && Double.isNaN(this.getRobot().getServo4().getPosition()))
            this.getRobot().getServo4().setPosition(straight[3]);
    }

    public void point(Direction direction) {
        if(direction == Direction.NINETY) {
            /*
             * wheel positions:
             * _   _
             *
             * _   _
             *
             */
            this.getRobot().getServo1().setPosition(ninety[0]);
            this.getRobot().getServo2().setPosition(ninety[1]);
            this.getRobot().getServo3().setPosition(ninety[2]);
            this.getRobot().getServo4().setPosition(ninety[3]);
        } else if(direction == Direction.FORTYFIVE) {
            /*
             * wheel positions:
             * /   \
             *
             * \   /
             *
             */
            this.getRobot().getServo1().setPosition(ff[0]);
            this.getRobot().getServo2().setPosition(ff[1]);
            this.getRobot().getServo3().setPosition(ff[2]);
            this.getRobot().getServo4().setPosition(ff[3]);
        } else {
            /*
             * wheel positions:
             * |   |
             *
             * |   |
             *
             */
            this.getRobot().getServo1().setPosition(straight[0]);
            this.getRobot().getServo2().setPosition(straight[1]);
            this.getRobot().getServo3().setPosition(straight[2]);
            this.getRobot().getServo4().setPosition(straight[3]);
        }

        dir = direction;
    }

    public void drive(double power) {
        if (dir == Direction.FORTYFIVE) {
            // forty five degrees (rotate)
            this.getRobot().getMotor1().setPower(power);
            this.getRobot().getMotor2().setPower(power);
            this.getRobot().getMotor3().setPower(-power);
            this.getRobot().getMotor4().setPower(-power);
        } else if (dir == Direction.NINETY) {
            // ninety (horizontal)
            this.getRobot().getMotor1().setPower(power);
            this.getRobot().getMotor2().setPower(-power);
            this.getRobot().getMotor3().setPower(power);
            this.getRobot().getMotor4().setPower(-power);
        } else if (dir == Direction.STRAIGHT) {
            // straight (vertical)
            this.getRobot().getMotor1().setPower(-power);
            this.getRobot().getMotor2().setPower(-power);
            this.getRobot().getMotor3().setPower(-power);
            this.getRobot().getMotor4().setPower(-power);
        }
    }

    public boolean driveInches(double inches) {
        double inchespertick = 136.5;//128.97;

        double distance = inches * inchespertick;

        //double motor1 = this.getRobot().getMotor1().getDcMotor().getCurrentPosition();
        double motor2 = this.getRobot().getMotor2().getDcMotor().getCurrentPosition();
        double motor3 = this.getRobot().getMotor3().getDcMotor().getCurrentPosition();
        //double motor4 = this.getRobot().getMotor4().getDcMotor().getCurrentPosition();

        //motor1 = motor1 + distance; // - it's all good
        motor2 = motor2 - distance; // +
        motor3 = motor3 + distance; // +
        //motor4 = motor4 - distance; // -

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        boolean conditionssatisfied = false;

        double multiplier = (double)(int)(distance > 0 ? 1 : -1);

        this.getRobot().reset();
        drive(0.5 * multiplier); // half speed

        while(timer.seconds() < 10 && !conditionssatisfied) { // 10 sec timeout
            int conditions = 0;

            /*if(this.getRobot().getMotor1().getDcMotor().getCurrentPosition() >= motor1) { // it's all good
                this.getRobot().getMotor1().reset();
                conditions++;
            }*/

            if(this.getRobot().getMotor2().getDcMotor().getCurrentPosition() <= motor2) {
                this.getRobot().getMotor2().reset();
                conditions++;
            }

            if(this.getRobot().getMotor3().getDcMotor().getCurrentPosition() >= motor3) {
                this.getRobot().getMotor3().reset();
                conditions++;
            }

            /*if(this.getRobot().getMotor4().getDcMotor().getCurrentPosition() <= motor4) {
                this.getRobot().getMotor4().reset();
                conditions++;
            }*/

            if(conditions >= /*4*/2) {
                conditionssatisfied = true;
            }
        }

        this.getRobot().reset();

        return conditionssatisfied;
    }

    private Position armpos = Position.STOPPED;

    /* sets the position of the claw */
    protected void setClaw(Position pos) {
        if(pos == Position.IN) {
            // pull in
            this.getRobot().getServoArm1().setPosition(0);
            this.getRobot().getServoArm2().setPosition(1);
        } else if(pos == Position.OUT) {
            // push out
            this.getRobot().getServoArm1().setPosition(1);
            this.getRobot().getServoArm2().setPosition(0);
        } else {
            // stop servos
            this.getRobot().getServoArm1().setPosition(0.5);
            this.getRobot().getServoArm2().setPosition(0.5);
        }

        // save the position to "history"
        this.armpos = pos;
    }

    public void setRelicGrabberOpen(boolean open) {
        if(open) {
            this.getRobot().getServoRelicGrabber().setPosition(0.5);
        } else {
            this.getRobot().getServoRelicGrabber().setPosition(0.3);
        }
    }
}
