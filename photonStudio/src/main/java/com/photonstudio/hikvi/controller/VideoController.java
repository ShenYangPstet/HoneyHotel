package com.photonstudio.hikvi.controller;

import com.photonstudio.common.vo.Result;
import com.photonstudio.hikvi.HikVisionService;
import com.photonstudio.hikvi.req.PreviewURLsReq;
import com.photonstudio.hikvi.req.VideoPlaybackReq;
import com.photonstudio.hikvi.resp.HiKviResult;
import com.photonstudio.hikvi.resp.PreviewURLsResp;
import com.photonstudio.hikvi.resp.VideoPlaybackResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监控点controller
 *
 * @author guohaoxing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/HikVision/video")
@Api(tags = "监控点")
@Validated
public class VideoController {
  private final HikVisionService hikVisionService;

  @PostMapping("/previewURLs")
  @ApiOperation("获取监控点预览取流URLv2")
  public Result<PreviewURLsResp> previewURLs(
      @RequestBody @Validated PreviewURLsReq previewURLsReq) {
    HiKviResult<PreviewURLsResp> result = hikVisionService.previewURLs(previewURLsReq);
    return Result.ok(result.getData());
  }

  @PostMapping("/getVideoPlayBack")
  @ApiOperation("获取监控点回放取流URLv2")
  public Result<VideoPlaybackResp> getVideoPlayBack(@RequestBody @Validated VideoPlaybackReq req) {
    HiKviResult<VideoPlaybackResp> result = hikVisionService.getPlayBack(req);
    if (result.isSuccess()) {
      return Result.ok(result.getData());
    }
    return Result.build(50009, result.getMsg());
  }
}
