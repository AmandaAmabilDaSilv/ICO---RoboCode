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
 * Samara - robô do grupo, nas cores roxo e rosa.
 * <p>
 * Fica parada girando o canhão para procurar inimigos. Ao encontrar um, atira
 * com força proporcional à distância e se desloca na perpendicular ao inimigo.
 * Também reage a tiros recebidos, colisões com paredes e com outros robôs, e
 * comemora quando vence a rodada.
 * <p>
 * Baseado em sample.Corners.
 */
public class Samara extends Robot {
	int others;
	static int corner = 0;
	boolean stopWhenSeeRobot = false;

	public void run() {
		setBodyColor(new Color(128, 0, 128));   // Corpo: Roxo
		setGunColor(new Color(255, 105, 180));  // Canhão: Rosa
		setRadarColor(new Color(186, 85, 211)); // Radar: Orquídea
		setBulletColor(Color.pink);             // Balas: Rosa
		setScanColor(Color.magenta);            // Feixe do Radar: Magenta

		others = getOthers();

		//goCorner();

		int gunIncrement = 20;

		while (true) {
			for (int i = 0; i < 360; i++) {
				turnGunLeft(gunIncrement);
			}
			gunIncrement *= -1;
		}
	}
      
  	    public void onHitByBullet(HitByBulletEvent e) {
 	   // Desvia quando for atingido por uma bala
    		fire(2); 
			turnRight(30);
		    back(100);
		
		}
		
// Ao colidir com a parede, gira pelo inverso do ângulo da parede e dá ré
	public void onHitWall(HitWallEvent e) {
		// Gira o inverso do ângulo (bearing) em que a parede foi atingida
		turnRight(-e.getBearing());
		// Dá ré para se afastar da borda
		back(150);
	}
	 
	/**
	 * Ao colidir com outro robo, atira na forca maxima e se afasta dele.
	 */
	public void onHitRobot(HitRobotEvent e) {
		// Robo a frente: atira e recua; robo atras: atira e avanca
		if (e.getBearing() > -90 && e.getBearing() <= 90) {
			fire(3);
			back(80);
		} else {
			fire(3);
			ahead(80);
		}
	}

	public void goCorner() {
		stopWhenSeeRobot = false;
		turnRight(normalRelativeAngleDegrees(corner - getHeading()));
		stopWhenSeeRobot = true;
		ahead(5000);
		turnLeft(90);
		ahead(5000);
		turnGunLeft(90);
		}

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

	public void smartFire(double robotDistance) {
		if (robotDistance > 200 || getEnergy() < 15) {
			fire(1);
			fire(1);
		} else if (robotDistance > 50) {
			fire(2);
			fire(2);
		} else {
			fire(3);
			fire(3);
		}
	}


	public void onDeath(DeathEvent e) {
		if (others == 0) {
			return;
		}

		if (getOthers() / (double) others >= .75) {
			corner += 90;
			if (corner == 270) {
				corner = -90;
			}
			out.println("Eu deixei você ganha  " + corner);
		} else {
			out.println("Cai mas cai atirando!!" + corner);
		}
	}
	
	/**
	 * Comemoração ao vencer a rodada na arena
	 */
	 public void onWin(WinEvent e) {
		 for (int i = 0; i < 8; i++) {
			 turnRight(45);
			 turnGunLeft(45);
		}
		out.println("Ganhamos, querida! Samara arrasou com todes e venceu.");
	}
		
}
