package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shooter {
	private static double start;
	
	public static void firingRoutine(double speed){
		boolean enablepid = false;
		if (Robot.xbox1.getRawButton(4)){
			
			if (Robot.xbox1.getRawButton(6) && !Robot.shootertrigger.get()) {
				Robot.shootertrigger.set(true);
				start = System.currentTimeMillis();
			}
			if (Robot.shootertrigger.get() && (System.currentTimeMillis() > start + 250)) {
				Robot.shootertrigger.set(false);
			}
			Robot.leftpid.setTarget(speed);
			Robot.rightpid.setTarget(speed);
			enablepid = true;
		}
		else {
			enablepid = false;
		}
		Robot.shooterleftmotor.set(Robot.leftpid.compute(Robot.leftencoder.getRate(), enablepid));
		Robot.shooterrightmotor.set(Robot.rightpid.compute(Robot.rightencoder.getRate(), enablepid));
	}
}
