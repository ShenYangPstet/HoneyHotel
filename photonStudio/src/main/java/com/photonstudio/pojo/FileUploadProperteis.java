package com.photonstudio.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 *  文件上传相关属性
 * @author Administrator
 *
 */
@Data
@Component
@PropertySource(value="classpath:/properties/file.properties")
public class FileUploadProperteis {
	 //静态资源对外暴露的访问路径
	@Value("${file.staticAccessPath}")
    private String staticAccessPath;

    //文件上传目录
	@Value("${file.uploadFolder}")
    private String uploadFolder ;
	
}
