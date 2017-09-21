package com.arinerron.ftc;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.*;

public class Robot {
    private ElapsedTime timer = new ElapsedTime(); // strictly for the wait function!

    private Motor left = null, middle = null, right = null;

    /* Robot constructor, initialize stuff */
    public Robot(DcMotor middle, DcMotor left, DcMotor right) {
        this.left = new Motor(this, left);
        this.middle = new Motor(this, middle);
        this.right = new Motor(this, right);
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

    /* wait x seconds */
    private void wait(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
    }
}
