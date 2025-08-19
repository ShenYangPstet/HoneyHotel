package com.photonstudio.mqtt.config;

import static com.photonstudio.mqtt.constant.MqttConstant.MQTT_INPUT_CHANNEL;
import static com.photonstudio.mqtt.constant.MqttConstant.MQTT_OUTBOUND_CHANNEL;

import java.security.SecureRandom;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.event.MqttMessageDeliveredEvent;
import org.springframework.integration.mqtt.event.MqttMessageSentEvent;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class MqttConfig {

  private final MQQTProperties mqttProperties;

  /** 创建MqttPahoClientFactory 设置MQTT Broker连接属性 */
  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
    // 创建MqttPahoClientFactory客户端工厂，用来创建MQTT客户端
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    // 设置要用于连接的用户名
    options.setUserName(mqttProperties.getUsername());
    // 设置用于连接的密码
    options.setPassword(mqttProperties.getPassword().toCharArray());
    // 设置“保持活动状态”间隔
    options.setKeepAliveInterval(mqttProperties.getKeepalive());
    // 设置如果连接丢失，客户端是否自动尝试重新连接到服务器
    options.setAutomaticReconnect(true);
    // 设置连接超时值
    options.setConnectionTimeout(mqttProperties.getTimeout());
    // 设置“最大飞行时间”。请在高流量环境中增加此值
    options.setMaxInflight(1000000);
    // 多个服务器地址时处理
    options.setServerURIs(mqttProperties.getHostUrl().split(","));
    factory.setConnectionOptions(options);
    return factory;
  }

  /**
   * 出站消息通道
   *
   * @return 消息通道
   */
  @Bean
  public MessageChannel mqttOutboundChannel() {
    return new DirectChannel();
  }

  /**
   * 消息生产者 默认主题
   *
   * @return 消息处理程序
   */
  @Bean
  @ServiceActivator(inputChannel = MQTT_OUTBOUND_CHANNEL)
  public MessageHandler mqttOutbound() {
    // clientId每个连接必须唯一,否则,两个相同的clientId相互挤掉线
    String clientIdStr = mqttProperties.getClientId() + new SecureRandom().nextInt(10);
    MqttPahoMessageHandler messageHandler =
        new MqttPahoMessageHandler(clientIdStr, mqttClientFactory());
    // 设置默认主题
    messageHandler.setDefaultTopic(mqttProperties.getDefaultTopic());
    // 设置异步  async如果为true，则调用方不会阻塞。而是在发送消息时等待传递确认。默认值为false（发送将阻塞，直到确认发送)
    messageHandler.setAsync(true);
    // 设置异步事件
    messageHandler.setAsyncEvents(true);
    messageHandler.setDefaultQos(0);
    return messageHandler;
  }

  /**
   * 当async和async事件(async - events)都为true时, 将发出MqttMessageSentEvent
   * 它包含消息、主题、客户端库生成的消息id、clientId和clientInstance（每次连接客户端时递增）
   */
  @EventListener(MqttMessageSentEvent.class)
  public void mqttMessageSentEvent(MqttMessageSentEvent event) {
    log.info("发送信息: info={}", event.toString());
  }

  /**
   * 当async和async事件(async - events)都为true时, 将发出MqttMessageDeliveredEvent
   * 当客户端确认传递时，将发出MqttMessageDeliveredEvent 它包含messageId、clientId和clientInstance，使传递与发送相关。
   */
  @EventListener(MqttMessageDeliveredEvent.class)
  public void mqttMessageDeliveredEvent(MqttMessageDeliveredEvent event) {
    log.info("发送成功信息:  info={}", event.toString());
  }

  /**
   * 入站消息通道(收到的消息)
   *
   * @return 消息通道
   */
  @Bean
  public MessageChannel mqttInputChannel() {
    return new DirectChannel();
  }

  /** 配置client,监听的topic */
  @Bean
  public MessageProducer inbound() {
    // clientId每个连接必须唯一,否则,两个相同的clientId相互挤掉线
    String serverIdStr = mqttProperties.getClientId() + UUID.randomUUID();
    // MQTT 卫生消息驱动通道适配器
    MqttPahoMessageDrivenChannelAdapter adapter =
        new MqttPahoMessageDrivenChannelAdapter(
            serverIdStr, mqttClientFactory(), mqttProperties.getDefaultTopic());
    // 设置转换器
    adapter.setConverter(new DefaultPahoMessageConverter());
    // 设置完成超时
    adapter.setCompletionTimeout(mqttProperties.getTimeout());
    // 设置服务质量
    adapter.setQos(0);
    // 设置输出通道
    adapter.setOutputChannel(mqttInputChannel());
    return adapter;
  }

  /**
   * 处理接收到的消息
   *
   * @return 接收客户端发来的的消息
   */
  @Bean
  @ServiceActivator(inputChannel = MQTT_INPUT_CHANNEL)
  public MessageHandler handler() {
    return message -> {
      MqttDeliveryToken token = new MqttDeliveryToken();
      String payload = message.getPayload().toString();
      String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
      // 处理订阅到的所有的数据
      System.out.println(payload);
    };
  }

  /**
   * @apiNote 成功订阅到主题
   */
  @EventListener(MqttSubscribedEvent.class)
  public void mqttSubscribedEvent(MqttSubscribedEvent event) {
    log.info("成功订阅到主题:  info={}", event.toString());
  }
}
