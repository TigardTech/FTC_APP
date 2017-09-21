package com.arinerron.ftc;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Motor {
    private Robot robot = null;
    private DcMotor motor = null;
    private ElapsedTime timer = new ElapsedTime();

    public Motor(Robot robot, DcMotor motor) {
        this.robot = robot;
        this.motor = motor;
    }

    /* speed: 0.0 to 1.0, time: seconds to drive for*/
    public void drive(double speed, double time) {
        this.setPower(speed);
        this.wait(time);
        this.reset();
    }

    public Robot getRobot() {
        return this.robot;
    }

    public DcMotor getDcMotor() {
        return this.motor;
    }

    /* set the motor's power */
    public void setPower(double power) {
        this.getDcMotor().setPower(power);
    }

    /* reset the motor */
    public void reset() {
        this.setPower(0);
    }

    private void wait(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
    }

    /* returns the power of the motor */
    public double getPower() {
        return this.getDcMotor().getPower();
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
