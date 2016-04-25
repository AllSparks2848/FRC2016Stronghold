
package org.usfirst.frc.team2848.robot;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.usfirst.frc.team2848.robot.navigation.AutoRoutine;
import org.usfirst.frc.team2848.robot.subsystems.Arm;
import org.usfirst.frc.team2848.robot.subsystems.DriveShifter;
import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.robot.subsystems.Turret;
import org.usfirst.frc.team2848.util.ArduinoComm;
import org.usfirst.frc.team2848.util.DigitDriver;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	CameraServer server;
	int currsession;
	int front;
	int back;
	static Image frame;
	static boolean lastbutton4 = false;
	static boolean frontfailed = false;
	static boolean backfailed = false;
	static boolean lastbutton3 = false;
	
    public void robotInit() {
    	System.load("/usr/local/lib/lib_OpenCV/java/libopencv_java2410.so");
    	Definitions.initPeripherals();
//    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
//    	
//    	try {
//    		front = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//    	}
//    	catch (Exception e){
//    		frontfailed = true;
//    	}
//    	try {
//    	back = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//    	}
//    	catch (Exception e){
//    		backfailed = true;
//    	}
//    	if (!backfailed){
//    		currsession = back;
//    		NIVision.IMAQdxConfigureGrab(currsession);
//    	}
//    	else if (!frontfailed){
//    		currsession = front;
//    		NIVision.IMAQdxConfigureGrab(currsession);
//    	}
//    	else {
//    		System.out.println("No cameras");
//    	}
    	
    	
    }
    
    public void autonomousInit() {
    	AtomicInteger positionget = new AtomicInteger(0);
    	Definitions.digit.getPosition(positionget);
    	AtomicInteger defenseget = new AtomicInteger(0);
    	Definitions.digit.getDefense(defenseget);
    	AtomicBoolean doingauto = new AtomicBoolean(false);
    	Definitions.digit.getAuto(doingauto);
    	AtomicBoolean shooting = new AtomicBoolean(false);
    	Definitions.digit.getShooting(shooting);
    	AutoRoutine.auto(doingauto.get(), shooting.get(), positionget.get(), defenseget.get(), 0);
    }
    public void autonomousPeriodic() {
    	Definitions.drivetrain.drive(0, 0);
    	ArduinoComm.communicate();
    	System.out.println(ArduinoComm.getYaw());
    	Timer.delay(0.4);
    }
    public void teleopInit() {
    	
    }
    public void teleopPeriodic() {
    	Definitions.drivetrain.arcadeDrive(Definitions.xbox1.getRawAxis(1), Definitions.xbox1.getRawAxis(4), false);
    	DriveShifter.checkGearShift();
    	SparkyIntakeBar.loadingRoutine();
    	Shooter.firingRoutine(5000);
    	ArduinoComm.communicate();

    	try {
	    	if (!backfailed && !frontfailed){
	        	if(Definitions.buttonbox.getRawButton(2) && !lastbutton4){
	        	    if(currsession == front){
	        	        NIVision.IMAQdxStopAcquisition(currsession);
	        	        currsession = back;
	        	        NIVision.IMAQdxConfigureGrab(currsession);
	        	    } else if(currsession == back){
	        	        NIVision.IMAQdxStopAcquisition(currsession);
	        	        currsession = front;
	        	        NIVision.IMAQdxConfigureGrab(currsession);
	        	    }
	        	}
	    	}
	    	lastbutton4 = Definitions.buttonbox.getRawButton(2);
	    	NIVision.IMAQdxGrab(currsession, frame, 1);
	    	CameraServer.getInstance().setImage(frame);
    	}
    	catch(Exception e) {
//    		System.out.println("Camera problem");
    	}
    	SmartDashboard.putNumber("Pressure", 250 * (Definitions.pressuretrans.getVoltage() / 5.0) - 25);
    	SmartDashboard.putNumber("ballSeater", Definitions.ballholder.get() ? 1 : 0);
    	SmartDashboard.putBoolean("catapultReady", (250 * (Definitions.pressuretrans.getVoltage() / 5.0) - 25) > 40);
    	
    	if (Definitions.joystick.getRawButton(3) && !lastbutton3){
    		Definitions.flashlightrelay.set(Definitions.flashlightrelay.get() == Relay.Value.kOff ? Relay.Value.kForward : Relay.Value.kOff);
    	}
//    	System.out.println(Definitions.flashlightrelay.get());
    	lastbutton3 = Definitions.joystick.getRawButton(3);
    	System.out.println(Definitions.pdp.getCurrent(4));
    	
    	Timer.delay(0.01);
    }
    
    public void testPeriodic() {
    
    }
    
}
