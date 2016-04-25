package org.usfirst.frc.team2848.robot.navigation;

import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.util.ArduinoComm;

public class TurnControl {
	public static boolean turn(double targetangle, double maxspeed) {
		Definitions.turnpid.setBounds(-maxspeed, maxspeed);
		Definitions.turnpid.setITermBounds(-maxspeed, maxspeed);
		Definitions.turnpid.setTarget(targetangle);
		double turnval = Definitions.turnpid.compute(ArduinoComm.getYaw(), null);
		Definitions.drivetrain.tankDrive(turnval, -turnval);
		
		System.out.println(ArduinoComm.getYaw() + " " + turnval + " " + (targetangle-ArduinoComm.getYaw()) + " " + targetangle);
		if(Math.abs(targetangle-ArduinoComm.getYaw()) < 4) return true;
		return false;
		
	}
}
