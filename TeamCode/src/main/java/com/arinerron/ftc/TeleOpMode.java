package com.arinerron.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

//@TeleOp(name = "TeleOpMode", group = "Teleop OpMode")
@Disabled
public abstract class TeleOpMode extends com.arinerron.ftc.OpMode {
    public abstract void run();

    /* returns the first gamepad */
    public Gamepad getGamepadA() {
        return this.gamepad1;
    }

    /* returns the second gamepad */
    public Gamepad getGamepadB() {
        return this.gamepad2;
    }

    /* returns the "main" gamepad (A) */
    public Gamepad getGamepad() {
        return this.getGamepadA();
    }
}
