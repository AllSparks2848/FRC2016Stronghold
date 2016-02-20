package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter {
	private static double start;
	private static boolean lastaxis3 = false;
	private static boolean lastbutton6 = false;
	public static void firingRoutine(double speed){
		if (Definitions.xbox2.getRawButton(6) && !Definitions.leftshooterpid.getEnabled() && !Definitions.lowerarmlimit.get()){
			Definitions.leftshooterpid.setTarget(speed);
			Definitions.rightshooterpid.setTarget(speed);
			Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
		}
//		else if (Definitions.xbox2.getRawButton(6) && !Definitions.leftshooterpid.getEnabled() && Definitions.lowerarmlimit.get()){
//			Definitions.leftshooterpid.setTarget(-1000);
//			Definitions.rightshooterpid.setTarget(-1000);
//			Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
//			Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
//		}
		else if (Definitions.xbox2.getRawButton(6) && Definitions.leftshooterpid.getEnabled() && !lastbutton6){
			Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
		}
		if (Definitions.xbox2.getRawAxis(3) > 0.75 && !lastaxis3){
			Definitions.shootertrigger.set(true);
			start = System.currentTimeMillis();
			lastaxis3 = true;
		}
		else if (Definitions.xbox2.getRawAxis(3) <= 0.75){
			lastaxis3 = false;
		}
		if (Definitions.shootertrigger.get() && (System.currentTimeMillis() > start + 250)){
			Definitions.shootertrigger.set(false);
			Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
		}
		Definitions.leftshooter.set(Definitions.leftshooterpid.compute(Definitions.leftshooterenc.getRate(), null));
		Definitions.rightshooter.set(Definitions.rightshooterpid.compute(Definitions.rightshooterenc.getRate(), null));
	}
}