package com.moft.xfapply.logic.location;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class DirectionSensor {
	private Context mContext = null;
    private OnDirectionListener listener = null;
    
    private SensorManager sm;
    private Sensor aSensor;
    private Sensor mSensor;
    float[] accelerometerValues = new float[3];
    float[] magneticFieldValues = new float[3]; 
    
	public DirectionSensor(Context ct, OnDirectionListener listener) {
		mContext = ct;
		this.listener = listener;
	}
		
	public void start() {        
        sm = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);  
        aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  
        mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);  
        sm.registerListener(myListener, aSensor, SensorManager.SENSOR_DELAY_NORMAL);  
        sm.registerListener(myListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);         
        
		return;
	}
	
	final SensorEventListener myListener = new SensorEventListener() {   
	    public void onSensorChanged(SensorEvent sensorEvent) {   
	        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)   
	            magneticFieldValues = sensorEvent.values;   
	        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)    
	            accelerometerValues = sensorEvent.values;   
	        calculateOrientation();   
	    }   
	    
	    public void onAccuracyChanged(Sensor sensor, int accuracy) {	        
	    }   
	};  
	
	private void calculateOrientation() {
	    float[] values = new float[3];
	    float[] R = new float[9];
	    SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
	    SensorManager.getOrientation(R, values);
	    
	    String direction = "";

	    values[0] = (float) Math.toDegrees(values[0]);
	    if(values[0] >= -5 && values[0] < 5){
	        direction = "正北";
	    } else if(values[0] >= 5 && values[0] < 85){
	        direction = "东北";
	    } else if(values[0] >= 85 && values[0] <=95){
	        direction = "正东";
	    } else if(values[0] >= 95 && values[0] <175){
	        direction = "东南";
	    } else if((values[0] >= 175 && values[0] <= 180) || (values[0]) >= -180 && values[0] < -175){
	        direction = "正南";
	    } else if(values[0] >= -175 && values[0] <-95){
	        direction = "西南";
	    } else if(values[0] >= -95 && values[0] < -85){
	        direction = "正西";
	    } else if(values[0] >= -85 && values[0] <-5){ 
	        direction = "西北";
	    }
	    
	    if (listener != null) {
	        listener.onResult(direction, "" + (int)values[0], "");
	    }
	    
	    return;
	}
	
	public void stop(){
        if(sm != null){
            sm.unregisterListener(myListener);  
            sm = null;
        }
	}
	
    public interface OnDirectionListener{
        void onResult(String direction, String degree, String acc);
    }
}
