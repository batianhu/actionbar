package com.example.weijunpeng.infiniteindicatorlayout;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Long curTime;//当前时间
    private Long lastTime = 0l;//上次时间
    private Long duration;//当前上次差值
    private Float last_x = 0.0f;
    private Float last_y = 0.0f;
    private Float last_z = 0.0f;
    private Long initTime;
    private Float shake = 0.0f;
    private Float totalShake = 0.0f;
    private int sensorType;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorType = android.hardware.Sensor.TYPE_ACCELEROMETER;

        //sensorManager.registerListener(mySensorEventListener, sensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_FASTEST);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_drawer_login_zhihu);
        actionBar.setDisplayUseLogoEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case R.id.fund:
                Toast.makeText(this,"搜索",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.ring:
                Toast.makeText(this,"通知",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_settings:
                Toast.makeText(this,"设置",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public final SensorEventListener mySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(android.hardware.Sensor sensor,
                                      int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if (event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER) {
                // 获取加速度传感器的三个参数
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                // 获取当前时刻的毫秒数
                curTime = System.currentTimeMillis();
                // 200毫秒检测一次
                float aa = curTime - lastTime;
                if ((curTime - lastTime) > 50) {
                    duration = (curTime - lastTime);
                    // 看是不是刚开始晃动
                    if (last_x == 0.0f && last_y == 0.0f && last_z == 0.0f) {
                        // last_x、last_y、last_z同时为0时，表示刚刚开始记录
                        initTime = System.currentTimeMillis();
                    } else {
                        // 单次晃动幅度
                        shake = (Math.abs(x - last_x) + Math.abs(y - last_y) + Math.abs(z - last_z));
                        // / duration * 1000;
                        System.out.println(Math.abs(x - last_x));
                    }
                    // 把每次的晃动幅度相加，得到总体晃动幅度
                    totalShake += shake;
                    // 判断是否为摇动
                    if (shake > 20) {
                        //action();
                        //onVibrator();
                        //initShake();
                    }
                    last_x = x;
                    last_y = y;
                    last_z = z;
                    lastTime = curTime;
                }
            }
        }
    };

    private void onVibrator() {
        Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator == null) {
            Vibrator localVibrator = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator = localVibrator;
        }
        long[] pattern = {100, 400, 100, 400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, 2);
    }
}
