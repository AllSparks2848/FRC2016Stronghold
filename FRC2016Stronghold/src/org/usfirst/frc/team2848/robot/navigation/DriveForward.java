package org.usfirst.frc.team2848.robot.navigation;

import java.util.ArrayList;

import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.util.ArduinoComm;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;

public class DriveForward {
	public static final double encoderratio = (1.0/256.0)*(15.0/22.0)*8.0*Math.PI;
	
	public static boolean driveForward(double distance, double maxspeed, double targetyaw) {
		System.out.println(Definitions.leftdriveenc.getDistance()*encoderratio + " " + Definitions.rightdriveenc.getDistance()*encoderratio);
		
		Definitions.turndrivepid.setTarget(targetyaw);		
		double turnmod = Definitions.turndrivepid.compute(ArduinoComm.getYaw(), null);
		Definitions.leftdrivepid.setTarget(maxspeed*Math.signum(distance) - turnmod);
		Definitions.rightdrivepid.setTarget(maxspeed*Math.signum(distance) + turnmod);
		double[] leftvals = new double[5];
		Definitions.drivetrain.tankDrive(-Definitions.leftdrivepid.compute(decimateLeft(Definitions.leftdriveenc.getRate()*encoderratio), leftvals), -Definitions.rightdrivepid.compute(decimateRight(Definitions.rightdriveenc.getRate()*encoderratio), null));
		System.out.println(leftvals[0] + " " + leftvals[1] + " " + leftvals[2]);
		if(Math.abs(Math.min(Definitions.leftdriveenc.getDistance()*encoderratio, Definitions.rightdriveenc.getDistance()*encoderratio)) >= Math.abs(distance) || Math.abs(Math.max(Definitions.leftdriveenc.getDistance()*encoderratio, Definitions.rightdriveenc.getDistance()*encoderratio)) >= Math.abs(Math.abs(distance) + 50)) return true;
		
		return false;
	}
	
	private static ArrayList<Double> leftdecimatevals = new ArrayList<Double>();
	public static double decimateLeft(double input) {
		leftdecimatevals.add(0, input);
		if(leftdecimatevals.size() > 10) leftdecimatevals.remove(10);
		double accumvals = 0;
		for(int i = 0; i < leftdecimatevals.size(); i++) {
			accumvals += leftdecimatevals.get(i);
		}
		accumvals /= leftdecimatevals.size();
		return accumvals;
	}
	
	private static ArrayList<Double> rightdecimatevals = new ArrayList<Double>();
	public static double decimateRight(double input) {
		rightdecimatevals.add(0, input);
		if(rightdecimatevals.size() > 10) rightdecimatevals.remove(10);
		double accumvals = 0;
		for(int i = 0; i < rightdecimatevals.size(); i++) {
			accumvals += rightdecimatevals.get(i);
		}
		accumvals /= rightdecimatevals.size();
		return accumvals;
	}
	
	public static void decimateResetLeft() { leftdecimatevals = new ArrayList<Double>(); }
	public static void decimateResetRight() { rightdecimatevals = new ArrayList<Double>(); }
}
