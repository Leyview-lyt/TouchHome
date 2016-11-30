package phonehome.leynew.com.phenehome.control;

import phonehome.leynew.com.phenehome.util.Util;

public class SendInfo {

	private static SendInfo sendInfo = null;
	
	private SendInfo(){}
	
	public static SendInfo getSendInfoTarget(){
		if(null == sendInfo){
			sendInfo = new SendInfo();
		}
		return sendInfo;
	}
	
	synchronized public static void sendUserData(final byte [] data){
		new Thread(){
			public void run(){
				Util.sendCommandForZigbeeResult(data);
				return ;
			}
		}.start();
	}
	
}
