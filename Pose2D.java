package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Pose2D {
    public double x; // in millimeters
    public double y; // in millimeters
    public double heading; // in radians

    public Pose2D(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading; // Stored in radians
    }

    public Pose2D(DistanceUnit distanceUnit, double x, double y, AngleUnit angleUnit, double heading) {
        this.x = distanceUnit.toMm(x); // Convert x to millimeters
        this.y = distanceUnit.toMm(y); // Convert y to millimeters
        this.heading = angleUnit.toRadians(heading); // Convert heading to radians
    }

    public double getX(DistanceUnit unit) {
        return unit.fromMm(x);
    }

    public double getY(DistanceUnit unit) {
        return unit.fromMm(y);
    }

    public double getHeading(AngleUnit unit) {
        return unit.fromRadians(heading);
    }
}

