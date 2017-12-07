package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Direction;
import com.arinerron.ftc.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Three Feet", group="Iterative OpMode")
public class ThreeFeetMode extends OpMode {
    public ThreeFeetMode() {
        super();
    }

    @Override
    public void run() {
        write("Driving...");
        point(Direction.STRAIGHT);
        driveInches(12 * 3);
        point(Direction.STRAIGHT);
        write("Done.");
    }

    @Override
    public void repeat() {

    }


    public static boolean isZero(double x) {
        return x < Constants.TRIGGER_THRESHOLD && x > -Constants.TRIGGER_THRESHOLD;
    }

    public static boolean isZero(double x, double threshold) {
        return x < threshold && x > -threshold;
    }

    public static double getAngle(double x, double y) {
        if(isZero(y) && isZero(y))
            return 0;
        return Math.toDegrees(1.5 * Math.PI - Math.atan2(y, x)) - 180; // supposed to be y,x
    }
}
