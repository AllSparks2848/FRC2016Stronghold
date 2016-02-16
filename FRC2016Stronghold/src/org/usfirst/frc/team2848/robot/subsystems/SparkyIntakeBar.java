package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SparkyIntakeBar {
	public static void startingPosition(){
		Robot.intakepancake.set(Value.kReverse);
		Robot.intakesolenoid.set(Value.kReverse);
	}
	public static void middlePosition(){
		Robot.intakepancake.set(Value.kForward);
		Robot.intakesolenoid.set(Value.kForward);
	}
	public static void bottomPosition(){
		Robot.intakepancake.set(Value.kReverse);
		Robot.intakesolenoid.set(Value.kForward);
	}
	public static void loadingRoutine(){
		if (Robot.xbox2.getRawButton(3)){
			startingPosition();
		}
		if (Robot.xbox2.getRawButton(4)){
			middlePosition();
		}
		if (Robot.xbox2.getRawButton(5)){
			bottomPosition();
		}
	}
}
