package com.photonstudio.mqtt.api;



import static com.photonstudio.mqtt.constant.MqttConstant.MQTT_OUTBOUND_CHANNEL;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service("mqttSend")
@MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL)
public interface MqqtSend {

    /**
     * 定义重载方法，用于消息发送
     *
     * @param payload 消息报文
     */
     void send(String payload);

    /**
     * 指定topic进行消息发送
     *
     * @param topic   主题
     * @param payload 消息报文
     */
     void send(@Header(MqttHeaders.TOPIC) String topic, String payload);

    /**
     * 指定topic和通道 进行消息发送
     *
     * @param topic   主题
     * @param qos     对消息处理的几种机制。
     *                0 表示的是订阅者没收到消息不会再次发送，消息会丢失。
     *                1 表示的是会尝试重试，一直到接收到消息，但这种情况可能导致订阅者收到多次重复消息。
     *                2 多了一次去重的动作，确保订阅者收到的消息有一次。
     * @param payload 消息报文
     */
     void send(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);
}
