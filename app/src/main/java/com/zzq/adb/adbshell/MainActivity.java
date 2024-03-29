package com.zzq.adb.adbshell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button newsInfoBut;

    private Button videoBut;

    private Button bookBut;

    private Button stopBtn;

    private final String command = "command";

    private final String permissionDenied = "Permission denied";

    private volatile boolean isRuning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        Toast.makeText(getApplicationContext(), "进入app!", Toast.LENGTH_SHORT).show();
        listener();
    }


    /**
     * zhouzhongqing
     * 2019年6月10日10:21:22
     * 初始化视图
     **/
    private void initializeView() {
        this.newsInfoBut = findViewById(R.id.button1);
        this.videoBut = findViewById(R.id.button2);
        this.bookBut = findViewById(R.id.button3);
        this.stopBtn = findViewById(R.id.stopAll);
    }

    /**
     * 执行shell命令 * * @param cmd
     */
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
            if (null != t.getMessage() && t.getMessage().contains(permissionDenied)) {
                Toast.makeText(getApplicationContext(), "抱歉没有权限执行 : " + cmd, Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();
        }
    }


    private Handler genericHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    //更新UI操作
                    try {
                        //返回
                       /* String adb_command_back = "input keyevent 4";
                        //execShellCmd(adb_command_swipe);
                        Runtime.getRuntime().exec(adb_command_back);
                        Toast.makeText(getApplicationContext(), "已经点击返回!", Toast.LENGTH_SHORT).show();*/

                        String command = msg.getData().getString("command");
                        //Toast.makeText(getApplicationContext(), "command : "+ command, Toast.LENGTH_SHORT).show();

                        execShellCmd(command);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    //倒计时
                    Bundle bundle = msg.getData();
                    Toast.makeText(getApplicationContext(), "倒计时" + bundle.getInt("time") + "秒!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };


    private void waitComplete() throws Exception {

        for (int i = 20; i > 0; i--) {
            Thread.sleep(1000);
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("time", i);
            msg.setData(bundle);
            msg.what = 0;
            genericHandler.sendMessage(msg);
            Log.d("ces", "倒计时" + i);
        }

    }

    private void listener() {
        newsInfoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {

                    @Override
                    public void run() {
                        try {

                            // 向下滑动随机
                            String adb_command_swipe_random = " input swipe 100 700 100 %s";

                            // 向下滑动
                            String adb_command_swipe = "input swipe 100 700 100 %s";


                            //返回
                            String adb_command_back = "input keyevent 4";


                            //点击
                            String adb_command_click = "input tap 200 300";

                            waitComplete();

                            while (isRuning) {

                                Message msg = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString(command, adb_command_click);
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);

                                int randomSwipe = randInt(10, 20);
                                for (int i = 0; i < randomSwipe; i++) {
                                    Thread.sleep(1000);
                                    int swipe_random = randInt(350, 450);

                                    msg = new Message();
                                    bundle = new Bundle();
                                    bundle.putString(command, String.format(adb_command_swipe_random, swipe_random));
                                    msg.setData(bundle);
                                    msg.what = 1;
                                    genericHandler.sendMessage(msg);
                                }


                                msg = new Message();
                                bundle = new Bundle();
                                bundle.putString(command, adb_command_back);
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);
                                // 确保退出再点一次退出
                                Thread.sleep(2000);
                                msg = new Message();
                                bundle = new Bundle();
                                bundle.putString(command, adb_command_back);
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);
                                //等2秒再强制退出
                                Thread.sleep(2000);
                                msg = new Message();
                                bundle = new Bundle();
                                bundle.putString(command, adb_command_back);
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);
                                //再往下滑动
                                int swipe_random_back = randInt(400, 450);
                                msg = new Message();
                                bundle = new Bundle();
                                bundle.putString(command, String.format(adb_command_swipe, swipe_random_back));
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();


            }
        });


        videoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            waitComplete();

                            while (isRuning) {
                                int loopCount = randInt(10, 20);
                                for (int i = 0; i < loopCount; i++) {
                                    Thread.sleep(1000);
                                }

                                // 向下滑动随机
                                String adb_command_swipe_random = " input swipe 100 700 100 %s";

                                // 返回
                                String adb_command_back = "input keyevent 4";


                                int swipe_random = randInt(350, 450);
                                Message msg = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString(command, String.format(adb_command_swipe_random, swipe_random));
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);


                                // 尝试退出
                                // 返回
                                msg = new Message();
                                bundle = new Bundle();
                                bundle.putString(command, adb_command_back);
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);
                                //确保退出再点一次退出
                                Thread.sleep(2000);
                                msg = new Message();
                                bundle = new Bundle();
                                bundle.putString(command, adb_command_back);
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);
                                // 等2秒再强制退出
                                Thread.sleep(2000);
                                msg = new Message();
                                bundle = new Bundle();
                                bundle.putString(command, adb_command_back);
                                msg.setData(bundle);
                                msg.what = 1;
                                genericHandler.sendMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });


        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRuning = false;
            }
        });
    }


    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
