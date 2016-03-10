package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	private static double start;
	private static boolean lastbutton1 = false;
	private static boolean lastbutton3 = false;
	private static boolean startposition = true;
	private static boolean lastbutton5 = false;
	private static boolean firing = false;

	public static void firingRoutine(double speed){
		if (Definitions.joystick.getRawButton(3)){
			Definitions.leftshooterpid.setTarget(speed);
			Definitions.rightshooterpid.setTarget(speed);
			
			if (!Definitions.leftshooterpid.getEnabled() && !lastbutton3) {
				Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
				Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
				
			}
			lastbutton3 = true;
		}
		if (Definitions.joystick.getRawButton(1) && !lastbutton1){
			startposition = true;
			start = System.currentTimeMillis();
			lastbutton1 = true;
			firing = true;
		}
		else if (Definitions.joystick.getRawButton(1)){
			lastbutton1 = false;
		}
		if (!Definitions.shootertrigger.get() && (System.currentTimeMillis() > start + 250) && firing){
			Definitions.shootertrigger.set(true);
		}
		if (Definitions.shootertrigger.get() && (System.currentTimeMillis() > start + 750) && firing){
			Definitions.shootertrigger.set(false);
			firing = false;
		}
		if (!Definitions.joystick.getRawButton(3))
		{
			lastbutton3 = false;
			Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
			
		}
		if (Definitions.buttonbox.getRawButton(10) && !lastbutton5){
			startposition = startposition ? false : true;
			lastbutton5 = true;
		}
		lastbutton5 = Definitions.buttonbox.getRawButton(10);
		
		Definitions.ballholder.setAngle(startposition ? 90 : 170);
		
		if (Definitions.leftshooterpid.getEnabled()){
			//double[] leftval = new double[5];
			Definitions.leftshooter.set(Definitions.leftshooterpid.compute(Definitions.leftshooterenc.getRate(), null));
			//System.out.println(leftval[0] + " " + leftval[1] + " " + leftval[2] + " " + leftval[3] + " " + leftval[4]);
			Definitions.rightshooter.set(Definitions.rightshooterpid.compute(Definitions.rightshooterenc.getRate(), null));
		}
		SmartDashboard.putBoolean("Servo Engaged", startposition);
		SmartDashboard.putBoolean("Shooter Ready", Definitions.leftshooterenc.getRate() >= 4200 && Definitions.rightshooterenc.getRate() >= 4200);
	}
} 
