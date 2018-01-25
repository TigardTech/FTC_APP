package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Direction;
import com.arinerron.ftc.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Inch Test", group="Autonomous")
public class TestAutonomousMode extends OpMode {
    public TestAutonomousMode() {
        super();
    }

    @Override
    public void init() {
        super.init();

        if(this.getRobot().getColorSensor() != null)
            this.getRobot().getColorSensor().enableLed(true);

        write("Auto OpMode initialized.");
    }

    public String getColor() {
        return "red";
    }

    @Override
    public void run() {
        point(Direction.STRAIGHT);

        this.waitr(3);

        double inches = 12 * 3;

        double inchespertick = 129;//136.5//128.97;

        double distance = inches * inchespertick;

        double motor1 = this.getRobot().getMotor1().getDcMotor().getCurrentPosition();
        double motor2 = this.getRobot().getMotor2().getDcMotor().getCurrentPosition();
        double motor3 = this.getRobot().getMotor3().getDcMotor().getCurrentPosition();
        double motor4 = this.getRobot().getMotor4().getDcMotor().getCurrentPosition();

        motor1 = motor1 + distance; // - it's all good
        motor2 = motor2 + distance; // + it was getting lower
        motor3 = motor3 - distance; // +
        motor4 = motor4 - distance; // -

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        boolean conditionssatisfied = false;

        double multiplier = (double)(int)(distance > 0 ? 1 : -1);

        this.getRobot().reset();
        drive(0.5 * multiplier); // half speed
int time = 0;
        while(timer.seconds() < 10 && !conditionssatisfied) { // 10 sec timeout
            int conditions = 0;
time++;
            if(this.getRobot().getMotor1().getDcMotor().getCurrentPosition() >= motor1) {
                this.getRobot().getMotor1().reset();
                conditions++;
            }

            if(this.getRobot().getMotor2().getDcMotor().getCurrentPosition() >= motor2) {
                this.getRobot().getMotor2().reset();
                conditions++;
            }

            if(this.getRobot().getMotor3().getDcMotor().getCurrentPosition() <= motor3) {
                this.getRobot().getMotor3().reset();
                conditions++;
            }

            if(this.getRobot().getMotor4().getDcMotor().getCurrentPosition() <= motor4) {
                this.getRobot().getMotor4().reset();
                conditions++;
            }

if(time % 50000 == 0)
            write("debug", "con=" + conditions + " 1=" + this.getRobot().getMotor1().getDcMotor().getCurrentPosition() + " & 2=" + this.getRobot().getMotor2().getDcMotor().getCurrentPosition() + " 3=" + this.getRobot().getMotor3().getDcMotor().getCurrentPosition() + " & 4=" + this.getRobot().getMotor4().getDcMotor().getCurrentPosition());

            if(conditions >= 4) {
                conditionssatisfied = true;
            }
        }

        this.getRobot().reset();
    }

    public double getMultiplier() {
        return 1;
    }

    @Override
    public void repeat() {

    }
    private ElapsedTime timer2 = new ElapsedTime();
    private void waitr(double seconds) {
        timer2.reset();
        while(timer2.seconds() < seconds);
    }

    public static boolean isZero(double x) {
        return x < Constants.TRIGGER_THRESHOLD && x > -Constants.TRIGGER_THRESHOLD;
    }

    public static boolean isZero(double x, double threshold) {
        return x < threshold && x > -threshold;
    }

    public static double getAngle(double x, double y) {
        if(isZero(y) && isZero(y))
            return 0;
        return Math.toDegrees(1.5 * Math.PI - Math.atan2(y, x)) - 180; // supposed to be y,x
    }
}
