package com.photonstudio.controller;

import com.photonstudio.common.CheckAuthorizeCode;
import com.photonstudio.common.EncoderFile;
import com.photonstudio.common.LicenseCode;
import com.photonstudio.common.vo.SysResult;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource(value="classpath:/properties/key.properties")
@RequestMapping("/zsqy")
public class LicenseController {
	 public String xzqh = "wuhan";
	 public String xm_name = "studio20";
	 @Value("${key.keydefault}")
	 private String key ;
	 @Value("${key.path}")
	 private String licensePath;

	 @RequestMapping("/license")
	 public SysResult license(){// 校验
		 System.out.println(licensePath);

	        try {
	        	File file = new File(licensePath);
				String encoder = EncoderFile.myread(file).trim();
				String en=LicenseCode.getPlaintext(encoder,key);
				if (!CheckAuthorizeCode.AuthorizeCode(key, licensePath, xm_name, xzqh)) {

					return SysResult.build(50000, LicenseCode.getApplyCode(key, xm_name, xzqh));
				}
				if(!CheckAuthorizeCode.dateyz(key, en))
					return SysResult.build(50000,encoder,"授权时间到期");

	        } catch (Exception e) {
	            e.printStackTrace();
	            return SysResult.build(50000, LicenseCode.getApplyCode(key,xm_name,xzqh));
	        }
	         return SysResult.oK();
	}
	 @RequestMapping("/licenseBySP")
	 public SysResult licenseBySP() {
		 System.out.println("SP授权");
		 File file = new File(licensePath);
			String encoder = EncoderFile.myread(file).trim();
			String en=LicenseCode.getPlaintext(encoder,key);
		 try {
	            if (CheckAuthorizeCode.AuthorizeCode(key, licensePath, xm_name, xzqh)&&CheckAuthorizeCode.dateyz(key, en)) {
	               
	                return SysResult.oK();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return SysResult.build(50000, "授权已到期，请联系管理员。");
	}
	 @RequestMapping("/importlicense")
	 public SysResult importlicense( String code) {
		 code=code.trim();
		 String en=LicenseCode.getPlaintext(code,key);
	       String[] split = en.split(",");
	       if(split.length!=7||StringUtils.isEmpty(split[4])||StringUtils.isEmpty(split[5])) {
	    	   return SysResult.build(50009, "授权码不完整请联系管理员");
	       }
	      
		 File file = new File(licensePath);
	        if(!file.exists()){
	            file.getParentFile().mkdirs();
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        EncoderFile.mywrite(file,code);
	        boolean flag = false;
	        
	        try {
	             if(CheckAuthorizeCode.AuthorizeCode(key, licensePath, xm_name, xzqh)&&CheckAuthorizeCode.dateyz(key, en))
	            	 flag=true ; //验证授权码是否正确
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        if(flag){ //授权成功
	            
	            return SysResult.oK();
	        }
	        
	        return SysResult.build(50009, "授权码错误");
		
		
	}
	 @RequestMapping("/licenseByUser")//获取申请码
	 public SysResult licenseByUser() {
		
		 try {
	        	File file = new File(licensePath);
				String encoder = EncoderFile.myread(file).trim();
				if (!CheckAuthorizeCode.AuthorizeCode(key, licensePath, xm_name, xzqh)) {

					return SysResult.oK(LicenseCode.getApplyCode(key, xm_name, xzqh));
				}else {
					 return SysResult.oK(encoder);
				}
//			 AuthorizationParametersReq authorizationParametersReq = new AuthorizationParametersReq();
//
//			       if (ObjectUtil.isEmpty(key)) {
//					authorizationParametersReq.setProjectId("202301010001");
//		       }else {
//					authorizationParametersReq.setProjectId(key);
//				}
//
//
//			 AuthorizedResult code = driveApiService.getCode(authorizationParametersReq); //获取申请码
//			 if (code.getCode()==0){
//				 return SysResult.oK(code.getData());
//			 }
//			return SysResult.build(50009,code.getMessage());
		 } catch (Exception e) {
	            e.printStackTrace();
	            return SysResult.oK(LicenseCode.getApplyCode(key,xm_name,xzqh));
	        }
		 
	}
	 
	 @RequestMapping("/licenseDate")
		public SysResult licenseDate() {
			File file = new File(licensePath);
			 //读取授权码文件，获取授权码
	        String encoder = EncoderFile.myread(file).trim();
	        String en=LicenseCode.getPlaintext(encoder,key);
	        String sqtime=en.substring(en.lastIndexOf(",")+1);
	       return SysResult.oK(sqtime);
		}
}
