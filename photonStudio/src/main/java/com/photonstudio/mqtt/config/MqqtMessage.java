package com.photonstudio.mqtt.config;

import static com.photonstudio.mqtt.constant.MqttConstant.DEVICE_MQTT_ALARM_TOPIC;
import static com.photonstudio.mqtt.constant.MqttConstant.MQTT_INPUT_CHANNEL;
import static com.photonstudio.mqtt.constant.MqttConstant.MQTT_RECEIVED_TOPIC;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;


import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MqqtMessage implements MessageHandler {

    /**
     * Handle the given message.
     *
     * @param message the message to be handled
     * @throws MessagingException if the handler failed to process the message
     */
    @Override
    @ServiceActivator(inputChannel = MQTT_INPUT_CHANNEL)
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = message.getHeaders().get(MQTT_RECEIVED_TOPIC).toString();
        String payload = message.getPayload().toString();
        log.info("订阅的主题:{} 发送的内容:{}", topic, payload);
    }



    /**
     *  设备报警主题订阅
     *
     */
    @ServiceActivator(inputChannel = DEVICE_MQTT_ALARM_TOPIC)
    public void deviceAlarmHandle(Message<?> message) {
        String topic = message.getHeaders().get(DEVICE_MQTT_ALARM_TOPIC).toString();
        String payload = message.getPayload().toString();
        log.info("订阅的主题:{} 发送的内容:{}", topic, payload);
    }
    /**
     * 打印日志
     *
     * @param payload  消息报文
     * @param megTrue  正确信息
     * @param msgFalse 错误信息
     */
    private void extracted(String payload, String megTrue, String msgFalse) {
        if (StrUtil.containsIgnoreCase(payload, "1")) {
            log.info(megTrue);
        } else {
            log.info(msgFalse);
        }
    }

}

