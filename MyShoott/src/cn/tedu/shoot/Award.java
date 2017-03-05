package cn.tedu.shoot;
/** 奖励接口*/
public interface Award {
	public int DOUBLE_FIRE = 0;//火力值
	public int LIFE = 1;//命
	/**获取奖励的类型 返回0 代表火力值，返回1代表命*/
	public int getType();
}
