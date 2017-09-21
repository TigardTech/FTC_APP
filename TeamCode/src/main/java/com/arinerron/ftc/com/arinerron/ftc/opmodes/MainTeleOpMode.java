package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.TeleOpMode;

public class MainTeleOpMode extends TeleOpMode {
    private boolean test = false;

    public MainTeleOpMode() {
        super();

        write("OpMode initialized.");
    }

    @Override
    public void run() {
        write("OpMode running...");
    }

    @Override
    public void repeat() {
        double time = 1;
        if(!test) {
            if(this.getGamepad().a) {
                test = true;
            }
        } else {
            if(isTimerDone(time)) {
                test = false;
                this.getRobot().reset();
            } else {
                this.getRobot().setPower(1.0);
            }
        }
    }
}
