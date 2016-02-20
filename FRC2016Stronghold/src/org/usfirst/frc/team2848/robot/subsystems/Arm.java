package org.usfirst.frc.team2848.robot.subsystems;
 
import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
 
public class Arm {
    private static boolean movingup = false;
    private static boolean movingdown = false;
    private static boolean isMiddle= false;
   
    public static void armRoutine(){
//    	if (Definitions.xbox2.getRawButton(3)){
//    		movingup = true;
//    	}
//    	if (Definitions.xbox2.getRawButton(1)){
//    		movingdown = true;
//    	}
//    	if (movingup){
//    		if (!Definitions.upperarmlimit.get()){
//    			Definitions.armbrake.set(Value.kReverse);
//    			Definitions.ptomotor.set(-0.3);
//    		}
//    		else if (Definitions.upperarmlimit.get()){
//    			Definitions.armbrake.set(Value.kForward);
//    			Definitions.ptomotor.set(0);
//    			movingup = false;
//    		}
//    	}
//    	else if (movingdown){
//    		if (!Definitions.lowerarmlimit.get()){
//    			Definitions.armbrake.set(Value.kReverse);
//    			Definitions.ptomotor.set(0.3);
//    		}
//    		else if (Definitions.lowerarmlimit.get()){
//    			Definitions.armbrake.set(Value.kForward);
//    			Definitions.ptomotor.set(0);
//    			movingdown = false;
//    		}
//    	}
    	
        if (!movingup && !movingdown){
        	if (Math.abs(Definitions.xbox2.getRawAxis(1)) <= 0.25){
    			Definitions.armbrake.set(Value.kForward);
    			Definitions.ptomotor.set(0);
        	}
        	else {
    			Definitions.armbrake.set(Value.kReverse);
    			Definitions.ptomotor.set(Definitions.xbox2.getRawAxis(1));
    			Definitions.ptoshifter.set(Value.kForward);
        	}
        }
    }
   
}