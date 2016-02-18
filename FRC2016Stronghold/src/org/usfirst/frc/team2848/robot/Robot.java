
package org.usfirst.frc.team2848.robot;

import org.usfirst.frc.team2848.robot.subsystems.autoShifter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Robot extends IterativeRobot {
	public static PowerDistributionPanel pdp;
    public void robotInit() {  	
    	Definitions.initPeripherals();
    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
    	Definitions.compressor.setClosedLoopControl(true);
        Definitions.drivetrain.arcadeDrive(Definitions.xbox1.getRawAxis(1), Definitions.xbox1.getRawAxis(4));
        autoShifter.shift();

        
    }
    
    public void testPeriodic() {
    	//hello
    }
    
}
