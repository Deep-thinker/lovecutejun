package cn.tedu.shoot;
import java.awt.image.BufferedImage;
public abstract class FlyingObject {
	protected BufferedImage image;//图片
	protected int width;//宽
	protected int height;//高
	protected int x;//x坐标
	protected int y;//y坐标
	
	/**飞行物走一步*/
	public abstract void step();
	
	public abstract boolean outOfBound();
	/**判断敌人是否被子弹撞击*/
	public boolean shootBy(Bullet bullet){
		int x1 = this.x;
		int x2 = this.x + this.width;
		
		int y1 = this.y;
		int y2 = this.y + this.height;
		return bullet.x+bullet.width <= x2 && bullet.x >= x1 && bullet.y >= y1 && bullet.y +bullet.width <= y2;
	}
	public void life(){
		
	}
	public int returnlife(){
		return 1;
	}
}
