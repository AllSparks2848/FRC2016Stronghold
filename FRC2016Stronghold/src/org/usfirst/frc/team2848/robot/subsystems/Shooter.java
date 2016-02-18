package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter {
	private static double start;
	private static boolean lastb = false;
	public static void firingRoutine(double speed){
		if (Definitions.xbox2.getRawButton(5)){
			if (Definitions.xbox2.getRawAxis(2) > 0.75 && !lastb) {
				lastb = true;
				Definitions.shootertrigger.set(Value.kForward);
				start = System.currentTimeMillis();
			}
			if (Definitions.xbox2.getRawAxis(6) <= 0.75){
				lastb = false;
			}
			
			Definitions.leftshooterpid.setTarget(speed);
			Definitions.rightshooterpid.setTarget(speed);
			Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
		}
		
		else if (Definitions.xbox2.getRawAxis(2) > 0.75){
			Definitions.leftshooterpid.setTarget(-1000);
			Definitions.rightshooterpid.setTarget(-1000);
			Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
		}
		else {
			Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
		}
		if (Definitions.shootertrigger.get() != Value.kReverse && (System.currentTimeMillis() > start + 250)) {
			Definitions.shootertrigger.set(Value.kReverse);
		}
		Definitions.leftshooter.set(Definitions.leftshooterpid.compute(Definitions.leftshooterenc.getRate(), null));
		Definitions.rightshooter.set(Definitions.rightshooterpid.compute(Definitions.rightshooterenc.getRate(), null));
		System.out.println(Definitions.ballholder.getAngle());
	}
}