
package org.usfirst.frc.team2848.robot;
import org.usfirst.frc.team2848.robot.subsystems.DriveHelper;
import org.usfirst.frc.team2848.robot.subsystems.autoShifter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {
public static Joystick xbox1;
public static Solenoid driveshifter;
public static RobotDrive drivetrain;
public static Compressor compressor;
public static Talon leftdrive;
public static Talon rightdrive;
public static Talon armMotor;

    public void robotInit() {  	
    	armMotor = new Talon(2);
    	leftdrive = new Talon(0);
    	rightdrive = new Talon(1);
    	drivetrain = new RobotDrive(leftdrive, rightdrive);
    	xbox1 = new Joystick(0);
    	shifter1 = new Solenoid(5,0);
    	compressor = new Compressor(5);
    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
    	compressor.setClosedLoopControl(true);
        autoShifter.downShift();
        drivetrain.arcadeDrive(xbox1.getRawAxis(1), xbox1.getRawAxis(4));


        
    }
    
    public void testPeriodic() {
    	//hello
    }
    
}
