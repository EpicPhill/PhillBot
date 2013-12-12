package phillbot;
import robocode.*;
import java.util.Random;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PhillBot - a robot by Phill! It's pretty crap.
 */
public class PhillBot extends AdvancedRobot
{

	private boolean forwards = true;
	private boolean lastMissed = false;
	private boolean doneRight = false;
	private boolean doneLeft = false;
	/**
	 * run: PhillBot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setColors(Color.black,Color.black,Color.red); // body,gun,radar
		boolean right;
		while(true) {
			setTurnGunRight(360);
			if (forwards)	
				setAhead(getRandom(1000));
			else 
				setBack(getRandom(1000));
			right = getRandomBool();
			if (right)
				setTurnRight(getRandom(180));
			else 
				setTurnLeft(getRandom(180));
			waitFor(new TurnCompleteCondition(this));
			right = getRandomBool();
			if (right)
				setTurnRight(getRandom(180));
			else 
				setTurnLeft(getRandom(180));
			waitFor(new TurnCompleteCondition(this));
			waitFor(new MoveCompleteCondition(this));
		}
	}
	
	private int getRandom(int limit){
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(limit);
		return randomInt;
	}

	private boolean getRandomBool(){
		int randInt = getRandom(1);
		if (randInt == 1){
			return true;
		} else {
			return false;
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		setStop();
		execute();
		if (!lastMissed)
			fire(1);
		else {
			if (!doneRight){
				turnGunRight(20);
				fire(1);
			} else if (!doneLeft){
				turnGunLeft(20);
				fire(1);
			} else {
				lastMissed = true;
				setResume();
			}
		}
	}

	public void onBulletMiss(){
		lastMissed = true;
	}

	public void onBulletHitBullet(){
		fire(3);
	}

	private void retreat(int dist){
		if (forwards)
			back(dist);
		else
			ahead(dist);	
	}

	public void onBulletHit(){
		fire(2);
	}

	public void onHitRobot(){
		setStop();
		execute();
		turnRight(30);
		retreat(400);
		setResume();
		execute();
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		setStop();
		retreat(100);
		turnGunRight(360);
		setResume();
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		retreat(200);
		forwards = !forwards;
	}	
}
