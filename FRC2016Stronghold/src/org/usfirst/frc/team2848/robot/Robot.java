
package org.usfirst.frc.team2848.robot;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.usfirst.frc.team2848.robot.navigation.AutoRoutine;
import org.usfirst.frc.team2848.robot.subsystems.Arm;
import org.usfirst.frc.team2848.robot.subsystems.DriveShifter;
import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.robot.subsystems.Turret;
import org.usfirst.frc.team2848.util.ArduinoComm;
import org.usfirst.frc.team2848.util.DigitDriver;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
    public void robotInit() {
    	System.load("/usr/local/lib/lib_OpenCV/java/libopencv_java2410.so");
    	Definitions.initPeripherals();
    	CameraServer camera = CameraServer.getInstance();
    	camera.setQuality(50);
    	camera.startAutomaticCapture("cam0");
    	
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
    	
    }
    public void teleopInit() {
    	
    }
    public void teleopPeriodic() {
    	Definitions.drivetrain.arcadeDrive(Definitions.xbox1.getRawAxis(1), Definitions.xbox1.getRawAxis(4), false);
    	DriveShifter.checkGearShift();
    	SparkyIntakeBar.loadingRoutine();
//    	Definitions.turret.set(Definitions.joystick.getRawAxis(1) * 0.35);
//    	Shooter.firingRoutine(5000);
//    	Arm.armRoutine();
//    	int turretmode = 0;
//    	if(Definitions.joystick.getRawButton(2)) turretmode = 1;
//    	else if(Definitions.buttonbox.getRawButton(16)) turretmode = 2;
//    	Turret.turretRoutine(turretmode);
//    	ArduinoComm.communicate();
//    	States.stateRoutine();

    	Timer.delay(0.01);
//    	if (Definitions.joystick.getRawButton(1)){
//    		if (!lastbutton1){
//    			Definitions.armbrake.set(Definitions.armbrake.get() == Value.kReverse ? Value.kForward : Value.kReverse);
//    		}
//    		
//    	}
    	if (Definitions.joystick.getRawButton(1)){
    		Definitions.armbrake.set(Value.kReverse);
    		Definitions.ptoshifter.set(Value.kForward);
    	}
    	else if (Definitions.joystick.getRawButton(2)){
    		Definitions.armbrake.set(Value.kForward);
    		Definitions.ptoshifter.set(Value.kReverse);
    	}
    	if (Definitions.joystick.getRawButton(4)){
    		Definitions.shootertrigger.set(true);
    	}
    	else if (Definitions.joystick.getRawButton(5)){
    		Definitions.shootertrigger.set(false);
    	}
    	if(Definitions.buttonbox.getRawButton(4) && !lastbutton4){
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
    	lastbutton4 = Definitions.buttonbox.getRawButton(4);
    	NIVision.IMAQdxGrab(currsession, frame, 1);
    	CameraServer.getInstance().setImage(frame);
    	System.out.println(250 * (Definitions.pressuretrans.getVoltage() / 5.0) - 25);
    	SmartDashboard.putNumber("Pressure", 250 * (Definitions.pressuretrans.getVoltage() / 5.0) - 25);
    }
    
    public void testPeriodic() {
    
    }
    
}
