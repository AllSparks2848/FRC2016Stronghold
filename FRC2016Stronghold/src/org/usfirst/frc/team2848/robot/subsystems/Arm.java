package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

public class Arm {
	
	public static void controlArm() {
		if (Robot.xbox1.getPOV() == 3){
			Robot.armmotor.set(-.5);
		}
		else {
			Robot.armmotor.set(0);
		}
	}
}
