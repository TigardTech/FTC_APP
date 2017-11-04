package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Motor;
import com.arinerron.ftc.Servo;
import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "DrivingOpMode", group = "TeleOp")
public class DrivingTeleOpMode extends TeleOpMode {
    public DrivingTeleOpMode() {
        super();

        write("Driving OpMode initialized.");
    }

    @Override
    public void init() {
        super.init();

        write("Preparing to run Driving OpMode...");
    }

    @Override
    public void run() {
        write("OpMode running...");
    }

    private boolean left = false, right = false, up = false, down = false;

    private final double[] straight /*0*/ = {1, 0.2, 0.7, 0.3}, ff /*1*/ = {0.6, 0.6, 0.3, 0.6}, ninety /*-1*/ = {0.3, 0.9, 0, 1};
    private int dir = 0;

    public void pointStraight() {
        /*
         * |   |
         *
         * |   |
         *
         */

        dir = 0;

        this.getRobot().getServo1().setPosition(straight[0]);
        this.getRobot().getServo2().setPosition(straight[1]);
        this.getRobot().getServo3().setPosition(straight[2]);
        this.getRobot().getServo4().setPosition(straight[3]);
    }

    public void pointFortyFive() {
        /*
         * /   \
         *
         * \   /
         *
         */

        dir = 1;

        this.getRobot().getServo1().setPosition(ff[0]);
        this.getRobot().getServo2().setPosition(ff[1]);
        this.getRobot().getServo3().setPosition(ff[2]);
        this.getRobot().getServo4().setPosition(ff[3]);
    }

    public void pointOppositeStraight() { // aka "90"
        /*
         * _   _
         *
         * _   _
         *
         */

        dir = -1;

        this.getRobot().getServo1().setPosition(ninety[0]);
        this.getRobot().getServo2().setPosition(ninety[1]);
        this.getRobot().getServo3().setPosition(ninety[2]);
        this.getRobot().getServo4().setPosition(ninety[3]);
    }

    private boolean pressed = false, holding = false, mode = true, apressed = false;

