package cn.tedu.shoot;
import cn.tedu.shoot.ShootingGame;
import java.util.Random;

/**子弹是飞行物*/
public class Bullet extends FlyingObject {
	
	public int xspeed = 3;//移动的速度
	public int yspeed = 1;
	public int rexspeed = 60;
	public Bullet(int x,int y){
		this.image = ShootingGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
//		x = (int)(Math.random())*(ShootingGame.WIDTH-this.width);
		this.x = x;
		this.y = y;
	}
	public void step(){
	}
	public void step(Hero x){
		if(this.x+this.width/2 >= x.x+x.width/2 && this.x != x.x +x.width*3/4+30){
			if(this.x != x.x+x.width/2 && this.x+this.width/2 < (x.x+x.width/2+x.width/10)){
				this.x += this.rexspeed;
			}
			else if(this.x == x.x+x.width/2){
				this.xspeed = 3;
			}
			else{
				this.x += xspeed;
				}
		}

		if(this.x+this.width/2< x.x+x.width/2 && this.x != x.x +x.width*1/4-30){
			if(this.x+this.width/2 > (x.x+x.width/2-x.width/10)){
				this.x -= this.rexspeed;
			}
			else{
				this.x -= xspeed;
				}			
		}
		
		this.y -= yspeed;
	}
	public boolean outOfBound(){
		return this.y < -this.height;
		
	}
}
