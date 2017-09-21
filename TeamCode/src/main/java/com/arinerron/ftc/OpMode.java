package com.arinerron.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
public abstract class OpMode extends LinearOpMode {
    public static String TAG = "OpMode";

    private HardwareMap map = null;
    private String name = "OpMode";
    private Robot robot = null;

    public OpMode() {
        this.map = hardwareMap;

        this.robot = new Robot(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract void run();

    @Override
    public void runOpMode() throws InterruptedException {
        this.run();
    }

    public HardwareMap getHardwareMap() {
        return this.map;
    }

    public void write(String data) {
        this.write("INFO", data);
    }

    public void write(String tag, String data) {
        telemetry.addData(tag.toUpperCase(), data);
        telemetry.update();
    }

    public Robot getRobot() {
        return this.robot;
    }

    public DcMotor getMotor(String name) {
        return this.getHardwareMap().dcMotor.get(name);
    }

    public Servo getServo(String name) {
        return this.getHardwareMap().servo.get(name);
    }
}
