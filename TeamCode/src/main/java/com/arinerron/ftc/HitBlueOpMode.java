package com.arinerron.ftc;

import com.arinerron.ftc.com.arinerron.ftc.opmodes.HitOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Hit BLUE", group="Iterative OpMode")
public class HitBlueOpMode extends HitOpMode {
    @Override
    public String getColor() {
        return "blue";
    }

    @Override
    public double getMultiplier() {
        return 1;
    }
}
