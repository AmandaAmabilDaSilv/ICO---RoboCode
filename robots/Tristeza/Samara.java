package Tristeza;
import robocode.*;
/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */


import robocode.DeathEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.HitByBulletEvent;

import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;


/**
 * Corners - a sample robot that demonstrates defensive corner positioning and adaptive strategy.
 * <p>
 * This robot moves to a corner, then rotates its gun back and forth scanning for enemies.
 * If it performs poorly in a round, it will try a different corner in the next round.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class Samara extends Robot {
	int others; // Tracks the initial number of opponents
	static int corner = 0; // Current corner angle (static to persist between rounds)
	boolean stopWhenSeeRobot = false; // Flag to control movement behavior when enemies are detected

	/**
	 * Main robot behavior. Sets colors, moves to the corner, and continuously scans for enemies.
	 */
	public void run() {
		// Set colors
		setBodyColor(Color.red);
		setGunColor(Color.black);
		setRadarColor(Color.yellow);
		setBulletColor(Color.green);
		setScanColor(Color.green);

		// Save # of other bots
		others = getOthers();

		// Move to a corner
		//goCorner();

		// Initialize gun turn speed to 17
		int gunIncrement = 17;

		// Spin gun back and forth
		while (true) {
			for (int i = 0; i < 360; i++) {
				turnGunLeft(gunIncrement);
			}
			gunIncrement *= -1;
		}
	}
      
  	    public void onHitByBullet(HitByBulletEvent e) {
 	   // Desvia quando for atingido por uma bala
    	turnRight(90);
 	  	 ahead(120);
		}
	/**
	 * Navigates to a corner of the battlefield using a simple but inefficient approach.
	 * This method could be optimized for better performance.
	 */
	public void goCorner() {
		// We don't want to stop when we're just turning...
		stopWhenSeeRobot = false;
		// turn to face the wall to the "right" of our desired corner.
		turnRight(normalRelativeAngleDegrees(corner - getHeading()));
		// Ok, now we don't want to crash into any robot in our way...
		stopWhenSeeRobot = true;
		// Move to that wall
		ahead(5000);
		// Turn to face the corner
		turnLeft(90);
		// Move to the corner
		ahead(5000);
		// Turn gun to starting point
		turnGunLeft(90);
		}

	/**
	 * Handles robot detection events. Either stops and fires or just fires
	 * depending on the current movement strategy.
	 *
	 * @param e The scanned robot event containing target information
	 */
	public void desviar(ScannedRobotEvent e) {
    // Direção absoluta do inimigo
    double anguloInimigo = getHeading() + e.getBearing();

    // Anda perpendicularmente à direção do inimigo
	fire(1);
    double direcao = anguloInimigo + 90;
	 

    // Vira para a direção calculada
    turnRight(normalRelativeAngleDegrees(
        direcao - getHeading()
    ));

    // Anda para mudar de posição
    ahead(100);
}


    public void onScannedRobot(ScannedRobotEvent e) {
       smartFire(e.getDistance());

    // Não para mais para atirar:
    // movimenta-se para dificultar os disparos inimigos
      desviar(e);
    }

	/**
	 * Adjusts fire power based on distance to target and current energy level.
	 * Uses higher fire power at closer ranges for maximum damage.
	 *
	 * @param robotDistance the distance to the target robot in pixels
	 */
	public void smartFire(double robotDistance) {
		if (robotDistance > 200 || getEnergy() < 15) {
			fire(1);
            fire(1); // Low power for long distance or when energy is low
		} else if (robotDistance > 50) {
			fire(2);
			fire(2); // Medium power for moderate distance
		} else {
			fire(3);
			fire(3); // High power for close range
		}
	}


	/**
	 * Evaluates performance after robot destruction and adjusts strategy.
	 * Changes corner position if performance was poor in the current round.
	 *
	 * @param e The death event
	 */
	public void onDeath(DeathEvent e) {
		// Safety check: avoids division by zero, though 'others' should never be 0
		if (others == 0) {
			return;
		}

		// If 75% of the robots are still alive when we die, we'll switch corners.
		if (getOthers() / (double) others >= .75) {
			corner += 90; // Rotate 90 degrees to try the next corner
			if (corner == 270) {
				corner = -90; // Reset to the first corner after completing the full rotation
			}
			out.println("Eu deixei você ganha  " + corner);
		} else {
			out.println("Cai mas cai atirando!!" + corner);
		}
	}
}
