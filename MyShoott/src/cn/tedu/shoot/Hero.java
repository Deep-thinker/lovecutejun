package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 英雄机：是飞行物*/
import java.util.Random;
public class Hero extends FlyingObject{//英雄机

	private int doubleFires;
	private int life;
	private BufferedImage[] Images;//图标数组
	private int index;//协助图片切换
	public Hero(){
		image = ShootingGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
		doubleFires =100000;
		life = 3;
		Images = new BufferedImage[]{ShootingGame.hero0,ShootingGame.hero1};
		index = 0;//协助切换
	}
	public void step(){
//		index++;
//		int a = index/10;
//		int b = a%2;
//		image = Images[b];改写如下：
		image  = Images[index++/10%Images.length];
	}
	public Bullet[] shoot(){
		int ystep = 20;
		if(doubleFires <= 1000&&doubleFires >300){
			Bullet[] bs = new Bullet[3];
			bs[0] = new Bullet(this.x + this.width/3,this.y-ystep);
			bs[1] = new Bullet(this.x + this.width*2/3,this.y-ystep);
			bs[2] = new Bullet(this.x + this.width/2,this.y-ystep);
			doubleFires -= 1;
			return bs;
		}
		else if(doubleFires > 1000 && doubleFires < 1500){//双倍
			Bullet[] bs = new Bullet[5];
			bs[0] = new Bullet(this.x + this.width/4,this.y-ystep);
			bs[1] = new Bullet(this.x + this.width/2,this.y-ystep);
			bs[2] = new Bullet(this.x + this.width*3/4,this.y-ystep);
			bs[3] = new Bullet(this.x-10,this.y-ystep);
			bs[4] = new Bullet(this.x + this.width+10,this.y-ystep);
			doubleFires -= 3;
			
			return bs;
			}
		else if(doubleFires > 1500){
			Bullet[] bs = new Bullet[7];
			bs[0] = new Bullet(this.x + this.width/4-30,this.y-ystep);
			bs[1] = new Bullet(this.x + this.width/2,this.y-ystep);
			bs[2] = new Bullet(this.x + this.width*3/4+30,this.y-ystep);
			bs[3] = new Bullet(this.x-25,this.y-ystep);
			bs[4] = new Bullet(this.x + this.width*3/4+25,this.y-ystep);
			bs[5] = new Bullet(this.x + this.width*1/4-25,this.y-ystep);
			bs[6] = new Bullet(this.x+this.width+25,this.y-ystep);
			doubleFires -= 5;
			return bs;
		}
		else{//单倍
			Bullet[]bs = new Bullet[2];
			bs[0] = new Bullet(this.x + this.width/2-40,this.y-ystep);
			bs[1] = new Bullet(this.x + this.width/2+40,this.y-ystep);
			return bs;
		}
	}
	/**英雄机随着鼠标移动：x：鼠标的x坐标 y：鼠标的y坐标 */
	public void moveTo(int x, int y){
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	public boolean outOfBound(){
		return false;//永不越界
	}
	public void addLife(){
		life++;
	}
	public int getLife(){
		return life;
	}
	public void substractLife(){
		life--;
	}
	public void clearDoubleFire(){
		doubleFires -= 80;
	}
	public void addDoubleFire(){
		doubleFires += 40;
	}
	public boolean hit(FlyingObject other){
		int x1 = other.x - this.width/2;
		int x2 = other.x + other.width + this.width/2;
		int y1 = other.y - this.height/2;
		int y2 = other.y + other.height + this.height/2;
		int x = this.x + this.width/2;
		int y = this.y + this.height/2;
		return x >= x1 && x <= x2 && y >= y1 && y <= y2;
	}
}
