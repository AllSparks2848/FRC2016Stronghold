package org.usfirst.frc.team2848.robot.subsystems;
import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class autoShifter{
	private static long start; 
	private static boolean danger;
	public static boolean state = true;
	public static int SHIFTTIME = 500;
	
	public static void shift() {	
		double voltage = Robot.pdp.getVoltage();
		if (Definitions.xbox1.getRawAxis(3) > 0.75) { //for drive train
				
			state = true;
		}
		if (Definitions.xbox1.getRawButton(6)){ //for drive train 
				
			state = false;
		}
		if(Definitions.driveshifter.get() != Value.kForward)
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
						state = true;
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
		Definitions.driveshifter.set(state ? Value.kForward : Value.kReverse);
//Its not that bad guys...
	}
	
}
