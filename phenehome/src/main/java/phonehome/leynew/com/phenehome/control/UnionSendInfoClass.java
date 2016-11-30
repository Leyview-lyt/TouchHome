
package phonehome.leynew.com.phenehome.control;


import phonehome.leynew.com.phenehome.util.Util;

public class UnionSendInfoClass {

	private static long lastSendTime = 0L;
	private static long currentTime = 0L;
	
	public UnionSendInfoClass(){}
	
	public static void sendCommonOrder(final byte [] commonData){
		currentTime = System.currentTimeMillis();
		if((currentTime - lastSendTime) >= 300){
			lastSendTime = currentTime;
			new Thread(){
				public void run(){
					System.out.println(Util.sendCommandForZigbeeResult(commonData));
					return ;
				}
			}.start();
		}
	}
	
	public static void sendSpecialOrder(final byte [] specialData){
		new Thread(){
			public void run(){
				try{
				Thread.sleep(300);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					return;
				}
			}
		}.start();
	}
	
	public static void sendButtonOrder(final byte[] buttonInfo){
		new Thread(){
			public void run(){
				Util.sendCommandForZigbeeResult(buttonInfo);
			}
		}.start();
	}
}
