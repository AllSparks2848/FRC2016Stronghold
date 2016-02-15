
package org.usfirst.frc.team2848.robot;
import org.usfirst.frc.team2848.robot.subsystems.DriveHelper;
import org.usfirst.frc.team2848.robot.subsystems.autoShifter;
import org.usfirst.frc.team2848.robot.subsystems.motorController;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {
public static Joystick xbox1;
public static Solenoid shifter1;
public static DoubleSolenoid shifterArm;
public static DoubleSolenoid raiseArm;
public static DoubleSolenoid extendArm;
public static RobotDrive drivetrain;
public static Compressor compressor;
public static Talon bottomleft;
public static Talon bottomright;
public static Talon intakeMotor;
public static Talon armMotor;

    public void robotInit() {  	
    	armMotor = new Talon(2);
    	bottomleft = new Talon(0);
    	bottomright = new Talon(1);
    	intakeMotor= new Talon(3);
    	drivetrain = new RobotDrive(bottomleft, bottomright);
    	xbox1 = new Joystick(0);
    	shifter1 = new Solenoid(5,0);
    	shifterArm = new DoubleSolenoid(5,1,2);
    	raiseArm = new DoubleSolenoid(5,3,4);
    	extendArm = new DoubleSolenoid(5,5,6);
    	compressor = new Compressor(5);
    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
    	compressor.setClosedLoopControl(true);
        autoShifter.downShift();
//       motorController.controlMotors();
       DriveHelper.arcadeDrive(xbox1, drivetrain); 
    	//implement code so that the motor cant go backwards when on its climbing gear
    	if (xbox1.getRawButton(3)) armMotor.set(.2);
    	else if (xbox1.getRawButton(4)) armMotor.set(-.2);
    	else armMotor.set(0);
    	
        
    }
    
    public void testPeriodic() {
    	//hello
    }
    
}
