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

@TeleOp(name="First try", group="Linear Opmode")

public class Newtest extends LinearOpMode{

    private DcMotor backl;
    private DcMotor backr;
    
    @Override
    public void runOpMode() {
    
        backl = hardwareMap.get(DcMotor.class, "drivel");
        backr = hardwareMap.get(DcMotor.class, "driver");
        
        waitForStart();
        //runtime.reset();
        
    while (opModeIsActive()){
        // Left stick drives forward/back
            double drive = gamepad1.right_stick_x;

            // Right stick turns robot
            double turn = -gamepad1.left_stick_y;

            // Mix drive + turn
            double leftPower  = drive + turn;
            double rightPower = drive - turn;

            // Apply power
            backr.setPower(leftPower);
            backl.setPower(rightPower);

            telemetry.addData("drivel (left)", leftPower);
            telemetry.addData("driver (right)", rightPower);
            telemetry.update();
        }
    }
}
    