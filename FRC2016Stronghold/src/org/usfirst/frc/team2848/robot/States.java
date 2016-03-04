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
	
	public static void stateRoutine(){
		if (!Definitions.upperarmlimit.get()){
			Definitions.ptoenc.reset();
			ptoposition = 0;
		}
		else if (!Definitions.lowerarmlimit.get()){
			ptoposition = 533;
			Definitions.ptoenc.reset();
			}
		else if (lastptoposition != Definitions.ptoenc.get()){
			ptoposition += (Definitions.ptoenc.get() - lastptoposition);
			
		}
		lastptoposition = Definitions.ptoenc.get();
		
		if (robotstate .equals("start")){
			Arm.armstate = 2;
			if (!Definitions.upperarmlimit.get()){
				SparkyIntakeBar.position = 0;
				lastrobotstate = "start";
				robotstate = "nothing";
			}
		}
		if (robotstate.equals("tuck")){
			Arm.armstate = 1;
			SparkyIntakeBar.position = 2;
			if (!Definitions.lowerarmlimit.get() && SparkyIntakeBar.lastintakeposition == 2){
				robotstate = "nothing";
				lastrobotstate = "tuck";
			}
		}
		if (robotstate .equals("intake")){
			SparkyIntakeBar.position = 1;
			if (SparkyIntakeBar.lastintakeposition == 1){
				if (!armstarted){
					Definitions.armbrake.set(Value.kReverse);
					Definitions.armpid.setEnabled(true, ptoposition);
					Definitions.armpid.setTarget(450);
					armstarted = true;
				}
				if (Definitions.armpid.getEnabled()){
					Definitions.ptomotor1.set(Definitions.armpid.compute(ptoposition, null));
					Definitions.ptomotor2.set(Definitions.armpid.compute(ptoposition, null));
				}
			}
			if (ptoposition < 452 && ptoposition > 448 && SparkyIntakeBar.lastintakeposition == 1){
				robotstate = "nothing";
				lastrobotstate = "intake";
				Definitions.armpid.setEnabled(false, ptoposition);
				armstarted = false;
				Definitions.armbrake.set(Value.kForward);
			}
		}
		if (robotstate .equals("shooting")){
			Arm.armstate = 2;
			if (ptoposition < 300){
				SparkyIntakeBar.position = 1;
			}
			if (SparkyIntakeBar.lastintakeposition == 1 && !Definitions.upperarmlimit.get()){
				robotstate = "nothing";
				lastrobotstate = "shooting";
			}
		}
		if (robotstate .equals("defense")){
			
		}
		if (robotstate.equals("portcullis")){
		
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
	}
}
