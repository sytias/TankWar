package com.TankWarBeta;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class MyTankGame extends JFrame{
	MyPanel mp = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame mtg1 = new MyTankGame();
	}
	
	public MyTankGame() {
		mp = new MyPanel();
		this.add(mp);
		Thread t = new Thread(mp);
		t.start();
		this.addKeyListener(mp);
		
		this.setVisible(true);
		this.setSize(400, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}

//my panel
class MyPanel extends JPanel implements KeyListener, Runnable{
	//define my tank
	Hero hero = null;
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	//define boom list
	Vector<Bomb> bombs = new Vector<Bomb>();
	
	//define three pictures, all 3 pics formed the bomb
	Image im1 = null;
	Image im2 = null;
	Image im3 = null;
	
	
	int enSize = 3;
	public MyPanel() {
		hero = new Hero(40,90);
		
		
		//initialize enemyTank
		for (int i = 0; i < enSize; i++) {
			EnemyTank et = new EnemyTank((i+1) * 50, 40);
			et.setColor(0);
			Thread t = new Thread(et);
			t.start();
			ets.add(et);
			//add one shot to enemyTank
			Shot s = new Shot(et.x, et.y + 15, 2);
			
			//add shot to vector
			et.ss.add(s);
			Thread t2 = new Thread(s);
			t2.start();
		}
		
		//initialize pictures
		im1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bomb1.gif"));
		im2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bomb2.gif"));
		im3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bomb3.gif"));
	}
	//override paint
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		
		//Draw my tank
		this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		
		//Draw enemies' tank
		for (int i = 0; i < ets.size(); i++) { 
			EnemyTank et = ets.get(i);
			if (et.isLive) {
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
				//Draw the enemy shot
				for (int j = 0; j < et.ss.size(); j++) {
					Shot enemyShot = et.ss.get(j);
					if (enemyShot != null && enemyShot.isLive) {
						System.out.println("the " + i + " tank " + j + " shot" + enemyShot.x);
						g.draw3DRect(enemyShot.x, enemyShot.y+15, 1, 1, false);
					} else {
						et.ss.remove(enemyShot);
					}
				}
			}
		}
		
		//Draw the hero shot
		for (int i = 0; i < hero.ss.size(); i++) {
			Shot myShot = hero.ss.get(i);
			if (myShot != null && myShot.isLive) {
				g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
			}
			
			if (myShot.isLive == false) {
				hero.ss.remove(i);
			}
		}

		
		//Draw the bomb
		for (int i = 0; i < bombs.size(); i++) {
			
			System.out.println("bombs.size() =  " + bombs.size());
			Bomb b = bombs.get(i);
			if (b.life > 6) {
				g.drawImage(im1, b.x - 15, b.y - 15, 30, 30, this);
			} else if (b.life > 3) {
				g.drawImage(im2, b.x - 15, b.y - 15, 30, 30, this);
			} else if (b.life > 0) {
				g.drawImage(im3, b.x - 15, b.y - 15, 30, 30, this);
			}
			
			b.lifeDown();
			if (b.life == 0) {
				bombs.remove(b);
			}
		}
		
	}
	
	//Is Enemy Tank destroyed by shot?
	public void hitTank(Shot s, EnemyTank et) {
		//to decide the direct of tank
		switch(et.direct) {
		//enemy tank is up or down, they are the same shape
		case 0:
		case 2:
			if (s.x >= et.x - 10 && s.x <= et.x + 10 && s.y >= et.y - 15 && s.y <= et.y + 15) {
				//hit, shot, enemytank disapeard
				s.isLive = false;
				et.isLive = false;
				//get a bomb
				Bomb b = new Bomb(et.x, et.y);
				bombs.addElement(b);
			}
			
		case 1:
		case 3:
			if (s.x >= et.x - 15 && s.x <= et.x + 15 && s.y >= et.y -10 && s.y <= et.y +10) {
				//hit, shot, enemytank disapeard
				s.isLive = false;
				et.isLive = false;
				Bomb b = new Bomb(et.x, et.y);
				bombs.addElement(b);
			}
		}
	}
	
	//draw Tank
	public void drawTank(int x, int y, Graphics g, int direct, int type) {
		switch(type) {
		case 0:
			g.setColor(Color.CYAN);
			break;
		
		case 1:
			g.setColor(Color.yellow);
			break;
		}	
		switch(direct) {
		//go up
		case 0:
			//draw my tank
			//left rectangle
			g.fill3DRect(x - 10, y - 15, 5, 30, false);
			g.fill3DRect(x + 5, y - 15, 5, 30, false);
			g.fill3DRect(x - 5, y - 10, 10, 20, false);
			g.fillOval(x - 5, y -5, 10, 10);
			g.drawLine(x, y, x, y - 15);
			break;
		case 1: //向右
			//draw my tank
			//left rectangle
			g.fill3DRect(x - 15, y - 10, 30, 5, false);
			g.fill3DRect(x - 15, y + 5, 30, 5, false);
			g.fill3DRect(x -10, y - 5, 20, 10, false);
			g.fillOval(x - 5, y -5, 10, 10);
			g.drawLine(x , y, x + 15, y);
			break;
		case 2: //向下
			//draw my tank
			//left rectangle
			g.fill3DRect(x - 10, y - 15, 5, 30, false);
			g.fill3DRect(x + 5, y - 15, 5, 30, false);
			g.fill3DRect(x - 5, y - 10, 10, 20, false);
			g.fillOval(x - 5, y -5, 10, 10);
			g.drawLine(x, y, x, y + 15);
			break;
		case 3: //向左
			//draw my tank
			//left rectangle
			g.fill3DRect(x - 15, y - 10, 30, 5, false);
			g.fill3DRect(x - 15, y + 5, 30, 5, false);
			g.fill3DRect(x -10, y - 5, 20, 10, false);
			g.fillOval(x - 5, y -5, 10, 10);
			g.drawLine(x , y, x - 15, y);
			break;
		}
	}

	@Override
	//设置我的坦克的方向
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar() == 's') {
			//e.getKeyCode() == KeyEvent.VK_S
			this.hero.setDirect(2);
			this.hero.moveDown();
		} else if (e.getKeyChar() == 'a') {
			this.hero.setDirect(3);
			this.hero.moveLeft();
		} else if (e.getKeyChar() == 'd') {
			this.hero.setDirect(1);
			this.hero.moveRight();
		} else if (e.getKeyChar() == 'w') {
			this.hero.setDirect(0);
			this.hero.moveUp();
		} 
		
		if (e.getKeyChar() == 'j') {
			//fire
			if (hero.ss.size() <= 4) {
				this.hero.shotEnemy();
			}
		}
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// refresh every 100 ms
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//judge if it is hit?
			for (int i = 0; i < hero.ss.size(); i++) {
				//get shot
				Shot myShot = hero.ss.get(i);
				//judge if shot is valid?
				if (myShot.isLive) {
					//get enemyTank
					for (int j = 0; j < ets.size(); j++) {
						EnemyTank et = ets.get(j);
						if (et.isLive) {
							this.hitTank(myShot, et);
						}
					}
				}
			}
//			//judge if we need to add new shots
//			for (int i = 0; i < ets.size(); i++) {
//				EnemyTank et = ets.get(i);
//				if (et.isLive) {
//					if (et.ss.size() < 10) {
//						Shot s = null;
//						switch(et.direct) {
//						case 0:
//							s = new Shot(et.x, et.y -15, 0);
//							et.ss.add(s);
//							break;
//						case 1:
//							s = new Shot(et.x + 15, et.y, 1);
//							et.ss.add(s);
//							break;
//						case 2:
//							s = new Shot(et.x, et.y +15, 2);
//							et.ss.add(s);
//							break;
//						case 3:
//							s = new Shot(et.x - 15, et.y -15, 3);
//							et.ss.add(s);
//							break;
//						}
//						Thread newShot = new Thread(s);
//						newShot.start();
//					}
//				} 
//			}
			
			this.repaint();
		}
	}
}


