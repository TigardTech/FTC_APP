package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Direction;
import com.arinerron.ftc.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

        this.point(Direction.FORTYFIVE);

        write("Extending arm...");
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        this.getRobot().getServoArmJ().setPosition(0);
        while(timer.seconds() < 1.5) {
            try {
                Thread.sleep(10); // get rid of thread locking
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        this.getRobot().getServoArmJ().setPosition(0.5);

        write("Checking balls...");
        String color = isColor(this.getRobot().getColorSensor().red(), this.getRobot().getColorSensor().green(), this.getRobot().getColorSensor().blue(), "red") ? "red" : (isColor(this.getRobot().getColorSensor().red(), this.getRobot().getColorSensor().green(), this.getRobot().getColorSensor().blue(), "blue") ? "blue" : "");

        String wantedcolor = getColor().toLowerCase();

        write("Got " + color + ". Hitting " + wantedcolor + "...");

        int dir;

        if(color.equalsIgnoreCase(wantedcolor)) {
            dir = 1;
        } else {
            dir = -1; // ik redundant, oh well
        }

        timer.reset();
        this.drive(dir);
        while(timer.seconds() < 0.2) {
            try {
                Thread.sleep(10); // get rid of thread locking
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        timer.reset();
        this.getRobot().getServoArmJ().setPosition(1);
        while(timer.seconds() < 1) {
            try {
                Thread.sleep(10); // get rid of thread locking
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        this.getRobot().getServoArmJ().setPosition(0.5);

        timer.reset();
        this.drive(-dir);
        while(timer.seconds() < 0.35) {
            try {
                Thread.sleep(10); // get rid of thread locking
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        this.getRobot().reset();

        // -1 is towards the color, 1 is away from it

        timer.reset();

        write("And here we go...");

        this.getRobot().getServoRelic().setPosition(0.5); // stop

        write("Driving...");
        
        point(Direction.STRAIGHT);
        driveInches(getMultiplier() * 12 * 3);
        point(Direction.STRAIGHT);
        write("Done.");
    }

    public double getMultiplier() {
        return 1;
    }

    @Override
    public void repeat() {

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
