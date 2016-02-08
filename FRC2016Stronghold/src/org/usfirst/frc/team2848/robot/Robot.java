
package org.usfirst.frc.team2848.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {
public static Talon motor1;
public static Talon motor2;
public static Joystick xbox1;
public static Solenoid shifter1;
public static RobotDrive drivetrain;
public static Compressor compressor;
    public void robotInit() {
    	motor1 = new Talon(0);
    	motor2 = new Talon(1);
    	drivetrain = new RobotDrive(new Talon(0), new Talon(1));
    	xbox1 = new Joystick(0);
    	shifter1 = new Solenoid(0);
    	compressor = new Compressor(6);
    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
    	compressor.setClosedLoopControl(true);
        SparkyDriveHelper.arcadeDrive(xbox1, drivetrain);
        SparkyMotorController.controlMotors();
        SparkyPnuematics.pneumaticToggle();
    }
    
    public void testPeriodic() {
    	//hello
    }
    
}
