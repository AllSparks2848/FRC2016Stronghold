package org.usfirst.frc.team2848.robot.subsystems;

import java.util.concurrent.atomic.AtomicBoolean;

import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.usfirst.frc.team2848.robot.Definitions;


public class Turret {
	public static final double TICKS_PER_PIXEL = 2;
	public static void turretRoutine(boolean shouldcenter) {
        if(shouldcenter) {
        	if(Definitions.processing.queue.size() == 0)Definitions.processing.queue.add(true);
        	MatOfPoint2f points = new MatOfPoint2f();
        	MatOfPoint2f bounding = new MatOfPoint2f();
        	AtomicBoolean changed = new AtomicBoolean(false);
        	AtomicBoolean bchanged = new AtomicBoolean(false);
        	Definitions.processing.getPoints(points, changed, bounding, bchanged);
        	if(bchanged.get()) {		
	        	Point leftbound = bounding.toArray()[1];
	        	Point rightbound = bounding.toArray()[2];
	        	Point pos = new Point((leftbound.x+rightbound.x)/2.0, (leftbound.y+rightbound.y)/2.0);
	        	double xpos = pos.x;
	        	System.out.println(xpos);
	        	double tickdifferential = (180-xpos)*TICKS_PER_PIXEL;
	        	//System.out.println(pos.x + " " + tickdifferential);
	        	Definitions.turretpid.setTarget(Definitions.turretenc.getDistance() - tickdifferential);
	        	//turretencoder.reset();
        	}
        	if(!Definitions.turretpid.getEnabled()) Definitions.turretpid.setEnabled(true, Definitions.turretenc.getDistance());
        	double output = Definitions.turretpid.compute(Definitions.turretenc.getDistance(), null);
        	//System.out.println(output + " " + turretencoder.getDistance() + " " + target);
        	Definitions.turret.set(output);
        }
        else {
        	if(Definitions.processing.queue.size() == 0) Definitions.processing.queue.add(false);
        	Definitions.turret.set(Definitions.xbox2.getRawAxis(4)*0.4);
        }
	}
}