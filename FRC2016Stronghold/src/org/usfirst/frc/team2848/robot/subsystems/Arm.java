package org.usfirst.frc.team2848.robot.subsystems;
 
import org.usfirst.frc.team2848.robot.Definitions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
 
public class Arm {
    private static boolean movingup = false;
    private static boolean movingdown = false;
    private static boolean lastx = false;
    private static boolean lasta = false;
    private static int armstate = 0; /*0 off, 1 moving down, 2 moving up*/
    public static void armRoutine(){
    	Definitions.ptoshifter.set(Value.kReverse);
    	if (Definitions.xbox2.getRawButton(3) && !lastx){
    		if(armstate == 0) armstate  = 2;
    		else armstate = 0;
    	}
    	else if (Definitions.xbox2.getRawButton(1) && !lasta){
    		if(armstate == 0) armstate = 1;
    		else armstate = 0;
    	}
    	
    	if (armstate == 2){
    		if (Definitions.upperarmlimit.get()){
    			Definitions.armbrake.set(Value.kReverse);
    			Definitions.ptomotor1.set(-0.35);
    			Definitions.ptomotor2.set(-0.35);
    		}
    		else {
    			Definitions.armbrake.set(Value.kForward);
    			Definitions.ptomotor1.set(0);
    			Definitions.ptomotor2.set(0);
    			armstate = 0;
    		}
    	}
    	else if (armstate == 1){
    		if (Definitions.lowerarmlimit.get()){
    			Definitions.armbrake.set(Value.kReverse);
    			Definitions.ptomotor1.set(0.45);
    			Definitions.ptomotor2.set(0.45);
    		}
    		else {
    			Definitions.armbrake.set(Value.kForward);
    			Definitions.ptomotor1.set(0);
    			Definitions.ptomotor2.set(0);
    			armstate = 0;
    		}
    	}
    	else if(armstate == 0){
    		if (Math.abs(Definitions.xbox2.getRawAxis(1)) < 0.25){
    			Definitions.ptomotor1.set(0);
    			Definitions.ptomotor2.set(0);
    			if (Definitions.xbox2.getRawButton(8)){
    				Definitions.armbrake.set(Value.kReverse);
    			}
    			else {
    				Definitions.armbrake.set(Value.kForward);
    			}
    		}
    		else {
    			if(!Definitions.upperarmlimit.get()) {
    				Definitions.ptomotor1.set(Definitions.xbox2.getRawAxis(1)*0.5 > 0 ? Definitions.xbox2.getRawAxis(1)*0.5 : 0);
    				Definitions.ptomotor2.set(Definitions.xbox2.getRawAxis(1)*0.5 > 0 ? Definitions.xbox2.getRawAxis(1)*0.5 : 0);
    			}
    			else if(!Definitions.lowerarmlimit.get()) {
        			Definitions.ptomotor1.set(Definitions.xbox2.getRawAxis(1)*0.5 < 0 ? Definitions.xbox2.getRawAxis(1)*0.5 : 0);
        			Definitions.ptomotor2.set(Definitions.xbox2.getRawAxis(1)*0.5 < 0 ? Definitions.xbox2.getRawAxis(1)*0.5 : 0);
    			}
    			else {
        			Definitions.ptomotor1.set(Definitions.xbox2.getRawAxis(1)*0.5);
        			Definitions.ptomotor2.set(Definitions.xbox2.getRawAxis(1)*0.5);
    			}
    			Definitions.armbrake.set(Value.kReverse);
    		}
    	}
    	lastx = Definitions.xbox2.getRawButton(3);
    	lasta = Definitions.xbox2.getRawButton(1);
    	//System.out.println(Definitions.upperarmlimit.get() + "  " + Definitions.lowerarmlimit.get());
    }
   
}
