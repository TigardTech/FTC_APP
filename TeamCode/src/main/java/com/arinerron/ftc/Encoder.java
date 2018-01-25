package com.arinerron.ftc;

import com.qualcomm.robotcore.hardware.DcMotor;

class Encoder {
    private Motor motor = null;
    private double inchespertick = 136.5;
    private double offset = 0;

    public Encoder(Motor motor) {
        this.motor = motor;
    }

    public void zero() {
        this.setOffset(this.getAbsoluteTicks());
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double ticksToInches(double ticks) {
        return ticks * this.getInchesPerTick();
    }

    public double getAbsoluteInchesTravelled() {
        return Math.abs(this.getAbsoluteInches());
    }

    public double getInchesTravelled() {
        return Math.abs(this.getInches());
    }

    public void setInchesPerTick(double inchespertick) {
        this.inchespertick = inchespertick;
    }

    public double getAbsoluteInches() {
        return ticksToInches(this.getAbsoluteTicks());
    }

    public double getInches() {
        return ticksToInches(this.getTicks());
    }

    public double getAbsoluteFeet() {
        return this.getAbsoluteInches() / 12;
    }

    public double getFeet() {
        return this.getInches() / 12;
    }

    public double getTicks() {
        return this.getAbsoluteTicks() - this.getOffset();
    }

    public double getAbsoluteTicks() {
        return this.getDcMotor().getCurrentPosition();
    }

    public double getOffset() {
        return this.offset;
    }

    public double getInchesPerTick() {
        return this.inchespertick;
    }

    public DcMotor getDcMotor() {
        return this.getMotor().getDcMotor();
    }

    public Motor getMotor() {
        return this.motor;
    }
}
