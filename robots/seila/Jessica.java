package seila;
import robocode.*;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Jessica - a robot by (your name here)
 */
public class Jessica extends Robot
{
	/**
	 * run: Jessica's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
		  
			ahead(270);
			turnLeft(100);
			ahead(100);
			turnRight(100);
			ahead(270);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		while(true){
		fire(3);
		fire(2);
		turnGunLeft(360);
		turnLeft(360);
        ahead(100);
           }
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet   
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(50);
		turnRight(30);
		fire(1);
		ahead(270);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
	int direcaoX =(int) getX();
		if(direcaoX == 0){
			turnRight(90);
			back(50);
		};
		
	int direcaoY =(int) getY();
		if(direcaoY == 0){
			turnRight(270);
			back(50);
		};
	}	
}
