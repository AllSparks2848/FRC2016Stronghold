package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

public class SparkyIntakeBar {
	private static boolean loading = false;
	private static double start;
	public static boolean loaded = false;
	public static void startingPosition(){
		Robot.leftintakepancake.set(false);
		Robot.rightintakepancake.set(false);
		Robot.leftintakesolenoid.set(false);
		Robot.rightintakesolenoid.set(false);
	}
	public static void middlePosition(){
		Robot.leftintakepancake.set(true);
		Robot.rightintakepancake.set(true);
		Robot.leftintakesolenoid.set(true);
		Robot.rightintakesolenoid.set(true);
	}
	public static void bottomPosition(){
		Robot.leftintakepancake.set(false);
		Robot.rightintakepancake.set(false);
		Robot.leftintakesolenoid.set(true);
		Robot.rightintakesolenoid.set(true);
	}
	public static void load(){
		Robot.intakewheel.set(0.5);
		loaded = true;
	}
	public static void isLoading(){
		if (Robot.xbox1.getRawButton(2)){
			loading = true;
		}
	}
	public static void loadingRoutine(){
		if (loading){
			startingPosition();
			
		}
	}
}
