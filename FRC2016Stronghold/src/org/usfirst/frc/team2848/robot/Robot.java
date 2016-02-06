
package org.usfirst.frc.team2848.robot;

import edu.wpi.first.wpilibj.Compressor;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
//import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Robot extends IterativeRobot {
    public static Joystick xbox1;
    public static Talon bottomLeft;
    public static Talon topRight;
    public static Solenoid shifter1;
    public static Compressor compressor1;
    public static PowerDistributionPanel pdp;
    /*
    public static PID pidleft;
    public static PID pidright;
    double outputbottom;
    double outputtop;
    double gearratio; 
    public static double targetbottom;
    public static double targettop;
    public static Encoder encoderleft;
    public static Encoder encoderright;
    */
	RobotDrive drivetrain;
    public void robotInit() {
    	bottomLeft = new Talon(0);
    	topRight = new Talon(1);
    	drivetrain = new RobotDrive(0,1);
    	shifter1 = new Solenoid(0,1);
    	compressor1 = new Compressor();
    	pdp = new PowerDistributionPanel();
    	//gearratio = (2/1); //get real gear ratio from mechanical subteam when they knows it
    	/*
    	  targetbottom = 0;
          targettop = 0;
          encoderleft = new Encoder(0, 1, false, EncodingType.k4X);
          encoderright = new Encoder(2, 3, true, EncodingType.k4X);
         // encoderleft.setReverseDirection(true); if one is negative reverse it using this
          encoderleft.setDistancePerPulse((1/1024.0)*gearratio*60);
          encoderright.setDistancePerPulse((1/1024.0)*gearratio*60);
          
          pidleft = new PID(0, 0, 0, 0, encoderleft.getRate());
          pidright = new PID(0, 0, 0, 0, encoderright.getRate());
          
          pidleft.setBounds(-1, 1);
          pidright.setBounds(-1, 1);
          pidleft.setITermBounds(-1, 1);
          pidright.setITermBounds(-1, 1);
          */
    }

  
    public void autonomousPeriodic() {

    }

 
    public void teleopPeriodic() {
    	compressor1.setClosedLoopControl(true);
    	SparkyPnuematics.pneumaticToggle();
        SparkyDriveHelper.arcadeDrive(xbox1, drivetrain);
        System.out.println(pdp.getCurrent(12) + " "+ pdp.getCurrent(13) + " "+ pdp.getCurrent(14) + " " + pdp.getCurrent(15));
     
    }
    
    
    public void testPeriodic() {
    
    }
    
}
