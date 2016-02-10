package org.usfirst.frc.team2848.robot.subsystems;
import org.usfirst.frc.team2848.robot.Robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class autoShifter{
	public static PowerDistributionPanel pdp;
	private static boolean  state = false;
	public static void downShift()
	{
		pdp = new PowerDistributionPanel();
		long start = System.nanoTime();
		long end = System.nanoTime() + 500;
		double voltage = pdp.getVoltage();
			while(voltage < 9.0 && start < end)
			{
				state = true;
				voltage = pdp.getVoltage();
				if(voltage > 9.0)
				{
					state = false;
					break;
				}
			}
			if(state)
			{
				Robot.shifter1.set(false);
				System.out.println( "Down SHIFTED!!!!!!!!!!!!" +"\n"+ "Down SHIFTED!!!!!!!!!!!!" +"\n"+ "Down SHIFTED!!!!!!!!!!!!" +"\n"+ "Down SHIFTED!!!!!!!!!!!!" +"\n"+ "Down SHIFTED!!!!!!!!!!!!" + "\n"+"Down SHIFTED!!!!!!!!!!!!" + "\n" +"Down SHIFTED!!!!!!!!!!!!" + "\n"+ "Down SHIFTED!!!!!!!!!!!!" +"\n"+"Down SHIFTED!!!!!!!!!!!!" + "\n"+ "Down SHIFTED!!!!!!!!!!!!" +"\n"+"Down SHIFTED!!!!!!!!!!!!" + "\n"+ "Down SHIFTED!!!!!!!!!!!!" +"\n"+"Down SHIFTED!!!!!!!!!!!!" + "\n"+ "Down SHIFTED!!!!!!!!!!!!" +"\n"+ "Down SHIFTED!!!!!!!!!!!!" +"\n");
			}
		state = false;
	}
	
}
