package com.photonstudio.common;

import java.io.File;

import org.springframework.util.StringUtils;

public class CheckAuthorizeCode {
	 /**
     * 验证授权方法
     * @param licensePath
     * @param xm_name
     * @param xzqh
     * @return
     * @throws Exception
     */
    public static boolean AuthorizeCode(String key,String licensePath,String xm_name,String xzqh) throws Exception {
    	File file = new File(licensePath);
        if(!file.exists()){
            return false; //如果授权文件都不存在，肯定还未授权直接返回false
        }
        //获取硬盘序号
        String code = LicenseCode.getBaseCode(xm_name,xzqh);
        //读取授权码文件，获取授权码
        String encoder = EncoderFile.myread(file).trim();
        if(StringUtils.isEmpty(encoder)){
            return false;
        }
        //解析授权码，与本机的系统名称+行政区划+硬盘序号进行比较，这里仅仅只是用的字符串contains方法
        String en=LicenseCode.getPlaintext(encoder,key);
        if(StringUtils.isEmpty(encoder)){
            return false;
        }
       String[] split = en.split(",");
       if(split.length!=7) {
    	   return false;
       }
        if (!en.contains(code)) {
        	 return false;
        }
        /**
         * 判断时间是否过期（这一步必须判断）
         * 因为系统名称+行政区划+硬盘号+当前时间构成了申请码
         * 系统名称+行政区划+硬盘号+授权时间+授权截止时间构成了授权码
         * 两者的不同，仅仅在于授权码多了一个截止时间。
         * 如果仅仅判断了是否包含，那会出现一个bug，直接把申请码当做授权码就能通过验证的
         * 这里没用到授权时间，你可以加密的时候不要当前时间。
         */
       // String sqtime=en.substring(en.lastIndexOf(",")+1);
       // if (!DateUtil.authorize_date(sqtime)) {
        //	 return false;
       // }
        return true;
    }
    public static boolean dateyz(String key,String en) {
    	String sqtime=en.substring(en.lastIndexOf(",")+1);
         if (!DateUtil.authorize_date(sqtime)) {
         	 return false;
         }
         return true;
	}
    /**
     * w获得授权中的字段属性
     * @param key
     * @param licensePath
     * @param index
     * @return
     */
    public static String getAppMaxNum(String key,String licensePath,Integer index) {
    	File file = new File(licensePath);
		String en=LicenseCode.getPlaintext(EncoderFile.myread(file).trim(),key);
		String[] split = en.split(",");
	return split[index];
    }
    
    public static void main(String[] args) {
    	String key="12345678";
    	String licensePath="D:/zsqy/encoder.txt";
    	File file=new File(licensePath);
    	String xm_name="zsqy";
    	String xzqh="wuhan";
    	String baseCode = LicenseCode.getBaseCode(xm_name, xzqh)+","+DateUtil.currenttime+",2019-09-15";
    	System.out.println(baseCode);
    	String hexString="";
    	try {
			hexString = DESUtils.toHexString(DESUtils.encrypt(baseCode, key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(hexString);
		EncoderFile.mywrite(file, hexString);
		  try { 
			  boolean falg = AuthorizeCode(key, licensePath, xm_name, xzqh);
		  System.out.println(falg); 
		  } catch (Exception e) { 
			  // TODO Auto-generated
		  e.printStackTrace(); }
		 
		
	}
}
