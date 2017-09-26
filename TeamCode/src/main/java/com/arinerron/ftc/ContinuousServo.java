package com.arinerron.ftc;

public class ContinuousServo {
    private com.qualcomm.robotcore.hardware.Servo servo = null;
    private Robot robot = null;
    private double center = 0.5; // calibrate servo using this
    
    /* constructor */
    public ContinuousServo(Robot robot, com.qualcomm.robotcore.hardware.Servo servo) {
        this.servo = servo;
        this.robot = robot;
    }

    /* rotate clockwise */
    public void right() {
        this.setPosition(1);
    }

    /* rotate counterclockwise */
    public void left() {
        this.setPosition(0);
    }

    /* sets the servo's position */
    private void setPosition(double position) {
        this.getServo().setPosition(position);
    }

    /* calibrate center */
    public void calibrate() {
        this.center = this.getServo().getPosition();
    }

    /* reset servo to center position */
    public void reset() {
        this.getServo().setPosition(this.getCenter());
    }

    /* returns the servo's position */
    private double getPosition() {
        return this.getServo().getPosition();
    }

    /* returns the servo's center position */
    public double getCenter() {
        return this.center;
    }

    /* returns robot object */
    public Robot getRobot() {
        return this.robot;
    }

    /* returns default servo instance */
    public com.qualcomm.robotcore.hardware.Servo getServo() {
        return this.servo;
    }
}
