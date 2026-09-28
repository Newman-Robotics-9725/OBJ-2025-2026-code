package org.firstinspires.ftc.teamcode;

import android.util.Size;
import org.firstinspires.ftc.vision.apriltag.AprilTagMetadata;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraException;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "autoBlockCodeTest2 (Blocks to Java)")
public class autoBlockCodeTest2 extends LinearOpMode {

  /**
   * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
   * Comment Blocks show where to place Initialization code (runs once, after touching the
   * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
   * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
   * Stopped).
   */
  @Override
  public void runOpMode() {
    
    //int viewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

    
    AprilTagMetadata myAprilTagMetadata;
    
    AprilTagLibrary myAprilTagLibrary;
    
    // Get the AprilTagLibrary for the current season.
    myAprilTagLibrary = AprilTagGameDatabase.getCurrentGameTagLibrary();
    
    //WebcamName camera;
    VisionPortal myVisionPortal;
    //WebcamName camera;
    VisionPortal.Builder myVisionPortalBuilder;

    AprilTagProcessor myAprilTagProcessor;
    // Create the AprilTag processor and assign it to a variable.
    //myAprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
    
    AprilTagProcessor.Builder myAprilTagProcessorBuilder;
    // Create a new AprilTag Processor Builder object.
    myAprilTagProcessorBuilder = new AprilTagProcessor.Builder();
    // Set the tag library.
    //myAprilTagLibrary.setTagSizeMeters(0.279f);
    myAprilTagProcessorBuilder.setTagLibrary(myAprilTagLibrary); // The OpMode must have already␣,→created a Library.
    //myAprilTagProcessorBuilder.getTagSizeMeters();
    //myAprilTagProcessorBuilder.setTagSizeMeters(0.279f);

    // Optional: set other custom features of the AprilTag Processor (4 are shown here).
    myAprilTagProcessorBuilder.setDrawTagID(true); // Default: true, for all detections.
    myAprilTagProcessorBuilder.setDrawTagOutline(true); // Default: true, when tag size was provided␣,→(thus eligible for pose estimation).
    myAprilTagProcessorBuilder.setDrawAxes(true); // Default: false.
    myAprilTagProcessorBuilder.setDrawCubeProjection(true); // Default: false.
    // Create an AprilTagProcessor by calling build()
    myAprilTagProcessor = myAprilTagProcessorBuilder.build();

    // Create a VisionPortal.Builder object so you can specify attributes about the cameras.
    myVisionPortalBuilder = new VisionPortal.Builder();
    // Build the VisionPortal object and assign it to a variable.
    //myVisionPortal = myVisionPortalBuilder.build();
    // Add the AprilTag processor.
    //myVisionPortalBuilder.addProcessor(myVisionPortal);
    // Set the camera to the specified webcam name.
    myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Ethernet Device")); //Webcam 1
    //myVisionPortalBuilder.setCameraMonitorViewId(viewId);
    //WebcamName name = map.get(hardwareMap.get(WebcamName.class, "Webcam 1"));
    //camera.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
    myVisionPortalBuilder.addProcessor(myAprilTagProcessor);
    myVisionPortalBuilder.enableLiveView(true);
    // Set the camera resolution.
    myVisionPortalBuilder.setCameraResolution(new Size(640, 480));

    // Build the VisionPortal object and assign it to a variable.
    myVisionPortal = myVisionPortalBuilder.build();
    myVisionPortal.setProcessorEnabled(myAprilTagProcessor, true);
    waitForStart();
  }
}
