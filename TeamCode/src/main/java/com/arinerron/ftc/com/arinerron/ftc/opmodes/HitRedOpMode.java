package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Hit RED", group="Iterative OpMode")
public class HitRedOpMode extends HitOpMode {
    @Override
    public String getColor() {
        return "red";
    }

    @Override
    public double getMultiplier() {
        return 0;
    }
}
