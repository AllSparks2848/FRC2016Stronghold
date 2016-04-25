package org.usfirst.frc.team2848.robot.navigation;

import org.usfirst.frc.team2848.robot.Definitions;

public class LidarDrive {
	public static double lidarDrive(double targetdistance, double turnval) {
		Definitions.lidar.update();
		double distance = Definitions.lidar.getDistance();
		Definitions.lidardrivepid.setTarget(targetdistance);
		
		Definitions.drivetrain.arcadeDrive(-Definitions.lidardrivepid.compute(distance, null), turnval);
		
		return distance;
	}
}
