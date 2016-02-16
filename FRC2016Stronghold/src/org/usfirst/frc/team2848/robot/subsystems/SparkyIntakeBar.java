package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SparkyIntakeBar {
	public static void startingPosition(){
		Robot.intakepancake.set(Value.kReverse);
		Robot.leftintakesolenoid.set(Value.kReverse);
		Robot.rightintakesolenoid.set(Value.kReverse);
	}
	public static void middlePosition(){
		Robot.intakepancake.set(Value.kForward);
		Robot.leftintakesolenoid.set(Value.kForward);
		Robot.rightintakesolenoid.set(Value.kForward);
	}
	public static void bottomPosition(){
		Robot.intakepancake.set(Value.kReverse);
		Robot.leftintakesolenoid.set(Value.kForward);
		Robot.rightintakesolenoid.set(Value.kForward);
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
