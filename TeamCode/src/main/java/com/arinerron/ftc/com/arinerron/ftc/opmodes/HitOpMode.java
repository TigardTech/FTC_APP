package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Direction;
import com.arinerron.ftc.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
public class HitOpMode extends OpMode {
    public HitOpMode() {
        super();
    }

    @Override
    public void init() {
        super.init();

        if(this.getRobot().getColorSensor() != null)
            this.getRobot().getColorSensor().enableLed(true);

        write("Driving OpMode initialized.");
    }

    public String getColor() {
        return "red";
    }

    @Override
    public void run() {
        this.getRobot().getServoArm1().setPosition(0.5);
        this.getRobot().getServoArm2().setPosition(0.5);

        point(Direction.STRAIGHT);

        write("Extending arm...");
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        this.getRobot().getServoArmJ().setPosition(1); // extends arm
        this.waitr(1.25);
        this.getRobot().getServoArmJ().setPosition(0.5); // stop extending

        write("Checking balls..."); // see which color it is looking at
        String color = isColor(this.getRobot().getColorSensor().red(), this.getRobot().getColorSensor().green(), this.getRobot().getColorSensor().blue(), "red") ? "red" : (isColor(this.getRobot().getColorSensor().red(), this.getRobot().getColorSensor().green(), this.getRobot().getColorSensor().blue(), "blue") ? "blue" : "");

        String wantedcolor = getColor().toLowerCase(); // sees if the color matches

        if(color.length() != 0)
            write("Color sensor sees " + color + ". Hitting " + wantedcolor + "...");
        else
            write("No color identified. Rotating...");

        // wait 4 seconds for debug
        this.waitr(4);
        // stop waiting

        // if it sees nothing
        if(color.equalsIgnoreCase("")) {
            this.drive(-0.05); // start rotating slowly for 5 seconds
            timer.reset();
            while(timer.seconds() < 5 && color.length() == 0) {
                color = isColor(this.getRobot().getColorSensor().red(), this.getRobot().getColorSensor().green(), this.getRobot().getColorSensor().blue(), "red") ? "red" : (isColor(this.getRobot().getColorSensor().red(), this.getRobot().getColorSensor().green(), this.getRobot().getColorSensor().blue(), "blue") ? "blue" : "");
            }
            this.getRobot().reset(); // stop when it sees something or if more than 5 secs, whichever comes first

            write("Color sensor sees " + color + ". Hitting " + wantedcolor + "...");
        }

        int dir; // the direction variable

        if(color.equalsIgnoreCase(wantedcolor)) {
            dir = -1; // if the colors are the same, rotate 1 direction
        } else {
            dir = 1; // ik redundant, oh well. else, return other direction
        }

        this.drive(dir / 2.5); // drive for 0.5 secs
        this.waitr(0.5);
        this.drive(-dir / 2.5); // drive for 0.5 secs
        this.waitr(0.5);
        this.getRobot().getMotor1().setPower(0);
        this.getRobot().getMotor2().setPower(0);
        this.getRobot().getMotor3().setPower(0);
        this.getRobot().getMotor4().setPower(0);
        this.waitr(0.5);
        this.getRobot().getServoArmJ().setPosition(0); // push servo up
        this.point(Direction.NINETY);
        this.drive(-0.5);
        this.waitr(0.25);
        this.getRobot().getMotor1().setPower(0);
        this.getRobot().getMotor2().setPower(0);
        this.getRobot().getMotor3().setPower(0);
        this.getRobot().getMotor4().setPower(0);

        this.waitr(4);
        this.drive(0.5);
        this.waitr(0.25);
        this.getRobot().getMotor1().setPower(0);
        this.getRobot().getMotor2().setPower(0);
        this.getRobot().getMotor3().setPower(0);
        this.getRobot().getMotor4().setPower(0);
        this.waitr(1);
        this.point(Direction.STRAIGHT);
        this.waitr(1);

        // -1 is towards the color, 1 is away from it
/*
        timer.reset();

        write("And here we go...");

        this.getRobot().getServoRelic().setPosition(0.5); // stop

        write("Driving...");
        
        point(Direction.STRAIGHT);
        this.waitr(0.25);
        stop();*/

        driveInches(28);
        this.getRobot().getServoArmJ().setPosition(0.5); // end arm movement



        write("Done.");
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
