package org.usfirst.frc.team2848.robot.subsystems;
 
import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.robot.Robot;
import org.usfirst.frc.team2848.robot.States;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
 
public class Arm {
    private static boolean lastx = false;
    private static boolean lasta = false;
    public static int armstate = 0; /*0 off, 1 moving down, 2 moving up*/
    public static void armRoutine(){
    	Definitions.catapulttwo.set(Value.kReverse);
//    	if (Definitions.joystick.getRawButton(3) && !lastx){
//    		if(armstate == 0) {
//    			armstate  = 2;
//    		}
//    		else armstate = 0;
//    	}
//    	else if (Definitions.joystick.getRawButton(1) && !lasta){
//    		if(armstate == 0) armstate = 1;
//    		else armstate = 0;
//    	}
    	if (armstate == 2){
    		if (Definitions.upperarmlimit.get()){
    			Definitions.catapultone.set(Value.kReverse);
    			Definitions.ptomotor1.set(-0.350);
//    			Definitions.ptomotor2.set(-0.30);
    		}
    		else {
    			Definitions.catapultone.set(Value.kForward);
    			Definitions.ptomotor1.set(0);
//    			Definitions.ptomotor2.set(0);
    			armstate = 0;
    		}
    	}
    	else if (armstate == 1){
    		if (Definitions.lowerarmlimit.get()){
    			Definitions.catapultone.set(Value.kReverse);
    			Definitions.ptomotor1.set(0.5);
//    			Definitions.ptomotor2.set(0.45);
    		}
    		else {
    			Definitions.catapultone.set(Value.kForward);
    			Definitions.ptomotor1.set(0);
//    			Definitions.ptomotor2.set(0);
     			armstate = 0;
    		}
    	}
    	else if (States.robotstate.equals("nothing")){
    		if (Math.abs(Definitions.joystick.getRawAxis(1)) < 0.25){
    			Definitions.ptomotor1.set(0);
//    			Definitions.ptomotor1.set(0);
    			if (Definitions.buttonbox.getRawButton(9)){
    				Definitions.catapultone.set(Value.kReverse);
    			}
    			else {
    				Definitions.catapultone.set(Value.kForward);
    			}
    		}
    		else {
    			if(!Definitions.upperarmlimit.get() || (States.ptoposition < -150 && !Definitions.joystick.getRawButton(11))) {
    				Definitions.ptomotor1.set(Definitions.joystick.getRawAxis(1) > 0 ? Definitions.joystick.getRawAxis(1)*0.7 : 0);
//    				Definitions.ptomotor2.set(Definitions.joystick.getRawAxis(1) > 0 ? Definitions.joystick.getRawAxis(1)*0.4 : 0);
    				Definitions.catapultone.set(Definitions.joystick.getRawAxis(1) > 0 ? Value.kReverse : Value.kForward);
    			}
    			else if(!Definitions.lowerarmlimit.get() || (States.ptoposition > 680 && !Definitions.joystick.getRawButton(11))) {
        			Definitions.ptomotor1.set(Definitions.joystick.getRawAxis(1) < 0 ? Definitions.joystick.getRawAxis(1)*0.7 : 0);
//        			Definitions.ptomotor2.set(Definitions.joystick.getRawAxis(1) < 0 ? Definitions.joystick.getRawAxis(1)*0.4 : 0);
        			Definitions.catapultone.set(Definitions.joystick.getRawAxis(1) < 0 ? Value.kReverse : Value.kForward);
    			}
    			else {
        			Definitions.ptomotor1.set(Definitions.joystick.getRawAxis(1)*0.7);
//        			Definitions.ptomotor2.set(Definitions.joystick.getRawAxis(1)*0.8);
        			Definitions.catapultone.set(Value.kReverse);
    			}
    			
    		}
    	}
    	lastx = Definitions.joystick.getRawButton(3);
    	lasta = Definitions.joystick.getRawButton(1);
    }
   
}