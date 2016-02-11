
package org.usfirst.frc.team2848.robot;

import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.util.PID;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
public static Joystick xbox1;
    
    public static Talon shooterleftmotor;
    public static Talon shooterrightmotor;
    public static Talon intakewheel;
    
    public static Encoder shooterleftenc;
    public static Encoder shooterrightenc;
    
    public static PID shooterpidleft;
    public static PID shooterpidright;

    double shootergearratio;
    
    public static Solenoid shootertrigger;
    
    public static Solenoid leftintakesolenoid;
    public static Solenoid rightintakesolenoid;
    public static Solenoid leftintakepancake;
    public static Solenoid rightintakepancake;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
    public void robotInit() {
    	xbox1 = new Joystick(0);
        
        shooterleftmotor = new Talon(0);
        shooterrightmotor = new Talon(1);
        intakewheel = new Talon(7);
        shooterrightmotor.setInverted(false);
        shooterleftmotor.setInverted(true);
        intakewheel.setInverted(false);
        
        shootergearratio = (1/3.0);
        
        shooterleftenc = new Encoder(0, 1, false, EncodingType.k4X);
        shooterrightenc = new Encoder(2, 3, true, EncodingType.k4X);
        shooterleftenc.setDistancePerPulse((1/3.0)*shootergearratio*60);
        shooterrightenc.setDistancePerPulse((1/3.0)*shootergearratio*60);
        
        shooterpidleft = new PID(0.0023, 0.0008, 0, 0, shooterleftenc.getRate());
        shooterpidright = new PID(0.0023, 0.0008, 0, 0, shooterleftenc.getRate());
        
        shooterpidleft.setBounds(-1, 1);
        shooterpidright.setBounds(-1, 1);
        shooterpidleft.setITermBounds(-1, 1);
        shooterpidleft.setITermBounds(-1, 1);
        
        shootertrigger = new Solenoid(0);
        
        leftintakesolenoid = new Solenoid(4);
        rightintakesolenoid = new Solenoid(5);
        leftintakepancake = new Solenoid(6);
        rightintakepancake = new Solenoid(7);
        
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
    	Shooter.firingRoutine();
    	;
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }
    
}
