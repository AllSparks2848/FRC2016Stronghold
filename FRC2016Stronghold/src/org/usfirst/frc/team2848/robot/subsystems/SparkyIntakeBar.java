package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

public class SparkyIntakeBar {
	private static boolean loading = false;
	private static double start;
	public static boolean loaded = true;
	public static void startingPosition(){
		Robot.intakepancake.set(false);
		Robot.leftintakesolenoid.set(false);
		Robot.rightintakesolenoid.set(false);
	}
	public static void middlePosition(){
		Robot.intakepancake.set(true);
		Robot.leftintakesolenoid.set(true);
		Robot.rightintakesolenoid.set(true);
	}
	public static void bottomPosition(){
		Robot.intakepancake.set(false);
		Robot.leftintakesolenoid.set(true);
		Robot.rightintakesolenoid.set(true);
	}
	public static void loadingRoutine(){
		if (Robot.xbox1.getRawButton(3)){
			startingPosition();
		}
		if (Robot.xbox1.getRawButton(4)){
			middlePosition();
		}
		if (Robot.xbox1.getRawButton(5)){
			bottomPosition();
		}
	}
}
