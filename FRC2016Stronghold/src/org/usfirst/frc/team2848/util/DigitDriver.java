package org.usfirst.frc.team2848.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;

public class DigitDriver extends Thread
{
//I2C address of the digit board is 0x70
	I2C i2c = new I2C(Port.kMXP, 0x70);
	AtomicInteger positionout;
	AtomicInteger defenseout;
	AtomicBoolean doingauto;
	AtomicBoolean shooting;
	
// Buttons A and B are keyed to dgital inputs 19 and 20	
	DigitalInput buttonA = new DigitalInput(19);
	DigitalInput buttonB = new DigitalInput(20);
	
// The potentiometer is keyed to AI 3	
	AnalogInput pot = new AnalogInput(7);
	
	boolean doingAutonomous=false;
	boolean isShooting=false;
	boolean selectingAuton=false;
	boolean selectingShooting=false;
	boolean selectingPosition=false;
	boolean lastButtonStateTop=false;
	boolean lastButtonStateBottom=false;
	boolean selectingDefence=false;
	boolean display=false;
	int position=0;
	String defence="";
	
	boolean lastastate = false;
		
    public boolean readTopButton(){
    	
    	boolean buttonState= false;
    	buttonState = !buttonA.get() && !lastastate;
    	lastastate = !buttonA.get();
    	return buttonState;
    }
    
    boolean lastbstate = false;
    
    public boolean readBottomButton(){
    	
    	boolean buttonState= false;
    	buttonState = !buttonB.get() && !lastbstate;
    	lastbstate = !buttonB.get();
    	return buttonState;
    }
	

    public void run() 
    {
    	positionout = new AtomicInteger(0);
    	defenseout = new AtomicInteger(0);
    	doingauto = new AtomicBoolean(false);
    	shooting = new AtomicBoolean(false);
 // set up the board - turn on, set blinking and brightness   
    	byte[] osc = new byte[1];
    	byte[] blink = new byte[1];
    	byte[] bright = new byte[1];
    	osc[0] = (byte)0x21;
    	blink[0] = (byte)0x81;
    	bright[0] = (byte)0xEF;

	i2c.writeBulk(osc);
	Timer.delay(.01);
	i2c.writeBulk(bright);
	Timer.delay(.01);
	i2c.writeBulk(blink);
	Timer.delay(.01);
	selectingAuton=true;
	
	
	
	while(true) {
		loop();
	}


    }
 
