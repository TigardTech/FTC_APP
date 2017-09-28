package com.arinerron.ftc;

public class Servo {
    private com.qualcomm.robotcore.hardware.Servo servo = null;
    private Robot robot = null;
    private double center = 0; // calibrate servo using this
    private double multiplier = 0; // multiplies by the position

    /* constructor */
    public Servo(Robot robot, com.qualcomm.robotcore.hardware.Servo servo) {
        this.servo = servo;
        this.robot = robot;
    }

    /* replaces the NaN with valid data. should be run once opmode is started */
    public void fix() {
        if(Double.isNaN(this.getPosition()))
            this.setPosition(0);
    }

    /* sets the servo's position */
    public void setPosition(double position) {
        this.getServo().setPosition(this.getCenter() + position);
    }

    /* sets the servo's position to an "angle" from 0-360 */
    public void setAngle(double angle) {
        this.setPosition(angle * this.getMultiplier());
    }

    /* sets the position multiplier */
    public void setMultiplier(double m) {
        this.multiplier = m;
    }

    /* calibrate center */
    public void calibrate() {
        this.center = this.getServo().getPosition();
    }

    /* reset servo to center position */
    public void reset() {
        this.getServo().setPosition(this.getCenter());
    }

    /* returns "angle" */
    public double getAngle() {
        return this.getPosition() * this.getMultiplier();
    }

    /* returns the servo's position */
    public double getPosition() {
        return this.getServo().getPosition();
    }

    /* returns the multiplier */
    public double getMultiplier() {
        return this.multiplier;
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
