package phonehome.leynew.com.phenehome.control;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import phonehome.leynew.com.phenehome.util.Util;

public class SingleChildDeviceType {
	
	private static SingleChildDeviceType reqChildType;
	private  SingleChildDeviceType(){}
	
	public static SingleChildDeviceType getDeviceType(){
		if(null == reqChildType){
			reqChildType = new SingleChildDeviceType();
		}
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reqChildType;
	}
	
	 public static void  getChildDeviceType(final byte [] sendInfo, final Handler handler, final String sequence){
		new Thread(){
			public void run(){
				String result = Util.sendCommandForZigbeeResult(sendInfo);
				String matchString = null ;
				if(null != result){
					String regex = "(WF\\d{3})";
					//Matcher m = Pattern.compile("(WF\\d{3})").matcher(xx);
					Pattern  pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(result);
					while(matcher.find()){
						matchString = matcher.group(1);
					}
					if(null != matchString){
						Bundle  bundle = new Bundle();
						bundle.putString("sequence", sequence);
						Message msg  =  new Message();
						bundle.putString("childType", matchString);
						msg.setData(bundle);
						msg.what = HandlerConstantByDxy.receiveType;
						handler.sendMessage(msg);
					}					
				}
			}
		}.start();
	}
	
}
