package com.arinerron.ftc;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/* wrapper class for the DcMotor */
public class Motor {
    private Robot robot = null;
    private DcMotor motor = null;
    private ElapsedTime timer = new ElapsedTime();
    private Encoder encoder = new Encoder(this);
    private int multiplier = 1;

    /* init */
    public Motor(Robot robot, DcMotor motor) {
        this.robot = robot;
        this.motor = motor;
    }

    /* returns encoder object for this motor */
    public Encoder getEncoder() {
        return this.encoder;
    }

    /* speed: 0.0 to 1.0, time: seconds to drive for*/
    public void drive(double speed, double time) {
        this.setPower(speed);
        this.wait(time);
        this.reset();
    }

    /* get Robot object that owns this Motor */
    public Robot getRobot() {
        return this.robot;
    }

    /* get DcMotor for this Motor */
    public DcMotor getDcMotor() {
        return this.motor;
    }

    /* set the motor's power */
    public void setPower(double power) {
        this.getDcMotor().setPower(multiplier * power);
    }

    /* returns the power of the motor */
    public double getPower() {
        return this.getDcMotor().getPower();
    }

    /* reset the motor */
    public void reset() {
        this.setPower(0);
    }

    /* wait x seconds */
    private void wait(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
    }

    /* invert direction of motor */
    public void invert() {
        multiplier *= -1;
    }

    /* invert direction of motor */
    public void invert(boolean inverted) {
        multiplier = (inverted ? -1 : 1);
    }


    /* check motor to make sure it isn't running faster than it should */
    public void check(DcMotor motor) {
        if(this.getPower() < -1.0 || this.getPower() > 1.0)
            this.setPower(-1.0);
    }

    /* set driving mode */
    public void setDriveMode(DcMotor.RunMode mode) {
        this.getDcMotor().setMode(mode);
    }
}
