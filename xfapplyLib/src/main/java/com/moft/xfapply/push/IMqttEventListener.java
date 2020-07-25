package com.moft.xfapply.push;

//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.IMqttToken;
//import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by cuihaijun on 2017/9/28.
 */

public interface IMqttEventListener {
    public void onConnectionLost();
    public void onMessageArrived(String s);

    public void onConnectSuccess();
    public void onConnectFailure();
}
