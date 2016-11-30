package phonehome.leynew.com.phenehome.control;


import phonehome.leynew.com.phenehome.util.Util;

public class SendInfoList32X {

	private static SendInfoList32X sendInfo32x = null;
	
	private SendInfoList32X(){}
	
	public static SendInfoList32X getSendInfoList32XTagert(){
		if( null == sendInfo32x )
			sendInfo32x = new SendInfoList32X();
		return sendInfo32x;
	}
	
	synchronized public static void sendSingleInfo(){
		if(SendInfoList.sendInfoList.size()  >0){
			for(int i = 0; i < SendInfoList.sendInfoList.size(); ){
				final byte [] info = SendInfoList.sendInfoList.get(i);
				new Thread(){
					public void run(){
						Util.sendCommandForZigbeeResult(info);
						return;
					}
				}.start();
				SendInfoList.sendInfoList.remove(i);
			}
			
		}
	}
	
	synchronized public static void wf322BrightnessInfo(){
		
	}
	
	
	synchronized public static  void sendArrayInfoBydxy(){
		int z = 0;
		while(SendInfoList.sendInfoFlag){
			for(int i = 0; 0 != SendInfoList.sendInfoList.size();){
				final byte [] info = SendInfoList.sendInfoList.get(i);
				Util.sendCommandForZigbeeResult(info);
				SendInfoList.sendInfoList.remove(i);
				z++;
				if(0 == SendInfoList.sendInfoList.size()){
					break;
				}
			}
		}
	}
	
	synchronized public void sendInfoThread(){
		new Thread(){
			public void run(){
				while(SendInfoList.sendInfoFlag){
					SendInfoList32X.sendInfoByteInfoBydxy();
				}
				return ;
			}
		}.start();
	}
	
	public  static void sendInfoByteInfoBydxy(){
		if(0 < SendInfoList.sendInfoList.size()){
			byte [] result = SendInfoList.sendInfoList.get(0);
			Util.sendCommandForZigbeeResult(result);
			SendInfoList.sendInfoList.remove(0);
		}
	}
	
}
