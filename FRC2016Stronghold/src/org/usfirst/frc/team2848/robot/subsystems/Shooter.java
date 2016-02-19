package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter {
	private static double start;
	private static boolean lastb = false;
	private static boolean lastaxis3 = false;
	private static boolean lastbutton5 = false;
	public static void firingRoutine(double speed){
		if (Definitions.xbox2.getRawButton(5)){
			if (Definitions.xbox2.getRawAxis(2) > 0.75 && !lastb) {
				lastb = true;
				Definitions.shootertrigger.set(Value.kForward);
				start = System.currentTimeMillis();
				Definitions.ballholder.setAngle(270);
			}
			if (Definitions.xbox2.getRawAxis(2) <= 0.75){
				lastb = false;
			}
			
			Definitions.leftshooterpid.setTarget(speed);
			Definitions.rightshooterpid.setTarget(speed);
			if (!lastbutton5){
				Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
				Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
			}
			lastbutton5 = true;
		}
		
		else if (Definitions.xbox2.getRawAxis(3) > 0.75){
			Definitions.leftshooterpid.setTarget(-1000);
			Definitions.rightshooterpid.setTarget(-1000);
			if (!lastaxis3){
				Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
				Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
			}
			lastaxis3 = true;
		}
		
		else {
			Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
		}
		if (Definitions.xbox2.getRawAxis(3) <= 0.75 && lastaxis3){
			lastaxis3 = false;
		}
		if (!Definitions.xbox2.getRawButton(5) && lastbutton5){
			lastbutton5 = false;
		}
		if (Definitions.shootertrigger.get() != Value.kReverse && (System.currentTimeMillis() > start + 250)) {
			Definitions.shootertrigger.set(Value.kReverse);
			Definitions.ballholder.setAngle(-90);
		}
		//double[] values = new double[5];
		Definitions.leftshooter.set(Definitions.leftshooterpid.compute(Definitions.leftshooterenc.getRate(), null));
		//System.out.println(values[0] + " " + values[1] + " " + values[2] + " " + values[3] + " " + values[4]);
		Definitions.rightshooter.set(Definitions.rightshooterpid.compute(Definitions.rightshooterenc.getRate(), null));
		
	}
}