package phonehome.leynew.com.phenehome.control;


import phonehome.leynew.com.phenehome.util.Util;

public class SingleSend322Info {

	private static SingleSend322Info singleSend323 = null;
	
	private SingleSend322Info() {
		
	}
	
	public static SingleSend322Info get322target(){
		if(null == singleSend323)
			singleSend323 = new SingleSend322Info();
		return singleSend323;
	}
	
	synchronized public static void send322BrightnessInfo(){
		if(SendInfoList.wf322BrightnessByte.size() > 0){
			for(int i = 0;  i < SendInfoList.wf322BrightnessByte.size();){
				final byte [] sendInfo = SendInfoList.wf322BrightnessByte.get(i);
				new Thread(){
					public void run(){
						System.out.println(Util.sendCommandForZigbeeResult(sendInfo));
						return;
					}
				}.start();
				SendInfoList.wf322BrightnessByte.remove(i);
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
//	synchronized public static void send323CoodAndWarmColor(){
//		if(SendInfoList.wf322CoodAndWarmByte.size() >0){
//			for(int i = 0; i < SendInfoList.wf322CoodAndWarmByte.size();){
//				final byte [] sendInfo = SendInfoList.wf322CoodAndWarmByte.get(i);
//				new Thread(){
//					public void run(){
//						Util.sendCommandForZigbeeResult(sendInfo);
//						return ;
//					}
//				}.start();
//				SendInfoList.wf322CoodAndWarmByte.remove(i);
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//	}
//
//	synchronized public static void send323SingleCoodProgress(){
//		if(SendInfoList.wf322CoodByte.size() >0){
//			for(int i = 0; i < SendInfoList.wf322CoodByte.size();){
//				final byte[] sendInfo = SendInfoList.wf322CoodByte.get(i);
//				new Thread(){
//					public void run(){
//						Util.sendCommandForZigbeeResult(sendInfo);
//					}
//				}.start();
//				SendInfoList.wf322CoodByte.remove(i);
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	} 
//	
//	synchronized public static void send323SingleWarmProgress(){
//		if(SendInfoList.wf322WarmByte.size() > 0){
//			for(int i = 0; i<SendInfoList.wf322WarmByte.size();){
//				final byte [] sendInfo = SendInfoList.wf322WarmByte.get(i);
//				new Thread(){
//					public void run(){
//						Util.sendCommandForZigbeeResult(sendInfo);
//					}
//				}.start();
//				SendInfoList.wf322WarmByte.remove(i);
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	} 
//	
//	synchronized public static void send323SecondBrightness(){
//		if(SendInfoList.wf322SecondBrightnessByte.size() > 0){
//			for(int i = 0; i<SendInfoList.wf322SecondBrightnessByte.size();){
//				final byte [] sendInfo = SendInfoList.wf322SecondBrightnessByte.get(i);
//				new Thread(){
//					public void run(){
//						Util.sendCommandForZigbeeResult(sendInfo);
//						return ;
//					}
//				}.start();
//				SendInfoList.wf322SecondBrightnessByte.remove(i);
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	} 
	
}
