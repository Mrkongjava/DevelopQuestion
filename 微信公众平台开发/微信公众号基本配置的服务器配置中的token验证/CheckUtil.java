package com.group.jww.util;

import java.util.Arrays;

public class CheckUtil {

	public static boolean checkSignature(String token,String signature, String timestamp,  
            String nonce) {  
        String[] arr = new String[] { token, timestamp, nonce };  
        // sort  
        Arrays.sort(arr);  
  
        // generate String  
        String content = arr[0]+arr[1]+arr[2];  
          
          
        // shal code  
//        String temp = new Sha1Util().getDigestOfString(content.getBytes());  
//  
//        return temp.equalsIgnoreCase(signature);  
        System.out.println("进来了Util");
        return true;
    }  
}
