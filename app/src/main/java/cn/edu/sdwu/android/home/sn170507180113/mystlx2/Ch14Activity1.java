package cn.edu.sdwu.android.home.sn170507180113.mystlx2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ch14Activity1 extends AppCompatActivity {
    private MyOpenHelper myOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch14_1);

        myOpenHelper=new MyOpenHelper(this);
    }

    public void insert(View view){
        //以可写方法打开数据库(如果数据库不存在，自动创建数据库)
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            //事务处理
            sqLiteDatabase.beginTransaction();//开启事务
            //将插入的数据放置在ContentValues中
            ContentValues contentValues=new ContentValues();
            contentValues.put("stuname","Tom");
            contentValues.put("stutel","133122144");
            sqLiteDatabase.insert("student",null,contentValues);
            sqLiteDatabase.setTransactionSuccessful();//所有操作结束后，调用setTransactionSuccessful方法，才会将数据保存到数据库中
        }catch (Exception e){
            Log.e(Ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事务
            //使用完毕，关闭数据库
            sqLiteDatabase.close();
        }
    }
    public void query(View view){
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getReadableDatabase();//只读方式打开
        try{
            Cursor cursor=sqLiteDatabase.rawQuery("select * from student where stuname=?",new String[]{"Tom"});
            while(cursor.moveToNext()){
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String stuname=cursor.getString(cursor.getColumnIndex("stuname"));
                String stutel=cursor.getString(cursor.getColumnIndex("stutel"));
                Log.i(Ch14Activity1.class.toString(),"id:"+id+",stuname:"+stuname+",stutel:"+stutel);
            }
            cursor.close();
        }catch (Exception e){
            Log.e(Ch14Activity1.class.toString(),e.toString());
        }finally {
            //使用完毕，关闭数据库
            sqLiteDatabase.close();
        }
    }

    public void delete(View view){
        //以可写方法打开数据库(如果数据库不存在，自动创建数据库)
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            //事务处理
            sqLiteDatabase.beginTransaction();//开启事务
            sqLiteDatabase.delete("student","id=?",new String[]{"1"});//删除id=1的数据
            sqLiteDatabase.setTransactionSuccessful();//所有操作结束后，调用setTransactionSuccessful方法，才会将数据保存到数据库中
        }catch (Exception e){
            Log.e(Ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事务
            //使用完毕，关闭数据库
            sqLiteDatabase.close();
        }
    }
    public void modify(View view){
        //以可写方法打开数据库(如果数据库不存在，自动创建数据库)
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            //事务处理
            sqLiteDatabase.beginTransaction();//开启事务
            ContentValues contentValues=new ContentValues();
            contentValues.put("stutel","133222222");
            sqLiteDatabase.update("student",contentValues,"id=?",new String[]{"2"});
            sqLiteDatabase.setTransactionSuccessful();//所有操作结束后，调用setTransactionSuccessful方法，才会将数据保存到数据库中
        }catch (Exception e){
            Log.e(Ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事务
            //使用完毕，关闭数据库
            sqLiteDatabase.close();
        }
    }

}