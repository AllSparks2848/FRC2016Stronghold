
package org.usfirst.frc.team2848.robot;

import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.util.PID;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
public static Joystick xbox1;
    
    public static Talon motorleft;
    public static Talon motorright;
    
    public static Encoder encoderleft;
    public static Encoder encoderright;
    
    public static PID shooterpidleft;
    public static PID shooterpidright;

    
    
    double gearratio;
    
    public static Solenoid shifter1;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
    public void robotInit() {
    	xbox1 = new Joystick(0);
        
        motorleft = new Talon(2);
        motorright = new Talon(3);
        
        gearratio = (22.0/16.0);
        
        encoderleft = new Encoder(4, 5, false, EncodingType.k4X);
        encoderright = new Encoder(6, 7, false, EncodingType.k4X);
        encoderleft.setDistancePerPulse((1/1024.0)*gearratio*60);
        encoderright.setDistancePerPulse((1/1024.0)*gearratio*60);
        
        shooterpidleft = new PID(0, 0, 0, 0, encoderleft.getRate());
        shooterpidright = new PID(0, 0, 0, 0, encoderright.getRate());
        
        shooterpidleft.setBounds(-1, 1);
        shooterpidright.setBounds(-1, 1);
        shooterpidleft.setITermBounds(-1, 1);
        shooterpidleft.setITermBounds(-1, 1);
        
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	Shooter.isFiring();
        Shooter.fire();
        Shooter.spinUp(5000);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }
    
}
