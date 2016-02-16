
package org.usfirst.frc.team2848.robot;
import org.usfirst.frc.team2848.robot.subsystems.autoShifter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {
public static Joystick xbox1;
public static DoubleSolenoid driveshifter;
public static RobotDrive drivetrain;
public static Compressor compressor;
public static Talon leftdrive;
public static Talon rightdrive;
public static Talon armmotor;
public static PowerDistributionPanel pdp;

    public void robotInit() {  	
    	armmotor = new Talon(2);
    	leftdrive = new Talon(0);
    	rightdrive = new Talon(1);
    	drivetrain = new RobotDrive(leftdrive, rightdrive);
    	xbox1 = new Joystick(0);
    	driveshifter = new DoubleSolenoid(5,0, 1);
    	compressor = new Compressor(5);
    	pdp = new PowerDistributionPanel();
    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
    	compressor.setClosedLoopControl(true);
        drivetrain.arcadeDrive(xbox1.getRawAxis(1), xbox1.getRawAxis(4));


        
    }
    
    public void testPeriodic() {
    	//hello
    }
    
}
