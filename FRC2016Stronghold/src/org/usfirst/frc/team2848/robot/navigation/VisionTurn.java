package org.usfirst.frc.team2848.robot.navigation;

import java.util.concurrent.atomic.AtomicBoolean;

import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.usfirst.frc.team2848.robot.Definitions;

public class VisionTurn {
	private static double xpos = 0;
	
	public static double[] turnVision(int targetpixel, boolean firstrun) {
		if(Definitions.processing.queue.size() == 0)Definitions.processing.queue.add(true);
		
		Definitions.driveaimpid.setTarget(targetpixel);
		
		MatOfPoint2f points = new MatOfPoint2f();
		AtomicBoolean pointschanged = new AtomicBoolean(false);
		MatOfPoint2f bounding = new MatOfPoint2f();
		AtomicBoolean boundingboxchanged = new AtomicBoolean(false);
		Definitions.processing.getPoints(points, pointschanged, bounding, boundingboxchanged);
		
		if(boundingboxchanged.get()) {
			Point average = new Point();
			for(int i = 0; i < bounding.toArray().length; i++) {
				average.x += bounding.toArray()[i].x;
				average.y += bounding.toArray()[i].y;
			}
			average.x /= bounding.toArray().length;
			average.y /= bounding.toArray().length;
			
			xpos = average.x;
		}
		
		if(firstrun) {
			Definitions.driveaimpid.setEnabled(true, xpos);
		}
		double turnval = -Definitions.driveaimpid.compute(xpos, null);
//		Definitions.drivetrain.arcadeDrive(Definitions.xbox1.getRawAxis(1), +Definitions.xbox1.getRawAxis(4));
		System.out.println(xpos);
		return new double[]{xpos, turnval};
	}
}
