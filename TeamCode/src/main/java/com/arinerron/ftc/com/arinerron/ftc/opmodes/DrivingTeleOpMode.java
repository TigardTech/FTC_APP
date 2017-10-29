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

    private final double[] straight = {1, 0.2, 0.7, 0.3}, ff = {0.6, 0.6, 0.3, 0.6}, ninety = {0.3, 0.9, 0, 1};

    public void pointStraight() {
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

    public void pointFortyFive() {
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
    }

    public void pointOppositeStraight() { // aka "90"
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
    }

    private boolean pressed = false, holding = false;

    @Override
    public void repeat() {
        double x = this.getGamepad().right_stick_x;
        double y = -this.getGamepad().right_stick_y;

        //this.write("debug", "(" + x + ", " + y + "): " + ((double) ((int) ((double) getAngle(x, y) * 100)) / (double) 100) + " degrees");

        if(this.getRobot().getServo1() != null && Double.isNaN(this.getRobot().getServo1().getPosition()))
            this.getRobot().getServo1().setPosition(straight[0]);
        if(this.getRobot().getServo2() != null && Double.isNaN(this.getRobot().getServo2().getPosition()))
            this.getRobot().getServo2().setPosition(straight[1]);
        if(this.getRobot().getServo3() != null && Double.isNaN(this.getRobot().getServo3().getPosition()))
            this.getRobot().getServo3().setPosition(straight[2]);
        if(this.getRobot().getServo4() != null && Double.isNaN(this.getRobot().getServo4().getPosition()))
            this.getRobot().getServo4().setPosition(straight[3]);

        if(this.getRobot() != null) {
            if (this.getGamepad().dpad_left) {
                pointOppositeStraight(); // previously known as "90"
            } else if (this.getGamepad().dpad_up) {
                this.getRobot().reset();
            } else if (this.getGamepad().dpad_right) {
                pointFortyFive();
            } else if (this.getGamepad().dpad_down) {
                pointStraight();
            }

            if(!isZero(y)) {
                this.getRobot().getMotor1().setPower(y);
                this.getRobot().getMotor2().setPower(y);
                this.getRobot().getMotor3().setPower(y);
                this.getRobot().getMotor4().setPower(y);
            } else {
                this.getRobot().getMotor1().setPower(0);
                this.getRobot().getMotor2().setPower(0);
                this.getRobot().getMotor3().setPower(0);
                this.getRobot().getMotor4().setPower(0);
            }

            if(this.getGamepad().right_bumper) {
                if(!pressed) {
                    pressed = true;

                    if(holding) {
                        this.getRobot().getServoArm1().setPosition(0);
                        this.getRobot().getServoArm2().setPosition(1);
                        holding = false;
                    } else {
                        this.getRobot().getServoArm1().setPosition(1);
                        this.getRobot().getServoArm2().setPosition(0);
                        holding = true;
                    }
                }
            } else
                pressed = false;

            if(this.getGamepad().left_trigger > Constants.TRIGGER_THRESHOLD) {
                this.getRobot().getMotorArm().setPower(-this.getGamepad().left_trigger);
            } else if(this.getGamepad().right_trigger > Constants.TRIGGER_THRESHOLD) {
                this.getRobot().getMotorArm().setPower(-this.getGamepad().right_trigger);
            } else {
                this.getRobot().getMotorArm().setPower(0);
            }
        }
    }


    public static boolean isZero(double x) {
        return x < Constants.TRIGGER_THRESHOLD && x > -Constants.TRIGGER_THRESHOLD;
    }

    public static double getAngle(double x, double y) {
        if(isZero(y) && isZero(y))
            return 0;
        return Math.toDegrees(1.5 * Math.PI - Math.atan2(y, x)) - 180; // supposed to be y,x
    }
}
