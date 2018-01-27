package com.arinerron.ftc.com.arinerron.ftc.opmodes;

import com.arinerron.ftc.Constants;
import com.arinerron.ftc.Direction;
import com.arinerron.ftc.Motor;
import com.arinerron.ftc.Position;
import com.arinerron.ftc.Servo;
import com.arinerron.ftc.TeleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class EncodersTest extends TeleOpMode {
    public EncodersTest() {
        super();
    }

    @Override
    public void init() {
        super.init();

        if(this.getRobot().getColorSensor() != null)
            this.getRobot().getColorSensor().enableLed(true);

        write("Driving OpMode initialized.");
    }

    @Override
    public void run() {
        write("OpMode running...");
        this.getRobot().getServoArm1().setPosition(0.5);
        this.getRobot().getServoArm2().setPosition(0.5);
    }

    private boolean left = false, right = false, up = false, down = false;

    public String getColorBR() {
        return (this.getRobot().getColorSensor().red() > this.getRobot().getColorSensor().blue() ? "red" : (this.getRobot().getColorSensor().blue() > this.getRobot().getColorSensor().red() ? "blue" : "neither"));
    }

    public void stop() {
        this.getRobot().getMotor1().setPower(0);
        this.getRobot().getMotor2().setPower(0);
        this.getRobot().getMotor3().setPower(0);
        this.getRobot().getMotor4().setPower(0);
    }

    private boolean pressed = false, holding = false, mode = false, pressedl = false, holdingl = true, apressed = false, rpressed = false;
    private Position armpos = Position.STOPPED;
    private boolean armtop = true, armbottom = true;
    private String color = "";
    private boolean run = false, open = true, pressedclaw = false, first = true;

    private Servo curser = null;
    private boolean cpressed = 0;
    private int index = 0;

    @Override
    public void repeat() {
        double x = this.getGamepad().right_stick_x;
        double y = -this.getGamepad().right_stick_y;
        double x1 = this.getGamepad().left_stick_x;
        double y1 = -this.getGamepad().left_stick_y;

        if(first) {
            first = false;
            point(Direction.STRAIGHT);
        }

        if(curser == null)
            curser = this.getRobot().getServoRelicGrabber

        // debug stuff
        this.write("\nMode: " + (mode ? "advanced" : "simple") + "\n" +
                "Stick: [" + ((double)Math.round(x1 * 10d) / 10d) + ", " + ((double)Math.round(y1 * 10d) / 10d) + "] & [" + ((double)Math.round(x * 10d) / 10d) + ", " + ((double)Math.round(y * 10d) / 10d) + "]\n" +
                "Motors: " + ((!isZero(this.getRobot().getMotor1().getPower()) || !isZero(this.getRobot().getMotor2().getPower()) || !isZero(this.getRobot().getMotor3().getPower()) || !isZero(this.getRobot().getMotor4().getPower())) ? "active" : "inactive") + "\n" +
                "Arm: " + (armpos == Position.IN ? "pulling in" : (armpos == Position.OUT ? "pushing out" : "inactive")) + "\n" +
                "Direction: " + (dir == Direction.STRAIGHT ? "straight" : (dir == Direction.FORTYFIVE ? "forty five" : "horizontal")) + "\n" +
                "Color: " + (color.length() != 0 ? color.toLowerCase() : "N/A") + "(" + (this.getRobot().getColorSensor().blue() > this.getRobot().getColorSensor().red() ? "blue" : "red") + "?)\n" +
                "Encoders: [1=" + this.getRobot().getMotor1().getDcMotor().getCurrentPosition() + ", 2=" + this.getRobot().getMotor2().getDcMotor().getCurrentPosition() + ", 3=" + this.getRobot().getMotor3().getDcMotor().getCurrentPosition() + ", 4=" + this.getRobot().getMotor4().getDcMotor().getCurrentPosition() + "]\n", true);

        // make sure servos & motors aren't dying
        //check();

        /*if(this.getGamepadB().dpad_up) {
            this.getRobot().getServoRelic().setPosition(1.0);
            this.getRobot().getMotorRelic().setPower(1.0);
        } else if(this.getGamepadB().dpad_down) {
            this.getRobot().getServoRelic().setPosition(0.0);
            this.getRobot().getMotorRelic().setPower(-1.0);
        } else {
            this.getRobot().getServoRelic().setPosition(0.5);
            this.getRobot().getMotorRelic().setPower(0);
        }*/

        if(this.getGamepadB().dpad_right) {
            this.getRobot().getServoArmJ().setPosition(1.0);
        } else if(this.getGamepadB().dpad_left) {
            this.getRobot().getServoArmJ().setPosition(0.0);
        } else {
            this.getRobot().getServoArmJ().setPosition(0.5);
        }

        if(Double.isNaN(curser.getPosition()))
            curser.setPosition(0.5);

        if(this.getGamepadB().dpad_up) {
            if(!cpressed)
                curser.setPosition(curser.getPosition() + 0.1);
            cpressed = true;
        } else if(this.getGamepadB().dpad_down) {
            if(!cpressed)
                curser.setPosition(curser.getPosition() - 0.1);
            cpressed = true;
        } else if(this.getGamepadB().x) {
            if(!cpressed) {
                index++;
                switch(index) {
                    case 0:
                        curser = this.getRobot().getServoRelic();
                        break;
                    case 1:
                        curser = this.getRobot().getServoRelicGrabber();
                        break;
                }
            }
            cpressed = true;
        } else {
            cpressed = false;
        }

        // get colors
        if(this.getRobot().getColorSensor() != null)
            this.color = isColor(this.getRobot().getColorSensor().red(), this.getRobot().getColorSensor().green(), this.getRobot().getColorSensor().blue(), "red") ? "red" : (isColor(this.getRobot().getColorSensor().red(), this.getRobot().getColorSensor().green(), this.getRobot().getColorSensor().blue(), "blue") ? "blue" : "");

        // change modes from simple to advanced or vice versa
        if(this.getGamepad().x) {
            apressed = true;
        } else {
            if(apressed) {
                stop();
                mode = !mode;
            }

            apressed = false;
        }

        if(this.getGamepad().a) {
            this.point(dir);
        }

        if(this.getRobot() != null) {
            // push out or pull in arms
            if (this.getGamepad().b) {
                if (!pressedl) {
                    pressedl = true;

                    // could optimize ik
                    if (holdingl) {
                        // open arms
                        this.getRobot().getDriver().setServo("arme", true);
                        holdingl = false;
                    } else {
                        // close arms
                        this.getRobot().getDriver().setServo("arme", false);
                        holdingl = true;
                    }
                }
            } else {
                pressedl = false;
            }

            // stop all motors if pressed
            if(this.getGamepad().y) {
                stop();
                this.setClaw(Position.STOPPED);
                // this.getRobot().getMotorArm().setPower(0);
            }

            if(mode) {
                if (this.getGamepad().dpad_left) {
                    point(Direction.NINETY);
                } else if (this.getGamepad().dpad_up) {
                    this.getRobot().reset();
                } else if (this.getGamepad().dpad_right) {
                    point(Direction.FORTYFIVE);
                } else if (this.getGamepad().dpad_down) {
                    point(Direction.STRAIGHT);
                }

                if (!isZero(y)) {
                    drive(y);
                } else if (!isZero(x)) {
                    drive(x);
                } else {
                    stop();
                }
            } else {
                boolean adjusting = false;

                // arm stuff and small adjustment stuff
                if (this.getGamepad().dpad_left) {
                    adjusting = true;
                    if (dir != Direction.FORTYFIVE && !rpressed) {
                        rpressed = true;

                        point(Direction.FORTYFIVE);
                    }
                    drive(-0.2);
                } else if (this.getGamepad().dpad_up) {
                    if(!rpressed) {
                        armtop = !armtop;
                        rpressed = true;
                    }
                } else if (this.getGamepad().dpad_right) {
                    adjusting = true;
                    if (dir != Direction.STRAIGHT && !rpressed) {
                        rpressed = true;

                        point(Direction.FORTYFIVE);
                       // point(Direction.STRAIGHT);
                    }
                    drive(0.2);
                } else if (this.getGamepad().dpad_down) {
                    if(!rpressed) {
                        armbottom = !armbottom;
                        rpressed = true;

                    }
                } else {
                    rpressed = false;
                    if(adjusting) {
                        adjusting = false;
                        drive(0);
                    }
                }

                // other mode
                if(!isZero(y, 0.3)) {
                    // straight
                    if(dir != Direction.STRAIGHT) {
                        point(Direction.STRAIGHT);
                    }

                    drive(y);

                } else if(!isZero(x, 0.3)) {
                    // 90
                    if(dir != Direction.NINETY) {
                        point(Direction.NINETY);
                    }

                    drive(x);

                } else if(!isZero(x1, 0.3)) {
                    // ff
                    if (dir != Direction.FORTYFIVE) {
                        point(Direction.FORTYFIVE);
                    }

                    drive(x1);
                } else {
                    // slow down
                    stop();
                }
            }
        }

        if(this.getGamepad().right_bumper) {
            if(!pressed) {
                this.pressed = true;
                if(holding) {
                    setClaw(Position.STOPPED);
                    holding = false;
                } else {
                    setClaw(Position.IN);
                    holding = true;
                }
            }
        } else if(this.getGamepad().left_bumper) {
            if(!pressed) {
                this.pressed = true;
                if(holding) {
                    setClaw(Position.STOPPED);
                    holding = false;
                } else {
                    setClaw(Position.OUT);
                    holding = true;
                }
            }
        } else {
            this.pressed = false;
        }

        // move motor arm around
        /*   / (this.getGamepad().left_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorArm().setPower(-this.getGamepad().left_trigger / 2);
        } else if (this.getGamepad().right_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorArm().setPower(this.getGamepad().right_trigger / 2);
        } else if (this.getGamepadB().left_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorArm().setPower(-this.getGamepadB().left_trigger / 2);
        } else if (this.getGamepadB().right_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorArm().setPower(this.getGamepadB().right_trigger / 2);
        } else {
            this.getRobot().getMotorArm().setPower(0);
        }*/

        /*
         * Constants.TRIGGER_THRESHOLD : The minimum amount the trigger must be pressed to move the lift
         * this.getGamepad().right_trigger : The amount that the right trigger is pressed, from 0 to 1
         * this.getRobot().getMotorArmLift().setPower(power) : Sets the amount of power on the lifting motor
         */
         if(this.getGamepad().right_trigger > Constants.TRIGGER_THRESHOLD) {
             // lift the grabber up
             this.getRobot().getMotorArmLift().setPower(-this.getGamepad().right_trigger);
         } else if(this.getGamepad().left_trigger > Constants.TRIGGER_THRESHOLD) {
             // let the grabber down
             // divide by two to slow down-- gravity helps already, no need to speed things up that much
             this.getRobot().getMotorArmLift().setPower(this.getGamepad().left_trigger / 2);
         } else if(this.getGamepadB().right_bumper) {
             // lift the grabber up
             this.getRobot().getMotorArmLift().setPower(-1);
         } else if(this.getGamepadB().left_bumper) {
             // let the grabber down
             // divide by two to slow down-- gravity helps already, no need to speed things up that much
             this.getRobot().getMotorArmLift().setPower(1 / 2);
         } else {
            // stop the arm lift
            this.getRobot().getMotorArmLift().setPower(0);
        }

        // relic grabber
        if(this.getGamepadB().a) {
            // open claw
            if(!pressedclaw) {
                open = !open;
                this.setRelicGrabberOpen(open);
                pressedclaw = true;
            }
        } else {
            pressedclaw = false;
        }

        if(this.getGamepadB().right_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorRelic1().setPower(1);
            this.getRobot().getMotorRelic2().setPower(-1);
        } else if(this.getGamepadB().left_trigger > Constants.TRIGGER_THRESHOLD) {
            this.getRobot().getMotorRelic1().setPower(-1);
            this.getRobot().getMotorRelic2().setPower(1);
        } else {
            this.getRobot().getMotorRelic1().setPower(0);
            this.getRobot().getMotorRelic2().setPower(0);
        }
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
