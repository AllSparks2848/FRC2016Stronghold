package org.usfirst.frc.team2848.robot.navigation;

import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.robot.States;
import org.usfirst.frc.team2848.robot.subsystems.Arm;
import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.robot.subsystems.Turret;
import org.usfirst.frc.team2848.util.ArduinoComm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class AutoRoutine {
	private static long startdelaytime = 0;
	public static void auto(boolean doingauto, boolean shooting, int position, int obstacle, int delay) {
		if(doingauto) {
			Definitions.leftdriveenc.reset();
			Definitions.rightdriveenc.reset();
//			Definitions.ballholder.set(170);
			startdelaytime = System.currentTimeMillis();
    		Definitions.armbrake.set(Value.kForward);
    		Definitions.ptoshifter.set(Value.kReverse);
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
			setArmState(obstacle);
			Timer.delay(0.25);
//			while(!States.robotstate.equals("nothing")) {
//				Arm.armRoutine();
//				States.stateRoutine();
//				SparkyIntakeBar.loadingRoutine();
//			}
//			Definitions.ptomotor1.set(0);
//			Definitions.ptomotor2.set(0);
			long statedelay = System.currentTimeMillis()-startdelaytime;
			if(statedelay < delay*1000) Timer.delay(delay-((double)statedelay/1000.0));
			
			driveForward(position, obstacle);
			
			if(obstacle == 9) {
				Definitions.shootertrigger.set(true);
	    		SparkyIntakeBar.position = 2;
	    		SparkyIntakeBar.loadingRoutine();
				Timer.delay(2);
				Definitions.armbrake.set(Value.kReverse);
	    		Definitions.ptoshifter.set(Value.kForward);
			}
//			if(shooting) {
//				States.robotstate = "shooting";
//				s
//				while(!States.robotstate.equals("nothing")) {
//					Arm.armRoutine();
//					States.stateRoutine();
//					SparkyIntakeBar.loadingRoutine();
//				}
//				Definitions.ballholder.set(90);
//				Definitions.ptomotor1.set(0);
//				Definitions.ptomotor2.set(0);
//				setTurretInRange(position);
//				
//				int inrangecount = 0;
//				
//				Definitions.leftshooterpid.setEnabled(true, Definitions.leftshooterenc.getRate());
//				Definitions.rightshooterpid.setEnabled(true, Definitions.rightshooterenc.getRate());
//				Definitions.leftshooterpid.setTarget(5000);
//				Definitions.rightshooterpid.setTarget(5000);
//				boolean isshooting = true;
//				while(inrangecount < 80 ) {			
//					Turret.turretRoutine(1);
//					if(Math.abs(Turret.getPixelTarget()-Turret.TARGET_PIXEL) <= 5) inrangecount++;
//					else inrangecount = 0;
//					Timer.delay(0.01);
//					Definitions.leftshooter.set(Definitions.leftshooterpid.compute(Definitions.leftshooterenc.getRate(), null));
//					Definitions.rightshooter.set(Definitions.rightshooterpid.compute(Definitions.rightshooterenc.getRate(), null));
//					if(System.currentTimeMillis()-startdelaytime > 15000) {
//						isshooting = false;
//						break;
//					}
//				}
//				if(isshooting) {
//				Definitions.shootertrigger.set(true);
//				Definitions.turret.set(0);
//				Timer.delay(0.5);
//				Definitions.leftshooterpid.setEnabled(false, 0);
//				Definitions.leftshooter.set(0);
//				Definitions.rightshooter.set(0);
//				Definitions.rightshooterpid.setEnabled(false, 0);
//				Definitions.shootertrigger.set(false);
//				}
//			}
		}
	}
	
	private static void setTurretInRange(int position) {
		if(position == 0) {
			while (Definitions.turretenc.getDistance() < 500) {
				Definitions.turret.set(0.4);
				Timer.delay(0.01);
			}
			Definitions.turret.set(0);
		}
		else if(position == 1) {
			while (Definitions.turretenc.getDistance() < 300) {
				Definitions.turret.set(0.4);
				Timer.delay(0.01);
				System.out.println(Definitions.turretenc.getDistance());
			}
			Definitions.turret.set(0);
		}
		else if(position == 2) {
			return;
		}
		else if(position == 3) {
			while (Definitions.turretenc.getDistance() > -200) {
				Definitions.turret.set(-0.4);
				Timer.delay(0.01);
			}
			Definitions.turret.set(0);
		}
		else if(position == 4) {
			while (Definitions.turretenc.getDistance() > -200) {
				Definitions.turret.set(-0.4);
				Timer.delay(0.01);
			}
			Definitions.turret.set(0);
		}
	}

	private static void driveForward(int position, int obstacle) {
		ArduinoComm.communicate();
		double yaw = ArduinoComm.getYaw();
		Definitions.leftdriveenc.reset();
		Definitions.rightdriveenc.reset();
		Definitions.leftdrivepid.setEnabled(true, Definitions.leftdriveenc.getRate()*DriveForward.encoderratio);
		Definitions.rightdrivepid.setEnabled(true, Definitions.rightdriveenc.getRate()*DriveForward.encoderratio);
		Definitions.turnpid.setEnabled(true, yaw);
		if(position == 0) { // leftmost, always low bar
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio);
				if(System.currentTimeMillis()-startdelaytime > 10000) {
					break;
				}
			}
			while(!DriveForward.driveForward(-200, 40, yaw));
		}
		else if(position == 1) { // left of center
			double speed = 0;
			if(obstacle == 5) {
				speed = 40;
			}
			else if(obstacle == 2) {
				speed = 50;
			}
			else {
				speed = 70;
			}
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				if(System.currentTimeMillis()-startdelaytime > 10000) {
					break;
				}
			}
			while(!DriveForward.driveForward(-160, speed, yaw));
		}
		else if(position == 2) { //center
			double speed = 0;
			if(obstacle == 5) {
				speed = 40;
			}
			else if(obstacle == 2) {
				speed = 50;
			}
			else {
				speed = 70;
			}
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				if(System.currentTimeMillis()-startdelaytime > 10000) {
					break;
				}
			}
			while(!DriveForward.driveForward(-160, speed, yaw));
		}
		else if(position == 3) { // right of center
			double speed = 0;
			if(obstacle == 5) {
				speed = 40;
			}
			else if(obstacle == 2) {
				speed = 50;
			}
			else {
				speed = 70;
			}
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				if(System.currentTimeMillis()-startdelaytime > 10000) {
					break;
				}
			}
			while(!DriveForward.driveForward(-160, speed, yaw));
		}
		else if(position == 4) { //rightmost
			double speed = 0;
			if(obstacle == 5) {
				speed = 40;
			}
			else if(obstacle == 2) {
				speed = 50;
			}
			else {
				speed = 70;
			}
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				if(System.currentTimeMillis()-startdelaytime > 10000) {
					break;
				}
			}
			while(!DriveForward.driveForward(-160, speed, yaw));
		}
		else if(position == 5) {
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				if(System.currentTimeMillis()-startdelaytime > 10000) {
					break;
				}
			}
			while(!DriveForward.driveForward(-75, 15, yaw));
		}
		Definitions.leftdrivepid.setEnabled(false, 0);
		Definitions.rightdrivepid.setEnabled(false, 0);
		Definitions.turnpid.setEnabled(false, 0);
		for(int i = 0; i < 50; i++){
		if(obstacle == 9) {
		Definitions.drivetrain.tankDrive(0.3, 0.29);
		}
		else {
			Definitions.drivetrain.tankDrive(0.35, 0.35);
		}
		Timer.delay(0.01);
		}
		Definitions.drivetrain.tankDrive(0, 0);
		
	}

	private static void setArmState(int obstacle) {
		if(obstacle == 0) { //low bar
    		SparkyIntakeBar.position = 2;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 1) { //rough terrain
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 2) { //rock wall
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 3 ) { //moat
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 4) { //rampart
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 5) { //portcullis (do not use)
    		SparkyIntakeBar.position = 2;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 6) { //CdF (do not use)
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 7) { //Sally Port (do not use)
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 8) { //drawbridge (do not use)
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
		}
		else if(obstacle == 9) { //spybox
    		SparkyIntakeBar.position = 0;
    		SparkyIntakeBar.loadingRoutine();
		}
	}
} 