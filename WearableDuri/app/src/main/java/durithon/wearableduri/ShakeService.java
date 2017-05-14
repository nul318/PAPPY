package durithon.wearableduri;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class ShakeService extends Service implements SensorEventListener {
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;
    int SHAKE_THRESHOLD = 1000;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;
    SensorManager sensorManager;
    Sensor accelerormeterSensor;
    Handler handler = new Handler();

    String sender_id;
    String sender_name;
    String user_id;
    int cnt = 0;

    // 서비스를 생성할 때 호출
    public void onCreate() {
        super.onCreate();
        Log.i("ShakeService", "Shake Service 시작");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
//    public void onStart(Intent intent, int startId) {
//        // Log.e("MyService", "Service startId = " + startId);
//        super.onStart(intent, startId);
//        if (accelerormeterSensor != null)
//            sensorManager.registerListener(this, accelerormeterSensor,
//                    SensorManager.SENSOR_DELAY_GAME);
//    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];
                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime
                        * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    Log.e("걸음 수", String.valueOf(cnt));
                    MainActivity.netty.sendmessage("a"+MainActivity.ascii+String.valueOf(cnt));
                    cnt++;
//                    MusicListUtil.nextSong(this);
//                    if(MusicListUtil.current_check) {
//                        MusicListUtil.stopSong();
//                    }
//                    else {
//                        MusicListUtil.r   esumeSong();
//                    }
                }
                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);

        return startId;
    }


}