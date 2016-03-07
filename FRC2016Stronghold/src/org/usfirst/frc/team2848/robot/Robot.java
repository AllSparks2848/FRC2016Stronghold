
package org.usfirst.frc.team2848.robot;

import org.usfirst.frc.team2848.robot.navigation.AutoRoutine;
import org.usfirst.frc.team2848.robot.subsystems.Arm;
import org.usfirst.frc.team2848.robot.subsystems.DriveShifter;
import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.robot.subsystems.Turret;
import org.usfirst.frc.team2848.util.ArduinoComm;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

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
    	AutoRoutine.auto(1, 1, 5);
    }
    public void autonomousPeriodic() {
    	
    }
    public void teleopInit() {
    	
    }
    public void teleopPeriodic() {
    	Definitions.drivetrain.arcadeDrive(Definitions.xbox1.getRawAxis(1), Definitions.xbox1.getRawAxis(4), false);
    	DriveShifter.checkGearShift();
    	SparkyIntakeBar.loadingRoutine();
    	Shooter.firingRoutine(4700);
    	Arm.armRoutine();
    	int turretmode = 0;
    	if(Definitions.joystick.getRawButton(2)) turretmode = 1;
    	else if(Definitions.buttonbox.getRawButton(7)) turretmode = 2;
    	Turret.turretRoutine(turretmode);
    	//ArduinoComm.communicate();
    	States.stateRoutine();
    	System.out.println(States.ptoposition);
    	Timer.delay(0.01);
    	ArduinoComm.communicate();
    	//System.out.println(ArduinoComm.getYaw()  + " " + Definitions.leftdriveenc.getRate() + " " + Definitions.rightdriveenc.getRate());
    }
    
    public void testPeriodic() {
    
    }
    
}
