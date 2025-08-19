package com.photonstudio.hikvi.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.photonstudio.hikvi.config.HikVisionConfig;
import com.photonstudio.hikvi.req.PageQuery;
import com.photonstudio.hikvi.resp.HiKviResult;
import com.photonstudio.hikvi.resp.PageResp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 海康威视工具类
 *
 * @author 沈景杨
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HikVisionUtil {

  private final HikVisionConfig config;

  public <T> HiKviResult<T> doPost(String url, Object object) {
    /** STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数. */
    ArtemisConfig.host = config.getHost(); // artemis网关服务器ip端口
    ArtemisConfig.appKey = config.getAppKey(); // 秘钥appkey
    ArtemisConfig.appSecret = config.getAppSecret(); // 秘钥appSecret

    /** STEP2：设置OpenAPI接口的上下文 */
    final String ARTEMIS_PATH = "/artemis";
    /** STEP3：设置接口的URI地址 */
    final String previewURLsApi = ARTEMIS_PATH + url;
    Map<String, String> path =
        new HashMap<String, String>(2) {
          {
            put("https://", previewURLsApi); // 根据现场环境部署确认是http还是https
          }
        };
    /** STEP4：设置参数提交方式 */
    String contentType = "application/json";

    /** STEP5：组装请求参数 */
    String body = JSONObject.toJSONString(object);

    /** STEP6：调用接口 */
    String result =
        ArtemisHttpUtil.doPostStringArtemis(
            path, body, null, null, contentType, null); // post请求application/json类型参数
    return JSONObject.parseObject(result, new TypeReference<HiKviResult<T>>() {
    });
  }

  public <T> HiKviResult<T> doPost(
      String url, Object object, TypeReference<HiKviResult<T>> typeReference) {
    /** STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数. */
    ArtemisConfig.host = config.getHost(); // artemis网关服务器ip端口
    ArtemisConfig.appKey = config.getAppKey(); // 秘钥appkey
    ArtemisConfig.appSecret = config.getAppSecret(); // 秘钥appSecret

    /** STEP2：设置OpenAPI接口的上下文 */
    final String ARTEMIS_PATH = "/artemis";
    /** STEP3：设置接口的URI地址 */
    final String previewURLsApi = ARTEMIS_PATH + url;
    Map<String, String> path =
        new HashMap<String, String>(2) {
          {
            put("https://", previewURLsApi); // 根据现场环境部署确认是http还是https
          }
        };
    /** STEP4：设置参数提交方式 */
    String contentType = "application/json";

    /** STEP5：组装请求参数 */
    String body = JSONObject.toJSONString(object);

    /** STEP6：调用接口 */
    String result =
        ArtemisHttpUtil.doPostStringArtemis(
            path, body, null, null, contentType, null); // post请求application/json类型参数
    return JSONObject.parseObject(result, typeReference);
  }

  /**
   * get请求
   *
   * @param url           请求地址
   * @param object        请求参数
   * @param typeReference 返回类型
   * @param <T>           返回类型
   * @return 返回结果
   */
  public <T> HiKviResult<T> doGet(
      String url, Object object, Map<String, String> header, String contentType,
      TypeReference<HiKviResult<T>> typeReference) {
    /** STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数. */
    ArtemisConfig.host = config.getHost(); // artemis网关服务器ip端口
    ArtemisConfig.appKey = config.getAppKey(); // 秘钥appkey
    ArtemisConfig.appSecret = config.getAppSecret(); // 秘钥appSecret

    /** STEP2：设置OpenAPI接口的上下文 */
    final String ARTEMIS_PATH = "/artemis";
    /** STEP3：设置接口的URI地址 */
    final String previewURLsApi = ARTEMIS_PATH + url;
    Map<String, String> path =
        new HashMap<String, String>(2) {
          {
            put("https://", previewURLsApi); // 根据现场环境部署确认是http还是https
          }
        };

    /** STEP5：组装请求参数 */
    Map<String, String> stringMap = new HashMap<>();
    Map<String, Object> objectMap = BeanUtil.beanToMap(object);
    if (ObjectUtil.isNotEmpty(objectMap)) {
      for (Entry<String, Object> entry : objectMap.entrySet()) {
        stringMap.put(entry.getKey(), entry.getValue().toString());
      }
    }

    String result = ArtemisHttpUtil.doGetArtemis(path, stringMap, null, contentType, header);
    return JSONObject.parseObject(result, typeReference);
  }

  /**
   * 分页查询组装成查询全部
   *
   * <p>循环调用分页查询接口，每次查询1000条数据，直到查询到的数据小于1000条为止，组装成全部数据
   *
   * @param pageQuery 分页查询参数
   * @param queryFunc 查询函数···
   * @param <T>       分页查询参数类型
   * @param <R>       返回数据类型
   * @return 全部数据集合
   */
  public <T extends PageQuery, R> List<R> pageQuery(
      T pageQuery, Function<T, HiKviResult<PageResp<R>>> queryFunc) {
    int pageSize = 500;
    int pageIndex = 1;
    pageQuery.setPageNo(pageIndex);
    pageQuery.setPageSize(pageSize);
    List<R> list = queryFunc.apply(pageQuery).getData().getList();
    if (list.size() < pageSize) {
      return list;
    }
    while (true) {
      pageIndex++;
      pageQuery.setPageNo(pageIndex);
      List<R> pageList = queryFunc.apply(pageQuery).getData().getList();
      if (pageList.size() < pageSize) {
        list.addAll(pageList);
        break;
      }
      list.addAll(pageList);
    }
    return list;
  }


}
