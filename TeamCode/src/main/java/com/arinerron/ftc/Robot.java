package com.arinerron.ftc;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.*;

public class Robot {
    private ElapsedTime timer = new ElapsedTime(); // strictly for the wait function!

    private Motor left = null, middle = null, right = null;
    private OpMode mode = null;

    /* robot constructor, initialize stuff */
    public Robot(OpMode mode) {
        this.mode = mode;

        this.left = new Motor(this, this.getOpMode().getMotor(Constants.LEFT_MOTOR));
        this.middle = new Motor(this, this.getOpMode().getMotor(Constants.ROTATING_MOTOR));
        this.right = new Motor(this, this.getOpMode().getMotor(Constants.RIGHT_MOTOR));

        this.getLeftMotor().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getMiddleMotor().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getRightMotor().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /* drive for x seconds at x speed */
    public void drive(double speed, double time) {
        this.getLeftMotor().setPower(speed);
        this.getRightMotor().setPower(speed);

        this.wait(time);

        this.getLeftMotor().reset();
        this.getRightMotor().reset();
    }

    /* get left motor */
    public Motor getLeftMotor() {
        return this.left;
    }

    /* get middle motor */
    public Motor getMiddleMotor() {
        return this.middle;
    }

    /* get right motor */
    public Motor getRightMotor() {
        return this.right;
    }

    /* easter egg */
    public Motor getPoliticallyNeutralMotor() {
        return this.middle;
    }

    /* get Opmode */
    public OpMode getOpMode() {
        return this.mode;
    }

    /* wait x seconds */
    private void wait(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
    }
}
