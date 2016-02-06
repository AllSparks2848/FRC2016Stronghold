package org.usfirst.frc.team2848.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class SparkyDriveHelper {
	public static void arcadeDrive(Joystick xbox, RobotDrive drive){
		
		drive.arcadeDrive(-xbox.getRawAxis(1), -xbox.getRawAxis(4)); 
        	
        
	}
}
