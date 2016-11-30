package phonehome.leynew.com.phenehome.control;

import java.util.ArrayList;
import java.util.List;

public class SendInfoList {
	
	public static List<byte []> sendInfoList = new ArrayList<byte[]>();

	public static List<byte []> wf322BrightnessByte = new ArrayList<byte[]>();
	public static List<byte []> wf322CoodAndWarmByte = new ArrayList<byte[]>();
	public static List<byte []> wf322CoodByte = new ArrayList<byte[]>();
	public static List<byte []> wf322WarmByte = new ArrayList<byte[]>();
	public static List<byte []> wf322SecondBrightnessByte  = new ArrayList<byte[]>();

	public static List<byte []> wf323AdjustBrightnessByte = new ArrayList<byte[]>();
	public static List<byte []> wf323AdjustRGBColorByte = new ArrayList<byte[]>();
	public static List<byte []> wf323SecondBrightByte = new ArrayList<byte[]>();
	public static List<byte []> wf323SecondSpeedByte = new ArrayList<byte[]>();
	
	//WF325
	public static boolean sendInfoFlag = true;          // 325
	 
	//WF322 or WF326
	public static boolean wf322BrightnessFlag = true;   // 322
	public static boolean wf322CoodAndWarFlag = true;   // 322
	public static boolean wf322CoodFlag = true;         // 322
	public static boolean wf322WarmFlag = true;         // 322
	public static boolean wf322SecondBrightnessFlag = true;    //322
	
	//WF323��Ϣ���ͱ�־
	public static boolean wf323AdjustBrighFlag = true;  // WF323
	public static boolean wf323AdjustRGBColorFlag = true;   // WF323
	public static boolean wf323SecondBrightFlag = true;
	public static boolean wf323SecondSpeedFlag = true;
	
}
