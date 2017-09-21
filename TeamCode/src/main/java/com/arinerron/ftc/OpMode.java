package com.arinerron.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Disabled
public abstract class OpMode extends LinearOpMode {
    public static String TAG = "OpMode";

    private HardwareMap map = null;
    private String name = "OpMode";

    public OpMode() {
        this.map = hardwareMap;
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
}
