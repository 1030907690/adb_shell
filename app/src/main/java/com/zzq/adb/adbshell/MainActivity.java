package com.zzq.adb.adbshell;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private Button newsInfoBut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        Toast.makeText(getApplicationContext(), "xxxxxxxxxxxx!", Toast.LENGTH_SHORT).show();
        listener();
    }


    /**
     * zhouzhongqing
     * 2019年6月10日10:21:22
     * 初始化视图
     **/
    private void initializeView() {
        this.newsInfoBut = findViewById(R.id.button1);

    }
    /** * 执行shell命令 * * @param cmd */
    private void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private Handler handlerNewsInfo = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    //更新UI操作
                    try {
                        //向下滑动
                        String adb_command_swipe = "input keyevent 4";
                        execShellCmd(adb_command_swipe);
                        Thread.sleep(1000);
                        Toast.makeText(getApplicationContext(), "已经点击!", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        };
    };

    private void listener() {
        newsInfoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "点击了新闻!", Toast.LENGTH_SHORT).show();

                new Thread() {

                    @Override
                    public void run() {
                        try {
                            for (int i = 20; i > 0; i--) {
                                Thread.sleep(1000);
                                Log.d("ces", "倒计时" + i);
                            }


                            handlerNewsInfo.sendEmptyMessage(0);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();


            }
        });
    }


    public static void sendKeyEvent(final int KeyCode) {
        new Thread() {     //不可在主线程中调用
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }
}
