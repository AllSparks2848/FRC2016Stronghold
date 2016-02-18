package org.usfirst.frc.team2848.robot;

import org.usfirst.frc.team2848.util.PID;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;

public class Definitions {
	//port constants
	public static final int XBOX1_PORT = 0;
	public static final int XBOX2_PORT = 1;
	
	public static final int PCM_0 = 0;
	public static final int PCM_1 = 1;
	
	public static final int INTAKE_SOLENOID_A = 3;
	public static final int INTAKE_SOLENOID_B = 0;
	public static final int INTAKE_PANCAKE_A = 1;
	public static final int INTAKE_PANCAKE_B = 2;
	public static final int DRIVE_SHIFTER_A = 0;
	public static final int DRIVE_SHIFTER_B = 7;
	public static final int PTO_SHIFTER_A = 6;
	public static final int PTO_SHIFTER_B = 1;
	public static final int SHOOTER_TRIGGER_A = 4;
	public static final int SHOOTER_TRIGGER_B = 3;
	public static final int ARM_BRAKE_A = 2;
	public static final int ARM_BRAKE_B = 5;
	
	public static final int LEFT_DRIVE_PORT = 0;
	public static final int RIGHT_DRIVE_PORT = 1;
	public static final int LEFT_SHOOTER_PORT = 2;
	public static final int RIGHT_SHOOTER_PORT = 3;
	public static final int PTO_MOTOR_PORT = 4;
	public static final int TURRET_PORT = 5;
	public static final int INTAKE_WHEEL_PORT = 6;
	
	public static final int BALL_HOLDER_PORT = 7;
	
	public static final int LEFT_DRIVE_ENC_A = 0;
	public static final int LEFT_DRIVE_ENC_B = 1;
	public static final int RIGHT_DRIVE_ENC_A = 2;
	public static final int RIGHT_DRIVE_ENC_B = 3;
	public static final int LEFT_SHOOTER_ENC_A = 4;
	public static final int LEFT_SHOOTER_ENC_B = 5;
	public static final int RIGHT_SHOOTER_ENC_A = 6;
	public static final int RIGHT_SHOOTER_ENC_B = 7;
	public static final int TURRET_ENC_A = 8;
	public static final int TURRET_ENC_B = 9;
	
	public static final int ARM_PHOTOGATE_PORT = 10;
	public static final int UPPER_ARM_LIMIT_PORT = 11;
	public static final int LOWER_ARM_LIMIT_PORT = 12;
	
	public static final int PRESSURE_TRANSDUCER_PORT = 0;
	
	public static final int LIDAR_ADDRESS = 0x62;
	public static final int AUTO_SELECT_ADDRESS = 0x70;
	public static final int ARDUINO_ADDRESS = 0x08;
	
	public static final double DRIVE_P = 0;
	public static final double DRIVE_I = 0;
	public static final double DRIVE_D = 0;
	public static final double SHOOTER_P = 0.0023;
	public static final double SHOOTER_I = 0.0008;
	public static final double SHOOTER_D = 0;
	public static final double TURRET_P = 0;
	public static final double TURRET_I = 0;
	public static final double TURRET_D = 0;
	
	public static final double SHOOTER_GEAR_RATIO = (1/3.0);
	
	//control
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
	
	public static Compressor compressor;
	
	//sensors
	public static Encoder leftdriveenc;
	public static Encoder rightdriveenc;
	public static Encoder leftshooterenc;
	public static Encoder rightshooterenc;
	public static Encoder turretenc;
	
	public static AnalogInput pressuretrans;
	
	public static Counter armphotogate;
	
	public static DigitalInput upperarmlimit;
	public static DigitalInput lowerarmlimit;
	
	public static I2C lidar;
	public static I2C autoselect;
	public static I2C arduino;
	
	public static PowerDistributionPanel pdp;
	
