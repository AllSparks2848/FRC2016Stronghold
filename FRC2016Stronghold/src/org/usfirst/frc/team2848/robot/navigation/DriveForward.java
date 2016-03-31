package org.usfirst.frc.team2848.robot.navigation;

import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.util.ArduinoComm;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;

public class DriveForward {
	public static final double encoderratio = (1.0/256.0)*(15.0/22.0)*8.0*Math.PI;
	
	public static boolean driveForward(double distance, double maxspeed, double targetyaw) {
		System.out.println(Definitions.leftdriveenc.getDistance()*encoderratio + " " + Definitions.rightdriveenc.getDistance()*encoderratio);
		
		Definitions.turnpid.setTarget(targetyaw);		
		double turnmod = Definitions.turnpid.compute(ArduinoComm.getYaw(), null);
		Definitions.leftdrivepid.setTarget(maxspeed*Math.signum(distance));
		Definitions.rightdrivepid.setTarget(maxspeed*Math.signum(distance));
		
		Definitions.drivetrain.tankDrive(-Definitions.leftdrivepid.compute(Definitions.leftdriveenc.getRate()*encoderratio+turnmod, null), -Definitions.rightdrivepid.compute(Definitions.rightdriveenc.getRate()*encoderratio-turnmod, null));
		
		if(Math.abs(Math.min(Definitions.leftdriveenc.getDistance()*encoderratio, Definitions.rightdriveenc.getDistance()*encoderratio)) > Math.abs(distance) || Math.abs(Math.max(Definitions.leftdriveenc.getDistance()*encoderratio, Definitions.rightdriveenc.getDistance()*encoderratio)) > Math.abs(Math.abs(distance) + 50)) return true;
		
		return false;
	}
}
