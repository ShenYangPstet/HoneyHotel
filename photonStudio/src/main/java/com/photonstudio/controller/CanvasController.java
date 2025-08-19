package com.photonstudio.controller;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Canvas;
import com.photonstudio.service.CanvasService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/canvas")
@RequiredArgsConstructor
@Validated
public class CanvasController {

  private final CanvasService canvasService;

  @PostMapping("/getByPicId")
  public SysResult getByPicId(Integer picId) {
    return SysResult.oK(canvasService.getByPicId(picId));
  }

  @PostMapping("/insertOrUpdate")
  public SysResult insertOrUpdate(Canvas canvas) {
    canvasService.insertOrUpdate(canvas);
    return SysResult.oK();
  }
}
