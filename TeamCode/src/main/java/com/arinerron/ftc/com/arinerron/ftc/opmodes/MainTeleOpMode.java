package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOpMode extends TeleOpMode {
    public MainTeleOpMode() {
        super();

        write("OpMode initialized.");
    }

    @Override
    public void run() {
        write("OpMode running...");
    }

    boolean test = false;
    boolean go = false;

    @Override
    public void repeat() {
        write("Running: " + this.getGamepad().a + " and " + this.getRobot().getMiddleMotor().getPower() + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                `                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                and " + test);
        if(this.getGamepad().a) {
            test = false;
            go = true;
        } else {
            if(!test && go) {
                this.getRobot().getMiddleMotor().setPower(0.11   );
                this.startTimer(1.0);y  
                this.test = true;
                go = false;
            } else if(isTimerDone(1.0)) {
                this.test = false;
                this.getRobot().getMiddleMotor().reset();
            }
        }
    }
}
