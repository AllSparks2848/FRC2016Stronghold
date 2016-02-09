package org.usfirst.frc.team2848.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveHelper {

public static void arcadeDrive(Joystick xbox, RobotDrive drive){
	
		drive.arcadeDrive(xbox.getRawAxis(1), -xbox.getRawAxis(4)); 
        	
        
	}
	
}