	//utility
	public static PID leftshooterpid;
	public static PID rightshooterpid;
	public static PID turretpid;
	public static PID leftdrivepid;
	public static PID rightdrivepid;
	
	
	public static void initPeripherals() {
		xbox1 = new Joystick(XBOX1_PORT);
		xbox2 = new Joystick(XBOX2_PORT);
		
		intakesolenoid = new DoubleSolenoid(PCM_1, INTAKE_SOLENOID_A, INTAKE_SOLENOID_B);
		intakepancake = new DoubleSolenoid(PCM_1, INTAKE_PANCAKE_A, INTAKE_PANCAKE_B);
		driveshifter = new DoubleSolenoid(PCM_0, DRIVE_SHIFTER_A, DRIVE_SHIFTER_B);
		ptoshifter = new DoubleSolenoid(PCM_0, PTO_SHIFTER_A, PTO_SHIFTER_B);
		shootertrigger = new DoubleSolenoid(PCM_0, SHOOTER_TRIGGER_A, SHOOTER_TRIGGER_B);
		armbrake = new DoubleSolenoid(PCM_0, ARM_BRAKE_A, ARM_BRAKE_B);
		
		leftdrive = new Spark(LEFT_DRIVE_PORT);
		rightdrive = new Spark(RIGHT_DRIVE_PORT);
		leftshooter = new Spark(LEFT_SHOOTER_PORT);
		rightshooter = new Spark(RIGHT_SHOOTER_PORT);
		ptomotor = new Spark(PTO_MOTOR_PORT);
		turret = new Spark(TURRET_PORT);
		intakewheel = new Spark(INTAKE_WHEEL_PORT);
		
		leftdrive.setInverted(false);
		rightdrive.setInverted(false);
		leftshooter.setInverted(false);
		rightshooter.setInverted(false);
		ptomotor.setInverted(false);
		turret.setInverted(false);
		intakewheel.setInverted(false);
		
		drivetrain = new RobotDrive(leftdrive, rightdrive);
		
		ballholder = new Servo(BALL_HOLDER_PORT);
		
		compressor = new Compressor();
		
		leftdriveenc = new Encoder(LEFT_DRIVE_ENC_A, LEFT_DRIVE_ENC_B, false, EncodingType.k4X);
		rightdriveenc = new Encoder(RIGHT_DRIVE_ENC_A, RIGHT_DRIVE_ENC_B, false, EncodingType.k4X);
		leftshooterenc = new Encoder(LEFT_SHOOTER_ENC_A, LEFT_SHOOTER_ENC_B, false, EncodingType.k4X);
		rightshooterenc = new Encoder(RIGHT_SHOOTER_ENC_A, RIGHT_SHOOTER_ENC_B, false, EncodingType.k4X);
		turretenc = new Encoder(TURRET_ENC_A, TURRET_ENC_B, false, EncodingType.k4X);
		
		pressuretrans = new AnalogInput(PRESSURE_TRANSDUCER_PORT);
		
		armphotogate = new Counter(ARM_PHOTOGATE_PORT);
		upperarmlimit = new DigitalInput(UPPER_ARM_LIMIT_PORT);
		lowerarmlimit = new DigitalInput(LOWER_ARM_LIMIT_PORT);
		
		lidar = new I2C(Port.kMXP, LIDAR_ADDRESS);
		autoselect = new I2C(Port.kMXP, AUTO_SELECT_ADDRESS);
		arduino = new I2C(Port.kOnboard, ARDUINO_ADDRESS);
		
		pdp = new PowerDistributionPanel();
		
        leftshooterenc.setDistancePerPulse((1/3.0)*SHOOTER_GEAR_RATIO*60);
        rightshooterenc.setDistancePerPulse((1/3.0)*SHOOTER_GEAR_RATIO*60);
		
		leftshooterpid = new PID(SHOOTER_P, SHOOTER_I, SHOOTER_D, 0, leftshooterenc.getRate());
		rightshooterpid = new PID(SHOOTER_P, SHOOTER_I, SHOOTER_D, 0, rightshooterenc.getRate());
		turretpid = new PID(TURRET_P, TURRET_I, TURRET_D, 0, turretenc.getDistance());
		leftdrivepid = new PID(DRIVE_P, DRIVE_I, DRIVE_D, 0, 0);
		rightdrivepid = new PID(DRIVE_P, DRIVE_I, DRIVE_D, 0, 0);
		
	}
}
