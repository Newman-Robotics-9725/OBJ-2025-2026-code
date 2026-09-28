package org.firstinspires.ftc.teamcode.Autonaegae;

import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.vision.apriltag.AprilTagMetadata;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraException;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;



@Autonomous(name = "autoBlockCodeTest3 (Blocks to Java)")
public class CameraAuton extends LinearOpMode {

  @Override
  public void runOpMode() {
    AprilTagDetection myAprilTagDetection;
    
    AprilTagMetadata myAprilTagMetadata;
    
    AprilTagLibrary myAprilTagLibrary;
    
    // Get the AprilTagLibrary for the current season.
    myAprilTagLibrary = AprilTagGameDatabase.getCurrentGameTagLibrary();
    //could try getCenterStageTagLibrary()
    
    //WebcamName camera;
    VisionPortal myVisionPortal;
    //WebcamName camera;
    VisionPortal.Builder myVisionPortalBuilder;

    AprilTagProcessor myAprilTagProcessor;
    // Create the AprilTag processor and assign it to a variable.
    myAprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
    
    AprilTagProcessor.Builder myAprilTagProcessorBuilder;
    // Create a new AprilTag Processor Builder object.
    myAprilTagProcessorBuilder = new AprilTagProcessor.Builder();
    // Set the tag library.
    myAprilTagProcessorBuilder.setTagLibrary(myAprilTagLibrary); // The OpMode must have already␣,→created a Library.

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
    myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
    //WebcamName name = map.get(hardwareMap.get(WebcamName.class, "Webcam 1"));
    //camera.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
    myVisionPortalBuilder.addProcessor(myAprilTagProcessor);
    
    // Set the camera resolution.
    myVisionPortalBuilder.setCameraResolution(new Size(640, 480));

    //myVisionPortalBuilder.enableCameraMonitoring(true);
    // Build the VisionPortal object and assign it to a variable.
    myVisionPortal = myVisionPortalBuilder.build();
    myVisionPortal.setProcessorEnabled(myAprilTagProcessor, true);
    //waitForStart();
    
    while (opModeIsActive()) {
      if (myAprilTagProcessor.getDetections().size() > 0) {
        myAprilTagDetection = myAprilTagProcessor.getDetections().get(0);
        int myAprilTagID;
        String myAprilTagName;
        double myAprilTagSize;
        //INCH myAprilTagUnit;
        if (myAprilTagDetection.metadata != null){
          myAprilTagID = myAprilTagDetection.metadata.id;
          myAprilTagName = myAprilTagDetection.metadata.name;
          myAprilTagSize = myAprilTagDetection.metadata.tagsize;
          //myAprilTagUnits = myAprilTagDetection.metadata.units;
          // Use the detection's data
          double tagPoseX = myAprilTagDetection.ftcPose.x;
          double tagPoseY = myAprilTagDetection.ftcPose.y;
          double tagPoseZ = myAprilTagDetection.ftcPose.z;
          //int myAprilTagIdCode = myAprilTagDetection.id;
          telemetry.addData("PoseX", tagPoseX);
          telemetry.addData("PoseY", tagPoseY);
          telemetry.addData("PoseZ", tagPoseZ);
          telemetry.update();
        }
      }
    
    }
    }
  }

