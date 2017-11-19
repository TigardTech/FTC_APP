package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Drive Forward", group="Iterative OpMode")
public class AutonomousMode extends OpMode {
    public AutonomousMode() {
        super();

        write("Driving OpMode initialized.");
    }

    @Override
    public void init() {
        super.init();

        write("Preparing to run Driving OpMode...");
    }

    @Override
    public void repeat() {

    }

    private boolean left = false, right = false, up = false, down = false;

    private final double[] straight /*0*/ = {1, 0.2, 0.7, 0.3}, ff /*1*/ = {0.6, 0.6, 0.3, 0.6}, ninety /*-1*/ = {0.3, 0.9, 0, 1};
    private int dir = 0;

    public void point(int direction) {
        if(direction == 1) {
            /*
             * _   _
             *
             * _   _
             *
             */
            this.getRobot().getServo1().setPosition(ninety[0]);
            this.getRobot().getServo2().setPosition(ninety[1]);
            this.getRobot().getServo3().setPosition(ninety[2]);
            this.getRobot().getServo4().setPosition(ninety[3]);
        } else if(direction == -1) {
            /*
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

    public void drive(double x) {
        /* dir: 0=straight, 1=ff, -1=90 */
        if (dir == 1) {
            // ff
            this.getRobot().getMotor1().setPower(x);
            this.getRobot().getMotor2().setPower(-x);
            this.getRobot().getMotor3().setPower(x);
            this.getRobot().getMotor4().setPower(-x);
        } else if (dir == -1) {
            // ninety = invert some of these   l a t e r  ...
            this.getRobot().getMotor1().setPower(-x);
            this.getRobot().getMotor2().setPower(-x); // inverted
            this.getRobot().getMotor3().setPower(x); // inverted
            this.getRobot().getMotor4().setPower(x);
        } else if (dir == 0) {
            // straight
            this.getRobot().getMotor1().setPower(-x);
            this.getRobot().getMotor2().setPower(x); // inverted
            this.getRobot().getMotor3().setPower(x); // inverted
            this.getRobot().getMotor4().setPower(-x);
        }
    }

    public void stop() {
        this.getRobot().getMotor1().setPower(0);
        this.getRobot().getMotor2().setPower(0);
        this.getRobot().getMotor3().setPower(0);
        this.getRobot().getMotor4().setPower(0);
    }

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

    public void armsOpen(boolean yes) {
        if(yes) {
            this.getRobot().getServoArm1().setPosition(0.25);
            this.getRobot().getServoArm2().setPosition(0.75);
        } else {
            this.getRobot().getServoArm1().setPosition(0.75);
            this.getRobot().getServoArm2().setPosition(0.25);
        }
    }

    private boolean pressed = false, holding = false, mode = true, apressed = false;

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void run() {
        write("OpMode running...");

        // open claws
        this.getRobot().getDriver().setServo("arm1", true);
        this.getRobot().getDriver().setServo("arm2", true);

        // drive to the safe zone for points
        point(Constants.DIRECTION_STRAIGHT);
        this.getRobot().getMotor1().setPower(-1);
        this.getRobot().getMotor2().setPower(1);
        this.getRobot().getMotor3().setPower(1);
        this.getRobot().getMotor4().setPower(-1);
        this.waitr(1.25);
        stop();
    }



    private void waitr(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
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
