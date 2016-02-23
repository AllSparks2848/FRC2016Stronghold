package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SparkyIntakeBar {
	
	private static final int MIDDLEDELAY = 500;
	private static final int BOTTOMDELAY = 250;
	private static double start;
	private static double bottomstart;
	public static int position = 17;
	private static boolean bottomstarted = false;
	private static boolean middlestarted = false;
	private static int lastintakeposition = 17;
	
	public static void loadingRoutine(){
		if (Definitions.xbox1.getRawButton(5)){
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
			Definitions.intakepancake.set(Value.kReverse);
			lastintakeposition = 0;
		}
		else if (position == 1 && !middlestarted && lastintakeposition != 1 && lastintakeposition != 0){
			Definitions.intakesolenoid.set(Value.kReverse);
			Definitions.intakepancake.set(Value.kReverse);
			middlestarted = true;
			start = System.currentTimeMillis();
		}
		else if (position == 1 && (System.currentTimeMillis() >= start + MIDDLEDELAY || lastintakeposition == 0)){
			Definitions.intakesolenoid.set(Value.kForward);
			Definitions.intakepancake.set(Value.kForward);
			middlestarted = false;
			lastintakeposition = 1;
		}
		else if (position == 2 && !bottomstarted && lastintakeposition != 0 && lastintakeposition != 2 ){
			Definitions.intakesolenoid.set(Value.kReverse);
			Definitions.intakepancake.set(Value.kReverse);
			bottomstart = System.currentTimeMillis();
			bottomstarted = true;
		}
		else if (position == 2 && (System.currentTimeMillis() >= bottomstart + BOTTOMDELAY || lastintakeposition == 0)){
			Definitions.intakesolenoid.set(Value.kForward);
			Definitions.intakepancake.set(Value.kReverse);
			bottomstarted = false;
			lastintakeposition = 2;
		}
		if (position != 1){
			middlestarted = false;
		}
		if (position != 2){
			bottomstarted = false;
		}
		if (Definitions.xbox2.getRawButton(5)){
			if (Definitions.leftshooterpid.getEnabled()){
				Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
				Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
			}
			Definitions.leftshooter.set(-0.4);
			Definitions.rightshooter.set(-0.4);	
			Definitions.intakewheel.set(-0.75);
		}
//		else if (Definitions.xbox2.getRawAxis(2) > 0.75){
//			if (Definitions.leftshooterpid.getEnabled()){
//				Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
//				Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
//			}
//			Definitions.leftshooter.set(0.4);
//			Definitions.rightshooter.set(0.4);	
//			Definitions.intakewheel.set(0.75);
//		}
//		else if (Definitions.xbox1.getRawButton(3)){
//			if (Definitions.leftshooterpid.getEnabled()){
//				Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
//				Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
//			}
//			Definitions.leftshooter.set(-0.4);
//			Definitions.rightshooter.set(-0.4);	
//			Definitions.intakewheel.set(-0.75);
//		}
//		else if (Definitions.xbox1.getRawButton(1)){
//			if (Definitions.leftshooterpid.getEnabled()){
//				Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
//				Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
//			}
//			Definitions.leftshooter.set(0.4);
//			Definitions.rightshooter.set(0.4);	
//			Definitions.intakewheel.set(0.75);
//		}
//		if (!Definitions.xbox2.getRawButton(5) && Definitions.xbox2.getRawAxis(2) <= 0.75 && !Definitions.xbox1.getRawButton(1) && !Definitions.xbox1.getRawButton(3)){
//			Definitions.intakewheel.set(0);
//		}

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