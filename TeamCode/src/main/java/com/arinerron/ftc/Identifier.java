package com.arinerron.ftc;

import java.util.ArrayList;
import java.util.List;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class Identifier {
    private OpMode mode = null;
    private List<EventListener> events = new ArrayList<>();
    private boolean running = false;

    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private HardwareMap hardwareMap = null;

    public Identifier(OpMode mode) {
        this.mode = mode;
        this.hardwareMap = this.getOpMode().getHardwareMap();
    }

    public boolean start() {
        if(!this.isRunning()) {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            parameters.vuforiaLicenseKey = Constants.LICENSE_KEY;
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; // VuforiaLocalizer.CameraDirection.FRONT for front camera. Back has a greater range.
            this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

            VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
            VuforiaTrackable relicTemplate = relicTrackables.get(0);
            relicTemplate.setName(this.getOpMode().getName());

            relicTrackables.activate();

            while(this.isRunning()) {
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark != RelicRecoveryVuMark.UNKNOWN)
                    for(EventListener event : this.getEventListeners()) { // potential ConcurrentModificationException? Oh well.
                        event.onEvent(new Data(vuMark));
                    }
            }

            relicTrackables.deactivate();

            return true;
        }

        return false;
    }

    public boolean stop() {
        boolean running = this.isRunning();
        this.running = false;
        return running;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void addEventListener(EventListener event) {
        this.getEventListeners().add(event);
    }

    public List<EventListener> getEventListeners() {
        return this.events;
    }

    public OpMode getOpMode() {
        return this.mode;
    }

    private String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}
