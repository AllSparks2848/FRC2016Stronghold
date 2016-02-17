package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter {
	private static double start;
	public static void firingRoutine(double speed){
		boolean enablepid = false;
		if (Definitions.xbox2.getRawButton(1)){
			if (Definitions.xbox2.getRawButton(2) && Definitions.shootertrigger.get() != Value.kForward) {
				Definitions.shootertrigger.set(Value.kForward);
				start = System.currentTimeMillis();
			}
			if (Definitions.shootertrigger.get() != Value.kReverse && (System.currentTimeMillis() > start + 250)) {
				Definitions.shootertrigger.set(Value.kReverse);
			}
			Definitions.leftshooterpid.setTarget(speed);
			Definitions.rightshooterpid.setTarget(speed);
			enablepid = true;
		}
		else {
			enablepid = false;
		}
		Definitions.leftshooter.set(Definitions.leftshooterpid.compute(Definitions.leftshooterenc.getRate(), enablepid));
		Definitions.rightshooter.set(Definitions.rightshooterpid.compute(Definitions.rightshooterenc.getRate(), enablepid));
	}
}
