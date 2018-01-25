package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Motor;
import com.arinerron.ftc.Servo;
import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
    private Servo serv = null;
    private Motor mot = null;
    public boolean pressed = false;

    @Override
    public void repeat() { // 0.3=closed 0.5=open
        double x = this.getGamepad().right_stick_x;
        double y = -this.getGamepad().right_stick_y;
        if(Double.isNaN(this.getRobot().getServoRelicGrabber().getPosition()))
            this.getRobot().getServoRelicGrabber().setPosition(0.5);
        if(this.getRobot().getServoRelicGrabber() != null && !Double.isNaN(this.getRobot().getServoRelicGrabber().getPosition()))
            this.write("debug: " + this.getRobot().getServoRelicGrabber().getPosition());



        if(this.getRobot() != null) {
            if(this.getGamepad().x) {
                if(!pressed)
                    this.getRobot().getServoRelicGrabber().setPosition(this.getRobot().getServoRelicGrabber().getPosition() + 0.1);
                pressed = true;
            } else if(this.getGamepad().b) {
                if(!pressed)
                    this.getRobot().getServoRelicGrabber().setPosition(this.getRobot().getServoRelicGrabber().getPosition() - 0.1);
                pressed = true;
            } else {
                pressed = false;
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
