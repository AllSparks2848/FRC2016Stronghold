package org.usfirst.frc.team2848.robot.subsystems;

import java.util.concurrent.atomic.AtomicBoolean;

import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.robot.States;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Turret {
	public static final double TICKS_PER_PIXEL = 1.5;
	public static final double TARGET_PIXEL= 177;
	private static double xpos = 0;
	private static double currentpos = 0;
	private static boolean finecontrol = false;
	private static int targetstate = 0;
	private static long delaytime;
	private static double target = 0;
	
	public static void turretRoutine(int mode) {
        if(mode == 1) {
        	Definitions.turret.set(0);
        	Definitions.turretcenterpid.setEnabled(false,0);
        	if(Definitions.processing.queue.size() == 0)Definitions.processing.queue.add(true);
        	
        	if(targetstate == 0) {
        		Definitions.turret.set(0); 
        		Definitions.driveaimpid.setEnabled(false, 0);
        		delaytime = System.currentTimeMillis();
        		targetstate = 1;
        	}
        	
        	MatOfPoint2f points = new MatOfPoint2f();
        	MatOfPoint2f bounding = new MatOfPoint2f();
        	AtomicBoolean changed = new AtomicBoolean(false);
        	AtomicBoolean bchanged = new AtomicBoolean(false);
        	Definitions.processing.getPoints(points, changed, bounding, bchanged);
        	
        	if(bchanged.get()) {	
        		
	        	Point leftbound = bounding.toArray()[1];
	        	Point rightbound = bounding.toArray()[2];
	        	Point pos = new Point((leftbound.x+rightbound.x)/2.0, (leftbound.y+rightbound.y)/2.0);
	        	currentpos = pos.x;
	        	if(targetstate == 1 && System.currentTimeMillis()-delaytime > 750) { 
	        		xpos = pos.x;
	        		targetstate = 2;
	        		//System.out.println(xpos);
		        	double tickdifferential = (TARGET_PIXEL-xpos)*TICKS_PER_PIXEL;
		        	System.out.println(pos.x + " " + tickdifferential);
		        	target = Definitions.turretenc.getDistance() - tickdifferential;
		        	if(Math.abs(tickdifferential) > 75) {
			        	Definitions.turretcenterpid.setTarget(target);
			        	finecontrol = false;
		        	}
		        	else {
			        	Definitions.driveaimpid.setTarget(target);
			        	finecontrol = true;
		        	}
	        	}	        	
	        	
	        	//turretencoder.reset();
        	}
        	if(targetstate == 2) {
        		if(finecontrol) {
		        	if(!Definitions.driveaimpid.getEnabled()) Definitions.driveaimpid.setEnabled(true, Definitions.turretenc.getDistance());
		        	double output = Definitions.driveaimpid.compute(Definitions.turretenc.getDistance(), null);
		        	//System.out.println(output + " " + turretencoder.getDistance() + " " + target);
		        	Definitions.turret.set(-output);
		        	if(Math.abs(target-Definitions.turretenc.get()) < 3) {
		        		targetstate = 0;
		        		Definitions.turret.set(0);
		        		Definitions.driveaimpid.setEnabled(false, 0);
		        	}
        		}
        		else {
		        	if(!Definitions.turretcenterpid.getEnabled()) Definitions.turretcenterpid.setEnabled(true, Definitions.turretenc.getDistance());
		        	double output = Definitions.turretcenterpid.compute(Definitions.turretenc.getDistance(), null);
		        	//System.out.println(output + " " + turretencoder.getDistance() + " " + target);
		        	Definitions.turret.set(-output);
		        	if(Math.abs(target-Definitions.turretenc.get()) < 40) {
		        		targetstate = 0;
		        		Definitions.turret.set(0);
		        		Definitions.turretcenterpid.setEnabled(false, 0);
		        	}
        		}
        	}
        	
        	if(Math.abs(TARGET_PIXEL-currentpos) < 4) SmartDashboard.putBoolean("AutoAimReady", true);
        	else SmartDashboard.putBoolean("AutoAimReady", false);
        	
//        	MatOfPoint2f points = new MatOfPoint2f();
//        	MatOfPoint2f bounding = new MatOfPoint2f();
//        	AtomicBoolean changed = new AtomicBoolean(false);
//        	AtomicBoolean bchanged = new AtomicBoolean(false);
//        	Definitions.processing.getPoints(points, changed, bounding, bchanged);
//        	if(bchanged.get()) {		
//	        	Point leftbound = bounding.toArray()[1];
//	        	Point rightbound = bounding.toArray()[2];
//	        	Point pos = new Point((leftbound.x+rightbound.x)/2.0, (leftbound.y+rightbound.y)/2.0);
//	        	xpos = pos.x;
//	        	System.out.println(xpos);
//	        	double tickdifferential = (173-xpos)*TICKS_PER_PIXEL;
//	        	//System.out.println(pos.x + " " + tickdifferential);
//	        	Definitions.turretaimpid.setTarget(Definitions.turretenc.getDistance() - tickdifferential);
//	        	//turretencoder.reset();
//        	}
//        	if(!Definitions.turretaimpid.getEnabled()) Definitions.turretaimpid.setEnabled(true, Definitions.turretenc.getDistance());
//        	double output = Definitions.turretaimpid.compute(Definitions.turretenc.getDistance(), null);
//        	//System.out.println(output + " " + turretencoder.getDistance() + " " + target);
//        	Definitions.turret.set(-output);
        }
        else if(mode == 2) {
        	Definitions.driveaimpid.setEnabled(false, 0);
        	if(Definitions.processing.queue.size() == 0) Definitions.processing.queue.add(false);
        	if(!Definitions.turretcenterpid.getEnabled()) Definitions.turretcenterpid.setEnabled(true, Definitions.turretenc.getDistance());
        	Definitions.turretcenterpid.setTarget(0);
        	Definitions.turret.set(-Definitions.turretcenterpid.compute(Definitions.turretenc.getDistance(), null));
//        	System.out.println(Definitions.turret.get());
        	
        }
        else {
        	Definitions.driveaimpid.setEnabled(false, 0);
        	Definitions.turretcenterpid.setEnabled(false,0);
        	if(Definitions.processing.queue.size() == 0) Definitions.processing.queue.add(false);
        	if (!States.robotstate.equals("battershot")){
        		if(Definitions.joystick.getRawButton(4)){
            		Definitions.turret.set(-0.4);
            	}
            	else if (Definitions.joystick.getRawButton(5)){
            		Definitions.turret.set(0.4);
            	}
            	else Definitions.turret.set(0);
        	}
//        	System.out.println(Definitions.turretpot.getVoltage());
        }
        if (Definitions.buttonbox.getRawButton(8)){
        	Definitions.turretenc.reset();
        }
	}
	
	public static double getPixelTarget() {
		return currentpos;
	}
}
