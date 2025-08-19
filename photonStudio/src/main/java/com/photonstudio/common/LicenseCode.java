package com.photonstudio.common;

public class LicenseCode {
	/*
	 * 
	 */
	public static String getApplyCode(String key,String xm_name,String xzqh){

        return getApplyCode( key,getBaseCode(xm_name,xzqh));
    }

    /**
     * 申请码里加上了当前时间，
     * 因为系统名称+行政区划+硬盘号+当前时间构成了申请码
     * 系统名称+行政区划+硬盘号+授权时间+授权截止时间构成了授权码
     * 两者的不同，仅仅在于授权码多了一个截止时间。
     * @param no
     * @return
     */
    public static String getApplyCode(String key,String no){
        //因为申请码
        no=no+","+DateUtil.currenttime;
        try {
            return DESUtils.toHexString(DESUtils.encrypt(no,key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return no;
    }

    /**
     * 解密申请码或者授权码的方法
     * @param no
     * @return
     */
    public static String getPlaintext(String no,String key){
        try {
            return DESUtils.decrypt(no,key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 拼接字符串
     * @param xm_name
     * @param xzqh
     * @return
     */
    public static String getBaseCode(String xm_name,String xzqh){
        String motherboard = DiskUtils.getHdSerialInfo(); //获取硬盘序号
        StringBuilder sb = new StringBuilder(100);
        //系统,行政区划,硬盘序号
        sb.append(xm_name).append(",")
                .append(xzqh).append(",")
                .append(motherboard);
        return sb.toString();
}
}