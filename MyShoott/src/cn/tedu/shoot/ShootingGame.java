package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
public class ShootingGame extends JPanel{
	public static final int WIDTH = 400;//窗口宽
	public static final int HEIGHT = 654;//窗口高
	public static BufferedImage background; //背景图
	public static BufferedImage start;		//启动图
	public static BufferedImage pause;		//暂停图
	public static BufferedImage gameover;	//游戏结束图
	public static BufferedImage airplane;	//小敌机
	public static BufferedImage bee;		//小蜜蜂
	public static BufferedImage bullet;		//子弹图
	public static BufferedImage hero0;		//英雄机0
	public static BufferedImage hero1;		//英雄机1
	static{//初始化静态资源
		try {
			background = ImageIO.read(ShootingGame.class.getResource("background.png"));
			start = ImageIO.read(ShootingGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootingGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootingGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootingGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootingGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootingGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootingGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootingGame.class.getResource("hero1.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	
	
	private Hero hero = new Hero();//英雄机对象
	private FlyingObject[] flyings = {};//敌人（敌机+小蜜蜂)
//	private Bullet[][] bullets = new Bullet[0][];//子弹数组
	private Bullet[] bullets = new Bullet[]{};
	/**随机生成小蜜蜂和敌机*/
	public  FlyingObject nextOne(){
		Random rand  = new Random();
		int type = rand.nextInt(20);
		if(type < 10)
			return new Bee();
		else
			return new Airplane();
	}

	
	int flyEnteredIndex = 0;
	
	/**敌人（敌机+小蜜蜂入场）对象*/
	public void enterAction(){//10毫秒走一次
		flyEnteredIndex++;//敌人入场的计数
		if(flyEnteredIndex%40 == 0){//每400毫秒走一次
			FlyingObject obj = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = obj;
			
		}
		if(flyEnteredIndex%40 == 0){
				Bullet[] b = hero.shoot();
				bullets = Arrays.copyOf(bullets, bullets.length+b.length);
				System.arraycopy(b,0 , bullets, bullets.length - b.length, b.length);
				
		}
	}
	int returnIndex = 0;
	public void returnspeed(){
		returnIndex++;
		if(returnIndex%40 == 0){
			for(int i = 0;i < bullets.length;i++){
				if(bullets[i].x> hero.x+hero.width/2){
					bullets[i].xspeed = bullets[i].xspeed - 1;

				}
				if(bullets[i].x < hero.x+hero.width/2){
					bullets[i].xspeed = bullets[i].xspeed - 1;

				}
				bullets[i].yspeed = bullets[i].yspeed + 1; 
			}
		}
	}
	public void stepAction(){
		hero.step();
		for(int i = 0 ; i < flyings.length;i++)
		{
			flyings[i].step();
		}
		for(int i = 0; i < bullets.length;i++)
		{	
			bullets[i].step(hero);
		}
	}
	/**启动程序的执行*/
	public void outOfBoundsAction(){
		int index0 = 0;
		int index1 = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for(int i = 0; i < flyings.length;i++)
		{
			
			FlyingObject f = flyings[i];
			if(!f.outOfBound()){
				flyingLives[index0] = f;
				index0++;
			}
			
		}
		flyings = Arrays.copyOf(flyingLives, index0);
		
		Bullet [] bulletLives = new Bullet[bullets.length];
		for(int i = 0; i < bullets.length;i++)
		{
			Bullet l = bullets[i];
			if(!l.outOfBound()){
		
			bulletLives[index1] = l;
			index1++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index1);
	}
	
	public void bangAction(){
		for(int i = 0; i < bullets.length;i++)
		{
			Bullet b = bullets[i];
			bang (b,i);
		}
	}
	int score = 0;
	public void bang(Bullet b,int index){
		int index0 = -1;
		int index1 = -1;
		Bullet bbb;
		for(int i = 0;i <flyings.length;i++){
			FlyingObject f = flyings[i];
			if(f.shootBy(b)){
			index0 = i;
			bbb = b;
			index1 = index;
			break;
			}
			
		}
		if(index1 != -1){
			System.arraycopy(bullets, index1+1, bullets, index1, bullets.length-index1-1);
			bullets = Arrays.copyOf(bullets, bullets.length-1);
		}
		if(index0 != -1){
		FlyingObject one = flyings[index0];
		if(one instanceof Award){
			Bee k = (Bee)one;
			if(k.lifes <= 0){				
				Award a = (Award)k;
				int type = a.getType();
				switch(type){
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire();
					break;
				case Award.LIFE:
					hero.addLife();
					break;
				}
			}
			}
		if(one instanceof Enemy){
			if(one instanceof Bee){
				Bee bb = (Bee)one;
				bb.life();
				if(bb.lifes <= 0){
				Enemy e  = (Enemy)one;
				score += e.getScore();
				}
			}
			if(one instanceof Airplane){
				Airplane a = (Airplane)one;
				a.life();
				if(a.lifes <= 0){
					Enemy e = (Enemy)one;
					score += e.getScore();
				}
			}
		}
		if(one.returnlife() < 0){
			FlyingObject  t = flyings[index0];
			flyings[index0] = flyings[flyings.length-1];
			flyings[flyings.length-1] = t;
			flyings = Arrays.copyOf(flyings, flyings.length-1);
		}
		}
	}
	
	public void hitAction(){
		for(int i = 0;i < flyings.length;i++){
			FlyingObject f = flyings[i];
			if(hero.hit(f)){
				hero.substractLife();
				hero.clearDoubleFire();
				System.arraycopy(flyings, i+1, flyings, i, flyings.length - i - 1);
				flyings = Arrays.copyOf(flyings, flyings.length-1);
			}
		}
	}
	public void checkGameOverAction(){
		if(hero.getLife() <= 0){
			state = GAME_OVER;
			
		}
	}
	public void action(){
		MouseAdapter l = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(state == RUNNING){
				int x = e.getX();
				int y = e.getY();
				hero.moveTo(x, y);
				}
			}
			public void mouseClicked(MouseEvent e){
				switch(state){
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;
					break;
				}
			}

			public void mouseExited(MouseEvent e){
				if(state == RUNNING){
					state = PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e){
				if(state == PAUSE){
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(l);//处理鼠标操作事件
		this.addMouseMotionListener(l);//处理鼠标拖拽事件
		
		
		Timer timer = new Timer();
		int intervel = 10;//定时间隔，以毫秒为单位
		timer.schedule(new TimerTask(){
		public void run(){//10毫秒走一次
			if(state == RUNNING){
				enterAction();
				returnspeed();
				stepAction();
				outOfBoundsAction();
				bangAction();
				hitAction();
				checkGameOverAction();
			}
			
			repaint();
			
		}
		}, intervel,intervel);
	}

	
	/**重写paint（）*/
	public void paint(Graphics g){
		g.drawImage(background,0,0,null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScoreAndLife(g);
		paintState(g);
	}
	public void paintHero(Graphics g){
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	public void paintFlyingObjects(Graphics g){
		for (int i = 0; i <flyings.length;i++)
		{
			FlyingObject f = flyings[i];
			g.drawImage(f.image,f.x , f.y, null);
		}
	}
	public void paintBullets(Graphics g){
		for(int i = 0;i < bullets.length;i++)
			
		{
			Bullet f = bullets[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(255,0,255));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
		g.drawString("SCORE:"+score, 10, 35);
		g.drawString("LIFE:"+hero.getLife(), 10, 75);
	}
	public void paintState(Graphics g){
		switch(state){
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
			}
	}
		public static void main(String[] args) {
		JFrame frame = new JFrame("Fly");
		ShootingGame game  = new ShootingGame();
		frame.add(game);
		frame.setSize(WIDTH,HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.action();
		//启动程序的执行。
	}

}
