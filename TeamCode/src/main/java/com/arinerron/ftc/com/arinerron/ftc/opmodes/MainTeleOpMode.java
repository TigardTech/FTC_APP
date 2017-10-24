package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ServoTest", group = "TeleOp")
public class MainTeleOpMode extends TeleOpMode {
    public MainTeleOpMode() {
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

    @Override
    public void repeat() {
        double x = this.getGamepad().right_stick_x;
        double y = -this.getGamepad().right_stick_y;

        this.write("debug", "(" + x + ", " + y + "): " + ((double) ((int) ((double) getAngle(x, y) * 100)) / (double) 100) + " degrees");

        if(Double.isNaN(this.getRobot().getServo1().getPosition()))
            this.getRobot().getServo1().setPosition(0);

        // write("Servo position: " + this.getRobot().getServo1().getPosition() + "    &    center: " + this.getRobot().getServo1().getCenter());

        if(this.getGamepad().x) {
            this.left = true;
        } else if(this.getGamepad().b) {
            this.right = true;
        } else {
            if(this.left && !this.getGamepad().x) {
                this.left = false;
                this.getRobot().getServo1().setPosition((double) this.getRobot().getServo1().getPosition() - (double) 0.1);
            } else if(this.right && !this.getGamepad().b) {
                this.right = false;
                this.getRobot().getServo1().setPosition((double) this.getRobot().getServo1().getPosition() + (double) 0.1);
            }
        }

        if(this.getGamepad().y) {
            this.up = true;
        } else if(this.getGamepad().a) {
            this.down = true;
        } else if(this.getGamepad().right_bumper) {
            this.up = false;
            this.down = false;
            this.getRobot().getMotor1().reset();
        } else {
            if(this.up && !this.getGamepad().y) {
                this.up = false;
                this.getRobot().getMotor1().setPower(1.0);
            } else if(this.down && !this.getGamepad().a) {
                this.down = false;
                this.getRobot().getMotor1().setPower(-1.0);
            }
        }
    }


    public static boolean isZero(double x, double y) {
        return x < Constants.TRIGGER_THRESHOLD && x > -Constants.TRIGGER_THRESHOLD && y < Constants.TRIGGER_THRESHOLD && y > -Constants.TRIGGER_THRESHOLD;
    }

    public static double getAngle(double x, double y) {
        if(isZero(x, y))
            return 0;
        return Math.toDegrees(1.5 * Math.PI - Math.atan2(y, x)) - 180; // supposed to be y,x
    }
}
