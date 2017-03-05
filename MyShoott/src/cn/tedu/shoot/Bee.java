package cn.tedu.shoot;

import java.util.Random;

/**小蜜蜂：是飞行物也是奖励*/
public class Bee extends FlyingObject implements Enemy,Award{
	
	private int xspeed = 2;//x坐标移动速度
	private int yspeed = 2;//y坐标移动速度
	private int awardType;//奖励的类型（0或1）
	public int lifes = 1;
	public Bee(){
		image = ShootingGame.bee;
		width = image.getWidth();
		height = image.getHeight();
//		x = (int)(Math.random())*(ShootingGame.WIDTH-this.width);
		Random rand = new Random();
		x = rand.nextInt(ShootingGame.WIDTH - this.width);
		y = -this.height;
		awardType = rand.nextInt(2);
	}
	public int getType(){
		return awardType;//返回奖励的类型
	}
	public int getScore(){
		return 10;
	}
	public void step(){
		this.y += this.yspeed;
		this.x += this.xspeed;
		if(x >= ShootingGame.WIDTH-this.width)
			this.xspeed = -2;
		if(x <= 0)
			this.xspeed = 2;
	}
	public boolean outOfBound(){
		return this.y >= ShootingGame.HEIGHT;
	}
	public void life(){
		lifes -= 1;
	}
	public int returnlife(){
		return this.lifes;
	}
}
