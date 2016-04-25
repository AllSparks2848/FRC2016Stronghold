package org.usfirst.frc.team2848.robot;

import org.usfirst.frc.team2848.robot.navigation.ImageProcessing;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.util.ArduinoComm;
import org.usfirst.frc.team2848.util.DigitDriver;
import org.usfirst.frc.team2848.util.LidarLiteSensor;
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
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Definitions {
	//port constants
	public static final int XBOX1_PORT = 0;
	public static final int JOYSTICK_PORT = 1;
	public static final int BUTTON_BOX_PORT = 2;
	
	public static final int PCM_0 = 0;
	public static final int PCM_1 = 1;
	
	public static final int INTAKE_SOLENOID_A = 3;
	public static final int INTAKE_SOLENOID_B = 0;
	public static final int INTAKE_PANCAKE_A = 1;
	public static final int INTAKE_PANCAKE_B = 2;
//	public static final int DRIVE_SHIFTER_A = 0;
	public static final int DRIVE_SHIFTER = 7;
	public static final int CATAPULT_TWO_A = 1;
	public static final int CATAPULT_TWO_B = 6;
	public static final int BALL_HOLDER = 4;
	public static final int CATAPULT_ONE_A = 2;
	public static final int CATAPULT_ONE_B = 5;
	
	public static final int LEFT_DRIVE_PORT_1 = 0;
	public static final int LEFT_DRIVE_PORT_2 = 1;
	public static final int RIGHT_DRIVE_PORT_1 = 2;
	public static final int RIGHT_DRIVE_PORT_2 = 3;
	public static final int LEFT_SHOOTER_PORT = 6;
	public static final int RIGHT_SHOOTER_PORT = 7;
	public static final int PTO_MOTOR_PORT_1 = 4;
	public static final int PTO_MOTOR_PORT_2 = 5;
	public static final int TURRET_PORT = 8;
	public static final int INTAKE_WHEEL_PORT = 17;
	
	public static final int BALL_HOLDER_PORT = 9;
	
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
	public static final int PTO_ENC_A = 12;
	public static final int PTO_ENC_B = 13;
	public static final int ARM_PHOTOGATE_PORT = 14;
	public static final int UPPER_ARM_LIMIT_PORT = 11;
	public static final int LOWER_ARM_LIMIT_PORT = 10;
	public static final int TURRET_POT_PORT = 2;
	
	public static final int PRESSURE_TRANSDUCER_PORT = 3;
	
	public static final int LIDAR_ADDRESS = 0x62;
	public static final int AUTO_SELECT_ADDRESS = 0x70;
	public static final int ARDUINO_ADDRESS = 0x08;
	
	public static final int FLASHLIGHT_RELAY_PORT = 1;
	
	public static final double DRIVE_P = 0.008;
	public static final double DRIVE_I = 0.008;
	public static final double DRIVE_D = -0.0005;
	public static final double TURNDRIVE_P = 10;
	public static final double TURNDRIVE_I = 2;
	public static final double TURNDRIVE_D = 0;
	public static final double TURN_P = 0.06;
	public static final double TURN_I = 0.05;
	public static final double TURN_D = -0.005;
	public static final double SHOOTER_P = 0.0008;
	public static final double SHOOTER_I = 0.001;
	public static final double SHOOTER_D = 0;
	public static final double DRIVEAIM_P = 0.03;
	public static final double DRIVEAIM_I = 0.02;
	public static final double DRIVEAIM_D = 0.000;
	public static final double TURRETCENTER_P = -0.01;
	public static final double TURRETCENTER_I = 0.000;
	public static final double TURRETCENTER_D = 0.000;
	public static final double ARM_P = 0.012;
	public static final double ARM_I = 0.001;
	public static final double ARM_D = -0.002;
	public static final double LIDAR_DRIVE_P = 0.05;
	public static final double LIDAR_DRIVE_I = 0;
	public static final double LIDAR_DRIVE_D = 0;
	
	public static final double SHOOTER_GEAR_RATIO = (1/3.0);
	
	//control
    public static Joystick xbox1;
    public static Joystick joystick;
    public static Joystick buttonbox;
    
    //actuators
    public static DoubleSolenoid intakesolenoid;
	public static DoubleSolenoid intakepancake;	
	public static Solenoid driveshifter;
	public static DoubleSolenoid catapulttwo;	
	public static Solenoid ballholder;
	public static DoubleSolenoid catapultone;
	
	
	public static Spark leftdrive1;
	public static Spark leftdrive2;
	public static Spark rightdrive1;
	public static Spark rightdrive2;
	public static Spark leftshooter;
	public static Spark rightshooter;
	public static Spark ptomotor1;
	public static Spark ptomotor2;
	public static Spark turret;
	public static Spark intakewheel;
	
	public static RobotDrive drivetrain;
	

	
	public static Compressor compressor;
	
	//sensors
	public static Encoder leftdriveenc;
	public static Encoder rightdriveenc;
	public static Encoder leftshooterenc;
	public static Encoder rightshooterenc;
	public static Encoder turretenc;
	public static Encoder ptoenc;
	
	public static AnalogInput pressuretrans;
	public static AnalogInput turretpot;
	
	public static Counter armphotogate;
	
	public static DigitalInput upperarmlimit;
	public static DigitalInput lowerarmlimit;
	
	public static I2C arduino;
	
	public static PowerDistributionPanel pdp;
	
	public static DigitDriver digit;
	
	public static LidarLiteSensor lidar;
	
	//utility
	public static PID leftshooterpid;
	public static PID rightshooterpid;
	public static PID driveaimpid;
	public static PID turretcenterpid;
	public static PID leftdrivepid;
	public static PID rightdrivepid;
	public static PID turndrivepid;
	public static PID turnpid;
	public static PID armpid;
	public static PID lidardrivepid;
	
	public static ImageProcessing processing;
	
	public static Relay flashlightrelay;
	
	public static void initPeripherals() {
		xbox1 = new Joystick(XBOX1_PORT);
		joystick = new Joystick(JOYSTICK_PORT);
		buttonbox = new Joystick(BUTTON_BOX_PORT);
		intakesolenoid = new DoubleSolenoid(PCM_1, INTAKE_SOLENOID_A, INTAKE_SOLENOID_B);
		intakepancake = new DoubleSolenoid(PCM_1, INTAKE_PANCAKE_A, INTAKE_PANCAKE_B);
		driveshifter = new Solenoid(PCM_0, DRIVE_SHIFTER);
		catapulttwo = new DoubleSolenoid(PCM_0, CATAPULT_TWO_A, CATAPULT_TWO_B);
		ballholder = new Solenoid(PCM_0, BALL_HOLDER);
		catapultone = new DoubleSolenoid(PCM_0, CATAPULT_ONE_A, CATAPULT_ONE_B);
		
		leftdrive1 = new Spark(LEFT_DRIVE_PORT_1);
		leftdrive2 = new Spark(LEFT_DRIVE_PORT_2);
		rightdrive1 = new Spark(RIGHT_DRIVE_PORT_1);
		rightdrive2 = new Spark(RIGHT_DRIVE_PORT_2);
		leftshooter = new Spark(LEFT_SHOOTER_PORT);
		rightshooter = new Spark(RIGHT_SHOOTER_PORT);
		ptomotor1 = new Spark(PTO_MOTOR_PORT_1);
		ptomotor2 = new Spark(PTO_MOTOR_PORT_2);
		turret = new Spark(TURRET_PORT);
		intakewheel = new Spark(INTAKE_WHEEL_PORT);
		
		leftdrive1.setInverted(true);
		leftdrive2.setInverted(true);
		rightdrive1.setInverted(true);
		rightdrive2.setInverted(true);
		leftshooter.setInverted(false);
		rightshooter.setInverted(false);
		ptomotor1.setInverted(false);
		ptomotor2.setInverted(false);
		turret.setInverted(true);
		intakewheel.setInverted(false);
		
		drivetrain = new RobotDrive(leftdrive1, leftdrive2, rightdrive1, rightdrive2);
		
		
		compressor = new Compressor();
		
		leftdriveenc = new Encoder(LEFT_DRIVE_ENC_A, LEFT_DRIVE_ENC_B, true, EncodingType.k4X);
		leftdriveenc.setDistancePerPulse(4);
		rightdriveenc = new Encoder(RIGHT_DRIVE_ENC_A, RIGHT_DRIVE_ENC_B, false, EncodingType.k4X);
		leftshooterenc = new Encoder(LEFT_SHOOTER_ENC_A, LEFT_SHOOTER_ENC_B, true, EncodingType.k4X);
		rightshooterenc = new Encoder(RIGHT_SHOOTER_ENC_A, RIGHT_SHOOTER_ENC_B, false, EncodingType.k4X);
		turretenc = new Encoder(TURRET_ENC_A, TURRET_ENC_B, false, EncodingType.k4X);
		ptoenc = new Encoder(PTO_ENC_A, PTO_ENC_B, true, EncodingType.k4X);
		
		leftdriveenc.reset();
		rightdriveenc.reset();
		leftshooterenc.reset();
		rightshooterenc.reset();
		turretenc.reset();
		ptoenc.reset();
		
		pressuretrans = new AnalogInput(PRESSURE_TRANSDUCER_PORT);
		turretpot = new AnalogInput(TURRET_POT_PORT);
		
		armphotogate = new Counter(ARM_PHOTOGATE_PORT);
		upperarmlimit = new DigitalInput(UPPER_ARM_LIMIT_PORT);
		lowerarmlimit = new DigitalInput(LOWER_ARM_LIMIT_PORT);
		
		
		arduino = new I2C(Port.kOnboard, ARDUINO_ADDRESS);
		
		pdp = new PowerDistributionPanel();
		
        leftshooterenc.setDistancePerPulse((1/3.0)*SHOOTER_GEAR_RATIO*60);
        rightshooterenc.setDistancePerPulse((1/3.0)*SHOOTER_GEAR_RATIO*60);
		
		leftshooterpid = new PID(SHOOTER_P, SHOOTER_I, SHOOTER_D, 0, leftshooterenc.getRate());
		leftshooterpid.setBounds(-1, 1);
		leftshooterpid.setITermBounds(-1, 1);
		rightshooterpid = new PID(SHOOTER_P, SHOOTER_I, SHOOTER_D, 0, rightshooterenc.getRate());
		rightshooterpid.setBounds(-1, 1);
		rightshooterpid.setITermBounds(-1, 1);
		driveaimpid = new PID(DRIVEAIM_P, DRIVEAIM_I, DRIVEAIM_D, turretenc.getDistance(), turretenc.getDistance());
		driveaimpid.setBounds(-0.65, 0.65);
		driveaimpid.setITermBounds(-0.65, 0.65);
		leftdrivepid = new PID(DRIVE_P, DRIVE_I, DRIVE_D, 0, leftdriveenc.getDistance());
		leftdrivepid.setBounds(-1, 1);
		leftdrivepid.setITermBounds(-1, 1);
		rightdrivepid = new PID(DRIVE_P, DRIVE_I, DRIVE_D, 0, rightdriveenc.getDistance());
		rightdrivepid.setBounds(-1, 1);
		rightdrivepid.setITermBounds(-1, 1);
		turndrivepid = new PID(TURNDRIVE_P, TURNDRIVE_I, TURNDRIVE_D, 0, ArduinoComm.getYaw());
		turndrivepid.setBounds(-10, 10);
		turndrivepid.setITermBounds(-10, 10);
		turnpid = new PID(TURN_P, TURN_I, TURN_D, 0, ArduinoComm.getYaw());
		turnpid.setBounds(-1, 1);
		turnpid.setITermBounds(-1, 1);
		turretcenterpid = new PID(TURRETCENTER_P, TURRETCENTER_I, TURRETCENTER_D, 0, turretenc.getDistance());
		turretcenterpid.setBounds(-0.4, 0.4);
		turretcenterpid.setITermBounds(-0.15, 0.15);
		armpid = new PID(ARM_P, ARM_I, ARM_D, 0, States.ptoposition);
		armpid.setBounds(-0.6, 0.6);
		armpid.setITermBounds(-0.2, 0.2);
		lidardrivepid = new PID(LIDAR_DRIVE_P, LIDAR_DRIVE_I, LIDAR_DRIVE_D, 0, 0);
		lidardrivepid.setBounds(-0.7, 0.7);
		lidardrivepid.setITermBounds(0, 0);
		
		processing = new ImageProcessing();
		processing.start();
		
		lidar = new LidarLiteSensor(Port.kMXP);
//		lidar.start(20);
		
    	digit = new DigitDriver();
    	digit.start();
	}
}
