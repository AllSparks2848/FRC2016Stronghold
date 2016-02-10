
package org.usfirst.frc.team2848.robot;
import org.usfirst.frc.team2848.robot.subsystems.DriveHelper;
import org.usfirst.frc.team2848.robot.subsystems.Pnuematics;
import org.usfirst.frc.team2848.robot.subsystems.autoShifter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {
public static Joystick xbox1;
public static Solenoid shifter1;
public static RobotDrive drivetrain;
public static Compressor compressor;
public static Talon bottomleft;
public static Talon bottomright;

    public void robotInit() {
    	
    	bottomleft = new Talon(0);
    	bottomright = new Talon(1);
    	drivetrain = new RobotDrive(bottomleft, bottomright);
    	xbox1 = new Joystick(0);
    	shifter1 = new Solenoid(5,0);
    	compressor = new Compressor(5);
    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
    	compressor.setClosedLoopControl(true);
        DriveHelper.arcadeDrive(xbox1, drivetrain);
        Pnuematics.pneumaticToggle();
        autoShifter.downShift();
        Timer.delay(.01);
    }
    
    public void testPeriodic() {
    	//hello
    }
    
}