    public void loop()
    {
    lastButtonStateTop=readBottomButton();
    lastButtonStateBottom=readBottomButton();
   // this is the array of all characters - this is not the most efficient way to store the data - but it works for now
    	byte[] byte1 = new byte[10];
    	byte[][] charreg = new byte[36][2]; //charreg is short for character registry
    	charreg[0][0] = (byte)0b00000110; charreg[0][1] = (byte)0b00000000; //1
    	charreg[1][0] = (byte)0b11011011; charreg[1][1] = (byte)0b00000000; //2
    	charreg[2][0] = (byte)0b11001111; charreg[2][1] = (byte)0b00000000; //3
    	charreg[3][0] = (byte)0b11100110; charreg[3][1] = (byte)0b00000000; //4
    	charreg[4][0] = (byte)0b11101101; charreg[4][1] = (byte)0b00000000; //5
    	charreg[5][0] = (byte)0b11111101; charreg[5][1] = (byte)0b00000000; //6
    	charreg[6][0] = (byte)0b00000111; charreg[6][1] = (byte)0b00000000; //7
    	charreg[7][0] = (byte)0b11111111; charreg[7][1] = (byte)0b00000000; //8
    	charreg[8][0] = (byte)0b11101111; charreg[8][1] = (byte)0b00000000; //9
    	charreg[9][0] = (byte)0b00111111; charreg[9][1] = (byte)0b00000000; //0
    	charreg[10][0] = (byte)0b11110111; charreg[10][1] = (byte)0b00000000; //A
    	charreg[11][0] = (byte)0b10001111; charreg[11][1] = (byte)0b00010010; //B
    	charreg[12][0] = (byte)0b00111001; charreg[12][1] = (byte)0b00000000; //C
    	charreg[13][0] = (byte)0b00001111; charreg[13][1] = (byte)0b00010010; //D
    	charreg[14][0] = (byte)0b11111001; charreg[14][1] = (byte)0b00000000; //E
    	charreg[15][0] = (byte)0b11110001; charreg[15][1] = (byte)0b00000000; //F
    	charreg[16][0] = (byte)0b10111101; charreg[16][1] = (byte)0b00000000; //G
    	charreg[17][0] = (byte)0b11110110; charreg[17][1] = (byte)0b00000000; //H
    	charreg[18][0] = (byte)0b00001001; charreg[18][1] = (byte)0b00010010; //I
    	charreg[19][0] = (byte)0b00011110; charreg[19][1] = (byte)0b00000000; //J
    	charreg[20][0] = (byte)0b01110000; charreg[20][1] = (byte)0b00001100; //K
    	charreg[21][0] = (byte)0b00111000; charreg[21][1] = (byte)0b00000000; //L
    	charreg[22][0] = (byte)0b00110110; charreg[22][1] = (byte)0b00000101; //M
    	charreg[23][0] = (byte)0b00110110; charreg[23][1] = (byte)0b00001001; //N
    	charreg[24][0] = (byte)0b00111111; charreg[24][1] = (byte)0b00000000; //O
    	charreg[25][0] = (byte)0b11110011; charreg[25][1] = (byte)0b00000000; //P
    	charreg[26][0] = (byte)0b00111111; charreg[26][1] = (byte)0b00001000; //Q
    	charreg[27][0] = (byte)0b11110011; charreg[27][1] = (byte)0b00001000; //R
    	charreg[28][0] = (byte)0b10001101; charreg[28][1] = (byte)0b00000001; //S
    	charreg[29][0] = (byte)0b00000001; charreg[29][1] = (byte)0b00010010; //T
    	charreg[30][0] = (byte)0b00111110; charreg[30][1] = (byte)0b00000000; //U
    	charreg[31][0] = (byte)0b00110000; charreg[31][1] = (byte)0b00100100; //V
    	charreg[32][0] = (byte)0b00110110; charreg[32][1] = (byte)0b00101000; //W
    	charreg[33][0] = (byte)0b00000000; charreg[33][1] = (byte)0b00101101; //X
    	charreg[34][0] = (byte)0b00000000; charreg[34][1] = (byte)0b00010101; //Y
    	charreg[35][0] = (byte)0b00001001; charreg[35][1] = (byte)0b00100100; //Z
    	
// store the desired characters in a byte array, then write array to the board

// first reset the array to zeros
    	for(int c = 0; c < 10; c++)
    	{
    		byte1[c] = (byte)(0b00000000) & 0xFF;
    	}
    	
    	if(selectingAuton){
    		if(doingAutonomous) {
    		byte1[0] = (byte)(0b0000111100001111);
    		byte1[2] = charreg[24][0];
    		byte1[3] = charreg[24][1];
    		byte1[4] = charreg[29][0];
    		byte1[5] = charreg[29][1];
    		byte1[6] = charreg[30][0];
    		byte1[7] = charreg[30][1];
    		byte1[8] = charreg[10][0];
    		byte1[9] = charreg[10][1];
    		}
    		else {
    		byte1[0] = (byte)(0b0000111100001111);
    		byte1[2] = charreg[29][0];
    		byte1[3] = charreg[29][1];
    		byte1[4] = charreg[30][0];
    		byte1[5] = charreg[30][1];
    		byte1[6] = charreg[10][0];
    		byte1[7] = charreg[10][1];
    		byte1[8] = charreg[23][0];
    		byte1[9] = charreg[23][1];
    		}
// send the array to the board
    		i2c.writeBulk(byte1);
    		
    		if(readTopButton()){
    			doingAutonomous=true;
    			selectingShooting=true;
    			selectingAuton=false;
    		}
    		else if(readBottomButton()){
    			doingAutonomous=false;
    			//selectingAuton=false;
    		}
    		
    		
    	}
    	
    	
    	if(selectingShooting){
    		if(isShooting) {
    		byte1[0] = (byte)(0b0000111100001111);
    		byte1[2] = charreg[29][0];
    		byte1[3] = charreg[29][1];
    		byte1[4] = charreg[24][0];
    		byte1[5] = charreg[24][1];
    		byte1[6] = charreg[17][0];
    		byte1[7] = charreg[17][1];
    		byte1[8] = charreg[28][0];
    		byte1[9] = charreg[28][1];
    		}
    		else {
        		byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[29][0];
        		byte1[3] = charreg[29][1];
        		byte1[4] = charreg[17][0];
        		byte1[5] = charreg[17][1];
        		byte1[6] = charreg[28][0];
        		byte1[7] = charreg[28][1];
        		byte1[8] = charreg[23][0];
        		byte1[9] = charreg[23][1];
    		}
// send the array to the board
    		i2c.writeBulk(byte1);
    		
    		if(readTopButton()){
    			isShooting=true;
    			selectingPosition=true;
    			selectingShooting=false;
    			
    		}
    		else if(readBottomButton()){
    			isShooting=false;
    			selectingPosition=true;
    			selectingShooting=false;
    		}
    	}
    	
    	
    	if(selectingPosition&&doingAutonomous){
    		
    		if(pot.getVoltage()<5.0&&pot.getVoltage()>5.0/6*5){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[9][0];
        		byte1[5] = charreg[9][1];
        		byte1[6] = charreg[28][0];
        		byte1[7] = charreg[28][1];
        		byte1[8] = charreg[25][0];
        		byte1[9] = charreg[25][1];
        		
    // send the array to the board
        		i2c.writeBulk(byte1);
        		Timer.delay(0.01);
        		position=0;
        		selectingDefence=true;
        		
    		}
    		if(pot.getVoltage()<5.0/6*5&&pot.getVoltage()>5.0/6*4){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[9][0];
        		byte1[5] = charreg[9][1];
        		byte1[6] = charreg[28][0];
        		byte1[7] = charreg[28][1];
        		byte1[8] = charreg[25][0];
        		byte1[9] = charreg[25][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		Timer.delay(0.01);
        		position=1;
        		selectingDefence=true;
        		
    		}
    		if(pot.getVoltage()<5.0/6*4&&pot.getVoltage()>5.0/6*3){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[2][0];
        		byte1[3] = charreg[2][1];
        		byte1[4] = charreg[9][0];
        		byte1[5] = charreg[9][1];
        		byte1[6] = charreg[28][0];
        		byte1[7] = charreg[28][1];
        		byte1[8] = charreg[25][0];
        		byte1[9] = charreg[25][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		Timer.delay(0.01);
        		position=2;
        		selectingDefence=true;
        		
    		}
    		if(pot.getVoltage()<5.0/6*3&&pot.getVoltage()>5.0/6*2){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[3][0];
        		byte1[3] = charreg[3][1];
        		byte1[4] = charreg[9][0];
        		byte1[5] = charreg[9][1];
        		byte1[6] = charreg[28][0];
        		byte1[7] = charreg[28][1];	
        		byte1[8] = charreg[25][0];
        		byte1[9] = charreg[25][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		Timer.delay(0.01);
        		position=3;
        		selectingDefence=true;
        		
    		}
    		if(pot.getVoltage()<5.0/6*2&&pot.getVoltage()>5.0/6*1){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[4][0];
        		byte1[3] = charreg[4][1];
        		byte1[4] = charreg[9][0];
        		byte1[5] = charreg[9][1];
        		byte1[6] = charreg[28][0];
        		byte1[7] = charreg[28][1];
        		byte1[8] = charreg[25][0];
        		byte1[9] = charreg[25][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		Timer.delay(0.01);
        		position=4;
        		selectingDefence=true;
        		
        		
    		}
    		if(pot.getVoltage()<5.0/6*1){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[5][0];
        		byte1[3] = charreg[5][1];
        		byte1[4] = charreg[9][0];
        		byte1[5] = charreg[9][1];
        		byte1[6] = charreg[28][0];
        		byte1[7] = charreg[28][1];
        		byte1[8] = charreg[25][0];
        		byte1[9] = charreg[25][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		Timer.delay(0.01);
        		position=5;
        		defence="spy";
        		display=true;
        		
        		
    		}
    		if(readTopButton()){
    			selectingPosition=false;
    		}
    		
    	}
    	
    	if(!selectingPosition&&selectingDefence&&doingAutonomous&&position!=6){
    		if(pot.getVoltage()<5.0&&pot.getVoltage()>5.0/9*8){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[10][0];
        		byte1[5] = charreg[10][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		defence="portcullis";
        		display=true;
        		
    		}
    		if(pot.getVoltage()<5.0/9*8&&pot.getVoltage()>5.0/9*7){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[10][0];
        		byte1[5] = charreg[10][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		defence="cdf";
        		display=true;
        		
    		}
    		if(pot.getVoltage()<5.0/9*7&&pot.getVoltage()>5.0/9*6){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[11][0];
        		byte1[5] = charreg[11][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		defence="moat";
        		display=true;
        		
    		}
    		if(pot.getVoltage()<5.0/9*6&&pot.getVoltage()>5.0/9*5){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[11][0];
        		byte1[5] = charreg[11][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		defence="ramparts";
        		display=true;
        		
    		}
    		if(pot.getVoltage()<5.0/9*5&&pot.getVoltage()>5.0/9*4){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[12][0];
        		byte1[5] = charreg[12][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		defence="drawbridge";
        		display=true;
        		
        		
    		}
    		if(pot.getVoltage()<5.0/9*4&&pot.getVoltage()>5.0/9*3){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[12][0];
        		byte1[5] = charreg[12][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		defence="sallyport";
        		display=true;
        		
        		
    		}
    		if(pot.getVoltage()<5.0/9*3&&pot.getVoltage()>5.0/9*2){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[13][0];
        		byte1[5] = charreg[13][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		
        		defence="rockwall";
        		display=true;
    		}
    		if(pot.getVoltage()<5.0/9*2&&pot.getVoltage()>5.0/9*1){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[13][0];
        		byte1[5] = charreg[13][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		
        		defence="roughterrain";
        		display=true;
        		
    		}
    		if(pot.getVoltage()<5.0/9*1){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[11][0];
        		byte1[3] = charreg[11][1];
        		byte1[4] = charreg[21][0];
        		byte1[5] = charreg[21][1];
        		byte1[6] = charreg[15][0];
        		byte1[7] = charreg[15][1];
        		byte1[8] = charreg[13][0];
        		byte1[9] = charreg[13][1];
    // send the array to the board
        		i2c.writeBulk(byte1);
        		
        		
        		defence="lowbar";
        		display=true;
    		}
    		if(readTopButton()){
    			selectingDefence=false;
    		}
    		
    	}
    	
    	if(!selectingAuton&&!selectingShooting&&!selectingPosition&&!selectingDefence&&display){
    		if(position==0){
    			byte1[6] = charreg[0][0];
        		byte1[7] = charreg[0][1];
        		byte1[8] = charreg[9][0];
        		byte1[9] = charreg[9][1];
    		}
    		if(position==1){
    			byte1[6] = charreg[1][0];
        		byte1[7] = charreg[1][1];
        		byte1[8] = charreg[9][0];
        		byte1[9] = charreg[9][1];
    		}
    		if(position==2){
    			byte1[6] = charreg[2][0];
        		byte1[7] = charreg[2][1];
        		byte1[8] = charreg[9][0];
        		byte1[9] = charreg[9][1];
    		}
    		if(position==3){
    			byte1[6] = charreg[3][0];
        		byte1[7] = charreg[3][1];
        		byte1[8] = charreg[9][0];
        		byte1[9] = charreg[9][1];
    		}
    		if(position==4){
    			byte1[6] = charreg[4][0];
        		byte1[7] = charreg[4][1];
        		byte1[8] = charreg[9][0];
        		byte1[9] = charreg[9][1];
    		}
    		if(position==5){
    			byte1[6] = charreg[5][0];
        		byte1[7] = charreg[5][1];
        		byte1[8] = charreg[9][0];
        		byte1[9] = charreg[9][1];
    		}
    		if(defence.equals("portcullis")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[10][0];
        		byte1[5] = charreg[10][1];
    		}
    		if(defence.equals("cdf")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[10][0];
        		byte1[5] = charreg[10][1];
    		}
    		if(defence.equals("moat")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[11][0];
        		byte1[5] = charreg[11][1];
    		}
    		if(defence.equals("ramparts")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[11][0];
        		byte1[5] = charreg[11][1];
    		}
    		if(defence.equals("drawbridge")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[12][0];
        		byte1[5] = charreg[12][1];
    		}
    		if(defence.equals("sallyport")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[12][0];
        		byte1[5] = charreg[12][1];
    		}
    		if(defence.equals("rockwall")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[0][0];
        		byte1[3] = charreg[0][1];
        		byte1[4] = charreg[13][0];
        		byte1[5] = charreg[13][1];
    		}
    		if(defence.equals("roughterrain")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[1][0];
        		byte1[3] = charreg[1][1];
        		byte1[4] = charreg[13][0];
        		byte1[5] = charreg[13][1];
    		}
    		if(defence.equals("lowbar")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[11][0];
        		byte1[3] = charreg[11][1];
        		byte1[4] = charreg[21][0];
        		byte1[5] = charreg[21][1];
    		}
    		if(defence.equals("spy")){
    			byte1[0] = (byte)(0b0000111100001111);
        		byte1[2] = charreg[25][0];
        		byte1[3] = charreg[25][1];
        		byte1[4] = charreg[28][0];
        		byte1[5] = charreg[28][1];
    		}
    		i2c.writeBulk(byte1);
    		
    		
    		if(readTopButton()){
    			selectingAuton=true; 
    		}
    				
  	}
    	try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	doingauto.set(doingAutonomous);
    	shooting.set(isShooting);
    	positionout.set(position);
    	if(defence.equals("portcullis")) {
    		defenseout.set(5);
    	}
    	else if(defence.equals("cdf")) {
    		defenseout.set(6);
    	}
    	else if(defence.equals("moat")) {
    		defenseout.set(3);
    	}
    	else if(defence.equals("ramparts")) {
    		defenseout.set(4);
    	}
    	else if(defence.equals("drawbridge")) {
    		defenseout.set(8);
    	}
    	else if(defence.equals("sallyport")) {
    		defenseout.set(7);
    	}
    	else if(defence.equals("rockwall")) {
    		defenseout.set(2);
    	}
    	else if(defence.equals("lowbar")) {
    		defenseout.set(0);
    	}
    	else if(defence.equals("roughterrain")) {
    		defenseout.set(1);
    	}
    	else if(defence.equals("spy")) {
    		defenseout.set(9);
    	}
    	
    }
    
    public synchronized void getDefense(AtomicInteger defense) {
    	defense.set(defenseout.get());
    }
    
    public synchronized void getPosition(AtomicInteger position) {
    	position.set(positionout.get());
    }
    
    public synchronized void getAuto(AtomicBoolean auto) {
    	auto.set(doingauto.get());
    }
    public synchronized void getShooting(AtomicBoolean shooting) {
    	shooting.set(this.shooting.get());
    }

}
