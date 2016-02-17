package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

public class Turret {
	public static void turretRoutine() {
		Definitions.turret.set(Definitions.xbox2.getRawAxis(4)*0.4);
	}
}
