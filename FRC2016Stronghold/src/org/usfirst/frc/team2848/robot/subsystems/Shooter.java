package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter {
	private static double start;
	private static boolean lastaxis3 = false;
	private static boolean lastbutton6 = false;
	private static final int HOLDINGPOSITION = 180;
	private static boolean startposition = true;
	private static boolean lastbutton7 = false;
	private static int ballposition = 77;
	private static int position = 0;
	public static void firingRoutine(double speed){
		if (Definitions.xbox2.getRawButton(6)){
			Definitions.leftshooterpid.setTarget(speed);
			Definitions.rightshooterpid.setTarget(speed);
			
			if (!Definitions.leftshooterpid.getEnabled() && !lastbutton6) {
				Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
				Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
			}
			lastbutton6 = true;
		}
		if (Definitions.xbox2.getRawAxis(3) > 0.75 && !lastaxis3){
			Definitions.shootertrigger.set(true);
			start = System.currentTimeMillis();
			lastaxis3 = true;
		}
		else if (Definitions.xbox2.getRawAxis(3) <= 0.75){
			lastaxis3 = false;
		}
		if (Definitions.shootertrigger.get() && (System.currentTimeMillis() > start + 500)){
			Definitions.shootertrigger.set(false);
		}
		if (!Definitions.xbox2.getRawButton(6))
		{
			lastbutton6 = false;
			Definitions.leftshooterpid.setEnabled(false, Definitions.leftshooterenc.getRate());
			Definitions.rightshooterpid.setEnabled(false, Definitions.rightshooterenc.getRate());
		}
		if (Definitions.xbox2.getRawButton(7) && !lastbutton7 && startposition){
			startposition = false;
			lastbutton7 = true;
		}
		else if (Definitions.xbox2.getRawButton(7) && !lastbutton7 && !startposition){
			startposition = true;
			lastbutton7 = true;
		}
		if (!Definitions.xbox2.getRawButton(7)){
			lastbutton7 = false;
		}
		if (!Definitions.xbox2.getRawButton(7)){
			lastbutton7 = false;
		}
		if (Definitions.xbox2.getRawButton(7)){
			position += 1;
		}
		//Definitions.ballholder.setAngle(position);
		System.out.println(position);
		Definitions.ballholder.setAngle(startposition ? 90 : 180);
		if (Definitions.leftshooterpid.getEnabled()){
			//double[] leftval = new double[5];
			Definitions.leftshooter.set(Definitions.leftshooterpid.compute(Definitions.leftshooterenc.getRate(), null));
			//System.out.println(leftval[0] + " " + leftval[1] + " " + leftval[2] + " " + leftval[3] + " " + leftval[4]);
			Definitions.rightshooter.set(Definitions.rightshooterpid.compute(Definitions.rightshooterenc.getRate(), null));
		}
	}
} 