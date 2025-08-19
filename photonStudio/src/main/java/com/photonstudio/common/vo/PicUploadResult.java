package com.photonstudio.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PicUploadResult {
	 private Integer  status=20000;		//图片上传错误不能抛出，抛出就无法进行jsp页面回调，所以设置这个标识，20000表示无异常，50008代表异常
	    private String url;
	    private String width;
	    private String height;
}
