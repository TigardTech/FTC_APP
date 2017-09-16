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

    public Robot getRobot() {
        return this.robot;
    }

    public DcMotor getDcMotor() {
        return this.motor;
    }

    private void wait(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
    }
}
