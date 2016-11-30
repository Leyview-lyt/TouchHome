package phonehome.leynew.com.phenehome.control;


import phonehome.leynew.com.phenehome.util.Util;

public class SingleSend323Info {

	private static SingleSend323Info sendTarget = null;
	
	private SingleSend323Info(){}
	
	public static SingleSend323Info getSend323Target(){
		if(null == sendTarget){
			sendTarget = new SingleSend323Info();
		}
		return sendTarget;
	}
	
	
	synchronized public  void send323DataInfo(){
		
		if(SendInfoList.wf323AdjustBrightnessByte.size() >0){
			
			for(int i = 0; i < SendInfoList.wf323AdjustBrightnessByte.size();){
				
				final byte [] info = SendInfoList.wf323AdjustBrightnessByte.get(i);
				new Thread(){
					public void run(){
						Util.sendCommandForZigbeeResult(info);
					}
				}.start();
				
				SendInfoList.wf323AdjustBrightnessByte.remove(i);
//				try {
//					Thread.sleep(20);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
			return;
		}
		
	}
}
