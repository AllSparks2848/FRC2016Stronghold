
package org.usfirst.frc.team2848.robot;
import org.usfirst.frc.team2848.robot.navigation.DriveForward;
import org.usfirst.frc.team2848.robot.subsystems.Arm;
import org.usfirst.frc.team2848.robot.subsystems.DriveShifter;
import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.robot.subsystems.Turret;
import org.usfirst.frc.team2848.util.ArduinoComm;

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
    	
    	
    }
    
    public void autonomousInit() {
    	DriveForward.driveInit();
    }
    public void autonomousPeriodic() {
    	DriveForward.driveForward(0, 0);
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
    	if(Definitions.xbox2.getRawButton(4)) turretmode = 1;
    	else if(Definitions.xbox2.getRawButton(2)) turretmode = 2;
    	Turret.turretRoutine(turretmode);
    	System.out.println(Definitions.leftshooterenc.getRate() + " " + Definitions.rightshooterenc.getRate());
    	//ArduinoComm.communicate();
    	Timer.delay(0.01);
    
    }
    
    public void testPeriodic() {
    	
    }
    
}
