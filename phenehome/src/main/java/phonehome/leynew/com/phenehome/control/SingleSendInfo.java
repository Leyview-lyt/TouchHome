package phonehome.leynew.com.phenehome.control;


import phonehome.leynew.com.phenehome.util.Util;

public class SingleSendInfo {

	private static SingleSendInfo singleSend = null;
	
	public static long firstSendTime = 0l;
	public static long lastSendTime = 0l;
	private SingleSendInfo(){}
	
	public static SingleSendInfo getSingleInstance(){
		if(null == singleSend){
			singleSend = new SingleSendInfo();
		}
		return singleSend;
	}
	
	synchronized public static void sendInfoMethod(final byte[] data){
		try{
		firstSendTime = System.currentTimeMillis();
		Thread.currentThread().sleep(30);
		new Thread(){
			public void run(){
						String result = Util.sendCommandForZigbeeResult(data);
					return ;
			}
		}.start();
		}catch(Exception e){
		}
	} 
}
