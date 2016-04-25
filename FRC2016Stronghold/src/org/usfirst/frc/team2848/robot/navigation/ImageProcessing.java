package org.usfirst.frc.team2848.robot.navigation;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.imgproc.Imgproc;

import com.ni.vision.NIVision;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.Timer;

public class ImageProcessing extends Thread {
//	FrameGrabber frame;
	AtomicBoolean newpoints = new AtomicBoolean(false);
	AtomicBoolean newboundingbox = new AtomicBoolean(false);
	public BlockingQueue<Boolean> queue = new LinkedBlockingQueue<Boolean>();
	public BlockingQueue<Mat> frames = new LinkedBlockingQueue<Mat>();
	VideoCapture video;
	private int m_id;
	private NIVision.Image i;
	NIVision.RawData imdata;
	public ImageProcessing() {
//		frame = new FrameGrabber();
//		frame.start();
	}
	
	MatOfPoint2f detectedpoints;
	MatOfPoint2f boundingbox;
	public void run() {
		
        m_id = NIVision.IMAQdxOpenCamera("cam2",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(m_id);
        NIVision.IMAQdxSetAttributeF64(m_id, "CameraAttributes::AcquisitionControl::AutoExposureTimeLowerLimit", 200);
        NIVision.IMAQdxSetAttributeF64(m_id, "CameraAttributes::AcquisitionControl::AutoExposureTimeUpperLimit", 210);
        NIVision.IMAQdxSetAttributeString(m_id, "CameraAttributes::AnalogControl::GainAuto", "Off");
//        NIVision.IMAQdxSetAttributeF64(m_id, "CameraAttributes::AnalogControl::AutoGainUpperLimit", 50);
        NIVision.IMAQdxSetAttributeF64(m_id, "CameraAttributes::AnalogControl::Gain", 23);
//        NIVision.IMAQdxSetAttributeBool(m_id, "CameraAttributes::AnalogControl::SaturationEnabled", 1);
//        NIVision.IMAQdxSetAttributeString(m_id, "CameraAttributes::AnalogControl::SaturationAuto", "Off");
//        NIVision.IMAQdxSetAttributeF64(m_id, "CameraAttributes::AnalogControl::Saturation", 100);
        NIVision.IMAQdxSetAttributeString(m_id, "CameraAttributes::AcquisitionControl::AcquisitionFrameRateAuto", "Off");
        NIVision.IMAQdxSetAttributeF64(m_id, "CameraAttributes::AcquisitionControl::AcquisitionFrameRate", 20);
        System.out.println("Capturing");
        i = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		while(true) {
		try { 
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Boolean processing = new Boolean(false);
		while(queue.peek() != null){processing = queue.poll();}
		if(processing) {
			compute();
			}
			else {
				//System.out.println("Not enabled");
			}
		System.gc();
		}
	}
	
	int number = 0;
	private void compute() {
		Mat image = new Mat(480, 640, CvType.CV_8UC4);
		
    	NIVision.IMAQdxGrab(m_id, i, 0);
    	imdata = NIVision.imaqFlatten(i, NIVision.FlattenType.FLATTEN_IMAGE, NIVision.CompressionType.COMPRESSION_NONE, 0);
    	imdata.getBuffer().clear();
    	imdata.getBuffer().position(172);
    	byte[] imbuffer = new byte[640*480*4];

    	imdata.getBuffer().get(imbuffer, 0, 640*480*4);
    	image.put(0, 0, imbuffer);
    	Imgproc.cvtColor(image, image, Imgproc.COLOR_BGRA2BGR);
    	Imgproc.resize(image, image, new Size(320,240), 0, 0, Imgproc.INTER_NEAREST);
    	Core.transpose(image, image);
    	Core.flip(image, image, 1);
    	image = image.submat(0, 260, 0, 240);
//    	Highgui.imwrite("/home/lvuser/test" + number +".png", image);
    	System.out.println("image wrote");
    	imdata.free();
		if(image.empty()) return;
		//Highgui.imwrite("/home/lvuser/imagefilter.png", image);
    	long time = System.currentTimeMillis();
    	Mat hsv = new Mat();
    	Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);
    	ArrayList<Mat> channels = new ArrayList<Mat>();
    	Core.split(hsv, channels);
    	hsv.release();
   		Mat huemin = new Mat();
   		Mat huemax = new Mat();
   		Mat hue = new Mat();
   		Mat valuemin = new Mat();
   		Mat valuemax = new Mat();
   		Mat value = new Mat();
   		Mat satmin = new Mat();
   		Mat satmax = new Mat();
   		Mat sat = new Mat();
   		Imgproc.threshold(channels.get(0), huemin, 50, 255, Imgproc.THRESH_BINARY);
   		Imgproc.threshold(channels.get(0), huemax, 100, 255, Imgproc.THRESH_BINARY_INV);
   		Core.min(huemin, huemax, hue);
   		huemin.release();
   		huemax.release();
    	Imgproc.threshold(channels.get(2), valuemin, 60, 255, Imgproc.THRESH_BINARY);
    	Imgproc.threshold(channels.get(2), valuemax, 240, 255, Imgproc.THRESH_BINARY_INV);
    	Core.min(valuemin, valuemax, value);
    	valuemin.release();
    	valuemax.release();
    	Imgproc.threshold(channels.get(1), satmin, 60, 255, Imgproc.THRESH_BINARY);
    	Imgproc.threshold(channels.get(1), satmax, 256, 255, Imgproc.THRESH_BINARY_INV);
    	Core.min(satmin, satmax, sat);
    	satmin.release();
    	satmax.release();
    	
   		Mat filtered = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
   		
    	Core.min(hue, value, filtered);
    	Core.min(sat, filtered, filtered);
    	channels.clear();
   		hue.release();
   		value.release();
   		sat.release();
   		//Highgui.imwrite("/home/lvuser/imagefilter.png", filtered);
   		filtered.convertTo(filtered, CvType.CV_8UC1);
   		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
   		Mat hierarchy = new Mat();
   		Imgproc.findContours(filtered, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
   		//System.out.println("New iteration");
   		if(contours.size() > 0) {
        	double maxarea = 0;
        	int maxindex = 0;
        	for(int i = 0; i < contours.size(); i++) {
        		double area = Imgproc.contourArea(contours.get(i));
        		if(area > maxarea) {
        			maxarea = area;
        			maxindex = i;
        		}
        	}
        		
        	Rect aabb = Imgproc.boundingRect(contours.get(maxindex));
        		
        	aabb.set(new double[]{aabb.x-1, aabb.y-1, aabb.width+2, aabb.height+2});
        	int minx = aabb.x;
        	int maxx = aabb.x+aabb.width;
        	int miny = aabb.y;
        	int maxy = aabb.y+aabb.height;
        		
        	Mat result = filtered.submat(aabb);
        	if(Core.countNonZero(result) > 0) {
        		boundingbox = new MatOfPoint2f(new Point(aabb.x,aabb.y), new Point(aabb.x,aabb.y+aabb.height), new Point(aabb.x + aabb.width,aabb.y + aabb.height), new Point(aabb.x + aabb.width,aabb.y));
        		newboundingbox.set(true);
        		Mat nonzeroes = new Mat();
            	Core.findNonZero(result, nonzeroes);
           		//System.out.println(nonzeroes.rows() + " " + nonzeroes.cols() + " " + nonzeroes.get(0,0).length);
	       		ArrayList<Point> left = new ArrayList<Point>();
	        	ArrayList<Point> right = new ArrayList<Point>();
	        	ArrayList<Point> south = new ArrayList<Point>();
	        		
	        	result.convertTo(result, CvType.CV_16UC1);
	        	Mat mask = Mat.zeros(result.rows(), result.cols(), CvType.CV_8UC1);
	        	for(int i = 0; i < nonzeroes.rows(); i++) {
	        		int y = (int) nonzeroes.get(i, 0)[1];
	        		int x = (int) nonzeroes.get(i, 0)[0];
	        		//System.out.println(x+ " " + y);
	        		result.put(y, x,1+ x + y);
	        		mask.put(y, x, 1);
	        		//System.out.println(mask.get(y,x)[0] + " " + x + " " + y);
	        	}
	        	
	        	for(int y = 0; y < result.rows(); y++) {
	        		Mat row = result.row(y);
	        		Mat rowmask = mask.row(y);
	        		MinMaxLocResult minmaxresult = Core.minMaxLoc(row, rowmask);
	        			
	        		if(minmaxresult.maxVal > 0)right.add(new Point(minmaxresult.maxLoc.x + minx, y + miny));
	        		if(minmaxresult.minVal > 0)left.add(new Point(minmaxresult.minLoc.x+ minx, y+ miny));
	        		//System.out.println((minmaxresult.minLoc.x) + " " + (y) + " " + minmaxresult.minVal);
	        			
	        	}
	        		
	        	for(int x = 0; x < result.cols(); x++) {
	        		Mat col = result.col(x);
	        		Mat colmask = mask.col(x);
	        		MinMaxLocResult minmaxresult = Core.minMaxLoc(col, colmask);
	        		if(minmaxresult.maxVal > 0)south.add(new Point(x+ minx, minmaxresult.maxLoc.y + miny));
	        			
	        	}
	        		//System.out.println(left.size() + " " + right.size() + "" + south.size());
	        	SimpleRegression lregression = new SimpleRegression();
	        	SimpleRegression rregression = new SimpleRegression();
	        	SimpleRegression sregression = new SimpleRegression();
	        	//Imgproc.cvtColor(result, result, Imgproc.COLOR_GRAY2BGR);
	        	//Highgui.imwrite("/home/lvuser/image1.png", result);
	        	
	        	Core.line(image, new Point(minx, miny), new Point(minx, maxy), new Scalar(0,0,255));
	        	Core.line(image, new Point(minx, maxy), new Point(maxx, maxy), new Scalar(0,0,255));
	        	Core.line(image, new Point(maxx, maxy), new Point(maxx, miny), new Scalar(0,0,255));
	        	Core.line(image, new Point(maxx, miny), new Point(minx, miny), new Scalar(0,0,255));
	        	
	        	double maxright = 0;
	        	double minyright = image.rows();
	        	double minleft = image.cols();
	        	double minyleft = image.rows();
	        	double maxsouth = 0;
	        	for(int i = 0; i < right.size(); i++) {
	        		if(right.get(i).x > maxright) maxright = right.get(i).x;
	        	}
	        	for(int i = 0; i < right.size(); i++) {
	        		if(maxright-right.get(i).x < 7) {
	            		//Core.line(image, right.get(i), right.get(i), new Scalar(0, 255, 0));
	            		rregression.addData(right.get(i).y, right.get(i).x);
	            		if(right.get(i).y < minyright) minyright = right.get(i).y;
	        		}
	        	}
	        	
	        	for(int i = 0; i < left.size(); i++) {
	        		if(left.get(i).y < minleft) minleft = left.get(i).x;
	        	}
	        	for(int i = 0; i < left.size(); i++) {
	        		if(Math.abs(left.get(i).x-minleft) < 7) {
	            		//Core.line(image, left.get(i), left.get(i), new Scalar(255, 255, 0));
	            		lregression.addData(left.get(i).y, left.get(i).x);
	            		if(left.get(i).y < minyleft) minyleft = left.get(i).y;
	        		}
	        	}
	        	
	        	for(int i = 0; i < south.size(); i++) {
	        		if(south.get(i).y > maxsouth) maxsouth = south.get(i).y;
	        	}
	        	for(int i = 0; i < south.size(); i++) {
	        		if(maxsouth-south.get(i).y < 10) {
	        			//Core.line(image, south.get(i), south.get(i), new Scalar(0, 255, 255));
	        			sregression.addData(south.get(i).x, south.get(i).y);
	        		}
	        	}
//	        	Highgui.imwrite("/home/lvuser/image.png", image);
	        	if(lregression.getN() <= 2 || rregression.getN() <= 2 || sregression.getN() <= 2) {
		        	System.out.println("End Test");
	        		return;
	        	}
	        	
	        	lregression.regress();
	        	rregression.regress();
	        	sregression.regress();
	        	
	        	double m = sregression.getSlope();
	        	double n = lregression.getSlope();
	        	double o = rregression.getSlope();
	        	double a = sregression.getIntercept();
	        	double b = lregression.getIntercept();
	        	double c = rregression.getIntercept();
	        	
	        	lregression.clear();
	        	sregression.clear();
	        	rregression.clear();
	        		
	        	double leftminy = (m*b + a)/(1-m*n);
	        	double leftminx = n*leftminy + b;
	        	Point leftmin = new Point(leftminx, leftminy);
	        		
	        	double rightminy = (m*c + a)/(1-m*o);
	        	double rightminx = o*rightminy + c;
	        	Point rightmin = new Point(rightminx, rightminy);
	        		
	        	Point leftmax = new Point(n*minyleft + b, minyleft);
	        	Point rightmax = new Point(o*minyright + c, minyright);
	        		
	        	Core.line(image, leftmax, leftmin, new Scalar(255,255,0), 1);
	        	Core.line(image, leftmin, rightmin, new Scalar(255,255,0), 1);
	        	Core.line(image, rightmax, rightmin, new Scalar(255,255,0), 1);
		   		
	        	detectedpoints = new MatOfPoint2f(leftmax, leftmin, rightmin, rightmax);
	        	newpoints.set(true);
	        	Highgui.imwrite("/home/lvuser/image.png", image);
	        	nonzeroes.release();
	
        	}
        	result.release();
        	//System.out.println(System.currentTimeMillis()-time);
        	
        	//System.out.println("Image made");
    		
			}
	    	filtered.release();
	    	hierarchy.release();
	   		image.release();  
	   		number++;
	}
	public synchronized void getPoints(MatOfPoint2f points, AtomicBoolean pointschanged, MatOfPoint2f bounding, AtomicBoolean boundingboxchanged) {
		if(detectedpoints != null) {
			detectedpoints.copyTo(points);
			pointschanged.set(newpoints.get());
			newpoints.set(false);
		}
		if(boundingbox != null) {
			boundingbox.copyTo(bounding);
			boundingboxchanged.set(newboundingbox.get());
			newboundingbox.set(false);
		}
	}
}