    @Override
    public void repeat() {
        double x = this.getGamepad().right_stick_x;
        double y = -this.getGamepad().right_stick_y;
        double x1 = this.getGamepad().left_stick_x;
        double y1 = -this.getGamepad().left_stick_y;

        this.write("Mode: " + (mode ? "advanced" : "simple") + "\n" +
                "Stick: (" + ((double)Math.round(x1 * 10d) / 10d) + ", " + ((double)Math.round(y1 * 10d) / 10d) + ") & (" + ((double)Math.round(x * 10d) / 10d) + ", " + ((double)Math.round(y * 10d) / 10d) + ")\n" +
                "Motors: " + ((!isZero(this.getRobot().getMotor1().getPower()) || !isZero(this.getRobot().getMotor2().getPower()) || !isZero(this.getRobot().getMotor3().getPower()) || !isZero(this.getRobot().getMotor4().getPower())) ? "active" : "inactive") + "\n" +
                "Arm: " + (holding ? "closed" : "opened") + "\n" +
                "Direction: " + (dir == 0 ? "straight" : (dir == 1 ? "forty five" : "horizontal")), true);

        //this.write("debug", "(" + x + ", " + y + "): " + ((double) ((int) ((double) getAngle(x, y) * 100)) / (double) 100) + " degrees");

        if(this.getRobot().getServo1() != null && Double.isNaN(this.getRobot().getServo1().getPosition()))
            this.getRobot().getServo1().setPosition(straight[0]);
        if(this.getRobot().getServo2() != null && Double.isNaN(this.getRobot().getServo2().getPosition()))
            this.getRobot().getServo2().setPosition(straight[1]);
        if(this.getRobot().getServo3() != null && Double.isNaN(this.getRobot().getServo3().getPosition()))
            this.getRobot().getServo3().setPosition(straight[2]);
        if(this.getRobot().getServo4() != null && Double.isNaN(this.getRobot().getServo4().getPosition()))
            this.getRobot().getServo4().setPosition(straight[3]);

        if(this.getGamepad().x) {
            apressed = true;
        } else {
            if(apressed) {
                this.getRobot().getMotor1().setPower(0);
                this.getRobot().getMotor2().setPower(0);
                this.getRobot().getMotor3().setPower(0);
                this.getRobot().getMotor4().setPower(0);
                mode = !mode;
            }

            apressed = false;
        }

        /* dir: 0=straight, 1=ff, -1=90 */

        if(this.getRobot() != null) {
            if(mode) {
                if (this.getGamepad().dpad_left) {
                    pointOppositeStraight(); // previously known as "90"
                } else if (this.getGamepad().dpad_up) {
                    this.getRobot().reset();
                } else if (this.getGamepad().dpad_right) {
                    pointFortyFive();
                } else if (this.getGamepad().dpad_down) {
                    pointStraight();
                }

                if (!isZero(y)) {
                    if (dir == 0) {
                        // straight
                        this.getRobot().getMotor1().setPower(-y);
                        this.getRobot().getMotor2().setPower(y); // inverted
                        this.getRobot().getMotor3().setPower(y); // inverted
                        this.getRobot().getMotor4().setPower(-y);
                    }
                } else if (!isZero(x)) {
                    if (dir == 1) {
                        // ff
                        this.getRobot().getMotor1().setPower(x);
                        this.getRobot().getMotor2().setPower(x);
                        this.getRobot().getMotor3().setPower(-x);
                        this.getRobot().getMotor4().setPower(-x);
                    } else if (dir == -1) {
                        // ninety = invert some of these   l a t e r  ...
                        this.getRobot().getMotor1().setPower(x);
                        this.getRobot().getMotor2().setPower(-x); // inverted
                        this.getRobot().getMotor3().setPower(x); // inverted
                        this.getRobot().getMotor4().setPower(-x);
                    }
                } else {
                    this.getRobot().getMotor1().setPower(0);
                    this.getRobot().getMotor2().setPower(0);
                    this.getRobot().getMotor3().setPower(0);
                    this.getRobot().getMotor4().setPower(0);
                }
            } else {
                /* dir: 0=straight, 1=ff, -1=90 */

                // other mode
                if(!isZero(y, 0.3)) {
                    // straight
                    if(dir != 0) {
                        pointStraight();
                    }

                    this.getRobot().getMotor1().setPower(-y);
                    this.getRobot().getMotor2().setPower(y); // inverted
                    this.getRobot().getMotor3().setPower(y); // inverted
                    this.getRobot().getMotor4().setPower(-y);

                } else if(!isZero(x, 0.3)) {
                    // 90
                    if(dir != -1) {
                        pointOppositeStraight();
                    }

                    this.getRobot().getMotor1().setPower(x);
                    this.getRobot().getMotor2().setPower(-x); // inverted
                    this.getRobot().getMotor3().setPower(x); // inverted
                    this.getRobot().getMotor4().setPower(-x);

                } else if(!isZero(x1)) {
                    // ff
                    if (dir != 1) {
                        pointFortyFive();
                    }

                    this.getRobot().getMotor1().setPower(x1);
                    this.getRobot().getMotor2().setPower(x1);
                    this.getRobot().getMotor3().setPower(-x1);
                    this.getRobot().getMotor4().setPower(-x1);
                } else {
                    // slow down
                    this.getRobot().getMotor1().setPower(0);
                    this.getRobot().getMotor2().setPower(0);
                    this.getRobot().getMotor3().setPower(0);
                    this.getRobot().getMotor4().setPower(0);
                }
            }
        }

        if (this.getGamepad().right_bumper) {
            if (!pressed) {
                pressed = true;

                /*
                 * NOTE:
                 * When closing, give 0.25 space so it doesn't freak out
                 */
                if (holding) {
                    // open arms
                    this.getRobot().getServoArm1().setPosition(0.25);
                    this.getRobot().getServoArm2().setPosition(0.75);
                    holding = false;
                } else {
                    // close arms
                    this.getRobot().getServoArm1().setPosition(0.75);
                    this.getRobot().getServoArm2().setPosition(0.25);
                    holding = true;
                }
            }
        } else
            pressed = false;

        if (this.getGamepad().left_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorArm().setPower(-this.getGamepad().left_trigger / 2);
        } else if (this.getGamepad().right_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorArm().setPower(this.getGamepad().right_trigger);
        } else {
            this.getRobot().getMotorArm().setPower(0);
        }
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
