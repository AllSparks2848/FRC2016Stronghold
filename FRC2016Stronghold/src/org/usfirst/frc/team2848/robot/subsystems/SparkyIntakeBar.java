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
	private static boolean pulsestarted = false;
	private static double pulsestart;
	private static boolean lastbutton9 = false;
	private static boolean pulsing = false;
	
	public static void loadingRoutine(){
		if (Definitions.xbox1.getRawButton(5) || Definitions.buttonbox.getRawButton(16)){
			position = 1;
		}
		else if (Definitions.xbox1.getRawAxis(2) > 0.75 || Definitions.buttonbox.getRawButton(5)){
			position = 2;
		}
		else if (Definitions.xbox1.getRawButton(2) || Definitions.buttonbox.getRawButton(13)){
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
//		if (Definitions.buttonbox.getRawButton(14)){
//			if (Definitions.leftshooterpid.getEnabled()){
//				Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
//				Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
//			}
//			Definitions.leftshooter.set(-0.6);
//			Definitions.rightshooter.set(-0.6);	
//			Definitions.intakewheel.set(-1);
////			Definitions.ptomotor2.set(-1);
//			Definitions.ptomotor1.set(-1);
//		}
//		else if (Definitions.buttonbox.getRawButton(11)){
//			if (Definitions.leftshooterpid.getEnabled()){
//				Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
//				Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
//			}
//			Definitions.leftshooter.set(0.6);
//			Definitions.rightshooter.set(0.6);	
//			Definitions.intakewheel.set(1);
////			Definitions.ptomotor2.set(1);
//			Definitions.ptomotor1.set(1);
//		}
//		else if (Definitions.xbox1.getRawButton(3)){
//			if (Definitions.leftshooterpid.getEnabled()){
//				Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
//				Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
//			}
//			Definitions.leftshooter.set(-0.6);
//			Definitions.rightshooter.set(-0.6);	
//			Definitions.intakewheel.set(-1);
////			Definitions.ptomotor2.set(-1);
//			Definitions.ptomotor1.set(-1);
//		}
//		else if (Definitions.xbox1.getRawButton(1)){
//			if (Definitions.leftshooterpid.getEnabled()){
//				Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
//				Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
//			}
//			Definitions.leftshooter.set(0.6);
//			Definitions.rightshooter.set(0.6);	
//			Definitions.intakewheel.set(1);
////			Definitions.ptomotor2.set(1);
//			Definitions.ptomotor1.set(1);
//		}
		if (Definitions.buttonbox.getRawButton(11) || Definitions.xbox1.getRawButton(3)){
			Definitions.leftshooter.set(0);
			Definitions.rightshooter.set(0);	
			Definitions.intakewheel.set(0.75);
//			Definitions.ptomotor2.set(1);
			Definitions.ptomotor1.set(0.75);
			
		}
		else if (Definitions.buttonbox.getRawButton(14) || Definitions.xbox1.getRawButton(1)){
			Definitions.leftshooter.set(0);
			Definitions.rightshooter.set(0);	
			Definitions.intakewheel.set(-0.6);
//			Definitions.ptomotor2.set(1);
			Definitions.ptomotor1.set(-0.6);
		}
		else if (Definitions.buttonbox.getRawButton(7)){
			if (!lastbutton9){
				start = System.currentTimeMillis();
				pulsing = true;
			}
			if (pulsing){
				Definitions.ptomotor1.set(-1);
				Definitions.intakewheel.set(-1);
				if (System.currentTimeMillis() - start > 250){
					pulsing = false;
					start = System.currentTimeMillis();
				}
			}
			else {
				Definitions.ptomotor1.set(0);
				Definitions.intakewheel.set(0);
				if (System.currentTimeMillis() - start > 250){
					pulsing = true;
					start = System.currentTimeMillis();
				}
			}
		}
		else {
			Definitions.intakewheel.set(0);
			Definitions.leftshooter.set(0);
			Definitions.rightshooter.set(0);
			Definitions.ptomotor1.set(0);
			pulsing = false;
		}
		lastbutton9 = Definitions.buttonbox.getRawButton(9);

	}

}