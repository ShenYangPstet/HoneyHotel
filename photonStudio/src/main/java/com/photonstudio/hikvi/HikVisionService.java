package com.photonstudio.hikvi;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.photonstudio.hikvi.req.PatrolReq;
import com.photonstudio.hikvi.req.PreviewURLsReq;
import com.photonstudio.hikvi.req.TaskDetailsReq;
import com.photonstudio.hikvi.req.TaskReq;
import com.photonstudio.hikvi.req.VideoPlaybackReq;
import com.photonstudio.hikvi.resp.HiKviResult;
import com.photonstudio.hikvi.resp.PageResp;
import com.photonstudio.hikvi.resp.PatrolResp;
import com.photonstudio.hikvi.resp.PatrolResultResp;
import com.photonstudio.hikvi.resp.PreviewURLsResp;
import com.photonstudio.hikvi.resp.TaskDetailsResp;
import com.photonstudio.hikvi.resp.TaskListResp;
import com.photonstudio.hikvi.resp.VideoPlaybackResp;
import com.photonstudio.hikvi.util.HikVisionUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 海康威视接口
 *
 * @author 沈景杨
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HikVisionService {

  private final HikVisionUtil hikVisionUtil;

  @Value("${hikvi.streamType}")
  private Integer streamType;

  @Value("${hikvi.protocol}")
  private String protocol;

  @Value("${hikvi.transmode}")
  private Integer transmode;

  @Value("${hikvi.expand}")
  private String expand;

  @Value("${hikvi.streamform}")
  private String streamform;

  /**
   * 巡更
   *
   * @param patrolReq 巡更请求参数
   * @return 巡更返回结果
   */
  public HiKviResult<PatrolResultResp<List<PatrolResp>>> patrol(PatrolReq patrolReq) {
    log.info("巡更请求参数:{}", JSON.toJSONString(patrolReq));
    HiKviResult<PatrolResultResp<List<PatrolResp>>> resp =
        hikVisionUtil.doPost("/api/v1/gtps/patrolHistory/records", patrolReq);
    log.info("巡更返回结果:{}", resp);
    return resp;
  }

  /** 获取任务列表 */
  public HiKviResult<PageResp<TaskListResp>> getPageTaskList(TaskReq taskReq) {
    log.info("获取任务列表请求参数:{}", JSON.toJSONString(taskReq));
    Map<String, String> header = new HashMap<>();
    header.put("App-Id", "sps");
    header.put("userId", Base64.encode("admin"));
    //    header.put("token",
    // "SElLIDJ0NnVoOFY0eDJ4V3pSVHg6SEJ2RTNzbGN6NTdBclEyOU1ITVN2MlZob1FrU29MNjNISEhIaC94b0V0az06MTY5Nzc4MDcxNzg4Ng==");
    HiKviResult<PageResp<TaskListResp>> resp =
        hikVisionUtil.doGet(
            "/api/v1/task/list",
            taskReq,
            header,
            "application/json; charset=utf-8",
            new TypeReference<HiKviResult<PageResp<TaskListResp>>>() {});
    log.info("获取任务列表返回结果:{}", resp);
    return resp;
  }

  /** 获取所有任务列表 */
  public List<TaskListResp> getTaskList() {
    return hikVisionUtil.pageQuery(new TaskReq(), this::getPageTaskList);
  }

  public List<TaskDetailsResp> getTaskDetails(TaskDetailsReq req) {
    log.info("获取任务详情请求参数:{}", req);
    List<TaskDetailsResp> taskDetails = new ArrayList<>();
    Map<String, String> header = new HashMap<>();
    header.put("appId", "sps");
    header.put("userId", "admin");
    List<TaskListResp> taskList = getTaskList();
    // 查询所有任务详情
    for (TaskListResp resp : taskList) {
      HiKviResult<TaskDetailsResp> result =
          hikVisionUtil.doGet(
              "/api/v1/task/query/" + resp.getPatrolTaskId(),
              "",
              header,
              "application/json",
              new TypeReference<HiKviResult<TaskDetailsResp>>() {});
      if (result.isSuccess()) {
        taskDetails.add(result.getData());
      }
    }
    return taskDetails.stream()
        .filter(
            task -> {
              // 时间过滤
              if (ObjectUtil.isNotEmpty(req.getStartTime())
                  && ObjectUtil.isNotEmpty(req.getEndTime())) {
                return DateUtil.parseUTC(task.getStartTime()).compareTo(req.getStartTime()) >= 0
                    && DateUtil.parseUTC(task.getEndTime()).compareTo(req.getEndTime()) <= 0;
              }
              return true;
            })
        .collect(Collectors.toList());
  }

  /** 获取监控点预览取流URLv2 */
  public HiKviResult<PreviewURLsResp> previewURLs(PreviewURLsReq previewURLsReq) {
    previewURLsReq.setStreamType(streamType);
    previewURLsReq.setProtocol(protocol);
    previewURLsReq.setTransmode(transmode);
    previewURLsReq.setExpand(expand);
    previewURLsReq.setStreamform(streamform);
    log.info("获取监控点预览取流URLv2请求参数:{}", JSON.toJSONString(previewURLsReq));
    HiKviResult<PreviewURLsResp> resp =
        hikVisionUtil.doPost("/api/video/v2/cameras/previewURLs", previewURLsReq);
    log.info("获取监控点预览取流URLv2返回结果:{}", JSON.toJSONString(resp));
    return resp;
  }

  /**
   * 获取监控点回放取流URLv2
   *
   * @param req 请求参数
   * @return 返回值
   */
  public HiKviResult<VideoPlaybackResp> getPlayBack(VideoPlaybackReq req) {
    req.setProtocol(protocol);
    req.setExpand(expand);
    req.setStreamform(streamform);
    req.setTransmode(transmode);
    System.out.println(req);
    log.info("获取监控点回放请求参数:{}", JSON.toJSONString(req));
    HiKviResult<VideoPlaybackResp> resp =
        hikVisionUtil.doPost(
            "/api/video/v2/cameras/playbackURLs",
            req,
            new TypeReference<HiKviResult<VideoPlaybackResp>>() {});
    log.info("回放返回值:{}", resp);
    return resp;
  }
}
