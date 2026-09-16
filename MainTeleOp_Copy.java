/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Thread;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Copy of Basic Op", group="Linear OpMode")

public class MainTeleOp_Copy extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive   = null;
    private DcMotor rightFrontDrive  = null;
    private DcMotor leftBackDrive  = null;
    private DcMotor rightBackDrive  = null;
    private DcMotor launchMotor = null;
    private Servo gateServo = null;
    private DcMotor actuator = null;
    private CRServo intake = null;
    boolean launchstabilizer= false;
    double servoGateClosed = 0;
    double servoGateOpen = 90;
    boolean actuatorPowSign = false;
    boolean upPult = false;
    boolean downPult = false;
    boolean justOnce = true;
    boolean runToPosition = false;
    double pPower = 0;
    double revolutions = 0;
    double amountToGoBack = 750;
    
    //Servos
    public void servoToPositionClosed (){
        gateServo.setDirection(Servo.Direction.REVERSE);
        gateServo.setPosition(servoGateClosed);
    }
    
    public void servoToPositionOpen (){
        gateServo.setDirection(Servo.Direction.FORWARD);
        gateServo.setPosition(servoGateClosed);
    }
    
    public void servoToPositionOpenTwo (boolean tPower){
        if (tPower == true){
            pPower = 0.6;
        }
        else{
            pPower = 0;
        }
        intake.setPower(pPower);
        //intake.setPosition(servoGateClosed);
    }
    
    //public void servoToPositionOpenThree (double tPower){
        //intake.setPower(tPower);
        //intake.setPosition(servoGateClosed);
    //}
    
    //Actuator
    public void actuatorPower(double aPower){
        if (actuatorPowSign){
            actuator.setPower(-aPower);
        }
        else{
        actuator.setPower(aPower);
        }
    }
    
    // public void motorToPositionPrep (){
    //     if (launchMotor.getPower() == 0){
    //             launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //     }
    //     //boolean launchstabilizer= true;
    //     launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //     launchMotor.setPower(-0.9);
    //     launchMotor.setTargetPosition(-370);
    //     launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //     launchMotor.setPower(-0.6);
    //     //launchMotor.setMode(DcMotor.RunMode.STOP_USING_ENCODER);
    // }
    
    // public void motorToPositionUp (){
    //     if (launchMotor.getPower() == 0){
    //             launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //     }
    //     //boolean launchstabilizer= false;
    //     launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //     launchMotor.setPower(-0.9);
    //     launchMotor.setTargetPosition(0);
    //     launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //     launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    // }
    
    // public void setLaucherPower(double launcherPower){
    //     launchMotor.setPower(-launcherPower);
    // }
    
    public void resetEncoder(){
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        revolutions = 0;
    }
    
    public void prepDown(){
        launchMotor.setPower(4);
    }
    
    public void launchBall(double input){
        // setLaucherPower(input);
        //launchMotor.resetPosition();
        //double motorPos = launchMotor.getCurrentPosition();
        //telemetry.addData("TriggerPosition", "Input:" + input);
        //telemetry.addData("MotorPosition", motorPos);
        
        launchMotor.setPower(input);
    }
    
    public void setDrivePower(double leftFrontPower, double rightFrontPower, double rightBackPower, double leftBackPower) {
            // Output the values to the motor drives.
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            rightBackDrive.setPower(rightBackPower);
            leftBackDrive.setPower(leftBackPower);
        }   
        
        public void driveRobot(double axial, double lateral, double yaw) {
            // Combine drive and turn for blended motion.
            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = axial - lateral - yaw;
            double rightFrontPower = axial + lateral - yaw;
            double leftBackPower   = axial + lateral + yaw;
            double rightBackPower  = axial - lateral + yaw;
            
            
        
    
            // Scale the values so neither exceed +/- 1.0
            //double max = Math.max(Math.abs(leftFrontDrive), Math.abs(leftBackDrive), Math.abs(rightBackDrive), Math.abs(rightFrontDrive));
            //if (max > 1.0)
            //{
              //  leftFrontPower /= max;
                //rightFrontPower /= max;
                //rightBackPower /= max;
                //leftBackPower /= max;
            //}

        // Use existing function to drive both wheels.
        setDrivePower(leftFrontPower, rightFrontPower, rightBackPower, leftBackPower);
        }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "drivefl");//port 0
        rightFrontDrive = hardwareMap.get(DcMotor.class, "drivefr");//port 1
        leftBackDrive = hardwareMap.get(DcMotor.class, "drivebl");//port 2
        rightBackDrive = hardwareMap.get(DcMotor.class, "drivebr");//port 3
        launchMotor = hardwareMap.get(DcMotor.class, "launcher");
        actuator= hardwareMap.get(DcMotor.class, "actuator1");
        gateServo = hardwareMap.get(Servo.class, "gateservo");
        intake = hardwareMap.get(CRServo.class, "intake");
        //launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);//FORWARD
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        actuator.setDirection(DcMotor.Direction.FORWARD);
        launchMotor.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();
        
        

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (runToPosition == true){
                launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            
            if (justOnce == true) {
                resetEncoder();
                justOnce = false;
            }
            // Setup a variable for each drive wheel to save power level for telemetry

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            
            if (gamepad1.dpad_up){
                resetEncoder();
            }
            
            if (gamepad1.dpad_down){
                actuatorPowSign = false;
            }
            
            if (gamepad1.dpad_left){
                servoToPositionOpenTwo(true);
            }
            
            if (gamepad1.dpad_right){
                servoToPositionOpenTwo(false);
            }
            
            actuatorPower(gamepad1.left_trigger);
            
            
            if (gamepad1.a == true){
                servoToPositionClosed();
            }
            
            else if (gamepad1.b == true){
                servoToPositionOpen();
            }
            
            if (gamepad1.y == true){
                upPult = true;
                //  if (upPult == false){
                //      upPult = true;
                //     }
                // else if (upPult == true){
                //       upPult = false;
                //     }
                }
                
            else if (gamepad1.x == true){
                upPult = false;
                downPult = true;
                //telemetry.addData("X is on");
                
            }
              
            if (upPult == true){ //resolution = 1,425.1
                launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                //launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //while(launchMotor.getCurrentPosition() != 400){
                    //launchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                    launchMotor.setPower(1);
                    launchMotor.setTargetPosition((int) (amountToGoBack + ((revolutions * 1425.1) - launchMotor.getCurrentPosition()) + (revolutions * 1425.1)));
                    double bigThing = (revolutions * 1425.1) - launchMotor.getCurrentPosition();
                    while (gamepad1.x != true){ //launchMotor.getCurrentPosition() < 459 || launchMotor.getCurrentPosition() > 461
                        driveRobot(gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x);
                        servoToPositionOpenTwo(gamepad1.dpad_left);
                        launchMotor.setPower(1);
                        launchMotor.setTargetPosition((int) (amountToGoBack + bigThing + (revolutions * 1425.1)));
                        launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                    
                    //launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    //launchMotor.setPower(0.1);
                    //launchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    //
                    //runToPosition = true;
                //}
                //downPult = true;
                upPult = false;
                downPult = true;
                //launchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                //launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                //launchMotor.setPower(0.6);
                // launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                // try{
                //     Thread.sleep(100);
                // } catch (InterruptedException e) {
                // // Handle the case where another thread interrupts the sleeping thread
                // Thread.currentThread().interrupt();
                // }
                //launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                //launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            
            if (downPult == true){
                //launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                //launchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                //runToPosition = false;
                //launchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                while(launchMotor.getCurrentPosition() < (1424 + (revolutions * 1425.1))){
                    launchMotor.setPower(1);
                    launchMotor.setTargetPosition((int) (1425.1 + (revolutions * 1425.1)));
                    launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                
                // try{
                //     Thread.sleep(500);
                // } catch (InterruptedException e) {
                // // Handle the case where another thread interrupts the sleeping thread
                // Thread.currentThread().interrupt();
                // }
                // launchMotor.setPower(0);
                // try{
                //     Thread.sleep(10);
                // } catch (InterruptedException e) {
                //     // Handle the case where another thread interrupts the sleeping thread
                // Thread.currentThread().interrupt();
                // }
                //launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                downPult = false;
                revolutions += 1;
            }
            else{
                launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                launchBall(gamepad1.right_trigger);
            }
            
            //driveRobot(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
            driveRobot(gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x);
            // else if (gamepad1.x == true){
            //     downPult = true ;
            // }
            
            //else{
              // launchMotor.setPower(launchMotor.getCurrentPosition()/4000);
            //}
            
            //if (launchMotor.getCurrentPosition() > 4000){
              //  launchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //}
            
            // if (launchstabilizer == true){
            //     launchMotor.setPower(0.2);
            // }
            
            //double launchPower = gamepad1.right_trigger;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            //leftDrive.setPower(leftPower);
            //rightDrive.setPower(rightPower);
            
            // Send power to the choo choo catapult
            //launchMotor.setPower(launchPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("downPult is true", "variable: " + downPult);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Encoder", "Position:" + launchMotor.getCurrentPosition());
            telemetry.addData("Encoder", "Motor Power:" + launchMotor.getPower());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            //telemetry.addData("Launch Power (%.2f)", launchPower);
            telemetry.update();
        }
    }
}
//.update();
 //       }
  //  }
//}
