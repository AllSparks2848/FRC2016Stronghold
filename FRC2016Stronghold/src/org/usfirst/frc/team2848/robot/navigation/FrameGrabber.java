package org.usfirst.frc.team2848.robot.navigation;


import java.net.MalformedURLException;
import java.nio.ByteBuffer;

import javax.swing.SwingUtilities;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class FrameGrabber extends Thread {
	public Mat recentimage;
	VideoCapture video;
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
		opened = video.open("rtsp://10.28.49.40/user=admin&password>&channel=1&stream=1.sdp?real_stream--rtp-caching=1?tcp");
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
				video.open("rtsp://10.28.49.40/user=admin&password>&channel=1&stream=1.sdp?real_stream--rtp-caching=1?tcp");
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
