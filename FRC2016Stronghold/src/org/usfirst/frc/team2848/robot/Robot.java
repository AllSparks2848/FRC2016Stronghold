
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
    	Shooter.firingRoutine(5000);
    	Arm.armRoutine();
    	int turretmode = 2;
    	if(Definitions.joystick.getRawButton(2)) turretmode = 1;
    	else if(Definitions.joystick.getRawButton(4) || Definitions.joystick.getRawButton(5) || Definitions.buttonbox.getRawButton(16)) turretmode = 0;
    	Turret.turretRoutine(turretmode);
    	//ArduinoComm.communicate();
    	States.stateRoutine();
//    	System.out.println(Definitions.upperarmlimit.get());
    	Timer.delay(0.01);
    	ArduinoComm.communicate();
    	SmartDashboard.putBoolean("Gear", Definitions.driveshifter.get());
    	SmartDashboard.putNumber("Pressure", Definitions.pressuretrans.getVoltage());
    	//System.out.println(Definitions.turretenc.get());
//    	System.out.println(ArduinoComm.getYaw());
//    	System.out.println(Definitions.turretenc.get());
    	//System.out.println(States.ptoposition);
    	//System.out.println(ArduinoComm.getYaw()  + " " + Definitions.leftdriveenc.getRate() + " " + Definitions.rightdriveenc.getRate());
    }
    
    public void testPeriodic() {
    
    }
    
}
