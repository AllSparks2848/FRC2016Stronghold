package org.usfirst.frc.team2848.robot.subsystems;
import org.usfirst.frc.team2848.robot.Robot;

public class motorController {
	public static void controlMotors()
	{
		
	if(Robot.xbox1.getPOV() == 3)//change button for later
	{
		 Robot.intakeMotor.set(-.5);
	}
	else
	{
		Robot.intakeMotor.set(0);
	}
		
	}
}
