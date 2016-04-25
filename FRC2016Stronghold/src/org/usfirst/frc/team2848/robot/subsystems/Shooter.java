package org.usfirst.frc.team2848.robot.subsystems;

import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {


	public static void firingRoutine(double speed){
    	if (Definitions.joystick.getRawButton(1)){
    		Definitions.catapultone.set(Value.kReverse);
    		Definitions.catapulttwo.set(Value.kForward);
    	}
    	else if (Definitions.joystick.getRawButton(2)){
    		Definitions.catapultone.set(Value.kForward);
    		Definitions.catapulttwo.set(Value.kReverse);
    	}
    	if (Definitions.joystick.getRawButton(4)){
    		Definitions.ballholder.set(true);
    	}
    	else if (Definitions.joystick.getRawButton(5)){
    		Definitions.ballholder.set(false);
    	}
	}
} 
