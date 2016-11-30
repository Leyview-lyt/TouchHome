package phonehome.leynew.com.phenehome.control;


import phonehome.leynew.com.phenehome.util.Util;

public class SringToHexString {
	
	public static String getHexString( String var ){
		char [] ss = var.toCharArray();
		String result = "";
		for(int i = 0; i<ss.length; i++){
			if( 0 == i){
				result = String.valueOf(ss[i]);
				if("0".equals( result)){
					result = "";
					continue;
				}
			}else{
				result += String.valueOf(ss[i]);
			}
		}
		Integer toInter = Integer.parseInt(result);
		String endResult = Util.integer2HexString(toInter);
		return endResult;
	}
	
}
