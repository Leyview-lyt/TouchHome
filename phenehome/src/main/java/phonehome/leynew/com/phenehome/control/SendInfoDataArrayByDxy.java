package phonehome.leynew.com.phenehome.control;

import phonehome.leynew.com.phenehome.util.Util;

public class SendInfoDataArrayByDxy {
	
	public SendInfoDataArrayByDxy(){
	}
	
	public static void SendInfoByByte(final byte [] sendData){
		new Thread(){
			public void run(){
				System.out.println(Util.sendCommandForZigbeeResult(sendData));
			}
		}.start();
	}
	
}
