package org.usfirst.frc.team2848.robot.subsystems;
import org.usfirst.frc.team2848.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class autoShifter{
	public static PowerDistributionPanel pdp;
	private static long start;
	private static boolean danger;
	public static boolean state = true;
	public static boolean stateArm = true;
	public static boolean raiseIntake = false;
	public static boolean extendIntake = false;
	public static int SHIFTTIME = 500;
	public static void downShift() {	
		if (Robot.xbox1.getRawAxis(3) > 0.75) { //for drive train
				
			state = true;
		}
		if (Robot.xbox1.getRawButton(6)){ //for drive train 
				
			state = false;
		}
		if(Robot.xbox1.getRawButton(1)) // a = 1 b = 2 //for gear box 
		{
			stateArm = true;
		}
		if(Robot.xbox1.getRawButton(2)) // a = 1 b = 2 //for gear box
		{
			stateArm = false;
		}
		if(Robot.xbox1.getRawButton(5)) // a = 1 b = 2 //for raise intake arm
		{
			raiseIntake = true;
		}
		if(Robot.xbox1.getRawAxis(2) > 0.75) // a = 1 b = 2 //Extend intake bar
		{
			extendIntake = true;
		}
		
		
		pdp = new PowerDistributionPanel();
		double voltage = pdp.getVoltage();
	
		if(!Robot.shifter1.get())
		{
			if(voltage <= 9.0) //&& start < end)
			{
				if(!danger) {
					danger = true;
					start = System.currentTimeMillis();
				}
				if(danger)
				{
					if(voltage <=8.0)
					{
						state = true; //low gear instant
						danger = false;
						System.out.println("below 8 V");
					}
				}
				if(danger) {
					if(System.currentTimeMillis()-start > SHIFTTIME) {
						state = true;
						danger = false;
					}
				}
			}
			else {
				danger = false;
			}
			
	}
	else {
		danger = false;
	}
			
		
	}
	
}
