package com.photonstudio.hikvi.controller;

import com.photonstudio.common.vo.Result;
import com.photonstudio.hikvi.HikVisionService;
import com.photonstudio.hikvi.req.PatrolReq;
import com.photonstudio.hikvi.req.TaskDetailsReq;
import com.photonstudio.hikvi.req.TaskReq;
import com.photonstudio.hikvi.resp.HiKviResult;
import com.photonstudio.hikvi.resp.PageResp;
import com.photonstudio.hikvi.resp.PatrolResp;
import com.photonstudio.hikvi.resp.PatrolResultResp;
import com.photonstudio.hikvi.resp.TaskDetailsResp;
import com.photonstudio.hikvi.resp.TaskListResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 巡更controller
 *
 * @author guohaoxing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/HikVision/patrol")
@Api(tags = "巡更")
@Validated
public class PatrolController {

  private final HikVisionService hikVisionService;

  @PostMapping("/query")
  @ApiOperation("巡更")
  public Result<PatrolResultResp<List<PatrolResp>>> patrol(
      @RequestBody @Validated PatrolReq patrolReq) {
    HiKviResult<PatrolResultResp<List<PatrolResp>>> patrol = hikVisionService.patrol(
        patrolReq);
    return Result.ok(patrol.getData());
  }

  @GetMapping("/getTaskList")
  @ApiOperation("获取任务列表")
  public Result<List<TaskListResp>> getTaskList() {
    return Result.ok(hikVisionService.getTaskList());
  }

  @PostMapping ("/getTaskDetails")
  @ApiOperation("获取任务详情")
  public Result<List<TaskDetailsResp>> getTaskDetails(@RequestBody  TaskDetailsReq taskDetailsReq) {



    return Result.ok(hikVisionService.getTaskDetails(taskDetailsReq));
  }

  @PostMapping("/getPageTaskList")
  @ApiOperation("获取分页任务列表")
  public Result<PageResp<TaskListResp>> getPageTaskList(TaskReq taskReq) {

    return Result.ok(hikVisionService.getPageTaskList(taskReq).getData());
  }
}
