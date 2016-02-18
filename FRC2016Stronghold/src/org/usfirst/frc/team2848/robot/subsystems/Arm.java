package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Arm {
	public static void armRoutine(){
		if (Math.abs(Definitions.xbox2.getRawAxis(1)) <= 0.25) {
			Definitions.ptoshifter.set(Value.kReverse);
			Definitions.armbrake.set(Value.kForward);
			Definitions.ptomotor.set(0);
		}
		else {
			Definitions.armbrake.set(Value.kReverse);
			
			Definitions.ptomotor.set(Definitions.xbox2.getRawAxis(1));
		}
	}
	
}