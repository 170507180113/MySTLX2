package cn.edu.sdwu.android.home.sn170507180113.mystlx2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ch15Activity1 extends AppCompatActivity {
    private ContentResolver contentResolver;
    private static final String CONTENT_URI_STRING="content://com.inspur.android02/student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_15_1);
        contentResolver=getContentResolver();
    }

    public void query(View view){
        String[] selColumns={"id","stuname","stuadd","stutel"};
        Uri uri= Uri.parse(CONTENT_URI_STRING);
        Cursor cursor=contentResolver.query(uri,selColumns,null,null,null);
        while(cursor.moveToNext()){
            Log.i(Ch15Activity1.class.toString(),cursor.getString(cursor.getColumnIndex("stuname"))+cursor.getString(cursor.getColumnIndex("id")));
        }
        cursor.close();

    }

    public void insert(View view){
        ContentValues contentValues=new ContentValues();
        contentValues.put("stuname","Tom");
        contentValues.put("stutel","109110120");
        contentValues.put("stuadd","add");
        contentResolver.insert(Uri.parse(CONTENT_URI_STRING),contentValues);
    }

    public void delete(View view){
        Uri uri=Uri.parse(CONTENT_URI_STRING+"/2");
        contentResolver.delete(uri,null,null);
    }

    public void modify(View view){
        ContentValues contentValues=new ContentValues();
        contentValues.put("stuname","jack");

        Uri uri=Uri.parse(CONTENT_URI_STRING+"/3");
        contentResolver.update(uri,contentValues,null,null);
    }




}