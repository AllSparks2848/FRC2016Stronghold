package org.usfirst.frc.team2848.util;

import java.nio.ByteBuffer;

import org.usfirst.frc.team2848.robot.Definitions;

public class ArduinoComm {
	static float pitch = 0;
	static float yaw = 0;
	static float roll = 0;
	static float x = 0;
	static float y = 0;
	static float z = 0;
	public static void communicate() {
		byte[] recievedbytes = new byte[24];
		Definitions.arduino.transaction(new byte[1], 1, recievedbytes, 24);
		ByteBuffer recievedbuffer = ByteBuffer.wrap(recievedbytes);
		z = recievedbuffer.getFloat();
		y = recievedbuffer.getFloat();
		x = recievedbuffer.getFloat();
		pitch = recievedbuffer.getFloat();
		yaw = recievedbuffer.getFloat();
		roll = recievedbuffer.getFloat();
		//System.out.println(pitch + " " + yaw + " " + roll + " " + x + " " + y + " " + z);
	}
	
	public static float getPitch() { return pitch; }
	public static float getYaw() { return yaw; }
	public static float getRoll() { return roll; }
	public static float getXAccel() { return x; }
	public static float getYAccel() { return y; }
	public static float getZAccel() { return z; }
}
