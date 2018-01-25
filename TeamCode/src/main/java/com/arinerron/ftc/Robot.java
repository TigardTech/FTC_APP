package com.arinerron.ftc;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.*;

public class Robot {
    private ElapsedTime timer = new ElapsedTime(); // strictly for the wait function!

    // wheel motors
    private Motor m1 = null;
    private Motor m2 = null;
    private Motor m3 = null;
    private Motor m4 = null;
    private Motor arm = null;
    private Motor armlift = null;
    private Motor mr1 = null;
    private Motor mr2 = null;
    // wheel servos
    private Servo s1 = null;
    private Servo s2 = null;
    private Servo s3 = null;
    private Servo s4 = null;
    // arm servos
    private Servo sa1 = null;
    private Servo sa2 = null;
    private Servo sae = null;
    private Servo saj = null;
    private Servo sag = null; // arm grabber
    private Servo sr = null;

    private ColorSensor sensorColor = null;
    private GyroSensor sensorGyro = null;
    private OpMode mode = null;

    private Driver driver = null;

    /* robot constructor, initialize stuff */
    public Robot(OpMode mode) {
        this.mode = mode;

        // create motor&servo instances
        this.m1 = new Motor(this, this.getOpMode().getMotor(Constants.M1_MOTOR));
        this.s1 = new Servo(this, this.getOpMode().getServo(Constants.S1_SERVO));
        this.m2 = new Motor(this, this.getOpMode().getMotor(Constants.M2_MOTOR));
        this.s2 = new Servo(this, this.getOpMode().getServo(Constants.S2_SERVO));
        this.m3 = new Motor(this, this.getOpMode().getMotor(Constants.M3_MOTOR));
        this.s3 = new Servo(this, this.getOpMode().getServo(Constants.S3_SERVO));
        this.m4 = new Motor(this, this.getOpMode().getMotor(Constants.M4_MOTOR));
        this.s4 = new Servo(this, this.getOpMode().getServo(Constants.S4_SERVO));
        this.mr1 = new Motor(this, this.getOpMode().getMotor(Constants.RELIC1_MOTOR));
        this.mr2 = new Motor(this, this.getOpMode().getMotor(Constants.RELIC2_MOTOR));
        //this.arm = new Motor(this, this.getOpMode().getMotor(Constants.ARM_MOTOR));
        this.armlift = new Motor(this, this.getOpMode().getMotor(Constants.ARM_LIFT_MOTOR));
        this.sa1 = new Servo(this, this.getOpMode().getServo(Constants.SA1_SERVO));
        this.sa2 = new Servo(this, this.getOpMode().getServo(Constants.SA2_SERVO));
        this.sae = new Servo(this, this.getOpMode().getServo(Constants.SAE_SERVO));
        this.saj = new Servo(this, this.getOpMode().getServo(Constants.SAJ_SERVO));
        this.sr = new Servo(this, this.getOpMode().getServo(Constants.SR_SERVO)); // relic servo
        this.sag = new Servo(this, this.getOpMode().getServo(Constants.SAG_SERVO));

        this.sa1.setCenter(0.5);
        this.sa2.setCenter(0.5);
        this.sae.setCenter(0.5);
        this.saj.setCenter(0.5);
        this.sr.setCenter(0.5);
        this.sag.setCenter(0.5);

        // enable/disable encoders on motors
        this.getMotor1().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getMotor2().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getMotor3().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getMotor4().setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.driver = new Driver(this);

        // define sensors
        if(Constants.COLOR_SENSOR != null && Constants.COLOR_SENSOR.length() != 0)
            this.sensorColor = this.getOpMode().getColorSensor(Constants.COLOR_SENSOR);
        if(Constants.GYRO_SENSOR != null && Constants.GYRO_SENSOR.length() != 0)
            this.sensorGyro = this.getOpMode().getGyroSensor(Constants.GYRO_SENSOR);

        // calibrate sensors
        if(this.getGyroSensor() != null)
            this.getGyroSensor().calibrate();
    }

    /* get driver object */
    public Driver getDriver() {
        return this.driver;
    }

    /* drive for x seconds at x speed */
    public void drive(double speed, double time) {
        setPower(speed);

        this.wait(time);

        reset(); // TODO: make only main motors stop
    }

    /* reset all motors */
    public void reset() {
        this.getMotor1().reset();
        this.getMotor2().reset();
        this.getMotor3().reset();
        this.getMotor4().reset();
    }

    /* set power on all motors */
    public void setPower(double speed) {
        this.getMotor1().setPower(speed);
        this.getMotor2().setPower(speed);
        this.getMotor3().setPower(speed);
        this.getMotor4().setPower(speed);
    }

    /* get motors */
    public Motor getMotor1() {
        return this.m1;
    }
    public Motor getMotor2() {
        return this.m2;
    }
    public Motor getMotor3() {
        return this.m3;
    }
    public Motor getMotor4() {
        return this.m4;
    }
    public Motor getMotorArm() {
        return this.arm;
    }
    public Motor getMotorArmLift() {
        return this.armlift;
    }
    public Motor getMotorRelic1() { return this.mr1; }
    public Motor getMotorRelic2() { return this.mr2; }

    /* get servos */
    public Servo getServo1() {
        return this.s1;
    }
    public Servo getServo2() {
        return this.s2;
    }
    public Servo getServo3() {
        return this.s3;
    }
    public Servo getServo4() {
        return this.s4;
    }
    public Servo getServoArm1() {
        return this.sa1;
    }
    public Servo getServoArm2() {
        return this.sa2;
    }
    public Servo getServoArmE() {
        return this.sae;
    }
    public Servo getServoArmJ() { return this.saj; }
    public Servo getServoRelic() { return this.sr; }
    public Servo getServoRelicGrabber() {return this.sag;}

    /* get Opmode */
    public OpMode getOpMode() {
        return this.mode;
    }

    /* get color sensor */
    public ColorSensor getColorSensor() {
        return this.sensorColor;
    }

    /* get gyro sensor */
    public GyroSensor getGyroSensor() {
        return this.sensorGyro;
    }

    /* wait x seconds */
    private void wait(double seconds) {
        timer.reset();
        while(timer.seconds() < seconds);
    }
}
