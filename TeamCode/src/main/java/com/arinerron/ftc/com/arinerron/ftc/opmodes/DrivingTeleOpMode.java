package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Motor;
import com.arinerron.ftc.Servo;
import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop", group = "TeleOp")
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
        this.getRobot().getDriver().setServo("arm1", yes);
        this.getRobot().getDriver().setServo("arm2", yes);
    }

    private boolean pressed = false, holding = false, mode = false, pressedl = false, holdingl = false, apressed = false, rpressed = false;
    private boolean armtop = true, armbottom = true;

    @Override
    public void repeat() {
        double x = this.getGamepad().right_stick_x;
        double y = -this.getGamepad().right_stick_y;
        double x1 = this.getGamepad().left_stick_x;
        double y1 = -this.getGamepad().left_stick_y;

        // debug stuff
        this.write("\nMode: " + (mode ? "advanced" : "simple") + "\n" +
                "Stick: (" + ((double)Math.round(x1 * 10d) / 10d) + ", " + ((double)Math.round(y1 * 10d) / 10d) + ") & (" + ((double)Math.round(x * 10d) / 10d) + ", " + ((double)Math.round(y * 10d) / 10d) + ")\n" +
                "Motors: " + ((!isZero(this.getRobot().getMotor1().getPower()) || !isZero(this.getRobot().getMotor2().getPower()) || !isZero(this.getRobot().getMotor3().getPower()) || !isZero(this.getRobot().getMotor4().getPower())) ? "active" : "inactive") + "\n" +
                "Arm: " + (holding ? "closed" : "opened") + "\n" +
                "Direction: " + (dir == 0 ? "straight" : (dir == 1 ? "forty five" : "horizontal")), true);

        // make sure servos & motors aren't dying
        check();

        // change modes from simple to advanced or vice versa
        if(this.getGamepad().x) {
            apressed = true;
        } else {
            if(apressed) {
                stop();
                mode = !mode;
            }

            apressed = false;
        }

        if(this.getRobot() != null) {
            // push out or pull in arms
            if (this.getGamepad().a) {
                if (!pressedl) {
                    pressedl = true;

                    // could optimize ik
                    if (holdingl) {
                        // open arms
                        this.getRobot().getDriver().setServo("arme", true);
                        holdingl = false;
                    } else {
                        // close arms
                        this.getRobot().getDriver().setServo("arme", false);
                        holdingl = true;
                    }
                }
            } else {
                pressedl = false;
            }

            // stop all motors if pressed
            if(this.getGamepad().b) {
                stop();
                this.getRobot().getMotorArm().setPower(0);
            }

            if(mode) {
                if (this.getGamepad().dpad_left) {
                    point(Constants.DIRECTION_NINETY); // previously known as "90"
                } else if (this.getGamepad().dpad_up) {
                    this.getRobot().reset();
                } else if (this.getGamepad().dpad_right) {
                    point(Constants.DIRECTION_FORTYFIVE);
                } else if (this.getGamepad().dpad_down) {
                    point(Constants.DIRECTION_STRAIGHT);
                }

                if (!isZero(y)) {
                    drive(y);
                } else if (!isZero(x)) {
                    drive(x);
                } else {
                    stop();
                }
            } else {
                /* dir: 0=straight, 1=ff, -1=90 */

                boolean adjusting = false;

                // arm stuff and small adjustment stuff
                if (this.getGamepad().dpad_left) {
                    adjusting = true;
                    if (dir != 1 && !rpressed) {
                        rpressed = true;
                        point(Constants.DIRECTION_FORTYFIVE);
                    }
                    drive(-0.2);
                } else if (this.getGamepad().dpad_up) {
                    if(!rpressed) {
                        armtop = !armtop;
                        rpressed = true;
                        this.getRobot().getDriver().setServo("arm1", armtop);
                    }
                } else if (this.getGamepad().dpad_right) {
                    adjusting = true;
                    if (dir != 1 && !rpressed) {
                        rpressed = true;
                        point(Constants.DIRECTION_FORTYFIVE);
                    }
                    drive(0.2);
                } else if (this.getGamepad().dpad_down) {
                    if(!rpressed) {
                        armbottom = !armbottom;
                        rpressed = true;
                        this.getRobot().getDriver().setServo("arm2", armbottom);
                    }
                } else {
                    rpressed = false;
                    if(adjusting) {
                        adjusting = false;
                        drive(0);
                    }
                }

                // other mode
                if(!isZero(y, 0.3)) {
                    // straight
                    if(dir != 0) {
                        point(Constants.DIRECTION_STRAIGHT);
                    }

                    drive(y);

                } else if(!isZero(x, 0.3)) {
                    // 90
                    if(dir != -1) {
                        point(Constants.DIRECTION_NINETY);
                    }

                    drive(x);

                } else if(!isZero(x1)) {
                    // ff
                    if (dir != 1) {
                        point(Constants.DIRECTION_FORTYFIVE);
                    }

                    drive(x1);
                } else {
                    // slow down
                    stop();
                }
            }
        }

        // open or close arms
        if (this.getGamepad().right_bumper) {
            if (!pressed) {
                pressed = true;

                // could optimize ik
                if (holding) {
                    // open arms
                    armsOpen(true);
                    holding = false;
                } else {
                    // close arms
                    armsOpen(false);
                    holding = true;
                }
            }
        } else
            pressed = false;

        // move motor arm around
        if (this.getGamepad().left_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorArm().setPower(-this.getGamepad().left_trigger / 2);
        } else if (this.getGamepad().right_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorArm().setPower(this.getGamepad().right_trigger / 2);
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
