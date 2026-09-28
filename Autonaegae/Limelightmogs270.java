package org.firstinspires.ftc.teamcode.Autonaegae;

import static java.lang.Math.tan;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.limelightvision.LLResult;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import static java.lang.Math.sqrt;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.limelightvision.LLResult;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import com.qualcomm.hardware.limelightvision.LLFieldMap;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.LLFieldMap.Fiducial;
//import com.qualcomm.robotcore.hardware.limelight;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous(name = "trying with the limelight")
public class Limelightmogs270 extends LinearOpMode {

    private Limelight3A limelight;
    //boolean hasTarget = LimelightHelpers.getTV();
    private DcMotor leftFrontDrive   = null;
    private DcMotor rightFrontDrive  = null;
    private DcMotor leftBackDrive  = null;
    private DcMotor rightBackDrive  = null;
    private DcMotor launchMotor = null;
    private CRServo intake = null;
    double revolutions = 0;
    double amountToGoBack = 750;
    boolean intakeIsRotated = false;
    //double distToCamera = fiducial.distToCamera;  // Distance to camera
    //double txnc = fiducial.txnc; //x offset from apriltag
    
    public void launchBall(double input){
        launchMotor.setPower(input);
    }
    
    public double ligmaDistanceCalc(double percentSize){
        double trueLength = 165.1; //In mm
        double cameraHorizontalResolution = 960; //960x720
        double entireCameraRez = 960 * 720;
        double cameraHorizontalFOV = 0.9512044; //radians
        double truePixelSize = percentSize * entireCameraRez;
        double pixelLength = Math.sqrt(truePixelSize);
        double calculation = ((trueLength * cameraHorizontalResolution)/ (2*pixelLength) * tan(cameraHorizontalFOV/2));
        return calculation;
    }
    
    public void setDrivePower(double leftFrontPower, double rightFrontPower, double rightBackPower, double leftBackPower) {
            // Output the values to the motor drives.
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            rightBackDrive.setPower(rightBackPower);
            leftBackDrive.setPower(leftBackPower);
        }   
    
    public void driveRobot(double axial, double lateral, double yaw) {
                         //Forward back,        straf         turn
            // Combine drive and turn for blended motion.
            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = axial - lateral - yaw;
            double rightFrontPower = axial + lateral - yaw;
            double leftBackPower   = axial + lateral + yaw;
            double rightBackPower  = axial - lateral + yaw;
            setDrivePower(leftFrontPower, rightFrontPower, rightBackPower, leftBackPower);
    }
    
    public void goToApriltag(double thisCalc){
        while (thisCalc > 1500){
            double dif = thisCalc - 1500;
            driveRobot(dif/1000, 0, 0);
        }
        
    }
    //private IMU imu;
    
    public void runOpMode(){ 
        
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "drivefl");//port 0
        rightFrontDrive = hardwareMap.get(DcMotor.class, "drivefr");//port 1
        leftBackDrive = hardwareMap.get(DcMotor.class, "drivebl");//port 2
        rightBackDrive = hardwareMap.get(DcMotor.class, "drivebr");//port 3
        launchMotor = hardwareMap.get(DcMotor.class, "launcher");
        intake = hardwareMap.get(CRServo.class, "intake");
        
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);//FORWARD
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        launchMotor.setDirection(DcMotor.Direction.REVERSE);
        
        limelight = hardwareMap.get(Limelight3A.class, "Ethernet Device");
        limelight.pipelineSwitch(0);
        
        //imu = hardwareMap.get(IMU.class, "imu");
        
        limelight.start();
        waitForStart();
        while (opModeIsActive()) {
            LLResult llresult = limelight.getLatestResult();
            if (llresult != null && llresult.isValid()){
                Pose3D botpose = llresult.getBotpose();
                double currentYaw = botpose.getOrientation().getYaw();
                double strafe = 0.001 / llresult.getTa() - 1;
                double forwardBack = -llresult.getTx() * 0.01;
                double turn = ((currentYaw + 45) * 0.01) / llresult.getTx();
                //0.003, 0.04
                driveRobot(strafe, forwardBack, turn); //-botpose.getOrientation().getYaw() * 0.03)
                //setDrivePower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
                telemetry.addData("Tx", llresult.getTx());
                telemetry.addData("Ty", llresult.getTy());
                telemetry.addData("Ta", llresult.getTa());
                if ((strafe < 0.1) && (forwardBack < 0.1) && (turn < 0.1) ){
                    launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    launchMotor.setPower(1);
                    double bigThing = (revolutions * 1425.1) - launchMotor.getCurrentPosition();
                    double targetPos = (int) ((amountToGoBack + ((revolutions * 1425.1) - launchMotor.getCurrentPosition()) + (revolutions * 1425.1)));
                    while (targetPos != launchMotor.getCurrentPosition()){ //launchMotor.getCurrentPosition() < 459 || launchMotor.getCurrentPosition() > 461
                        strafe = 0.001 / llresult.getTa() - 1;
                        forwardBack = -llresult.getTx() * 0.01;
                        turn = ((currentYaw + 45) * 0.01) / llresult.getTx();
                        driveRobot(strafe, forwardBack, turn);
                        launchMotor.setPower(1);
                        launchMotor.setTargetPosition((int) (amountToGoBack + bigThing + (revolutions * 1425.1)));
                        launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                    while (intakeIsRotated == false){
                        for (int i=0; i > 1000 ; i++){
                        //launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        intake.setPower(0.6);
                        //launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        launchMotor.setPower(1);
                        //launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        launchMotor.setTargetPosition((int) (amountToGoBack + bigThing + (revolutions * 1425.1)));
                        launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        try{
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                        // Handle the case where another thread interrupts the sleeping thread
                        Thread.currentThread().interrupt();
                        }
                        }
                        intakeIsRotated = true;
                    }
                    while(launchMotor.getCurrentPosition() < (1424 + (revolutions * 1425.1))){
                        launchMotor.setPower(1);
                        launchMotor.setTargetPosition((int) (1425.1 + (revolutions * 1425.1)));
                        launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                    revolutions += 1;
                }
            }
            else {
                telemetry.addLine("No valid target");
                driveRobot(0, 0, 0);
            }

            telemetry.update();
            sleep(10); //25
        }
    }
}
