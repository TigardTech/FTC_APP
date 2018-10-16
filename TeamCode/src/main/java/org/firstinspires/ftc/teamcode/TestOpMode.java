package com.arinerron.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="test", group="Iterative OpMode")
public class TestOpMode extends com.qualcomm.robotcore.eventloop.opmode.OpMode {
    public static String TAG = "OpMode";

    private HardwareMap map = null;
    private String name = "OpMode";
    private Robot robot = null;
    private ElapsedTime timer = new ElapsedTime();

    public OpMode() {

    }

    @Override
    public void init() {

    }

    @Override
    public void start() {
        this.map.dcMotor.get("base_motor0").setPower(1.0);
        this.map.dcMotor.get("base_motor1").setPower(1.0);
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
        this.map.dcMotor.get("base_motor0").setPower(0.0);
        this.map.dcMotor.get("base_motor1").setPower(0.0);
    }
}