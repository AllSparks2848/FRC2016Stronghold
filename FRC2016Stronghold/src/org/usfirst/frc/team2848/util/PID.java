package org.usfirst.frc.team2848.util;

public class PID {
	
	private double target;
	
	private double pterm;
	private double iterm;
	private double dterm;
	
	private long nanotime;
	private long lastnanotime;
	
	private double i = 0;
	private double min = -100;
	private double max = 100;
	private double mini = -100;
	private double maxi = 100;
	private double lastinput = 0;
	
	private boolean enabled = false;
	
	public PID(double pterm, double iterm, double dterm, double target, double startingval) {
		this.pterm = pterm; //initializes parameters
		this.iterm = iterm;
		this.dterm = dterm;
		this.target = target;
		
		nanotime = System.nanoTime(); // initializes time
		lastnanotime = System.nanoTime();
		initialize(startingval);
		 // gives a starting value to prevent big starting jump
	}
	
	private void initialize(double startingval) {
		lastinput = startingval;
	}
	
	public void setTarget(double target) { // sets target value
		this.target = target;
	}
	
	public double compute(double input, double[] values) {
		if(!enabled) return 0;
		double error = target-input; //calculate error of the system	
		double p = pterm*error; //get proportional term of error
		
		nanotime = System.nanoTime();
		double deltatime = (nanotime-lastnanotime)/1000000000.0; //calculate delta time in nano seconds and divide by a billion to get seconds
		lastnanotime = nanotime;
		
		double d = dterm*(input-lastinput)/deltatime; //calculate derivative of error, preventing derivative kick by only looking at input
		lastinput = input;
		
		i += iterm*error*deltatime; // calculates integral of error
		if(i < mini) i = mini; //clamps to prevent integral windup
		if(i > maxi) i = maxi;
		
		double output = p+i+d;
		if(output < min) output = min; // clamps output
		if(output > max) output = max;
		
		if(values != null) {
			values[0] = p;
			values[1] = i;
			values[2] = d;
			values[3] = error;
			values[4] = output;
		}
		
		return output;
	}
	
	public void setTuning(double pterm, double iterm, double dterm) { // sets p, i, and d terms
		this.pterm = pterm;
		this.iterm = iterm;
		this.dterm = dterm;
	}
	
	public void setBounds(double min, double max) { // sets bounds for overall output
		this.min = min;
		this.max = max;
	}
	
	public void setITermBounds(double mini, double maxi) { // sets bounds for integral windup clamp
		this.mini = mini;
		this.maxi = maxi;
	}
	
	public void setEnabled(boolean enabled, double startingval) {
		if(enabled && !this.enabled) {
			initialize(startingval);
		}
		
		this.enabled = enabled;	
	}
	
	public boolean getEnabled() {
		return enabled;
	}

}