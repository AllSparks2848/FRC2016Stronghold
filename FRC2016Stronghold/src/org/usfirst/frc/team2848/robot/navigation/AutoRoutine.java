package org.usfirst.frc.team2848.robot.navigation;

import java.util.ArrayList;

import org.usfirst.frc.team2848.robot.Definitions;
import org.usfirst.frc.team2848.robot.States;
import org.usfirst.frc.team2848.robot.subsystems.Arm;
import org.usfirst.frc.team2848.robot.subsystems.Shooter;
import org.usfirst.frc.team2848.robot.subsystems.SparkyIntakeBar;
import org.usfirst.frc.team2848.robot.subsystems.Turret;
import org.usfirst.frc.team2848.util.ArduinoComm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class AutoRoutine {
	private static long startdelaytime = 0;
	public static void auto(boolean doingauto, boolean shooting, int position, int obstacle, int delay) {
		if(doingauto) {
			deadReckonManeuver(position, obstacle);
			if(shooting) {
				shootingRoutine();
			}
		}
		else {
    		Definitions.catapultone.set(Value.kForward);
    		Definitions.catapulttwo.set(Value.kReverse);
    		Definitions.ballholder.set(false);
			ArduinoComm.communicate();
			Timer.delay(0.01);
			double yaw = ArduinoComm.getYaw();
			Definitions.leftdriveenc.reset();
			Definitions.rightdriveenc.reset();
			DriveForward.decimateResetLeft();
			DriveForward.decimateResetRight();
			Definitions.leftdrivepid.setEnabled(true, Definitions.leftdriveenc.getRate()*DriveForward.encoderratio);
			Definitions.rightdrivepid.setEnabled(true, Definitions.rightdriveenc.getRate()*DriveForward.encoderratio);
			Definitions.turndrivepid.setEnabled(true, ArduinoComm.getYaw());
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				//System.out.println(p + " " + i + " " + d);
				System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//				if(System.currentTimeMillis()-startdelaytime > 30000) {
//					break;
//				}
				
			}
			while(!DriveForward.driveForward(38, 50, yaw));
			Definitions.drivetrain.arcadeDrive(0.3, 0);
			
			Timer.delay(0.25);
			Definitions.intakesolenoid.set(Value.kForward);
			Definitions.intakepancake.set(Value.kForward);
			Timer.delay(1.5);
			Definitions.leftdriveenc.reset();
			Definitions.rightdriveenc.reset();
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				//System.out.println(p + " " + i + " " + d);
				System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//				if(System.currentTimeMillis()-startdelaytime > 30000) {
//					break;
//				}
				
			}
			while(!DriveForward.driveForward(100, 60, yaw));
			Timer.delay(0.25);
			Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
			do {
				ArduinoComm.communicate();
				Timer.delay(0.05);
			} while(!TurnControl.turn(yaw-40, 0.75));
			
			Definitions.drivetrain.arcadeDrive(0, 0);
			
		}
	}
	
	public static void deadReckonManeuver(int position, int obstacle) {
		Definitions.catapultone.set(Value.kForward);
		Definitions.catapulttwo.set(Value.kReverse);
		Definitions.ballholder.set(false);
		ArduinoComm.communicate();
		Timer.delay(0.01);
		double yaw = ArduinoComm.getYaw();
		Definitions.leftdriveenc.reset();
		Definitions.rightdriveenc.reset();
		DriveForward.decimateResetLeft();
		DriveForward.decimateResetRight();
		Definitions.leftdrivepid.setEnabled(true, Definitions.leftdriveenc.getRate()*DriveForward.encoderratio);
		Definitions.rightdrivepid.setEnabled(true, Definitions.rightdriveenc.getRate()*DriveForward.encoderratio);
		Definitions.turndrivepid.setEnabled(true, ArduinoComm.getYaw());
		
		
		if(position == 0) {
			Definitions.intakesolenoid.set(Value.kForward);
			Definitions.intakepancake.set(Value.kForward);
			Timer.delay(0.5);
			do {
				ArduinoComm.communicate();
				Timer.delay(0.01);
				//System.out.println(p + " " + i + " " + d);
				System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//				if(System.currentTimeMillis()-startdelaytime > 30000) {
//					break;
//				}
				if(Definitions.leftdriveenc.getDistance()*DriveForward.encoderratio > 120 || Definitions.rightdriveenc.getDistance()*DriveForward.encoderratio > 120) yaw += 20;
				
			}
			while(!DriveForward.driveForward(-210, 50, yaw));
//			Definitions.intakesolenoid.set(Value.kReverse);
//			Definitions.intakepancake.set(Value.kReverse);
			Definitions.drivetrain.arcadeDrive(-0.3, 0);
			
			Timer.delay(0.5);
			Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
			do {
				ArduinoComm.communicate();
				Timer.delay(0.05);
			} while(!TurnControl.turn(yaw-12.5, 0.75));
			
			Definitions.drivetrain.arcadeDrive(0, 0);
		}
		
		if(position == 1) {
			if(obstacle == 6) {
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//					if(System.currentTimeMillis()-startdelaytime > 30000) {
//						break;
//					}
					
				}
				while(!DriveForward.driveForward(38, 50, yaw));
				Definitions.drivetrain.arcadeDrive(0.3, 0);
				
				Timer.delay(0.25);
				Definitions.intakesolenoid.set(Value.kForward);
				Definitions.intakepancake.set(Value.kForward);
				Timer.delay(1.5);
				Definitions.leftdriveenc.reset();
				Definitions.rightdriveenc.reset();
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//					if(System.currentTimeMillis()-startdelaytime > 30000) {
//						break;
//					}
					
				}
				while(!DriveForward.driveForward(190, 70, yaw+1));
				Timer.delay(0.25);
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.05);
				} while(!TurnControl.turn(yaw+30, 0.75));
				
				Definitions.drivetrain.arcadeDrive(0, 0);
			}
			else {
				int speed = 0;
				if(obstacle == 2) speed = 50;
				else speed = 70;
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
	//				if(System.currentTimeMillis()-startdelaytime > 30000) {
	//					break;
	//				}
					
				}
				while(!DriveForward.driveForward(-275, speed, yaw));
	//			Definitions.intakesolenoid.set(Value.kReverse);
	//			Definitions.intakepancake.set(Value.kReverse);
				Definitions.drivetrain.arcadeDrive(-0.3, 0);
				
				Timer.delay(0.5);
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.05);
				} while(!TurnControl.turn(yaw-10, 0.75));
				
				Definitions.drivetrain.arcadeDrive(0, 0);
			}
		}
		
		if(position == 2) {
			if(obstacle == 6) {
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//					if(System.currentTimeMillis()-startdelaytime > 30000) {
//						break;
//					}
					
				}
				while(!DriveForward.driveForward(38, 50, yaw));
				Definitions.drivetrain.arcadeDrive(0.3, 0);
				
				Timer.delay(0.25);
				Definitions.intakesolenoid.set(Value.kForward);
				Definitions.intakepancake.set(Value.kForward);
				Timer.delay(1.5);
				Definitions.leftdriveenc.reset();
				Definitions.rightdriveenc.reset();
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//					if(System.currentTimeMillis()-startdelaytime > 30000) {
//						break;
//					}
					
				}
				while(!DriveForward.driveForward(100, 70, yaw));
				Timer.delay(0.25);
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.05);
				} while(!TurnControl.turn(yaw+40, 0.75));
				
				Definitions.drivetrain.arcadeDrive(0, 0);
			}
			else {
				int distmod = 0;
				if(obstacle == 4) distmod = -20;
				int speed = 0;
				if(obstacle == 2) speed = 50;
				else speed = 70;
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
	//				if(System.currentTimeMillis()-startdelaytime > 30000) {
	//					break;
	//				}
					
				}
				while(!DriveForward.driveForward(-140+distmod, speed, yaw));
	//			Definitions.intakesolenoid.set(Value.kReverse);
	//			Definitions.intakepancake.set(Value.kReverse);
				Definitions.drivetrain.arcadeDrive(-0.3, 0);
			}
		}
		
		if(position == 3) {
			if(obstacle == 6) {
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//					if(System.currentTimeMillis()-startdelaytime > 30000) {
//						break;
//					}
					
				}
				while(!DriveForward.driveForward(38, 50, yaw));
				Definitions.drivetrain.arcadeDrive(0.3, 0);
				
				Timer.delay(0.25);
				Definitions.intakesolenoid.set(Value.kForward);
				Definitions.intakepancake.set(Value.kForward);
				Timer.delay(1.5);
				Definitions.leftdriveenc.reset();
				Definitions.rightdriveenc.reset();
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//					if(System.currentTimeMillis()-startdelaytime > 30000) {
//						break;
//					}
					
				}
				while(!DriveForward.driveForward(100, 70, yaw));
				Timer.delay(0.25);
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.05);
				} while(!TurnControl.turn(yaw+40, 0.75));
				
				Definitions.drivetrain.arcadeDrive(0, 0);
			}
			else {
				int distmod = 0;
				if(obstacle == 4) distmod = -20;
				
				int speed = 0;
				if(obstacle == 2 || obstacle == 4) speed = 50;
				else speed = 70;
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
	//				if(System.currentTimeMillis()-startdelaytime > 30000) {
	//					break;
	//				}
					
				}
				while(!DriveForward.driveForward(-140+distmod, speed, yaw));
	//			Definitions.intakesolenoid.set(Value.kReverse);
	//			Definitions.intakepancake.set(Value.kReverse);
				Definitions.drivetrain.arcadeDrive(-0.3, 0);
			}
		}
		
		if(position == 4) {
			if(obstacle == 6) {
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//					if(System.currentTimeMillis()-startdelaytime > 30000) {
//						break;
//					}
					
				}
				while(!DriveForward.driveForward(38, 50, yaw));
				Definitions.drivetrain.arcadeDrive(0.3, 0);
				
				Timer.delay(0.25);
				Definitions.intakesolenoid.set(Value.kForward);
				Definitions.intakepancake.set(Value.kForward);
				Timer.delay(1.5);
				Definitions.leftdriveenc.reset();
				Definitions.rightdriveenc.reset();
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
//					if(System.currentTimeMillis()-startdelaytime > 30000) {
//						break;
//					}
					
				}
				while(!DriveForward.driveForward(100, 70, yaw));
				Timer.delay(0.25);
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.05);
				} while(!TurnControl.turn(yaw-30, 0.75));
				
				Definitions.drivetrain.arcadeDrive(0, 0);
				Timer.delay(0.25);
				Definitions.leftdriveenc.reset();
				Definitions.rightdriveenc.reset();
				Definitions.turnpid.setEnabled(false, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
	//				if(System.currentTimeMillis()-startdelaytime > 30000) {
	//					break;
	//				}
	//				if(Definitions.leftdriveenc.getDistance()*DriveForward.encoderratio > 120 || Definitions.rightdriveenc.getDistance()*DriveForward.encoderratio > 120) yaw -= 30;
					
				}
				while(!DriveForward.driveForward(-50, 50, yaw-35));
				Timer.delay(0.5);
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.05);
				} while(!TurnControl.turn(yaw-40, 0.75));
				
				Definitions.drivetrain.arcadeDrive(0, 0);
			}
			else {
				int distmod = 0;
				if(obstacle == 4) distmod = -20;
				
				int speed = 0;
				if(obstacle == 2 || obstacle == 4) speed = 50;
				else speed = 70;
				
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
	//				if(System.currentTimeMillis()-startdelaytime > 30000) {
	//					break;
	//				}
	//				if(Definitions.leftdriveenc.getDistance()*DriveForward.encoderratio > 120 || Definitions.rightdriveenc.getDistance()*DriveForward.encoderratio > 120) yaw -= 30;
					
				}
				while(!DriveForward.driveForward(-140, speed, yaw));
	//			Definitions.intakesolenoid.set(Value.kReverse);
	//			Definitions.intakepancake.set(Value.kReverse);
				Definitions.drivetrain.arcadeDrive(-0.3, 0);
				
				Timer.delay(0.5);
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.05);
				} while(!TurnControl.turn(yaw+10, 0.75));
				
				Definitions.drivetrain.arcadeDrive(0, 0);
				Timer.delay(0.25);
				Definitions.leftdriveenc.reset();
				Definitions.rightdriveenc.reset();
				Definitions.turnpid.setEnabled(false, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.01);
					//System.out.println(p + " " + i + " " + d);
					System.out.println(Definitions.leftdriveenc.getRate()*DriveForward.encoderratio + " " + Definitions.rightdriveenc.getRate()*DriveForward.encoderratio + " " + ArduinoComm.getYaw() + " " +(yaw-ArduinoComm.getYaw()));
	//				if(System.currentTimeMillis()-startdelaytime > 30000) {
	//					break;
	//				}
	//				if(Definitions.leftdriveenc.getDistance()*DriveForward.encoderratio > 120 || Definitions.rightdriveenc.getDistance()*DriveForward.encoderratio > 120) yaw -= 30;
					
				}
				while(!DriveForward.driveForward(-50, 50, yaw+15));
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				Definitions.drivetrain.arcadeDrive(-0.3, 0);
				
				Timer.delay(0.5);
				Definitions.turnpid.setEnabled(true, ArduinoComm.getYaw());
				do {
					ArduinoComm.communicate();
					Timer.delay(0.05);
				} while(!TurnControl.turn(yaw, 0.75));
				
				Definitions.drivetrain.arcadeDrive(0, 0);
			}
		}
	}
	
	public static void shootingRoutine() {
		Timer.delay(0.25);
		
		int pixeltarget = 95;
		
		int inrangecount = 0;
		VisionTurn.turnVision(pixeltarget, true);
		do {
			double[] aimvals = VisionTurn.turnVision(pixeltarget, false);
			Definitions.drivetrain.arcadeDrive(0, aimvals[1]);
			if(Math.abs(aimvals[0]-pixeltarget) < 10) inrangecount++;
			else inrangecount = 0;
			Timer.delay(0.01);
		} while(inrangecount < 10);
		
		double targetdistance = 140;
		double distance = 0;
		Definitions.lidardrivepid.setEnabled(true, 0);
		Definitions.intakesolenoid.set(Value.kForward);
		Definitions.intakepancake.set(Value.kForward);
		do {
			Definitions.lidar.update();
			distance =	Definitions.lidar.getDistance();
			System.out.println(distance);
			Timer.delay(0.01);
		} while(LidarDrive.lidarDrive(targetdistance, VisionTurn.turnVision(pixeltarget, false)[1]) > targetdistance);
		Timer.delay(0.5);
		Definitions.driveaimpid.setTuning(Definitions.DRIVEAIM_P, 0.03, Definitions.DRIVEAIM_D);
		
		Definitions.ballholder.set(true);
		do {
			double[] aimvals = VisionTurn.turnVision(pixeltarget, false);
			Definitions.drivetrain.arcadeDrive(0, aimvals[1]);
			if(Math.abs(aimvals[0]-pixeltarget) < 3) inrangecount++;
			else inrangecount = 0;
			Timer.delay(0.01);
		} while(inrangecount < 50);
		
		Timer.delay(0.25);
		
		Definitions.catapultone.set(Value.kReverse);
		Definitions.catapulttwo.set(Value.kForward);
	}
	private static ArrayList<Double> decimatevals = new ArrayList<Double>();

} 