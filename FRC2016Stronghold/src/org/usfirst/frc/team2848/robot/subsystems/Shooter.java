package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shooter {
	private static double start;
	
	public static void firingRoutine(double speed){
		if (Robot.xbox1.getRawButton(1)){
			if (Robot.xbox1.getRawButton(2) && Robot.shootertrigger.get() == DoubleSolenoid.Value.kReverse) {
				Robot.shootertrigger.set(DoubleSolenoid.Value.kForward);
				start = System.currentTimeMillis();
			}
			if (Robot.shootertrigger.get() == DoubleSolenoid.Value.kForward && (System.currentTimeMillis() > start + 250)) {
				Robot.shootertrigger.set(DoubleSolenoid.Value.kReverse);
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
