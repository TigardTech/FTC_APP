package com.arinerron.ftc;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.*;

public class Robot {
    private ElapsedTime timer = new ElapsedTime(); // strictly for the wait function!

    private Motor left = null, right = null;

    public Robot(Motor left, Motor right) {
        this.left = left;
        this.right = right;
    }

    private void wait(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
    }
}
