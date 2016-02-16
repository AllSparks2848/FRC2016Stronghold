package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class SparkyIntakeBar {
	private static boolean loading = false;
	private static double start;
	public static boolean loaded = true;
	public static void startingPosition(){
		Robot.intakepancake.set(DoubleSolenoid.Value.kReverse);
		Robot.intakesolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	public static void middlePosition(){
		Robot.intakepancake.set(DoubleSolenoid.Value.kForward);
		Robot.intakesolenoid.set(DoubleSolenoid.Value.kForward);
	}
	public static void bottomPosition(){
		Robot.intakepancake.set(DoubleSolenoid.Value.kReverse);
		Robot.intakesolenoid.set(DoubleSolenoid.Value.kForward);
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
