package com.photonstudio.hikvi.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取监控点预览取流URLv2返回结果
 *
 * @author guohaoxing
 */
@Data
@Accessors(chain = true)
@ApiModel("获取监控点预览取流URLv2返回结果")
public class PreviewURLsResp {

  @ApiModelProperty("取流URL")
  private String url;
}
