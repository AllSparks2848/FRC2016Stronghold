package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

public class Shooter {
	private static double start;
	public static void firingRoutine(double speed){
		if (Robot.xbox1.getRawButton(1)){
			if (Robot.xbox1.getRawButton(2) && !Robot.shootertrigger.get()) {
				Robot.shootertrigger.set(true);
				start = System.currentTimeMillis();
			}
			if (Robot.shootertrigger.get() && (System.currentTimeMillis() > start + 250)) {
				Robot.shootertrigger.set(false);
			}
			Robot.shooterpidleft.setTarget(speed);
			Robot.shooterpidright.setTarget(speed);
		}
		else {
			Robot.shooterpidleft.setTarget(0);
			Robot.shooterpidright.setTarget(0);
		}
		Robot.shooterleftmotor.set(Robot.shooterpidleft.compute(Robot.shooterleftenc.getRate()));
		Robot.shooterrightmotor.set(Robot.shooterpidright.compute(Robot.shooterrightenc.getRate()));
	}
}
