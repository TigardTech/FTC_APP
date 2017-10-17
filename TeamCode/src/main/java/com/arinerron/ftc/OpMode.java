package com.arinerron.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
public abstract class OpMode extends com.qualcomm.robotcore.eventloop.opmode.OpMode {
    public static String TAG = "OpMode";

    private HardwareMap map = null;
    private String name = "OpMode";
    private Robot robot = null;
    private ElapsedTime timer = new ElapsedTime();

    public OpMode() {

    }

    public void startTimer(double secs) {
        timer.reset();
    }

    public double getTimer() {
        return timer.seconds();
    }

    public boolean isTimerDone(double secs) {
        return this.getTimer() >= secs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract void run();
    public abstract void repeat();

    @Override
    public void init() {
        this.map = hardwareMap;
        this.robot = new Robot(this);
    }

    @Override
    public void loop() {
        this.repeat(); // DO NOT block!
    }

    @Override
    public void stop() {}

    @Override
    public void start() {
        this.robot = new Robot(this);
        new Thread(new Runnable() { public void run() {
            OpMode.this.run();
        }}).start();
    }

    public HardwareMap getHardwareMap() {
        return this.map;
    }

    public void write(String data) {
        this.write("INFO", data);
    }

    public void write(String tag, String data) {
        Log.write(tag, data);
        telemetry.addData("Log", "\n" + Log.getString());
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

    public ColorSensor getColorSensor(String name) {
        return this.getHardwareMap().colorSensor.get(name);
    }

    public GyroSensor getGyroSensor(String name) {
        return this.getHardwareMap().gyroSensor.get(name);
    }
}
