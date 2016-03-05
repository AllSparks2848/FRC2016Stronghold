package org.usfirst.frc.team2848.robot;

import org.usfirst.frc.team2848.robot.subsystems.Arm;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class States {
	public static String lastrobotstate = "start";
	public static String robotstate = "nothing";
	private static double start;
	private static boolean armstarted = false;
	private static boolean loaderstarted = false;
	private static boolean armfinished = false;
	private static boolean loaderfinished = false;
	public static int ptoposition = 0;
	private static int lastptoposition;
	private static boolean limitcancelled = false;
	private static int target;
	private static boolean statestarted = false;
	
	public static void stateRoutine(){
		if (!Definitions.upperarmlimit.get()){
			Definitions.ptoenc.reset();
			ptoposition = 0;
		}
//		else if (!Definitions.lowerarmlimit.get()){
//			ptoposition = 533;
//			Definitions.ptoenc.reset();
//			}
//		else if (lastptoposition != Definitions.ptoenc.get()){
//			ptoposition += (Definitions.ptoenc.get() - lastptoposition);
//			
//		}
		ptoposition = Definitions.ptoenc.get();
		lastptoposition = Definitions.ptoenc.get();
		
		if (robotstate .equals("start")){
			if (!statestarted){
				start = System.currentTimeMillis();
				statestarted = true;
			}
			Arm.armstate = 2;
			if (!Definitions.upperarmlimit.get()){
				SparkyIntakeBar.position = 0;
				lastrobotstate = "start";
				robotstate = "nothing";
				statestarted = false;
			}
		}
		if (robotstate.equals("tuck")){
			if (!statestarted){
				start = System.currentTimeMillis();
				statestarted = true;
			}
			Arm.armstate = 1;
			SparkyIntakeBar.position = 2;
			if (!Definitions.lowerarmlimit.get() && SparkyIntakeBar.lastintakeposition == 2){
				robotstate = "nothing";
				lastrobotstate = "tuck";
				statestarted = false;
			}
		}
		if (robotstate .equals("intake")){
			if (!statestarted){
				start = System.currentTimeMillis();
				statestarted = true;
			}
			SparkyIntakeBar.position = 1;
			if (SparkyIntakeBar.lastintakeposition == 1){
				if (!armstarted){
					Definitions.armbrake.set(Value.kReverse);
					Definitions.armpid.setEnabled(true, ptoposition);
					Definitions.armpid.setTarget(460);
					armstarted = true;
				}
				if (Definitions.armpid.getEnabled()){
					Definitions.ptomotor1.set(Definitions.armpid.compute(ptoposition, null));
					Definitions.ptomotor2.set(Definitions.armpid.compute(ptoposition, null));
				}
			}
			if (ptoposition < 465 && ptoposition > 455 && SparkyIntakeBar.lastintakeposition == 1){
				robotstate = "nothing";
				lastrobotstate = "intake";
				Definitions.armpid.setEnabled(false, ptoposition);
				armstarted = false;
				Definitions.armbrake.set(Value.kForward);
				statestarted = false;
			}
		}
		if (robotstate .equals("shooting")){
			if (!statestarted){
				start = System.currentTimeMillis();
				statestarted = true;
			}
			Arm.armstate = 2;
			if (ptoposition < 300){
				SparkyIntakeBar.position = 1;
			}
			if (SparkyIntakeBar.lastintakeposition == 1 && !Definitions.upperarmlimit.get()){
				robotstate = "nothing";
				lastrobotstate = "shooting";
				statestarted = false;
			}
		}
		if (robotstate .equals("defense")){
			if (!statestarted){
				start = System.currentTimeMillis();
				statestarted = true;
			}
			SparkyIntakeBar.position = 1;
			if (SparkyIntakeBar.lastintakeposition == 1){
				if (!armstarted){
					Definitions.armbrake.set(Value.kReverse);
					Definitions.armpid.setEnabled(true, ptoposition);
					Definitions.armpid.setTarget(300);
					armstarted = true;
				}
				if (Definitions.armpid.getEnabled()){
					Definitions.ptomotor1.set(Definitions.armpid.compute(ptoposition, null));
					Definitions.ptomotor2.set(Definitions.armpid.compute(ptoposition, null));
				}
			}
			if (ptoposition < 305 && ptoposition >295 && SparkyIntakeBar.lastintakeposition == 1){
				robotstate = "nothing";
				lastrobotstate = "intake";
				Definitions.armpid.setEnabled(false, ptoposition);
				armstarted = false;
				Definitions.armbrake.set(Value.kForward);
				statestarted = false;
			}
		}
		if (robotstate.equals("portcullis")){
		
		}
		if (robotstate.equals("adjusting")){
			if (!Definitions.armpid.getEnabled()){
				Definitions.armpid.setEnabled(true, ptoposition);
				target = ptoposition + 10;
				Definitions.armpid.setTarget(target);
				Definitions.armbrake.set(Value.kReverse);
			}
			if (Definitions.upperarmlimit.get()){
				Definitions.ptomotor1.set(Definitions.armpid.compute(ptoposition, null));
				Definitions.ptomotor2.set(Definitions.armpid.compute(ptoposition, null));
			}
			else {
				Definitions.armpid.setEnabled(false, ptoposition);
				Definitions.armbrake.set(Value.kForward);
				Definitions.ptomotor1.set(0);
				Definitions.ptomotor2.set(0);
				robotstate = "nothing";
			}
			if (ptoposition > target - 2 && ptoposition < target + 2){
				Definitions.armpid.setEnabled(false, ptoposition);
				Definitions.armbrake.set(Value.kForward);
				Definitions.ptomotor1.set(0);
				Definitions.ptomotor2.set(0);
				robotstate = "nothing";
			}
			
		}
		if (robotstate.equals("nothing")){
				if (!Definitions.upperarmlimit.get()){
					if (SparkyIntakeBar.lastintakeposition == 0){
						lastrobotstate = "start";
					}
					else if (SparkyIntakeBar.lastintakeposition == 1){
						lastrobotstate = "shooting";
					}
				}
				else if (!Definitions.lowerarmlimit.get()){
					if (SparkyIntakeBar.lastintakeposition == 2){
						lastrobotstate = "tuck";
					}
					else if (SparkyIntakeBar.lastintakeposition == 1){
						lastrobotstate = "intake";
					}
				}
				else {
					lastrobotstate = "defense";
				}
		}
		if (System.currentTimeMillis() > start + 5000 && !robotstate.equals("nothing")){
			Definitions.armpid.setEnabled(false, ptoposition);
			Definitions.armbrake.set(Value.kForward);
			Definitions.ptomotor1.set(0);
			Definitions.ptomotor2.set(0);
			robotstate = "nothing";
			statestarted = false;
			armstarted = false;
		}
		if (Definitions.buttonbox.getRawButton(1) && robotstate.equals("nothing")){
			robotstate = "start";
		}
		else if (Definitions.buttonbox.getRawButton(2) && robotstate.equals("nothing")){
			robotstate = "tuck";
		}
		else if (Definitions.buttonbox.getRawButton(3) && robotstate.equals("nothing")){
			robotstate = "intake";
		}
		else if (Definitions.buttonbox.getRawButton(4) && robotstate.equals("nothing")){
			robotstate = "shooting";
		}
		else if (Definitions.buttonbox.getRawButton(8) && robotstate.equals("nothing")){
			robotstate = "defense";
		}
		else if (Definitions.buttonbox.getRawButton(6) && robotstate.equals("nothing")){
			robotstate = "adjusting";
		}
		if (Definitions.buttonbox.getRawButton(9) ){
			Arm.armstate = 0;
			Definitions.ptomotor1.set(0);
			Definitions.ptomotor2.set(0);
			Definitions.armpid.setEnabled(false, ptoposition);
			Definitions.armbrake.set(Value.kForward);
			robotstate = "nothing";
			armstarted = false;
		}
		if (!limitcancelled && robotstate.equals("nothing") && (ptoposition > 680 || ptoposition < -150)){
			Arm.armstate = 0;
			Definitions.ptomotor1.set(0);
			Definitions.ptomotor2.set(0);
			Definitions.armpid.setEnabled(false, ptoposition);
			Definitions.armbrake.set(Value.kForward);
			robotstate = "nothing";
			limitcancelled = true;
			armstarted = false;
		}
		if (ptoposition < 680 && ptoposition > -150){
			limitcancelled = false;
		}
		
	}
}
