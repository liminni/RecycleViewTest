package com.lixiaoming.recycleviewtest.voice;

import android.content.ContentValues;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;

import java.io.File;
import java.io.IOException;

public class MediaRecorderActivity extends AppCompatActivity implements View.OnClickListener {
    // private TextView stateView, tv_time;

    private TextView tvCancle, tvConfirm;

    private MediaRecorder recorder;

    private MediaPlayer player;

    private File audioFile;

    private Uri fileUri;

    private ProgressBar progressBar;

    /** 判断是否点击了【开始录制】 */
    private boolean isStartRecode = false;

    private Task task = new Task();

    private Context mContext;

    private LinearLayout ll_startRecord, ll_stopRecord;

    private LinearLayout ll_startPlay, ll_stopPlay;

    private ImageView iv_start, iv_stop;

    private ImageView iv_play, iv_finish;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 1000:
                recode_time_for_stop = mCurrentProgress * 100;
                recodeTime = mCurrentProgress / 10;
                break;
            case 1001:
                int current1001 = mCurrentProgress / 10;
                if (current1001 < 10) {
                    // tv_time.setText("00:0" + current1001);
                } else {
                    // tv_time.setText("00:" + current1001);
                }
                break;
            case 1002:
                Log.d("fate", "捕获到中断异常");
                recode_time_for_stop = mCurrentProgress * 100;
                recodeTime = mCurrentProgress / 10;
                break;
            case 2000:// 正常播放完毕
                break;
            case 2001:
                int current2001 = mCurrentProgress / 10;
                if (current2001 < 10) {
                    // tv_recode_time.setText("00:0" + current2001);
                    // tv_time.setText("00:0" + current2001);
                } else {
                    // tv_recode_time.setText("00:" + current2001);
                    // tv_time.setText("00:" + current2001);
                }
                break;
            case 2002:
                Log.d("fate", "捕获到中断异常");
                break;
            }
        }
    };

    // private LinearLayout recode_time_ll;

    // private TextView tv_recode_time;

    private String base64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);
        mContext = this;
        initView();
    }

    private void initView() {
        // stateView = (TextView) this.findViewById(R.id.view_state);
        // stateView.setText("准备开始,限时10s");
        // tvStart = (TextView) this.findViewById(R.id.tv_start);
        // tvStop = (TextView) this.findViewById(R.id.tv_stop);
        // tvPlay = (TextView) this.findViewById(R.id.tv_play);
        // tvFinish = (TextView) this.findViewById(R.id.tv_finish);

        ll_startRecord = ((LinearLayout) findViewById(R.id.ll_startRecord));
        ll_stopRecord = ((LinearLayout) findViewById(R.id.ll_stopRecord));
        ll_startPlay = ((LinearLayout) findViewById(R.id.ll_startPlay));
        ll_stopPlay = ((LinearLayout) findViewById(R.id.ll_stopPlay));
        iv_start = ((ImageView) findViewById(R.id.iv_start));
        iv_stop = ((ImageView) findViewById(R.id.iv_stop));
        iv_play = ((ImageView) findViewById(R.id.iv_play));
        iv_finish = ((ImageView) findViewById(R.id.iv_finish));

        tvCancle = ((TextView) this.findViewById(R.id.tv_cancle));
        tvConfirm = ((TextView) this.findViewById(R.id.tv_confirm));
        progressBar = ((ProgressBar) findViewById(R.id.progressBar));
        // tv_time = ((TextView) findViewById(tv_time));
        // recode_time_ll = (LinearLayout) findViewById(recode_time_ll);
        // recode_time_ll.setVisibility(View.GONE);
        // tv_recode_time = ((TextView) findViewById(R.id.tv_show_recode_time));

        // tvStop.setEnabled(false);
        // tvPlay.setEnabled(false);
        // tvFinish.setEnabled(false);
        ll_stopRecord.setVisibility(View.GONE);
        ll_stopPlay.setVisibility(View.GONE);
        iv_play.setEnabled(false);
        tvConfirm.setEnabled(false);

        // tvStart.setOnClickListener(this);
        // tvStop.setOnClickListener(this);
        // tvPlay.setOnClickListener(this);
        // tvFinish.setOnClickListener(this);

        iv_start.setOnClickListener(this);
        iv_stop.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_start:
            // 开始录制
            isStartRecode = true;
            startRecord();
            break;
        case R.id.iv_stop:
            // 停止录制
            stopRecord();
            break;
        case R.id.iv_play:
            // 开始播放
            startPlay();
            break;
        case R.id.iv_finish:
            // 停止播放
            stopPlay();
            break;
        case R.id.tv_cancle:
            progressBar.setProgress(0);
            finish();
            break;
        case R.id.tv_confirm:
            Toast.makeText(mContext, "上传录音文件", Toast.LENGTH_SHORT).show();

            if (isStartRecode) {// 点击过【开始录制】
                stopRecord();
                // TODO 给js返回录制后的base64
                try {
//                    base64 = Base64Util.ioToBase64(audioFile.getAbsolutePath());
                    Log.d("fate", "base64:" + base64);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {// 未点击过【开始录制】
                    // TODO 给js传错误信息
            }

            finish();

            break;
        }
    }

    private void stopPlay() {
        if (player != null && player.isPlaying()) {
            player.stop();// 停止
            playProgressThread.interrupt();
            ll_startRecord.setVisibility(View.VISIBLE);
            ll_startPlay.setVisibility(View.VISIBLE);
            ll_stopRecord.setVisibility(View.GONE);
            ll_stopPlay.setVisibility(View.GONE);

            iv_play.setEnabled(true);
            iv_start.setEnabled(true);
            tvCancle.setEnabled(true);
            tvConfirm.setEnabled(true);
        }
    }

    private void startPlay() {
        // 准备播放
        // recode_time_ll.setVisibility(View.GONE);
        try {
            player.reset();// 资源重置
            String fileString = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/data/files/player35.amr";
            try {
//                String base64Str = Base64Util.ioToBase64(audioFile.getAbsolutePath());
//                Base64Util.base64ToIo(base64Str, fileString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("fate", "fileString:" + fileString);
            player.setDataSource(fileString);// 设置播放的音频文件
            player.prepare();// 播放音乐前，必须执行prepare，表示准备播放音乐
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 播放录音---注意，我们在录音结束的时候，已经实例化了MediaPlayer，做好了播放的准备
        if (player != null && !player.isPlaying()) {
            player.start();
        }
        Log.d("fate", "");
        showPlayPregressBar();
        // 更新状态
        // stateView.setText("正在播放");
        ll_startRecord.setVisibility(View.VISIBLE);
        ll_stopRecord.setVisibility(View.GONE);
        ll_startPlay.setVisibility(View.GONE);
        ll_stopPlay.setVisibility(View.VISIBLE);

        iv_start.setEnabled(false);
        iv_stop.setEnabled(false);
        iv_play.setEnabled(false);
        iv_finish.setEnabled(true);
        tvCancle.setEnabled(true);
        tvConfirm.setEnabled(false);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // stateView.setText("准备录制");

                ll_stopRecord.setVisibility(View.GONE);
                ll_startRecord.setVisibility(View.VISIBLE);
                ll_startPlay.setVisibility(View.VISIBLE);
                ll_stopPlay.setVisibility(View.GONE);

                iv_play.setEnabled(true);
                iv_start.setEnabled(true);
                // iv_finish.setEnabled(true);
                // iv_stop.setEnabled(false);
                tvConfirm.setEnabled(true);
                tvCancle.setEnabled(true);
            }
        });
    }

    private void stopRecord() {
        try {

            recorder.stop();
            recorder.release();
            handler.removeCallbacks(task);// 停止计时器
            progressThread.interrupt();
            // 然后我们可以将我们的录制文件存储到MediaStore中
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.TITLE, "this is my first record-audio");
            values.put(MediaStore.Audio.Media.DATE_ADDED, System.currentTimeMillis());
            values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
            fileUri = this.getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
            // 录制结束后，我们实例化一个MediaPlayer对象，然后准备播放
            player = new MediaPlayer();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer arg0) {
                    // 更新状态
                    // stateView.setText("准备录制");

                    ll_stopRecord.setVisibility(View.GONE);
                    ll_startRecord.setVisibility(View.VISIBLE);
                    ll_startPlay.setVisibility(View.VISIBLE);
                    ll_stopPlay.setVisibility(View.GONE);

                    iv_play.setEnabled(true);
                    iv_start.setEnabled(true);
                    // iv_finish.setEnabled(true);
                    // iv_stop.setEnabled(false);
                    tvConfirm.setEnabled(true);
                    tvCancle.setEnabled(true);

                    // tvStart.setEnabled(true);
                    // tvPlay.setEnabled(false);
                    // tvStop.setEnabled(false);
                }
            });
            // 更新状态
            // stateView.setText("准备播放");

            ll_startRecord.setVisibility(View.VISIBLE);
            ll_stopRecord.setVisibility(View.GONE);
            ll_startPlay.setVisibility(View.VISIBLE);
            ll_stopPlay.setVisibility(View.GONE);

            iv_play.setEnabled(true);
            iv_start.setEnabled(true);
            // iv_stop.setEnabled(false);
            // iv_finish.setEnabled(true);
            tvConfirm.setEnabled(true);
            tvCancle.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startRecord() {
        // recode_time_ll.setVisibility(View.GONE);
        prepareRecorde();
        recorder.start();
        showPregressBar();
        handler.postDelayed(task, 10 * 1000);// 在开始录音的时候开启计时器
        // stateView.setText("正在录制");

        ll_stopRecord.setVisibility(View.VISIBLE);
        ll_startPlay.setVisibility(View.VISIBLE);
        ll_startRecord.setVisibility(View.GONE);
        ll_stopPlay.setVisibility(View.GONE);

        // iv_start.setEnabled(false);
        iv_play.setEnabled(false);
        iv_stop.setEnabled(true);
        // iv_finish.setEnabled(false);
        tvCancle.setEnabled(true);
        tvConfirm.setEnabled(false);
    }

    /** 准备录音 */
    private void prepareRecorde() {
        // 我们需要实例化一个MediaRecorder对象，然后进行相应的设置
        recorder = new MediaRecorder();
        // 指定AudioSource 为MIC(Microphone audio source ),这是最长用的
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 指定OutputFormat,我们选择3gp格式
        // 其他格式，MPEG-4:这将指定录制的文件为mpeg-4格式，可以保护Audio和Video
        // RAW_AMR:录制原始文件，这只支持音频录制，同时要求音频编码为AMR_NB
        // THREE_GPP:录制后文件是一个3gp文件，支持音频和视频录制
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        // 指定Audio编码方式，目前只有AMR_NB格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // 接下来我们需要指定录制后文件的存储路径
        File fpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/files/");
        fpath.mkdirs();// 创建文件夹
        try {
            // 创建临时文件
            audioFile = File.createTempFile("recording", ".amr", fpath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.setOutputFile(audioFile.getAbsolutePath());

        // 下面就开始录制了
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    /** ================================计时器========================================= **/
    class Task implements Runnable {
        @Override
        public void run() {// 停止录音
            stopRecord();
        }
    }

    /** ============================以下部分是进度条====================================== **/
    private int mTotalProgress;

    private int mCurrentProgress;

    private Thread progressThread;

    private Thread playProgressThread;

    private int recodeTime;

    private int recode_time_for_stop;

    public void showPregressBar() {
        mTotalProgress = 100;
        mCurrentProgress = 0;
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressThread = new Thread(new ProgressRunable());
        progressThread.start();
    }

    public void showPlayPregressBar() {
        mTotalProgress = 100;
        mCurrentProgress = 0;
        progressBar.setMax(100);
        progressBar.setProgress(0);
        playProgressThread = new Thread(new PlayProgressRunable());
        try {
            Thread.sleep(500);
            playProgressThread.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // 自动化100秒更新进度
    class ProgressRunable implements Runnable {
        @Override
        public void run() {

            while (mCurrentProgress < mTotalProgress) {
                try {
                    mCurrentProgress += 1;
                    progressBar.setProgress(mCurrentProgress);
                    handler.sendEmptyMessage(1001);
                    if (mCurrentProgress == mTotalProgress) {
                        handler.sendEmptyMessage(1000);
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(1002);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // 自动化100秒更新进度(播放进度条)
    class PlayProgressRunable implements Runnable {
        @Override
        public void run() {

            while (mCurrentProgress < mTotalProgress) {
                try {
                    mCurrentProgress += 1;
                    progressBar.setProgress(mCurrentProgress);
                    handler.sendEmptyMessage(2001);
                    if (mCurrentProgress == recode_time_for_stop / 100) {
                        playProgressThread.interrupt();
                        handler.sendEmptyMessage(2000);
                    }
                    Thread.sleep(recode_time_for_stop / 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(2002);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    String BASE64 = "IyFBTVIKPJEXFr5meeHgAeev8AAAAIAAAAAAAAAAAAAAAAAAAAA8SHcklmZ54eAB57rwAAAAwAAA\n"
            + "AAAAAAAAAAAAAAAAADxVAIi2Znnh4AHnz/AAAACAAAAAAAAAAAAAAAAAAAAAPEj5H5ZmeeHgAeeK\n"
            + "8AAAAMAAAAAAAAAAAAAAAAAAAAA8VP0ftmZ54eAB58/wAAAAgAAAAAAAAAAAAAAAAAAAADxI9R+W\n"
            + "Znnh4AHnivAAAADAAAAAAAAAAAAAAAAAAAAAPFT9H7ZmeeHgAefP8AAAAIAAAAAAAAAAAAAAAAAA\n"
            + "AAA8SPUflmZ54eAB54rwAAAAwAAAAAAAAAAAAAAAAAAAADxU/R+2Znnh4AHnz/AAAACAAAAAAAAA\n"
            + "AAAAAAAAAAAAPEj1H5ZmeeHgAeeK8AAAAMAAAAAAAAAAAAAAAAAAAAA8VP0ftmZ54eAB58/wAAAA\n"
            + "gAAAAAAAAAAAAAAAAAAAADxI9R+WZnnh4AHnivAAAADAAAAAAAAAAAAAAAAAAAAAPFT9H7ZmeeHg\n"
            + "AefP8AAAAIAAAAAAAAAAAAAAAAAAAAA8SPUflmZ54eAB54rwAAAAwAAAAAAAAAAAAAAAAAAAADxU\n"
            + "/R+2Znnh4AHnz/AAAACAAAAAAAAAAAAAAAAAAAAAPEj1H5ZmeeHgAeeK8AAAAMAAAAAAAAAAAAAA\n"
            + "AAAAAAA8VP0ftmZ54eAB58/wAAAAgAAAAAAAAAAAAAAAAAAAADxI9R+WZnnh4AHnivAAAADAAAAA\n"
            + "AAAAAAAAAAAAAAAAPFT9H7ZmeeHgAefP8AAAAIAAAAAAAAAAAAAAAAAAAAA8SPUflmZ54eAB54rw\n"
            + "AAAAwAAAAAAAAAAAAAAAAAAAADxU/R+2Znnh4AHnz/AAAACAAAAAAAAAAAAAAAAAAAAAPEj1H5Zm\n"
            + "eeHgAeeK8AAAAMAAAAAAAAAAAAAAAAAAAAA8VP0ftmZ54eAB58/wAAAAgAAAAAAAAAAAAAAAAAAA\n"
            + "ADxI9R+WZnnh4AHnivAAAADAAAAAAAAAAAAAAAAAAAAAPFT9H7ZmeeHgAefP8AAAAIAAAAAAAAAA\n"
            + "AAAAAAAAAAA8Bn5x4Pjxw8iPTXbXIyMDgABTQXk4PgQAB9NFDvKm0Dzed2e3dVaU5aGeClxzEI3Y\n"
            + "s/Bn0O+BzBVsMdNnRosQPDB/Ie18egjh76CamJ1YWGv+OnTwQqGeBsWJexCE1PA8UvljurDiAkH2\n"
            + "hSpUt7L/1ffKfZxhPD35icY2+4ARcDwSkSjmWXwQ4fbUes5NTduFrLefTiB2Kk8ajy/2aBhQPN56\n"
            + "i7LErEpluk74kKNRcMjfyAubz2oGUIJW7LBRhxA8IPh66grWASPdmxo4CSzM7ytdGvV7HVzmXUfz\n"
            + "wmw8oDzggR+/PbgFof+oulOAu7NhOsKJGOBwCKFC/C5XQXvQPCkFeef2OBhj1HR6jjJc7f6uD0aP\n"
            + "78T9vUyR/J1V66A83nR6tA3QCKH/wWrmupGpjkEgA88a7YuZVPp/Q7ZOQDzYd2bnUYoUsO5N+Ucq\n"
            + "m2ni7ZVeHAuCB5kTYnmhmevgPCDIe7fmoB3h/mJ5z6ebYN0dFANtZXpqfPOXmcXAjtA84Qd54FGA\n"
            + "GLSq1/kh/1VMtyR9n8a/blFuqDxit8+KsDzYyHu/LEwVgf97Kx/JMFtEc2RmfIElW+KcUD1X4t8Q\n"
            + "PBjpdkq9LwIj3IUpjpYONuu7RQvmPcCo9N/2qfF3HrA8RJJxR+0QAYH+2/s6ijE6RFpALebweQ3s\n"
            + "BckljTWEYDzgP4JH/qsMIf8lGAQkv+jGWVOArAlBYox5UWfkSmxQPCY/dyUXYBAB/3RLNsOLa2cK\n"
            + "NiiaNpVqp+qvtqwGckA84DV/hrhyEOH3W9oIf9QECEsdBDo367/GTQlrOKv6cDwbfaJH84k6I9uj\n"
            + "d/KomiiV9IEgur899Xq7tKZZfJfQPAIiE1bLORGB9r3bvY65jWnd3zMSO7KAUSji7D5CVrA8UlOj\n"
            + "L/tGBoHvfLqPhT6VEFUHC6dXoGodpE5uFlEPEDwGOBpXnWIDo9xUS+VRbnO0ETD8Psi3r0Ywi9ud\n"
            + "UAagPFR7mTOFLgOD3YL0vU/mGLRvN0Vtyzjz2JA6NCosElA8AjZvX8xHOQPctxW+ZOQ7iWpc1iqR\n"
            + "QYJuanzBExnfsDxMjYf7jvAB9KvhVGPPVplLf0j30tsJHfRiVSNxlsuwPAjZWp3CCjLvEt5VGIsK\n"
            + "ojxBhhJMTkL5D4G44fp7uHA8BC9nsSoOZ6eZh+W9HHmaoBvAC8Sd2JfdM+NoBXresDwGIPEw1VL3\n"
            + "Bb8sdc6ht4UgAvLZ9ct3TBUmDubqkatAPAQGUrh/NrV0qtQqY4gnl/xP5uzFrFOYgwsEJsvfQzA8\n"
            + "BhBbiHz7XjLe0WRSlmf01gOgsxH+GcUoPAHG4uTicDwEEC+QedefbxmAe6Edyj5KJJSJ0kY2g9sz\n"
            + "aykgSkGwPAwxhkh971geEQGyXT4wLf1l3/JEtST0sINeCZKIfPA8Bm1/OlsYH54IfZu3crQ8VXyW\n"
            + "kk/dJsYMJKRVAltwMDwSOYJIeBoMPhDGRefGChutEx5JZp+vmjFp6/YTKMUgPAY3gCh9gBQ8IjcV\n"
            + "p2UdO0ZdLeqvzhySYkzo3GZpfhA8Bgw56GxtdnSr0pVY5LG8eMSH6oAZ2NQTTdLJ4WEuMDwEE4Wc\n"
            + "YqbSLTOk3Cw3JMEeDE7CLRULqTVQ8x5LLQBQPAYhe+h/n19SzHmU/gx3NPbACKCe8HVltNzEFO98\n"
            + "0/A8BB11oSimnk07niRZXMkFaH7DGx5X+7Rsc/XxOAQZMDwGJCfg1YlXbTNt890rtFhavFvLOZLN\n"
            + "A8cwfzO/yd8wPAZDgixsqoE8K/Q0cZWL0xurpMYD6CQ6TWR5Vd+VCgA8Bh2f4YG5FZSqQXKfd5g+\n"
            + "1g/e4Yy/Y3tfUxiOrumhQDwEKhFIe61f2kRbq3go7V9Rb4SlKswcNN9wtLAYg3kgPAYgJoh/7j5Q\n"
            + "7yrUZx6BeyT8z2c30K/slljdlilboqA8BBwe6NV7yyl3LYsvAt9YbT7zArV/EPQ2BWPa7UQ/cDwG\n"
            + "JCeI1bfLjTNo1FknCHm4Z12Lf/pNPvv2LhKbh5FQPAQpjeGB55aJdnXyC6Hg++A3BpcXlAfj/knh\n"
            + "voXCUuA8BCVro4WEH80zl9LmJJH57icHpPfFBsPFugEJygsiMDwEqx3hhx+Nq1RddG1VaHvUH10A\n"
            + "8q4W6fqanCwB3ROQPAQIWaGA3tIJd7wT3VMeR3JBXai57zbJMjhLauYdE7A8BCXWsYDXX1Su+Pdl\n"
            + "tKgmbkknrYG2Nqnztq8M2dPWcDwEDJI8e7yteGe8JSX9VpYZdpISsLB9+TSTkar9atBwPAQYVrkr\n"
            + "5to2iPK3FKmTjIphxR0pPsPdmdp8utB1NbA8BCCSmYAtvBLJhVEXo3toqUk8X0Yds3FSzdvUtcta\n"
            + "cDwGGFuJhD+sg9gRe04dR9ZmL0VYG/587F2IrWqowTSwPAQYL0B9H4JB/nv8YlLctSxqQGj0aAMx\n"
            + "FcDJuPaALKA8DDwniHsfBIH1RRUdkC96S91Is+1VBXPQKZ+9yMJrgDwIX3ca8lJYAfl7+p/GWaPH\n"
            + "z+Kg05ylzntUBLCFmMNwPCBKY+JnbCRh7K93ixC6Oj8rTMX/B7+Al7l0K68pqsA8GsmDHzlKFoHu\n"
            + "QXvpGuoraKdB0tYQ1pfZMJDZbK9W0DwaKGO8UVAQgecAJFvJ6wYPc22EtmTS1QU8T3MYbM2gPA5f\n"
            + "ih8+osJB73S7updToX9wkqJQswCDQRxk/G4/DdA8KHIkilKYDUH6xio7lWq/LNwYZVaHXk4upn/v\n"
            + "456bEDwmsh0rYucZYf6Y8t2kioKCEcU3CsPZhruk2c44GgFAPER/h+rp8E9JdtoKu+K+o8e3iKBw\n"
            + "f7vXZEfINWt/GYA8GtBrrWHWgVhnZVyzsAssx5R4dNklmSXSo2KwCd+0ADziXnGPUx4w8swPapTI\n"
            + "tY45mcDrSwtNpY1Lk8FdIxGgPAIyerGSvTO6RNpCcFc202z4WUEoVCtXlFIzZsfOADA8BgllsZjO\n"
            + "+20wS7obYLXk7KYTtZg7uZdJhzLvDeI28DwFeFJBh0/omHbUU1MJDtFbIfSmlpDVMtimMtQ8oD7w\n"
            + "PAQIWLmGleTvESMLqFItso2/l0eg30QEeTP+wiCo/3A8BBIvQYZEeZDu1evpK27I5EDBCRpRJMI+\n"
            + "A/99WYVRUDwECFa5g0VP2kWHqLLbCq5sH1Xgnc9m5QUsGvl0+W2wPAQQW5GEv9OrVNV6vH0iyd2f\n"
            + "abRZLGRYWoBL5cnfYBA8BBVzuYQ/xtSqeZjgU3W5YDQoKlMqoK0Ikw+ilaCesDwECFKRK8/xEO7A\n"
            + "SsIC1mCCgIgGiYaBhIBY9STvm32wPAYVzJB+d/XSzSj4lrkuvsEdr1dTx33lqBU7IdyW//A8BAQv\n"
            + "kHyzh2H3KMrW3VbVz/xuqwiiM0h/2rcgyeC2MDwGIGhAc92fwf2vWs98igcJ0q6WjUbs0Cv51/Da\n"
            + "vvlgPAQnfTB6lQgB/lIL8i6RlWD60A/ZVjY5b9vZapG49sA8GmljTe1chCHsFSVNsX2Nd9/Snkal\n"
            + "jP7pgLAu3JoCgDwENB2buNEWgfMu6q9WSCjDszYvMmvFmXavKAckwo3wPN5UVFqoAgIh+oMUB4tq\n"
            + "szVA90YzCBil+VZqdukw2DA8BkOAn+xjEkHtJxapwO7wjLSAqaez8bBU1q7DImvh8DzgQDAnYBJY\n"
            + "If38uwSwvptZE1yJh8fJ19QRc5GAoyYQPA4/f5f5hDJB73SXTJ67fYVpkcajQuskLLVEqm3Gx4A8\n"
            + "Clt0HmkYF0HvqHoKi6G7DqKVFhoyxCvmcPGUdXzNkDwgPh23+YpMAezTNOfBkjcyd98mzx+UJL5j\n"
            + "f9AXJHkgPODIIB1xpykh/q06qDC+uPhG1qrY9xofU/enkS1VNLA8A1oYjcVi9uH/w+tz5e4SexnP\n"
            + "5LyjThoBJLlJlEcucDwODhZRgWXkdKrTyoUQL6DqW1/xhYv9NEbSaUgWLSbwPAQPq1GBh/DFq4bb\n"
            + "uv7GAWxNvVuhtlwKu91qWEkli7A8Bg4h6YHv5aHvgwuPBs3ofBpKCSG0dR8z0wnW+ShS4DwHehl5\n"
            + "hhfbNKmBS3XI4j1Wq5HAbaPcvd9Si8H5THNwPAQI87mGpU5aRn6U8DaKHJ0CbZ2H/gAUI8BX3Src\n"
            + "IIA8BBJZkYQ/hOeZpcumWWu0fwkovf0etvNDaKEnJBvW8DwGBDeJgbbUS1QfuznSY3xbmYmnFKz0\n"
            + "+4x+qAX9wvYwPAIzkDh/qBMQ7f5bgR4/UmX0ch2Uz6mbpG1TjMpaMoA8BgedSYBu20H/w6qcTvsN\n"
            + "BalA0i8gt6uzuTWvaVGWsDwEH5RJg9f5gf8em+6e5HFBB/HpmUI2Wz/gWpc1Yr6wPAQHgjmGx8uh\n"
            + "/nOLJ3QcWGCrBjvzvs+zsphjT1f0DGA8BBwmiYcu8MWrKpxVmqBrBP3+rV7rb04jOGY5Es0MIDwE\n"
            + "CXE5hwe0wf+Nm1ULdB5LUo9gFXTOgFxhbb/WvtggPAQdivGHeayQ7tNsYVqCSADdgpugs2FUptHE\n"
            + "4u5sN2A8BBdriYeljzaJ2nu4t0RpeZNVb5Ws5Nfhek5hqmVo8DwEGbBJh9dXRbg92/0bRoZiNwIK\n"
            + "lkIYjD8ed/24AAZwPAQcLUGdMoUpdiDbGgL/9kCNA+8RzHJL6eM6NRdL4sA8BAgaQZbrA+H8wzvu\n"
            + "YwsAXNXeZxHQXUPDNrqLCqaDoDwEJYmBk6rXWkSoW9w+QuV+QAglKkRSLFBG2UW4C1gQPAQSNDGH\n"
            + "BO3pd7l8wNF3GRbWC2KP0YyC6B1E8dqtftA8BCGVkZCMsTLJvAyeJO+YN1zEX6Z/gx3mUzOunR9B\n"
            + "MDwEHliJkuW/MszX0/epHXhQgMkOUeicEmOUEZCG0hkQPAQYU5mFfwypd/jrS19EkGaZFBIpBpTc\n"
            + "xuBsETS0f3A8BChWiZs+nDLNQEU/628TLp06d+hPNYZVqIFM2W4KwDwECFG5l5IH5bvrKksFmjvC\n"
            + "OjOoJFIjpGywRkS7mGLgPAQZdTGEnJ5nmesnuDH6N/0XzXblK5SGvsbwsvd3PSA8BBehuNf6i+8B\n"
            + "Vi/IpYdmtbpmbVgePsATFvJFA4C9kDwEF6EpL61eJbmtF4TMh2A+k70WjGBmgSAS3uRAgW9wPAQh\n"
            + "oYx/zoeh+csaT9TbS+dqNqua/qNz041EQD4d2XA8BBV9SNWdwolzwCspk/4nSp1CMCuSOCStiiak\n"
            + "skCNEDwEHh9BgxEMIf9LOkBjaxzHG6o1prS+0Zm/gmFd0bhAPAQqJzmDH5Hh+c971z2kB1vZZgiA\n"
            + "yLScMyKffT66kHA8BLonUYwbD4H9BUo7A5ql3+7TImpBXctEIEfwxV1rIDwGIiRDqPxIIf64a4ST\n"
            + "hvtU4fyYBRMHVZlw/oLz4f2QPAhDhE2JNNih+5Gqwvr6rwOdZQK9HfsP73i9xr33toA8Bh92qS/p\n"
            + "6+H/+i8ZTewKrG7c6gdh52CaXP5+iUqvIDwEEjghLp/yZbrTqnKpUtEMo/cd1zg8AtSFpVJLmPmw\n"
            + "PAYe8sB/N/gPESqP/GvNoG3s35qhhhjY60xHfI1UCGA8Bg43mH5P0/pEeXSzpGAfAqI9JKTEFBDu\n"
            + "kE+UGFFWoDwEHFEwft4fNpjddAV2CLMxMDm77olbYHuv/XQ/8vGgPAY8HOmMNFSj3TwFFyXpv3UX\n"
            + "TUvafvlbFXIgBEihzrA84BmIV80wcRaJ/hZDRV91sA2p9946/pFurUPeyOxeQDwIYZHuvXYDFoh/\n"
            + "J2EoKXc4i0U6fO5Stln6tHt7XLswPAhAHbNV1J6PEFmFzn6rna9zpjnxuq3vjubRve0voLA8TnBp\n"
            + "7deWDW0yQJrrAeRmGJkuAXnoBFf0PRBxsw0boDwBf6vp1aYHvgCoC7u0hitiNUGxXVyCIc2ljLpi\n"
            + "IjZQPNhSDWmDzfBrVYW6SkTZtMCcHFkDvLxq3/i+CPXg7LA8AkeugY1HlvLM11dTMV6kHcJN6aRa\n"
            + "1kwG4CWB6YWmcDwGQ5vpmlwYaXeACtrnK6jhdJ46xCwxM1n4fvmFma5wPARWEemeV5eUq4KMlELb\n"
            + "+BzqetS9tnrgPiEU63nfTjA8CEwRS7MClHSqMfoRfwpCFqDVnQCLviATE4c3p+9aoDzcTZo76voC\n"
            + "g92+Zf6xSpxz3xHPOZE3EL9bx50rNlagPAIpe03C14Bh/pf6TsEZr84u93TX3aTPXTWFo6sVh3A8\n"
            + "3l2QN/MWDGH92PSvkX26MdkYkkP/Y1RKyHLwFoaqUDwCOGdP84AU4fxeSAOi6xPT7/IwFjkN3Zeu\n"
            + "dO0XovYAPDQZn0S7pAUQ6H0Yc3M/OysrThJVGGTrANiM2hx5LMA8ClFaQCZgRhDqPlgGZDQARncm\n"
            + "thVSFtm6XOaM55yj4DxCIlI/RgoUgfx/mDvwnTP33wZiLBIVjFOC4DCT0mqQPCRLnLAirDAaQCva\n"
            + "cmamwuSBcjjUHKVPtDrcYPbIVmA8IDzyNvdKDwPYc0K6qlSJPwSTx2C2dalHYmrVtwz+cDwCHcix\n"
            + "gJ3v2kXBW/IkmV5Ppba/TfqW0R/HAtKRPB2wPBV6NpGBE+Y8I4FMUrI2tBTf9e+scoaeitDU7XC3\n"
            + "cMA8BFGx6H/36AtU0Vu4EM5qbwbF0q3KLAAe58CeGkM98DwOPBH41X2PfCODVigtNhqJNP3Tmb0y\n"
            + "FFC5fVFXsawAPARXq+mGphPSzaKaZuDF8ds04oqGkeDshoNDPc3lg/A8WmQW7/8ehcl3awc71foV\n"
            + "0CujrpzYZl8ln9RS+aabADwIVZuP2+YC0s2DmEaCd5cz+Cvjc5vH8HpbPwHzU7xQPA5SDPmGgjSD\n"
            + "3FU3boOovRDXL2xnsvEbcH0hfViKXwA8Bjuq6YFPetSrgJjR4+D4/aabTOrG+9Q8JnFvy+T3IDwG\n"
            + "UBTpgH4/8O572lYh+rwc6W/TNtZAuFj8hLF/6zBwPA5EIekq6/lYZNXaB8uzhtGLSwCwCk29W6iU\n"
            + "XMCWXiA8BkeBiH/74JhnKZLxhP60+MWYQuxX0wWHAGzKnCUjUDwEIC/ofTw+EO/4ipQtXg79bC3y\n"
            + "EGmflVxpCJ0S0TwwPAYRiYh9FYZB/MbvkCz3tKPxNHwC0Qvhm+cKhfWA73A8BhltkHA3XwW7qFpu\n"
            + "Kgh66ZagzIlnIA2dtDoxUwATcDwGHvHIbHf3602W9JhNtDqENHUi+goWbuA4u3dHfkvwPAd6USh7\n"
            + "Y+QrVZacypzi9i+y+20GG36m0630nlnFBLA8Bjwi+Nf7KGeZDtTlKHpmGmcTTIu2J+d0S3pkT1lL\n"
            + "sDzgUBRRhwmQY9zdWj/0WSrfrAsf5MM0nCcRVnSbAFuwPERmC+ut6xHpd4ya8m7Q+DEfw68HUdoj\n"
            + "UVibCwHgjKA8UlGotr0MFZSqcapSotuuyqMFGJTk2J+V+iqehZeUYDxEZBZGUVAgyXZ+ZdvHfsOz\n"
            + "3Gu88QQ7RjoFDVKd2FaQPJBXrozTRRw2iSPKfKsy+SWC2kteiQacjeBrEZ9l3GA8AEw5INVwUtLM\n"
            + "lrvPJGJ/yX/HpxLBLiVqKTOI4hU9cDxEVbGQfW0ti1TqVXolppl3cSd84xwNszGORW6CBmCgPAJB\n"
            + "o0BtbDjSzdC6MqR4M8VEZrIQsGR3rZemXDXmiLA8NFYUMvtuAeW7wNzoz5g+J5GHkCgjG9TQ/Db2\n"
            + "NhDkQDwaQjJQenAYCXZjF7mOUgamHiIIsDX8kM+PyGJO1huAPOBiFj3sOBQh/3vaaxEdnB1O6rWJ\n"
            + "0bybGjAQYgrUE1A82EQyQ+zEBSHtJKrbYNsqJBZFdAxUF2YDgPFX3qs/EDzgUmVPrNcEIfQ1pE1J\n"
            + "tYfGF4XdBMtu6Pkta36XPbbAPAhTcJd3eBBB+2BKcyPDrtBpxScKawZq2mchElU8z3A8Dj+IJeXC\n"
            + "XcWzP7xYC4DkFLem//T8tLdaCzf9vSHmYDzgNGS3zC4F4fI92Baj6gtyuSYIUDntUUJooDmSRvrw\n"
            + "PA5lezP+YqUh+s8LMlv9x2m3+I392fhHbeER/nJjHbA8CCld50+8HCH9MHkxsj5qlgM5aG9KLC6c\n"
            + "KfvVMBKBoDwebDA1T+wYQfJfO66gfvp9x/LqDNyJomIp23eAB/0APAg0Z4988wQh9GOaHahIiwIx\n"
            + "BxxQDvXiEL6K31YQh4A82FVsN+0eBaH7MfZ6wl7rvZO0TPTPIhIup07WWS10sDwONmlH5xFIBTgU\n"
            + "e72Y+xplW8lakls1rUsaUMY6SeeAPNhsX4fac4Bh+2DYZwQmxNv3jQIM/KEebvIo87GFiBA8CDV5\n"
            + "JUZsCaH8xCvBLbvKd3pNkSBUIvTZWO5Vk3UJ8DzgQn+T6vqjwf5+j1FFfh7CM7X596E3q9lp1NRE\n"
            + "QBfgPA5beUftYhEh8nTqr6t2EtahucYWzl+eiIBK7MBUakA8GjZjh+dwhgH/nN+wLwfqTkvMT+cf\n"
            + "pSNAG4IfL5C+8DwaVaA8SeYSwfsmW0b6qv+KSVEBRmrXt3ACD7lQC+3APCBUaEC1BA3B/W2aGT3y\n"
            + "y4Tji4odwBwvpdPduLv0mlA8CFd/QfFsAZDpaouzCXy6ZLZuCvcphjVhxwuWuUBUwDzeiFotzjoU\n"
            + "gf/AWkutDmSOaet11m18x3RrSicKazvgPAxLfzUX/RbB9rEsjZJo3QUnLZcoPg+XuCXOhV9Ev5A8\n"
            + "Gj90Ov46RQH9ansTtKqqEwfuRtOYakYNYjE2qsFZgDzgcYGP7ZAN4f3W6q0riZjFMMV2f+1b34FN\n"
            + "EUEEnNqQPES5czf26EGw7jpsASCh4hRsMmblzdOujapisrko6mA8CG9/n7kMAYH/fpV0nB6SfOcz\n"
            + "9YgYPMsdw2wZ4C7xwDwIW6FP1H5F6WZUCaseiKPduU5z5Lg0zRAC5s6bhHEgPCBleaD...";

}
