package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Test TeleOp", group = "TeleOp")
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

    private boolean left = false, right = false;

    @Override
    public void repeat() {
        if(Double.isNaN(this.getRobot().getMiddleServo().getPosition()))
            this.getRobot().getMiddleServo().setPosition(0);

        write("Servo position: " + this.getRobot().getMiddleServo().getPosition() + "    &    center: " + this.getRobot().getMiddleServo().getCenter());

        if(this.getGamepad().x) {
            this.left = true;
        } else if(this.getGamepad().b) {
            this.right = true;
        } else {
            if(this./*politically*/left && !this.getGamepad().x) {
                this.left = false;
                this.getRobot().getMiddleServo().setPosition((double) this.getRobot().getMiddleServo().getPosition() - (double) 0.1);
            } else if(this.right && !this.getGamepad().b) {
                this.right = false;
                this.getRobot().getMiddleServo().setPosition((double) this.getRobot().getMiddleServo().getPosition() + (double) 0.1);
            }
        }
    }
}
