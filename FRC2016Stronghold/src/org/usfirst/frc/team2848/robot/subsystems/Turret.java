package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

public class Turret {
	public static void turretRoutine() {
		if(Definitions.lowerarmlimit.get())
		{
			if (!Definitions.xbox2.getRawButton(4)){
				Definitions.turret.set(Definitions.xbox2.getRawAxis(4)*0.4);
			}
		}
		else
		{
			Definitions.turret.set(0);
		}
	}
	public static void centerTurret()
	{
		double directionToMove = 0;
		if(Definitions.turretenc.get() > 0)
		{
			directionToMove = -.5;
		}
		else if(Definitions.turretenc.get() < 0)
		{
			directionToMove = .5;
		}
		else if(Definitions.turretenc.get() <= 150 && Definitions.turretenc.get() >= -150)
		{
			directionToMove = 0;
		}
		System.out.println(Definitions.turretenc.get());
		Definitions.turret.set(directionToMove);
	}
}
