package org.usfirst.frc.team2848.robot;

import org.usfirst.frc.team2848.util.PID;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;

public class Definitions {
    public static Joystick xbox1;
    public static Joystick xbox2;
    
    //actuators
    public static DoubleSolenoid intakesolenoid;
	public static DoubleSolenoid intakepancake;	
	public static DoubleSolenoid driveshifter;
	public static DoubleSolenoid ptoshifter;	
	public static DoubleSolenoid shootertrigger;
	public static DoubleSolenoid armbrake;
	
	
	public static Spark leftdrive;
	public static Spark rightdrive;
	public static Spark leftshooter;
	public static Spark rightshooter;
	public static Spark ptomotor;
	public static Spark turret;
	public static Spark intakewheel;
	
	public static RobotDrive drivetrain;
	
	public static Servo ballholder;
	
	//sensors
	public static Encoder leftdriveenc;
	public static Encoder rightdriveenc;
	public static Encoder leftshooterenc;
	public static Encoder rightshooterenc;
	public static Encoder turretenc;
	
	public static AnalogInput pressuretrans;
	
	public static DigitalInput armphotogate;
	public static DigitalInput upperarmlimit;
	public static DigitalInput lowerarmlimit;
	
	public static I2C lidar;
	public static I2C autoselect;
	public static I2C arduino;
	
	//utility
	public static PID leftshooterpid;
	public static PID rightshooterpid;
	public static PID turretpid;
	public static PID leftdrivepid;
	public static PID rightdrivepid;
	
	public static void initPeripherals() {
		xbox1 = new Joystick(0);
		
		
	}

}
