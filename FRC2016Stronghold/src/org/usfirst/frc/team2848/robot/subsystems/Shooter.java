package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

public class Shooter {
	private static boolean shifterstate= false;
	
	private static boolean firing = false;
	private static double start;
	
	public static void fire(){
		if (firing){
			if (Robot.xbox1.getRawButton(2) && !shifterstate) {
				shifterstate = true;
				start = System.currentTimeMillis();
				SparkyIntakeBar.loaded = false;
			}
			if (shifterstate && (System.currentTimeMillis() > start + 250)) {
				firing = false;
				shifterstate = false;
			}
			Robot.shootertrigger.set(shifterstate);
		}
	}
	public static void isFiring(){
		if (Robot.xbox1.getRawButton(1) && SparkyIntakeBar.loaded){
			firing = true;
		}
	}
	public static void spinUp(double speed){
		if (firing){
			Robot.shooterpidleft.setTarget(speed);
			Robot.shooterpidright.setTarget(speed);
		}
		else{
			Robot.shooterpidleft.setTarget(0);
			Robot.shooterpidright.setTarget(0);
		}
		Robot.shooterleftmotor.set(Robot.shooterpidleft.compute(Robot.shooterleftenc.getRate()));
		Robot.shooterrightmotor.set(Robot.shooterpidright.compute(Robot.shooterrightenc.getRate()));
	}
	public static void firingRoutine(){
		isFiring();
        fire();
        spinUp(1500);
	}
}
