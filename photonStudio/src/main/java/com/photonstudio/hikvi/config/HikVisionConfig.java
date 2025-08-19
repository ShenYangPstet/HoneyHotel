package com.photonstudio.hikvi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 海康威视配置
 *
 * @author 沈景杨
 */
@Configuration
@Lazy
@Data
public class HikVisionConfig {
  /** 海康威视接口地址 */
  @Value("${hikvi.host}")
  private String host;
  /** 海康威视接口key */
  @Value("${hikvi.app-key}")
  private String appKey;
  /** 海康威视接口密钥 */
  @Value("${hikvi.app-secret}")
  private String appSecret;
}
