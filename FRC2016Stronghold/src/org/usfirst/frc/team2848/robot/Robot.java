
package org.usfirst.frc.team2848.robot;
import org.usfirst.frc.team2848.robot.subsystems.Arm;
import org.usfirst.frc.team2848.robot.subsystems.DriveShifter;
import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.robot.subsystems.Turret;


import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
    public void robotInit() {
    	System.load("/usr/local/lib/lib_OpenCV/java/libopencv_java2410.so");
    	Definitions.initPeripherals();
    	
    	
    }
    
    public void autonomousPeriodic() {
    	
    }

    public void teleopPeriodic() {
    	Definitions.drivetrain.arcadeDrive(Definitions.xbox1.getRawAxis(1), Definitions.xbox1.getRawAxis(4));
    	DriveShifter.checkGearShift();
    	SparkyIntakeBar.loadingRoutine();
    	Shooter.firingRoutine(4000);
    	Arm.armRoutine();
    	Turret.turretRoutine(Definitions.xbox2.getRawButton(4));
    	System.out.println(Definitions.turretenc.getDistance());
    	
    
    }
    
    public void testPeriodic() {
    	
    }
    
}
