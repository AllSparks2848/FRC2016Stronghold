package org.usfirst.frc.team2848.robot.navigation;


import java.net.MalformedURLException;
import java.nio.ByteBuffer;

import javax.swing.SwingUtilities;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import edu.wpi.first.wpilibj.DriverStation;

public class FrameGrabber extends Thread {
	public Mat recentimage;
	VideoCapture video;
	String path = "rtsp://10.28.48.100/user=admin&password>&channel=1&stream=1.sdp?real_stream--rtp-caching=1?tcp";
	public FrameGrabber() {
		recentimage = new Mat();
	}
	
	public void run() {
		video = new VideoCapture();
		boolean opened = false;
		while(!opened) {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		opened = video.open(path);
		}
		System.out.println("Capturing");
		while(true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(video.isOpened()) {
			long time = 0;
			do {
			long prevtime = System.currentTimeMillis();
			video.read(recentimage);
			time = System.currentTimeMillis()-prevtime;
			//System.out.println(time);
			} while(time < 10);
			}
			else {
				video.release();
				video.open(path);
			}
			
		}
	    
	    // Quartz is abysmally slow at scaling video for some reason, so turn it off.
	    //System.setProperty("apple.awt.graphics.UseQuartz", "false");

}

	
	public synchronized void getImage(Mat image) {
		//System.out.println("copying");
		recentimage.copyTo(image);
		//Highgui.imwrite("image.png", recentimage);
		}
}
