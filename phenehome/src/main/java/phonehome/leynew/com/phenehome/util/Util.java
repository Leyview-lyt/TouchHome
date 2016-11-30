package phonehome.leynew.com.phenehome.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class Util {
//	public static boolean selectDevice(String sequence) {
//		boolean b = Util.sendCommand(LeyNew.READSTATUS, null, sequence);
//		if (b) {
//			List<String[]> strs = Util.sendCommandForResult(LeyNew.READSTATUS, null,
//					sequence);
//			if (strs.size() != 0) {//
//				return true;
//			}
//		}
//		return false;
//	}
//
	public static boolean sendCommand(byte[] command){
		try{
			InetAddress inetAddress = InetAddress.getByName(LeyNew.ADDRESS);
			DatagramSocket socket = new DatagramSocket(null);
			socket.setReuseAddress(true);
			socket.bind(new InetSocketAddress(LeyNew.PORT));
			System.out.println();
			DatagramPacket sendPacket = new DatagramPacket(command, command.length, inetAddress, LeyNew.PORT);
			socket.send(sendPacket);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public static boolean sendCommand(String command, String[] parameter, String sequence){
		standard();
		if (parameter!=null) {
			StringBuilder ss = new StringBuilder();
			for (String s : parameter) {
				ss.append(s+"_");
			}
			Log.i("=========", ss+"");
		}
		String ultimate = encrypt(command, parameter, sequence);
		DatagramSocket socket = null;
		boolean sendFlag = false;
		try{
			InetAddress inetAddress = InetAddress.getByName(LeyNew.ADDRESS);
			socket = new DatagramSocket(null);
			socket.setReuseAddress(true);
			socket.bind(new InetSocketAddress(LeyNew.PORT));
			DatagramPacket sendPacket = new DatagramPacket(ultimate.getBytes(),ultimate.getBytes().length, inetAddress, LeyNew.PORT);
			socket.send(sendPacket);
//			Thread.sleep(100);
//			socket.send(sendPacket);
			sendFlag = true;
		} catch (Exception e){
			e.printStackTrace();
			Log.i("====","发送数据有误  ");
			sendFlag = false;
		}finally{
			// TODO: 2016/10/27/027
			if(socket!=null) {
				socket.close();
			}
			return sendFlag;
		}
	}

	public static boolean sendCommand1(String command, String[] parameter,
											String sequence){
		standard();
		String ultimate = command;
		try {
			InetAddress inetAddress = InetAddress.getByName(LeyNew.ADDRESS);
			DatagramSocket socket = new DatagramSocket(null);
			socket.setReuseAddress(true);
			socket.bind(new InetSocketAddress(LeyNew.PORT));
			DatagramPacket sendPacket = new DatagramPacket(ultimate.getBytes(),
					ultimate.getBytes().length, inetAddress, LeyNew.PORT);
			socket.send(sendPacket);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

//

	/**
	 * Fragment_Deviece_Bound
	 * @param seton
	 * @param parameter
	 * @param sequence
     * @return
     */
	public static boolean sendCommand(byte[] seton, String[] parameter,
										   String sequence) {
		standard();
		try {
			InetAddress inetAddress = InetAddress.getByName(LeyNew.ADDRESS);
			DatagramSocket socket = new DatagramSocket(null);
			socket.setReuseAddress(true);
			socket.bind(new InetSocketAddress(LeyNew.PORT));
			DatagramPacket sendPacket = new DatagramPacket(seton,
					seton.length, inetAddress, LeyNew.PORT);
			socket.send(sendPacket);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public static boolean sendCommand(String command, String[] parameter,
			String sequence, List<int[]> list) {
		standard();
		System.out.println(command+sequence+list);
		System.out.println(parameter);
		int length = 0;//
		String end = "\r\n";//
		String star = sequence + " " + command + " ";
		for (int i = 0; parameter != null && i < parameter.length; i++) {
			star = star + parameter[i] + " ";
		}
		System.out.println("star��" + star);//
		length = length + star.length() + end.length();
		List<byte[]> blist = new ArrayList<byte[]>();
		for (int i = 0; i < list.size(); i++) {
			int num[] = list.get(i);
			byte bt[] = new byte[num.length + 1];
			for (int j = 0; j < num.length; j++) {
				if (num[j] == 0) //
					num[j] = 1;
				else if (num[j] == 13)//
					num[j] += 1;
				else if (num[j] == 10)//
					num[j] += 1;
				else if (num[j] == 32)//
					num[j] += 1;
				bt[j] = (byte) num[j];
			}

			bt[num.length] = (byte) 32;//
			blist.add(bt);
			length = length + bt.length;
		}
		byte ultimate[] = new byte[length];//
		int currentIndex = 0;//
		byte bstar[] = star.getBytes();
		for (int i = 0; i < bstar.length; i++) {
			ultimate[currentIndex] = bstar[i];
			currentIndex++;
		}
		for (int i = 0; i < blist.size(); i++) {
			byte centre[] = blist.get(i);
			for (int j = 0; j < centre.length; j++) {
				ultimate[currentIndex] = centre[j];
				currentIndex++;
			}
		}
		byte bend[] = end.getBytes();
		for (int i = 0; i < bend.length; i++) {
			ultimate[currentIndex] = bend[i];
			currentIndex++;
		}
		Util.standard();
		try {
			InetAddress inetAddress = InetAddress.getByName(LeyNew.ADDRESS);
			DatagramSocket socket = new DatagramSocket(null);
			socket.setReuseAddress(true);
			socket.bind(new InetSocketAddress(LeyNew.PORT));
			DatagramPacket sendPacket = new DatagramPacket(ultimate,
					ultimate.length, inetAddress, LeyNew.PORT);
			socket.send(sendPacket);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean sendCommand(String command, String[] parameter,
			String sequence, int[] num) {
		System.out.println(command+sequence+"hhhhhhhhhhhh");
		for(int i=0;i<num.length;i++){
			System.out.println(num[i]+"num");
		}
//		for(int i=0;i<parameter.length;i++){
//			System.out.println(parameter[i]+"parameter");
//		}
		List<int[]> list = new ArrayList<int[]>();
		int index = 0;
		for (int i = 0; i < num.length; i++) {
			int[] arr = new int[1];
			arr[0] = num[index];
			list.add(arr);
			index++;
		}
		return sendCommand(command, parameter, sequence, list);
	}

	public static  String sendCommandForZigbeeResult(byte[] sendInfo){
		sendCommand(sendInfo);
		return  receiveData(LeyNew.ADDRESS, LeyNew.PORT, "");
	}

	/**
	 * 发送指令,获取可连接的设备
	 * @param command
	 * @param parameter
	 * @param sequence
     * @return
     */
	public static List<String[]> sendCommandForResult(String command,String[] parameter, String sequence){
		sendCommand(command, parameter, sequence);
		List<String[]> list = receiveData(LeyNew.ADDRESS, LeyNew.PORT);

		for(String[] xx : list){

		}
		if(0 <= list.size()){
		}else{
			for(int i = 0 ; i< 3; i++){//如果没获取到,就在尝试三遍
				sendCommand(command, parameter, sequence);
				list = receiveData(LeyNew.ADDRESS, LeyNew.PORT);
				if(0 < list.size()){
					break;
				}
			}

		}
		return list;
//		return receiveData(LeyNew.ADDRESS, LeyNew.PORT);
	}

	public static List<String[]> sendCommandForResult(String command,
			String[] parameter, String sequence, int[] num) {
		sendCommand(command, parameter, sequence, num);
		return receiveData(LeyNew.ADDRESS, LeyNew.PORT);
	}



	/**
	 * 建立接收包,获取返回数据,转换成字符串格式
	 * @param address
	 * @param port
	 * @param arg
     * @return
     */
	public static String receiveData(String address , int port, String arg){
		String receiveData  = "";
		standard();
		try{
			InetAddress inetAddress = InetAddress.getByName(address);
			DatagramSocket  socket = new DatagramSocket(null);
			socket.setReuseAddress(true);
			socket.bind(new InetSocketAddress(port));
			int n = 0;
			while(n == 0){
				byte data[] = new byte[1024];
				DatagramPacket receicvePacket = new DatagramPacket(data,data.length, inetAddress, port);
				socket.setSoTimeout(5000);
				socket.receive(receicvePacket);
				n = receicvePacket.getLength();
				if(0<n){
					receiveData = new String(receicvePacket.getData(),receicvePacket.getOffset(),receicvePacket.getLength());
				}
			}
		}catch(Exception e){
			Log.i("=========","receiveData");
		}finally{
			return receiveData;
		}
	}

	/**
	 * 建立接收包,获取返回数据,
	 * @param address
	 * @param port
     * @return
     */
	public static List<String[]> receiveData(String address, int port){
		standard();
		List<String[]> list = new ArrayList<String[]>();
		DatagramSocket socket = null;
		try{
			InetAddress inetAddress = InetAddress.getByName(address);
			socket = new DatagramSocket(null);
			socket.setReuseAddress(true);
			socket.bind(new InetSocketAddress(port));
			int n = 0;
			while (n == 0){
				byte data[] = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(data,	data.length, inetAddress, port);
				// TODO: 2016/11/19/019  总是超时
				socket.setSoTimeout(3000);
				socket.receive(receivePacket);//获取信息
				n = receivePacket.getLength();//得到信息长度
				if (n > 0){
					String search_info = new String(receivePacket.getData(),receivePacket.getOffset(),receivePacket.getLength());
					System.out.println(" search_info   " + search_info);
					byte[] sByte = new byte[search_info.length()];
					sByte = search_info.getBytes();
					char[] sByte_ac = new char[sByte.length]; //
					for (int i = 0; i < sByte.length; i++){
						sByte_ac[i] = (char) sByte[i];
					}
					char[] encode = new char[sByte_ac.length];
					for (int i = 0; i < sByte_ac.length; i++){
						char ch2 = sByte_ac[i];
						encode[i] = DeEnCode(ch2, false);
					}
					String receive = new String(encode);
					// search ACCF2301011B V2.0 leynew WF510 room
					if(receive.contains("MAC")){
					receive = receive.substring(4, receive.indexOf("ok")).trim();
					}else{
						receive = receive.substring(0, receive.indexOf("\r\n")).trim();
					}
					String str[] = receive.split(" ");
					list.add(str);
					n = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("======","获取数据有误");
		}finally{
			if (socket!=null) {
				socket.close();
			}
			return list;
		}
	}

	/**
	 * 加密
	 * @param command
	 * @param parameter
	 * @param sequence
     * @return
     */
	private static String encrypt(String command, String[] parameter, String sequence){
		String end = "\r\n";
		String ultimate = "";
		String fuck="";
		if (sequence != null){
			byte sequenceByte[] = (sequence.trim() + " ").getBytes();
			char sequenceChar[] = new char[sequenceByte.length];
			for (int i = 0; i < sequenceByte.length; i++){
				sequenceChar[i] = (char) sequenceByte[i];
			}
			char newSequenceCharChar[] = new char[sequenceChar.length];
			for (int i = 0; i < sequenceChar.length; i++){
				newSequenceCharChar[i] = Util.DeEnCode(sequenceChar[i], true);
			}
			String s = new String(newSequenceCharChar);
			ultimate = s + ultimate;
			fuck=fuck+sequence+" ";
		}

		byte commandByte[] = (command.trim() + " ").getBytes();
		char commandChar[] = new char[commandByte.length];
		for (int i = 0; i < commandByte.length; i++) {
			commandChar[i] = (char) commandByte[i];
		}
		char newCommandChar[] = new char[commandChar.length];
		for (int i = 0; i < commandChar.length; i++) {
			newCommandChar[i] = Util.DeEnCode(commandChar[i], true);
		}
		String s = new String(newCommandChar);
		ultimate = ultimate + s;
		fuck=fuck+command+" ";


		for (int i = 0; null != parameter && parameter.length > 0 && i < parameter.length; i++) {

			byte tempByte[] = (parameter[i] + " ").getBytes();//变bite
			char tempChar[] = new char[tempByte.length];
			for (int j = 0; j < tempByte.length; j++) {
				tempChar[j] = (char) tempByte[j];//变char
			}
			char newTempChar[] = new char[tempChar.length];
			for (int j = 0; j < tempChar.length; j++) {
				newTempChar[j] = Util.DeEnCode(tempChar[j], true);//
			}
			String st = new String(newTempChar);
			ultimate = ultimate + st;

		}

		for(int i=0;null != parameter && parameter.length > 0 && i < parameter.length;i++){
			fuck=fuck+String.valueOf(parameter[i])+" ";
		}


		byte[] aaa=new byte[fuck.length()];
		aaa=fuck.getBytes();
		for(int a=0;a<aaa.length;a++){
			System.out.print(aaa[a]);
		}
		ultimate = ultimate + end;//
		return ultimate;
	}

	/**
	 * 编码
	 * @param resCh
	 * @param flag
     * @return
     */
	public static char DeEnCode(char resCh, boolean flag){
		 char key[] = {'L','e','y','N','e','w'};
		 char tempCh = resCh;
		 @SuppressWarnings("unused")
		char hightByte = 0;
		 int i = 0;
		 if (flag){
			 hightByte = (char)(tempCh&0x80);
			 for(i=0;i<6;i++){
				 tempCh = (char)(tempCh^key[i]);
			 }
		 }else{
			 tempCh = resCh;
			 for(i=0;i<6;i++){
				 tempCh = (char) (tempCh^key[i]);
			 }
			// hightByte = (char) (tempCh&0x80); //
		 }
		 return tempCh;
	}

	/**
	 * 用于监控程序代码质量
	 */
	public static void standard() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog()
				.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects()
				.penaltyLog()
				.penaltyDeath().build());

	}

	public static String[] getDisplayMetrics(Context cx){
		String str = "";
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		str += "The absolute width:" + String.valueOf(screenWidth) + "pixels\n";
		str += "The absolute heightin:" + String.valueOf(screenHeight)
				+ "pixels\n";
		str += "The logical density of the display.:" + String.valueOf(density)
				+ "\n";
		str += "X dimension :" + String.valueOf(xdpi) + "pixels per inch\n";
		str += "Y dimension :" + String.valueOf(ydpi) + "pixels per inch\n";
		return new String[] { str, screenWidth + "", screenHeight + "" };
	}
	public static int getDisplayMetricsWidth(Context cx) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		//int screenHeight = dm.heightPixels;
		return screenWidth ;
	}
	public static int getDisplayMetricsHeight(Context cx) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		//int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}
	public static void sendMessage(Handler handler, Bundle data){
		Message msg = new Message();
		if (data != null) {
			msg.setData(data);
		}
		handler.sendMessage(msg);
	}
//
//

	/**
	 * Fragment_Deviece_Bound
	 * @param src
	 * @return
     */
	 public static byte[] HexString2Bytes(String src){
         byte[] ret = new byte[src.length()/2];
         byte[] tmp = src.getBytes();
         for(int i=0; i< tmp.length/2; i++){
           ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]);
         }
         return ret;
       }

     public static byte uniteBytes(byte src0, byte src1){
     byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
     _b0 = (byte)(_b0 << 4);
     byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
     byte ret = (byte)(_b0 ^ _b1);
     return ret;
     }

	/**
	 * Fragment_Device_Bound
	 * @param c
	 * @param g
	 * @param id
	 * @param p
     * @param f
     * @return
     */
    public static String command(String c, String g, String id,String p,String f ){
    	String s = c + g + id + p + f ;
    	return s;
    }

    public static String integer2HexString(int i){
    	String s = "";
    	if(i<16){
    	s = "0" + Integer.toHexString(i);
    	}else{
    		s = Integer.toHexString(i);
    	}

    	return s;
    }
}
