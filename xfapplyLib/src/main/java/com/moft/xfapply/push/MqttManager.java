package com.moft.xfapply.push;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.utils.LogUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;

/**
 * Created by cuihaijun on 2017/9/28.
 */

public class MqttManager {
    private static final String TAG = PushService.class.getName();

    private Context mContext;
    private IMqttEventListener listener;
    private ConnectionFactory factory;
    private Connection conn;
    private String mClientId;
    private boolean isConnected;

    private static MqttManager instance = null;
    private final int MSG_ARRIVED = 1;
    private final int MSG_DISCONNECT_LOST = 2;
    private final int MSG_CONNECT_SUCCESS = 3;
    private final int MSG_RECONNECTED = 4;
    private final int MSG_CONNECT_FAILURE = 5;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case MSG_ARRIVED:
                    if(listener != null) {
                        listener.onMessageArrived((String)msg.obj);
                    }
                    break;
                case MSG_CONNECT_SUCCESS:
                    isConnected = true;
                    if(listener != null) {
                        listener.onConnectSuccess();
                    }
                    break;
                case MSG_CONNECT_FAILURE:
                    isConnected = false;
                    if(listener != null) {
                        listener.onConnectFailure();
                    }
                    break;
                case MSG_DISCONNECT_LOST:
                    if(listener != null) {
                        listener.onConnectionLost();
                    }
                    break;
                case MSG_RECONNECTED:
                    if(listener != null) {
                        listener.onConnectSuccess();
                    }
                    break;
                default:
                    break;
            }
        };
    };

    public static synchronized MqttManager getInstance() {
        if(instance == null) {
            instance = new MqttManager();
        }

        return instance;
    }

    private MqttManager() {
        mContext = LvApplication.getInstance();
        isConnected = false;
    }

    public void initMqttClient(IMqttEventListener listener, String clientId) {
        mClientId = clientId;
        if (mClientId == null || mClientId.equalsIgnoreCase("")) {
            return;
        }
        factory = new ConnectionFactory();
        factory.setUsername(Constant.RABBIT_MQ_USERNAME);
        factory.setPassword(Constant.RABBIT_MQ_PASSWORD);
//        factory.setVirtualHost(virtualHost);
        factory.setHost(Constant.RABBIT_MQ_IP);
        factory.setPort(Constant.RABBIT_MQ_PORT);

        setMqttEventListener(listener);
    }

    public synchronized void connectMqttServer() {
        LogUtils.d("doConnectMqttServer ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    conn = factory.newConnection();
                    final Channel channel = conn.createChannel();
                    final String queueName = "mq" + mClientId;
                    // 声明队列
                    channel.queueDeclare(queueName, false, false, false, null);

                    // 绑定队列到交换机
                    channel.queueBind(queueName, Constant.RABBIT_MQ_EXCHANGER, mClientId);
                    channel.basicConsume(queueName, false, "myTag", new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            String routingKey = envelope.getRoutingKey();
                            String contentType = properties.getContentType();
                            long deliveryTag = envelope.getDeliveryTag();
                            String content = new String(body);
                            channel.basicAck(deliveryTag, false);
                            Message msg = Message.obtain();
                            msg.what = MSG_ARRIVED;
                            msg.obj = content;
                            handler.sendMessage(msg);
                        }
                    });

                    channel.addReturnListener(new ReturnListener() {
                        @Override
                        public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            LogUtils.d(String.format("handleReturn exchange = [%s], routingKey = [%s]", exchange, routingKey));
                        }
                    });
                    channel.addShutdownListener(new ShutdownListener() {
                        @Override
                        public void shutdownCompleted(ShutdownSignalException cause) {
                            LogUtils.d("shutdownCompleted: " + cause.getMessage());
                            //isConnected = false;
                            handler.sendEmptyMessage(MSG_DISCONNECT_LOST);
                        }
                    });
                    ((Recoverable) channel).addRecoveryListener(new RecoveryListener() {
                        @Override
                        public void handleRecovery(Recoverable recoverable) {
                            LogUtils.d("handleRecovery");
                        }

                        @Override
                        public void handleRecoveryStarted(Recoverable recoverable) {
                            LogUtils.d("handleRecoveryStarted");
                            //isConnected = true;
                            handler.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                        }
                    });
                    handler.sendEmptyMessage(MSG_CONNECT_SUCCESS);
                } catch (Exception ex) {
                    LogUtils.d("connectMqttServer Exception: " + ex.getMessage());
                    handler.sendEmptyMessage(MSG_CONNECT_FAILURE);
                }
            }
        }).start();
    }

    public synchronized void disconnectMqttServer() {
        LogUtils.d("disconnectMqttServer ");
        if(isConnected && conn != null) {
            try {
                conn.close();
                isConnected = false;
            } catch (IOException e) {
                LogUtils.d("disconnectMqttServer: " + e.getMessage());
            }
        }

    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setMqttEventListener(IMqttEventListener listener) {
        this.listener = listener;
    }
}
