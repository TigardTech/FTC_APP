package com.arinerron.ftc;

public class Servo {
    private com.qualcomm.robotcore.hardware.Servo servo = null;
    private Robot robot = null;
    private double center = 0; // calibrate servo using this

    /* constructor */
    public Servo(Robot robot, com.qualcomm.robotcore.hardware.Servo servo) {
        this.servo = servo;
        this.robot = robot;
    }

    /* sets the servo's position */
    public void setPosition(double position) {
        this.getServo().setPosition(this.getCenter() + position);
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
    public double getPosition() {
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
