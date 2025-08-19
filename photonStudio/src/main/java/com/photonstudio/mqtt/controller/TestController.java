package com.photonstudio.mqtt.controller;

import com.photonstudio.mqtt.api.MqqtSend;
import com.photonstudio.mqtt.vo.MqqtVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 沈景杨
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

  private final MqqtSend mqqtSend;

  /**
   * 对接硬件设备
   *
   * @return
   */

  @PostMapping("/michale/led")
  public void openLed(@RequestBody MqqtVo mqqtvo) {
    mqqtSend.send(mqqtvo.getTopic(), mqqtvo.getPayload());
  }
}
