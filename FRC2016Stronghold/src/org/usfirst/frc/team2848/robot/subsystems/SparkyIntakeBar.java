package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SparkyIntakeBar {
	private static double start;
	private static boolean middle = false;
	public static void startingPosition(){
		Definitions.intakepancake.set(Value.kReverse);
		Definitions.intakesolenoid.set(Value.kReverse);
		middle = false;
	}
	public static void middlePosition(){
		if (!middle) {
			Definitions.intakesolenoid.set(Value.kForward);
			start = System.currentTimeMillis();
			middle = true;
			if (System.currentTimeMillis() - 500 > start){
				Definitions.intakepancake.set(Value.kForward);
			}
		}
	}
	public static void bottomPosition(){
		Definitions.intakepancake.set(Value.kReverse);
		Definitions.intakesolenoid.set(Value.kForward);
		middle = false;
	}
	public static void loadingRoutine(){
		if (Definitions.xbox2.getRawButton(3)){
			startingPosition();
		}
		if (Definitions.xbox2.getRawButton(4) || middle){
			middlePosition();
		}
		if (Definitions.xbox2.getRawButton(5)){
			bottomPosition();
		}
	}
}
