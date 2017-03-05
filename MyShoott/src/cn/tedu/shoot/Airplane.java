package cn.tedu.shoot;
import java.util.Random;
/**敌机：是飞行物，也是敌人*/
public class Airplane extends FlyingObject implements Enemy {
	private int speed  = 2;//移动速度
	public int lifes = 0;
	public Airplane(){
		image = ShootingGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
//		x = (int)(Math.random())*(ShootingGame.WIDTH-this.width);
		Random rand = new Random();
		x = rand.nextInt(ShootingGame.WIDTH - this.width);
		y = -this.height;
	}
	public int getScore()
	{
		return 5;//打掉敌机得5分。
	}
	public void step(){
		this.y += speed;
		
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
