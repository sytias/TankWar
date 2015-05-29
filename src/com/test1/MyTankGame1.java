package com.test1;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MyTankGame1 extends JFrame{
	MyPanel mp = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame1 mtg1 = new MyTankGame1();
	}
	
	public MyTankGame1() {
		mp = new MyPanel();
		this.add(mp);
		
		//this.addKeyListener(mp);
		
		this.setVisible(true);
		this.setSize(400, 300);
	}
	


}
//my panel
class MyPanel extends JPanel{
	//define my tank
	Hero hero = null;
	public MyPanel() {
		hero = new Hero(202, 10);
		
	}
	
	//override paint
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		this.drawTank(hero.getX(), hero.getY(), g, 0, 1);
		
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
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x + 15, y, 5, 30, false);
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			g.fillOval(x + 4, y + 10, 10, 10);
			g.drawLine(x + 9, y, x + 9, y + 15);
			break;
		}
	}

//	@Override
//	public void keyPressed(KeyEvent e) {
//		// TODO Auto-generated method stub
//		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//			hero.setY(hero.getY() + 1);
//		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//			hero.setX(hero.getX() - 1);
//		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//			hero.setX(hero.getX() + 1);
//		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
//			hero.setY(hero.getY() - 1);
//		}
//		repaint();
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
}


class Tank {
	//x-coodinate 
	int x = 0;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	//y-coodinate
	int y = 0;
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}

class Hero extends Tank{	
	public Hero(int x, int y) {
		super(x, y);
	}
}