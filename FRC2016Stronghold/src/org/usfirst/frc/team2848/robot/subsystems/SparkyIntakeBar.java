package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SparkyIntakeBar {
	
	private static final int MIDDLEDELAY = 500;
	
	private static int laststate = 0;
	private static double start;
	private static boolean middle = false;
	private static boolean lasta = false;
	private static boolean lastb = false;
	
	
//	public static void startingPosition(){
//		Definitions.intakepancake.set(Value.kReverse);
//		Definitions.intakesolenoid.set(Value.kReverse);
//		middle = false;
//		laststate = 0;
//	}
//	public static void middlePosition(){
//		if(laststate == 2) {
//			if (!middle) {
//				Definitions.intakesolenoid.set(Value.kReverse);
//				Definitions.intakepancake.set(Value.kForward);
//				start = System.currentTimeMillis();
//				middle = true;
//				if (System.currentTimeMillis() - start > MIDDLEDELAY){
//					Definitions.intakesolenoid.set(Value.kForward);
//					laststate = 1;
//					middle = false;
//				}
//			}
//		}
//		else {
//			Definitions.intakesolenoid.set(Value.kForward);
//			Definitions.intakesolenoid.set(Value.kForward);
//			laststate = 1;
//			middle = false;
//		}
//	}
//	public static void bottomPosition(){
//		Definitions.intakepancake.set(Value.kReverse);
//		Definitions.intakesolenoid.set(Value.kForward);
//		middle = false;
//		laststate = 2;
//	}
	public static void loadingRoutine(){
		if (Definitions.xbox2.getRawButton(1) && !lasta){
			if (Definitions.intakesolenoid.get() != Value.kReverse){
				Definitions.intakesolenoid.set(Value.kReverse);
			}
			else if (Definitions.intakesolenoid.get() != Value.kForward){
				Definitions.intakesolenoid.set(Value.kForward);
			}
			lasta = true;
		}
		else if (!Definitions.xbox2.getRawButton(1)){
			lasta = false;
		}
		
		if (Definitions.xbox2.getRawButton(2) && !lastb){
			if (Definitions.intakepancake.get() != Value.kReverse){
				Definitions.intakepancake.set(Value.kReverse);
			}
			else if (Definitions.intakepancake.get() != Value.kForward){
				Definitions.intakepancake.set(Value.kForward);
			}
			lastb = true;
		}
		else if (!Definitions.xbox2.getRawButton(2)){
			lastb = false;
		}
		if (Definitions.xbox2.getRawButton(6)){
			Definitions.intakewheel.set(0.75);
		}
		else if (Definitions.xbox2.getRawButton(4)){
			Definitions.intakewheel.set(-0.75);
		}
		else {
			Definitions.intakewheel.set(0);
		}
	}
}