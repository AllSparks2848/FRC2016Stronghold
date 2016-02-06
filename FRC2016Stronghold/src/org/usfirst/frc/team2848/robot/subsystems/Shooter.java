package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Robot;

public class Shooter {
	private static boolean shifterstate= false;
	private static boolean wasrightshoulder = false;
	
	private static boolean firing = false;
	private static double start;
	
	public static void fire(){
		wasrightshoulder = false;
		if (firing){
			if (Robot.xbox1.getRawAxis(3) > 0.75 && !wasrightshoulder) {
				wasrightshoulder = true;
				shifterstate = true;
				start = System.currentTimeMillis();
			}
			if (shifterstate && (System.currentTimeMillis() > start + 250)) {
				firing = false;
				shifterstate = false;
			}
			Robot.shifter1.set(shifterstate);
		}
	}
	public static void isFiring(){
		if (!firing && Robot.xbox1.getRawButton(1)){
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
		Robot.motorleft.set(Robot.shooterpidleft.compute(Robot.encoderleft.getRate()));
		Robot.motorright.set(Robot.shooterpidright.compute(Robot.encoderright.getRate()));
	}
}
