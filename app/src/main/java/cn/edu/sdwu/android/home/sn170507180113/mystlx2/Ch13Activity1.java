package cn.edu.sdwu.android.home.sn170507180113.mystlx2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ch13Activity1 extends AppCompatActivity {
    private EditText ip;
    private EditText port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch13_1);
        ip=(EditText)findViewById(R.id.ch13_1_ip);
        port=(EditText)findViewById(R.id.ch13_1_port);

        SharedPreferences sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
        ip.setText(sharedPreferences.getString("ip",""));
        port.setText(sharedPreferences.getString("port",""));
    }
    public void write(View view){
        EditText editText=(EditText)findViewById(R.id.ch13_1_et);
        String content=editText.getText().toString();
        try{
            FileOutputStream fileOutputStream=openFileOutput("android02.txt",MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (Exception e){
            Log.e(Ch13Activity1.class.toString(),e.toString());
        }
    }
    public void read(View view){
        try{
            FileInputStream fileInputStream=openFileInput("android02.txt");

            int size=fileInputStream.available();//获取文件的大小
            byte[] bytes=new byte[size];
            fileInputStream.read(bytes);
            String content=new String(bytes);

            fileInputStream.close();
            Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(Ch13Activity1.class.toString(),e.toString());
        }
    }

    public void saveSharePref(View view){
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("ip",ip.getText().toString());
        editor.putString("port",port.getText().toString());
        editor.commit();
    }

    //3.接收用户的授权结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==101){
            //4.如果用户同意，则进行下一步操作(写SD卡)
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)//如果是同意
            {
                EditText editText=(EditText)findViewById(R.id.ch13_1_et);
                String content=editText.getText().toString();
                writeExternal(content);
            }
        }
        if(requestCode==102){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)//如果是同意
            {
                //如果授权过 直接读取
                readExternal();
            }
        }
    }

    public void writeSd(View view){
        EditText editText=(EditText)findViewById(R.id.ch13_1_et);
        String content=editText.getText().toString();
        //对于6.0之后的系统，用户需要在运行时进行动态授权
        //动态授权的过程，一般分为：1.判断当前用户是否已经授权过；
        //                          2.如果尚未授权，弹出动态授权的对话框(同意或拒绝)
        //                          3.接收用户的授权结果
        //                          4.如果用户同意，则进行下一步操作(写SD卡)

        //判断当前用户手机系统版本，是否是6.0之后的
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //1.判断当前用户是否已经授权过；
            int result=checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(result== PackageManager.PERMISSION_GRANTED){
                writeExternal(content);
            }else{
                //2.如果尚未授权，弹出动态授权的对话框(同意或拒绝)
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
            }
        }else{
            writeExternal(content);
        }
    }

    private void writeExternal(String content){
        //写入外部存储
        //得到FileOutputStream的方法，与内部存储不同
        FileOutputStream fileOutputStream=null;
        //创建File对象
        File file=new File(Environment.getExternalStorageDirectory(),"abcde.txt");//构造方法中，提供文件所在的目录名和文件名
        try{
            //使用createNewFile创建文件
            file.createNewFile();
            //判断文件是否存在，是否可写
            if(file.exists()&&file.canWrite()){
                fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(content.getBytes());
            }
        }catch (Exception e){
            Log.e(Ch13Activity1.class.toString(),e.toString());
        }
        if(fileOutputStream!=null){
            try{
                fileOutputStream.flush();
                fileOutputStream.close();
            }catch (Exception e){
                Log.e(Ch13Activity1.class.toString(),e.toString());
            }
        }
    }

    public void readSd(View view){
        EditText editText=(EditText)findViewById(R.id.ch13_1_et);
        String content=editText.getText().toString();
        //对于6.0之后的系统，用户需要在运行时进行动态授权
        //动态授权的过程，一般分为：1.判断当前用户是否已经授权过；
        //                          2.如果尚未授权，弹出动态授权的对话框(同意或拒绝)
        //                          3.接收用户的授权结果
        //                          4.如果用户同意，则进行下一步操作(写SD卡)

        //判断当前用户手机系统版本，是否是6.0之后的
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //1.判断当前用户是否已经授权过；
            int result=checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            if(result== PackageManager.PERMISSION_GRANTED){
                readExternal();
            }else{
                //2.如果尚未授权，弹出动态授权的对话框(同意或拒绝)
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},102);
            }
        }else{
            readExternal();
        }
    }

    private void readExternal(){
        File file=new File(Environment.getExternalStorageDirectory(),"abcde.txt");//构造方法中，提供文件所在的目录名和文件名
        FileInputStream fileInputStream=null;
        try{
            if(file.exists()&&file.canRead()){
                fileInputStream=new FileInputStream(file);
                int size=fileInputStream.available();
                byte[] bytes=new byte[size];
                fileInputStream.read(bytes);
                String content=new String(bytes);
                Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.e(Ch13Activity1.class.toString(),e.toString());
        }finally {
            try{
                fileInputStream.close();
            }catch (Exception e){
                Log.e(Ch13Activity1.class.toString(),e.toString());
            }
        }
    }

    public void readRaw(View view){
        Resources resources=getResources();
        InputStream inputStream=resources.openRawResource(R.raw.readme);
        try{
            int size=inputStream.available();
            byte[] bytes=new byte[size];
            inputStream.read(bytes);
            String content=new String(bytes);
            Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(Ch13Activity1.class.toString(),e.toString());
        }finally {
            try{
                inputStream.close();
            }catch (Exception e){
                Log.e(Ch13Activity1.class.toString(),e.toString());
            }
        }
    }

}
