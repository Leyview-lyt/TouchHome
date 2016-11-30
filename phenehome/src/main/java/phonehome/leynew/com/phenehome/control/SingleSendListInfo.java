package phonehome.leynew.com.phenehome.control;



import java.util.Iterator;

import phonehome.leynew.com.phenehome.util.Util;

public class SingleSendListInfo {
	
	private  static SingleSendListInfo singleSendListInfo = null;
	
	private SingleSendListInfo(){}
	
	public static SingleSendListInfo getSingleSendListInstance(){
		if(null == singleSendListInfo)
			singleSendListInfo = new SingleSendListInfo();
		return singleSendListInfo;
	} 
	
	public static void sendInfo(){
		Iterator iterator = SendInfoList.sendInfoList.iterator();
		while(iterator.hasNext()){
			byte [] info = (byte [])iterator.next();
				sendByteArrayInfo(info);
			iterator.remove();
		}
		
//		for(int i = 0; i < SendInfoList.sendInfoList.size()-1;i++){
//			sendByteArrayInfo()
//		}
	}
	
	synchronized private static void sendByteArrayInfo(final byte [] array){
		
		new Thread(){
			public void run(){
				try {
					Thread.currentThread().sleep(50);
					Util.sendCommandForZigbeeResult(array);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
}
