package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MechanicsTest", group = "TeleOp")
public class TestTeleOpMode extends TeleOpMode {
    public TestTeleOpMode() {
        super();

        write("Test OpMode initialized.");
    }

    @Override
    public void init() {
        super.init();

        write("Preparing to run test OpMode...");
    }

    @Override
    public void run() {
        write("OpMode running...");
    }

    private boolean left = false, right = false, up = false, down = false;

    private boolean pressed = false, holding = false;

    @Override
    public void repeat() {
        double x = this.getGamepad().right_stick_x;
        double y = -this.getGamepad().right_stick_y;

        this.write("debug", "(" + x + ", " + y + "): " + ((double) ((int) ((double) getAngle(x, y) * 100)) / (double) 100) + " degrees");

        if(Double.isNaN(this.getRobot().getServo1().getPosition()))
            this.getRobot().getServo1().setPosition(0);

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

        if(this.getGamepad().x) {
            this.left = true;
        } else if(this.getGamepad().b) {
            this.right = true;
        } else {
            if(this.left && !this.getGamepad().x) {
                this.left = false;
                this.getRobot().getServo1().setPosition((double) this.getRobot().getServo1().getPosition() - (double) 0.1);
                this.getRobot().getServo2().setPosition((double) this.getRobot().getServo2().getPosition() - (double) 0.1);
                this.getRobot().getServo3().setPosition((double) this.getRobot().getServo3().getPosition() - (double) 0.1);
                this.getRobot().getServo4().setPosition((double) this.getRobot().getServo4().getPosition() - (double) 0.1);
            } else if(this.right && !this.getGamepad().b) {
                this.right = false;
                this.getRobot().getServo1().setPosition((double) this.getRobot().getServo1().getPosition() + (double) 0.1);
                this.getRobot().getServo2().setPosition((double) this.getRobot().getServo2().getPosition() + (double) 0.1);
                this.getRobot().getServo3().setPosition((double) this.getRobot().getServo3().getPosition() + (double) 0.1);
                this.getRobot().getServo4().setPosition((double) this.getRobot().getServo4().getPosition() + (double) 0.1);
            }
        }

        if(!this.isZero(y)) {
            this.getRobot().getMotor1().setPower(y);
            this.getRobot().getMotor2().setPower(y);
            this.getRobot().getMotor3().setPower(y);
            this.getRobot().getMotor4().setPower(y);
        } else {
            this.getRobot().getMotor1().reset();
            this.getRobot().getMotor2().reset();
            this.getRobot().getMotor3().reset();
            this.getRobot().getMotor4().reset();
        }

        if(this.getGamepad().left_bumper) {
            this.getRobot().getMotor1().reset();
            this.getRobot().getMotor2().reset();
            this.getRobot().getMotor3().reset();
            this.getRobot().getMotor4().reset();
            this.getRobot().getServo1().setPosition(0);
            this.getRobot().getServo2().setPosition(0);
            this.getRobot().getServo3().setPosition(0);
            this.getRobot().getServo4().setPosition(0);
        }
    }


    public static boolean isZero(double x) {
        return x < Constants.TRIGGER_THRESHOLD && x > -Constants.TRIGGER_THRESHOLD;
    }

    public static double getAngle(double x, double y) {
        if(isZero(x) || isZero(y))
            return 0;
        return Math.toDegrees(1.5 * Math.PI - Math.atan2(y, x)) - 180; // supposed to be y,x
    }
}
