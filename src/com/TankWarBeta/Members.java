package com.TankWarBeta;

import java.util.Vector;

class Bomb {
	int x;
	int y;
	//bomb life
	int life = 9;
	boolean isLive = true;
	public Bomb(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	//life eclipse
	public void lifeDown() {
		if (life > 0) {
			life--;
		} else {
			this.isLive = false;
		}
	}
}

class Tank {
	//x-coodinate 
	int x = 0;
	int y = 0;
	
	
	
	//颜色
	int color; 
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}




	//Tank Direct
	//0--up, 1 -- right, 2--down, 3--left
	int direct = 0;
	
	
	//坦克的方向
	//坦克的速度
	int speed= 1;
	
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
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
	
	
	
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}

class Hero extends Tank{	
	
	//shot comes from Hero
	Shot s = null;
	Vector<Shot> ss = new Vector<Shot>();
	public Hero(int x, int y) {
		super(x, y);
		
	}
	
	//fire
	public void shotEnemy() {
		
		switch(this.direct) {
		case 0:
			s = new Shot(x, y - 15, 0);
			ss.add(s);
			break;
		case 1:
			s = new Shot(x + 15, y, 1);
			ss.add(s);
			break;
		case 2:
			s = new Shot(x, y + 15, 2);
			ss.add(s);
			break;
		case 3:
			s = new Shot(x - 15, y, 3);
			ss.add(s);
			break;
		}
		Thread t = new Thread(s);
		t.start();

	}
	
	
	
	//up
	public void moveUp() {
		y -= speed;
	}
	public void moveRight() {
		x += speed;
	}
	public void moveDown() {
		y += speed;
	}
	public void moveLeft() {
		x  -= speed;
	}
	
}
//enemy tank
class EnemyTank extends Tank implements Runnable{	
	
	boolean isLive = true;
	Vector<Shot> ss = new Vector<Shot>();
	 
	public EnemyTank(int x, int y) {
		super(x, y);
	}
	
	//坦克想上移动
	public void moveUp() {
		y -= speed;
	}
	public void moveRight() {
		x += speed;
	}
	public void moveDown() {
		y += speed;
	}
	public void moveLeft() {
		x  -= speed;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			int steps = (int)(Math.random() * 40);
			switch(direct) {
			case 0:
				for (int i = 0; i < steps; i++) {
					if (y <= 15) {
						break;
					}
					y -= speed;
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 1:
				for (int i = 0; i < steps; i++) {
					if (x >= 385) {
						break;
					}
					x += speed;
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 2:
				for (int i = 0; i < steps; i++) {
					if (y >= 285) {
						break;
					}
					y += speed;
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 3:
				for (int i = 0; i < steps; i++) {
					if (x <= 15) {
						break;
					}
					x -= speed;
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			}
			this.direct = (int)(Math.random() * 4);
			
			//judge if the enemy's tank dead
			if (this.isLive == false) {
				break;
			}
			
		}
	}
	
}

class Shot implements Runnable{
	int x;
	int y;
	int direct;
	int speed = 10;
	//still alive?
	boolean isLive = true;
	public Shot(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(direct) {
			case 0:
				//up
				y -= speed;
				break;
			case 1:
				x += speed;
				break;
			case 2:
				y += speed;
				break;
			case 3:
				x -= speed;
				break;
			}
			//System.out.println(x + "," + y);
			//when will the shot disappear???????????????
			
			
			//is shot arrive the edge?
			if (x < -1 || x > 401 || y < -1 || y > 301) {
				this.isLive = false;
				break;
			}
		}
	}
}


