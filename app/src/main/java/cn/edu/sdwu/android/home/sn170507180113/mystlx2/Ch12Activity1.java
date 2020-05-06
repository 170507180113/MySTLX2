package cn.edu.sdwu.android.home.sn170507180113.mystlx2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ch12Activity1 extends AppCompatActivity {
    private ServiceConnection serviceConnection;
    private MyService2 myService2;
    private boolean bindSucc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch12_1);
        bindSucc=false;
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //当调用者与服务建立起连接后，会自动调用本方法
                //第二个参数，是service中onBind方法的返回值
                MyService2.MyBinder myBinder=(MyService2.MyBinder)iBinder;
                myService2=myBinder.getRandomService();
                bindSucc=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //当调用者与服务断开连接后，会自动调用本方法
                bindSucc=false;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,MyService2.class);
        //绑定服务
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解绑
        unbindService(serviceConnection);
    }

    public void start_service(View view){
        //使用intent启动服务
        Intent intent=new Intent(this,MyService.class);
        //使用startService以启动方式使用服务
        startService(intent);
    }
    public void stop_service(View view){
        //停止服务
        Intent intent=new Intent(this,MyService.class);
        stopService(intent);
    }
    public void getRandom(View view){
        if(bindSucc){
            int ran=myService2.getRandom();
            Toast.makeText(this,ran+"",Toast.LENGTH_SHORT).show();
        }
    }
}
