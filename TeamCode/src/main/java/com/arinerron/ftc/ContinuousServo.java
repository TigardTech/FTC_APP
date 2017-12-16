package com.arinerron.ftc;

public class ContinuousServo extends Servo {
    /* continuousservo constructor */
    public ContinuousServo(Robot robot, com.qualcomm.robotcore.hardware.Servo servo) {
        super(robot, servo);
    }

    @Override
    public double getCenter() {
        return 0.5;
    }

    /* rotate clockwise */
    public void right() {
        this.setPosition(1);
    }

    /* rotate counterclockwise */
    public void left() {
        this.setPosition(0);
    }

    /* reset servo to center position */
    @Override
    public void reset() {
        this.getServo().setPosition(this.getCenter());
    }
}
