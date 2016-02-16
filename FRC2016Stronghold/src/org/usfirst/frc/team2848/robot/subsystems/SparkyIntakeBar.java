package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SparkyIntakeBar {
	public static void startingPosition(){
		Definitions.intakepancake.set(Value.kReverse);
		Definitions.intakesolenoid.set(Value.kReverse);
	}
	public static void middlePosition(){
		Definitions.intakepancake.set(Value.kForward);
		Definitions.intakesolenoid.set(Value.kForward);
	}
	public static void bottomPosition(){
		Definitions.intakepancake.set(Value.kReverse);
		Definitions.intakesolenoid.set(Value.kForward);
	}
	public static void loadingRoutine(){
		if (Definitions.xbox2.getRawButton(3)){
			startingPosition();
		}
		if (Definitions.xbox2.getRawButton(4)){
			middlePosition();
		}
		if (Definitions.xbox2.getRawButton(5)){
			bottomPosition();
		}
	}
}
