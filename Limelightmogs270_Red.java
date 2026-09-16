package org.firstinspires.ftc.teamcode.Autonaegae;

import static java.lang.Math.tan;
import com.qualcomm.robotcore.hardware.Servo;
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
import com.qualcomm.hardware.limelightvision.LLFieldMap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
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

@Autonomous(name = "limelight shoot (red)")
public class Limelightmogs270_Red extends LinearOpMode {

    private Limelight3A limelight;
    //boolean hasTarget = LimelightHelpers.getTV();
    private DcMotor leftFrontDrive   = null;
    private DcMotor rightFrontDrive  = null;
    private DcMotor leftBackDrive  = null;
    private DcMotor rightBackDrive  = null;
    private DcMotor launchMotor = null;
    private Servo intake = null;
    double revolutions = 0;
    double amountToGoBack = 1125;//1000
    double incriment = 0;
    double counter = 0;
    int ohNo = 0;
    boolean intakeIsRotated = false;
    boolean intakeIsRotated2 = false;
    boolean justOnce = true;
    boolean justOnce2 = false;
    boolean justOnce3 = true;
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
        intake = hardwareMap.get(Servo.class, "intake");
        
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
            if (justOnce == true){
                driveRobot(-0.3, 0, 0); 
                sleep(1200);
                driveRobot(0, 0, 0); 
                intake.setPosition(0);
                //launchMotor.setPosition(0);
                launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                revolutions = 0;
                justOnce = false;
            }
            LLResult llresult = limelight.getLatestResult();
            if (llresult != null && llresult.isValid()){
                llresult = limelight.getLatestResult();
                Pose3D botpose = llresult.getBotpose();
                double currentYaw = botpose.getOrientation().getYaw();
                double strafe = 0.001 / llresult.getTa() - 1;
                double turn = (llresult.getTx() - 0.5) * 0.02;
                double direction = llresult.getTx();
                double forwardBack = -llresult.getTx() * 0.01;
                intakeIsRotated = false;
                while ((direction > 0.5 || direction < -1.0)){
                    llresult = limelight.getLatestResult();
                    turn = (llresult.getTx() - 0.5) * 0.02;
                    
                    if (turn < 0.08 && turn > 0){
                        turn = 0.09;
                    }
                    else if (turn > -0.08 && turn < 0 ){
                        turn = -0.09;
                    }
    
                    direction = llresult.getTx();
                    //0.003, 0.04
                    driveRobot(0, 0, turn); //-botpose.getOrientation().getYaw() * 0.03)
                    //setDrivePower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
                    telemetry.addData("Tx", llresult.getTx());
                    telemetry.addData("Ty", llresult.getTy());
                    telemetry.addData("Ta", llresult.getTa());
                    telemetry.update();
                    //sleep(1);
                    
                    if (justOnce2 == true){
                        direction = 0;
                        justOnce2 = false;
                    }
                    sleep(5);
                }
                
                driveRobot(0, 0, 0);
                sleep(10);
                // if (justOnce3 == true){
                //     driveRobot(0, -0.3, 0);
                //     sleep(200);
                //     justOnce3 = false;
                // }
                // driveRobot(0, 0, 0);
                
                if ((direction < 0.5) && (direction > -1.0)){
                    driveRobot(0, 0, 0);
                    //driveRobot(0, -0.3, 0);
                    //sleep(400);
                    //driveRobot(0, 0, 0);
                    launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    launchMotor.setPower(1);
                    double bigThing = (revolutions * 1425.1) - launchMotor.getCurrentPosition();
                    double targetPos = (int) ((amountToGoBack + (revolutions * 1425.1)));//(revolutions * 1425.1) - launchMotor.getCurrentPosition()
                    while (targetPos != launchMotor.getCurrentPosition()){ //launchMotor.getCurrentPosition() < 459 || launchMotor.getCurrentPosition() > 461
                        //strafe = 0.001 / llresult.getTa() - 1;
                        //forwardBack = -llresult.getTx() * 0.01;
                        //turn = ((currentYaw + 45) * 0.01) / llresult.getTx();
                        //driveRobot(strafe, forwardBack, turn);
                        launchMotor.setPower(1);
                        launchMotor.setTargetPosition((int) (amountToGoBack + (revolutions * 1425.1))); //+ bigThing
                        launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        telemetry.addData("Arm back", launchMotor.getCurrentPosition());
                        telemetry.addData("Arm back", targetPos);
                    }
                    while (intakeIsRotated == false){
                        while (intake.getPosition() != 1){
                            intake.setPosition(incriment);
                            launchMotor.setTargetPosition((int) (amountToGoBack + (revolutions * 1425.1))); //+ bigThing
                            launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                            telemetry.addData("Servo is rotating", intake.getPosition());
                            telemetry.update();
                            incriment += 0.01;
                        }
                        intakeIsRotated = true;
                    }
                    while (intakeIsRotated2 == false){
                        incriment = 0.5;
                        while (intake.getPosition() != 0){
                        intake.setPosition(incriment);
                        launchMotor.setTargetPosition((int) (amountToGoBack + (revolutions * 1425.1))); //+ bigThing
                        launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        telemetry.addData("Servo is rotating2", intake.getPosition());
                        telemetry.update();
                        incriment -= 0.001;
                        }
                        intakeIsRotated2 = true;
                    }
                    while(launchMotor.getCurrentPosition() < (1424 + (revolutions * 1425.1))){
                        launchMotor.setPower(1);
                        launchMotor.setTargetPosition((int) (1425.1 + (revolutions * 1425.1)));
                        launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                    launchMotor.setPower(0);
                    revolutions += 1;
                    counter += 1;
                    incriment = 0;
                    justOnce2 = true;
                    if (counter >= 2){
                        driveRobot(-0.3, 0, 0);
                        sleep(1000);
                        driveRobot(0, 0, 0);
                        while(launchMotor.getCurrentPosition() != (1425.1 + (revolutions * 1425.1))){
                            launchMotor.setPower(1);
                            launchMotor.setTargetPosition((int) (1425.1 + (revolutions * 1425.1)));
                            launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        }
                        break;
                    }
                    intakeIsRotated = false;
                    intakeIsRotated2 = false;
                    sleep(500);
                }
            }
            else if (llresult.isValid() == false) {
                double rightLeft = 1;
                int COUNTER_MAX = 100;
                //April tag not detected
                while (llresult.isValid() == false){
                    for (int i = 0; i <= COUNTER_MAX; i++){
                        sleep(10);
                        llresult = limelight.getLatestResult();
                        telemetry.addLine("No valid target");
                        driveRobot(0, 0, 0.2 * rightLeft);
                            // try{
                            //     Thread.sleep((50+ohNo)/2);
                            // } catch (InterruptedException e) {
                            //     telemetry.addLine("Interrupted at lie 200");
                            //     telemetry.update();
                            // // Handle the case where another thread interrupts the sleeping thread
                            // Thread.currentThread().interrupt();
                            // }
                        sleep(50);
                        llresult = limelight.getLatestResult();
                        sleep(10);
                        if (llresult != null && llresult.isValid()){
                            i = COUNTER_MAX;
                            ohNo = 0;
                            rightLeft = 1;
                            driveRobot(0, 0, 0);
                            llresult = limelight.getLatestResult();
                            break;
                        }
                        // sleep(10);
                        // llresult = limelight.getLatestResult();
                        // telemetry.addLine("No valid target");
                        // driveRobot(0, 0, 0.4 * rightLeft);
                        // try{
                        //     Thread.sleep((50+ohNo)/2);
                        // } catch (InterruptedException e) {
                        //     telemetry.addLine("Interrupted at lie 200");
                        //     telemetry.update();
                        // // Handle the case where another thread interrupts the sleeping thread
                        // Thread.currentThread().interrupt();
                        // }
                        // llresult = limelight.getLatestResult();
                        // sleep(10);
                        // if (llresult != null && llresult.isValid()){
                        //     i = COUNTER_MAX;
                        //     ohNo = 0;
                        //     rightLeft = 1;
                        //     driveRobot(0, 0, 0);
                        //     llresult = limelight.getLatestResult();
                        //     break;
                        // }
                        // try{
                        //     Thread.sleep((50+ohNo)/2);
                        // } catch (InterruptedException e) {
                        //     telemetry.addLine("Interrupted at lie 217");
                        //     telemetry.update();
                        // // Handle the case where another thread interrupts the sleeping thread
                        // Thread.currentThread().interrupt();
                        // }
                        // telemetry.update();
                        // llresult = limelight.getLatestResult();
                        // sleep(10);
                        // if (llresult != null && llresult.isValid()){
                        //     i = COUNTER_MAX;
                        //     ohNo = 0;
                        //     rightLeft = 1;
                        //     driveRobot(0, 0, 0);
                        //     llresult = limelight.getLatestResult();
                        //     break;
                        else{
                            rightLeft *= 1.0;
                            ohNo += 50;
                            telemetry.addLine("No valid target");
                            telemetry.update();
                        }
                    }
            }
            }
            // else{
            //   telemetry.addLine("No valid target");
            //   telemetry.update();
            // }
            telemetry.update();
            sleep(10); //25
        }
    }

}
