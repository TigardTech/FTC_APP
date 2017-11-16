package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Motor;
import com.arinerron.ftc.Servo;
import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp(name = "ServoTest", group = "TeleOp")
@Disabled
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
    private Servo serv = null;
    private Motor mot = null;

    @Override
    public void repeat() {
        double x = this.getGamepad().right_stick_x;
        double y = -this.getGamepad().right_stick_y;

        this.write("debug", "(" + x + ", " + y + "): " + ((double) ((int) ((double) getAngle(x, y) * 100)) / (double) 100) + " degrees");

        if(serv != null && Double.isNaN(serv.getPosition()))
            serv.setPosition(0);



        if(this.getRobot() != null) {
            if(this.getGamepad().y) {
                mot.reset();
                this.getRobot().getMotor1().reset();
                this.getRobot().getMotor2().reset();
                this.getRobot().getMotor3().reset();
                this.getRobot().getMotor4().reset();
            }

            if(mot != null)
                write("servo" + serv.getPosition() + "    &    center: " + serv.getCenter() + " and motor:" + mot.getPower());

            if (this.getGamepad().dpad_left) {
                serv = this.getRobot().getServo1();
                mot = this.getRobot().getMotor1();
            } else if (this.getGamepad().dpad_up) {
                serv = this.getRobot().getServo2();
                mot = this.getRobot().getMotor2();
            } else if (this.getGamepad().dpad_right) {
                serv = this.getRobot().getServo3();
                mot = this.getRobot().getMotor3();
            } else if (this.getGamepad().dpad_down) {
                serv = this.getRobot().getServo4();
                mot = this.getRobot().getMotor4();
            }
        }

        if(serv != null) {
            if (this.getGamepad().x) {
                this.left = true;
            } else if (this.getGamepad().b) {
                this.right = true;
            } else {
                if (this.left && !this.getGamepad().x) {
                    this.left = false;
                    serv.setPosition((double) serv.getPosition() - (double) 0.1);
                } else if (this.right && !this.getGamepad().b) {
                    this.right = false;
                    serv.setPosition((double) serv.getPosition() + (double) 0.1);
                }
            }
        }

        if(mot != null) {
            if (this.getGamepad().y) {
                this.up = true;
            } else if (this.getGamepad().a) {
                this.down = true;
            } else if (this.getGamepad().right_bumper) {
                this.up = false;
                this.down = false;
                this.mot.reset();
            } else {
                if (this.up && !this.getGamepad().y) {
                    this.up = false;
                    this.mot.setPower(1.0);
                } else if (this.down && !this.getGamepad().a) {
                    this.down = false;
                    this.mot.setPower(-1.0);
                }
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
