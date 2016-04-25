package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.robot.States;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class SparkyIntakeBar {
	
	private static final int MIDDLEDELAY = 1000;
	private static final int BOTTOMDELAY = 500;
	private static double start;
	private static double bottomstart;
	public static int position = 17;
	private static boolean bottomstarted = false;
	private static boolean middlestarted = false;
	public static int lastintakeposition = 17;
	private static int intakethreshold = 7;
	private static boolean lastbutton16 = false;
	private static int intakechannel = 4;
	
	public static void loadingRoutine(){
		if (Definitions.xbox1.getRawButton(5) || Definitions.buttonbox.getRawButton(15)){
			position = 1;
		}
		else if (Definitions.xbox1.getRawAxis(2) > 0.75 || Definitions.buttonbox.getRawButton(6)){
			position = 2;
		}
		else if (Definitions.xbox1.getRawButton(2) || Definitions.buttonbox.getRawButton(12)){
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
		
		//spit out
		if (Definitions.buttonbox.getRawButton(13) || Definitions.xbox1.getRawButton(3)){	
			Definitions.intakewheel.set(0.75);
			
		}
		
		//intake in with current checks
		else if (Definitions.buttonbox.getRawButton(16) || Definitions.xbox1.getRawButton(1)){
			if (!lastbutton16){	
				Definitions.intakewheel.set(-0.5);
				
			}
			if (Definitions.pdp.getCurrent(intakechannel) > intakethreshold){
				Definitions.intakewheel.set(0);
			}
		}
		
		//transfer
		else if (Definitions.buttonbox.getRawButton(5) || Definitions.xbox1.getRawButton(4)){
			Definitions.intakewheel.set(-1);
		}
		else {
			Definitions.intakewheel.set(0);
		}
		lastbutton16 = Definitions.buttonbox.getRawButton(16) || Definitions.xbox1.getRawButton(1);
	}

}