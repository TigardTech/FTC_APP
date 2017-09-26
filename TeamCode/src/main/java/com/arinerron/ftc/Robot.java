package com.arinerron.ftc;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.*;

public class Robot {
    private ElapsedTime timer = new ElapsedTime(); // strictly for the wait function!

    private Motor left = null;
    private Servo middle = null;
    private Motor right = null;

    private ColorSensor sensorColor = null;
    private GyroSensor sensorGyro = null;
    private OpMode mode = null;

    /* robot constructor, initialize stuff */
    public Robot(OpMode mode) {
        this.mode = mode;

        // create motor instances
        this.left = new Motor(this, this.getOpMode().getMotor(Constants.LEFT_MOTOR));
        this.middle = new Servo(this, this.getOpMode().getServo(Constants.MIDDLE_SERVO));
        this.right = new Motor(this, this.getOpMode().getMotor(Constants.RIGHT_MOTOR));

        // enable/disable encoders on motors
        this.getLeftMotor().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getRightMotor().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // define sensors
        if(Constants.COLOR_SENSOR != null && Constants.COLOR_SENSOR.length() != 0)
            this.sensorColor = this.getOpMode().getColorSensor(Constants.COLOR_SENSOR);
        if(Constants.GYRO_SENSOR != null && Constants.GYRO_SENSOR.length() != 0)
            this.sensorGyro = this.getOpMode().getGyroSensor(Constants.GYRO_SENSOR);

        // calibrate sensors
        if(this.getGyroSensor() != null)
            this.getGyroSensor().calibrate();
    }

    /* drive for x seconds at x speed */
    public void drive(double speed, double time) {
        setPower(speed);

        this.wait(time);

        this.getLeftMotor().reset();
        this.getRightMotor().reset();
    }

    /* reset all motors */
    public void reset() {
        this.getLeftMotor().reset();
        this.getRightMotor().reset();
    }

    /* set power on all motors */
    public void setPower(double speed) {
        this.getLeftMotor().setPower(speed);
        this.getRightMotor().setPower(speed);
    }

    /* get left motor */
    public Motor getLeftMotor() {
        return this.left;
    }

    /* get middle motor */
    public Servo getMiddleServo() {
        return this.middle;
    }

    /* get right motor */
    public Motor getRightMotor() {
        return this.right;
    }

    /* easter egg */
    public Motor getPoliticallyNeutralMotor() {
        return this.right;
    }

    /* get Opmode */
    public OpMode getOpMode() {
        return this.mode;
    }

    /* get color sensor */
    public ColorSensor getColorSensor() {
        return this.sensorColor;
    }

    /* get gyro sensor */
    public GyroSensor getGyroSensor() {
        return this.sensorGyro;
    }

    /* wait x seconds */
    private void wait(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
    }
}
