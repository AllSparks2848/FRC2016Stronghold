package org.usfirst.frc.team2848.robot.subsystems;
import org.usfirst.frc.team2848.robot.Definitions;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveShifter {
	private static long start;
	private static boolean danger;
	private static boolean high = false;
	private static int SHIFTTIME = 500;
	
	public static void checkGearShift() {	
		if (Definitions.xbox1.getRawAxis(3) > 0.75) {
			high = false;
		}
		if (Definitions.xbox1.getRawButton(6)){
			high = true;
		}
		double voltage = Definitions.pdp.getVoltage();
	
		if(Definitions.driveshifter.get())
		{
			if(voltage <= 9.0)
			{
				if(!danger) {
					danger = true;
					start = System.currentTimeMillis();
				}
				if(danger)
				{
					if(voltage <=8.0)
					{
						high = false;
						danger = false;
						System.out.println("below 8 V");
					}
				}
				if(danger) {
					if(System.currentTimeMillis()-start > SHIFTTIME) {
						high = false;
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
		Definitions.driveshifter.set(high);
		SmartDashboard.putString("Drive Gear", high ? "high" : "low");
	}
}

