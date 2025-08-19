package com.photonstudio.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.photonstudio.common.DatabaseThreadLocal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

/** MybatisPlus配置类 */
@Configuration
public class MybatisPlusConfig {
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    DynamicTableNameInnerInterceptor tableNameInnerInterceptor =
        new DynamicTableNameInnerInterceptor();
    tableNameInnerInterceptor.setTableNameHandler(
        // 动态表名处理器，没有数据库名时从请求头中获取拼接
        (sql, tableName) -> {
          String[] split = tableName.split("\\.");
          if (!StrUtil.containsIgnoreCase(sql, "create table") && split.length == 1) {
            String database = DatabaseThreadLocal.get();
            DatabaseThreadLocal.remove();
            if (StrUtil.isBlank(database)) {
              HttpServletRequest request =
                  ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
                      .getRequest();
              database = request.getHeader("database");
              if (StrUtil.isBlank(database)) {
                Map<String, String> pathVars =
                    (Map<String, String>)
                        request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                database = pathVars.get("dBname");
              }
            }
            if (StrUtil.isNotBlank(database)) {
              return database + "." + tableName;
            }
          }
          return tableName;
        });
    interceptor.addInnerInterceptor(tableNameInnerInterceptor);
    return interceptor;
  }
}
