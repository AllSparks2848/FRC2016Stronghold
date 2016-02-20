package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SparkyIntakeBar {
	
	private static final int MIDDLEDELAY = 1500;
	private static double start;
	public static int position;
	private static boolean middlestarted = false;
	private static boolean lastbutton5 = false;
	private static boolean lastaxis2 = false;
	public static int lastintakeposition;
	
	public static void loadingRoutine(){
		if (Definitions.xbox2.getRawAxis(1) > 0.25 && position == 0){
			position = 2;
		}
		else if (Definitions.xbox1.getRawButton(5)){
			position = 1;
		}
		else if (Definitions.xbox1.getRawAxis(2) > 0.75){
			position = 2;
		}
		else if (Definitions.xbox1.getRawButton(2)){
			position = 0;
		}
		
		if (position == 0){
			Definitions.intakesolenoid.set(Value.kReverse);
			Definitions.intakepancake.set(Value.kForward);
			lastintakeposition = 0;
		}
		else if (position == 1 && !middlestarted && lastintakeposition == 2){
			Definitions.intakesolenoid.set(Value.kReverse);
			Definitions.intakepancake.set(Value.kReverse);
			middlestarted = true;
			start = System.currentTimeMillis();
		}
		else if ((position == 1 && System.currentTimeMillis() >= start + MIDDLEDELAY) || lastintakeposition == 0){
			Definitions.intakesolenoid.set(Value.kForward);
			Definitions.intakepancake.set(Value.kForward);
			middlestarted = false;
			lastintakeposition = 1;
		}
		else if (position == 2){
			Definitions.intakesolenoid.set(Value.kForward);
			Definitions.intakepancake.set(Value.kReverse);
			lastintakeposition = 2;
		}
		if (position != 1){
			middlestarted = false;
		}
		if (Definitions.xbox2.getRawButton(5) && !lastbutton5){
			Definitions.intakewheel.set(-0.75);
			lastbutton5 = true;
		}
		else if (Definitions.xbox2.getRawAxis(2) > 0.75 && !lastaxis2){
			Definitions.intakewheel.set(0.75);
			lastaxis2 = true;
		}
		else if (!Definitions.xbox2.getRawButton(5) && Definitions.xbox2.getRawAxis(2) <= 0.75){
			Definitions.intakewheel.set(0);
		}
		if (!Definitions.xbox2.getRawButton(5)){
			lastbutton5 = false;
		}
		if (Definitions.xbox2.getRawAxis(2) <= 0.75){
			lastaxis2 = false;
		}
	}
	public static void intakeInit(){
		if (Definitions.intakesolenoid.get() == Value.kReverse) {
			lastintakeposition = 0;
		}
		else {
			if (Definitions.intakepancake.get() == Value.kForward){
				lastintakeposition = 1;
			}
			else {
				lastintakeposition = 2;
			}
		}
	}
}