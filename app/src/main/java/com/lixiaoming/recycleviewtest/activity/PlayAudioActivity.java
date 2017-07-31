package com.lixiaoming.recycleviewtest.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;

import java.io.IOException;

public class PlayAudioActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private String audioPath;

    private ProgressBar progressBar;

    private LinearLayout ll_startPlay;

    private LinearLayout ll_stopPlay;

    private ImageView iv_play;

    private ImageView iv_stopPlay;

    private MediaPlayer mediaPlayer;

    private int playTotalSeconds = 0;

    private TextView tv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        audioPath = getIntent().getStringExtra("audioPath");
        initview();
    }

    private void initview() {
        progressBar = ((ProgressBar) findViewById(R.id.progressBar));
        ll_startPlay = ((LinearLayout) findViewById(R.id.ll_startPlay));
        ll_stopPlay = ((LinearLayout) findViewById(R.id.ll_stopPlay));
        iv_play = ((ImageView) findViewById(R.id.iv_play));
        iv_stopPlay = ((ImageView) findViewById(R.id.iv_stopPlay));
        tv_close = ((TextView) findViewById(R.id.tv_close));
        iv_play.setOnClickListener(this);
        iv_stopPlay.setOnClickListener(this);
        tv_close.setOnClickListener(this);
        ll_stopPlay.setVisibility(View.VISIBLE);
        ll_startPlay.setVisibility(View.GONE);
        initPlayer();
        startPlay();
    }

    /** 开始播放 */
    private void startPlay() {
        try {
            ll_startPlay.setVisibility(View.GONE);
            ll_stopPlay.setVisibility(View.VISIBLE);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(false);// 设置不循环播放
            playTotalSeconds = mediaPlayer.getDuration() / 1000;
            mTotalProgress = playTotalSeconds;
            Log.d("fate","=======音频播放时间："+playTotalSeconds);
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                Thread.sleep(1000);
                showProgress(playTotalSeconds,0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext,"播放路径有误",Toast.LENGTH_SHORT).show();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /** 停止播放 */
    private void stopPlay() {
        ll_startPlay.setVisibility(View.VISIBLE);
        ll_stopPlay.setVisibility(View.GONE);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            playProgressThread.interrupt();
            mCurrentProgress = 0;
            mTotalProgress = 0;

        }
    }

    private void initPlayer() {
//        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
//                // 资源重置
//                mediaPlayer.reset();
//                // 设置该播放器播放的数据源音频文件
//                mediaPlayer.setDataSource(audioPath);
//                // 准备工作，
//                mediaPlayer.prepare();// 播放音乐前，必须执行prepare，表示准备播放音乐
//                mediaPlayer.setLooping(false);// 设置不循环播放
//                playTotalSeconds = mediaPlayer.getDuration() / 1000;
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        progressBar.setProgress(0);
                        mCurrentProgress = 0;
                        mTotalProgress = 0;
                        ll_startPlay.setVisibility(View.VISIBLE);
                        ll_stopPlay.setVisibility(View.GONE);
                    }
                });
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(mContext,"播放路径有误",Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_play:
            /** 开始播放 */
            startPlay();
            break;
        case R.id.iv_stopPlay:
            /** 停止播放 */
            stopPlay();
            break;
        case R.id.tv_close:
            finish();
            break;
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
        progressBar.setProgress(0);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /** ============================以下部分是进度条====================================== **/
    private int mTotalProgress;

    private int mCurrentProgress;

    private Thread playProgressThread;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 1001:
                // showAudioTime(mCurrentProgress);
                break;
            case 1002:
                Log.d("fate", "捕获到中断异常");
                Log.d("fate", "mCurrentProgress:" + mCurrentProgress);
                break;
            }

        }
    };

    public void showProgress(int mTotalProgress, int mCurrentProgress) {
        progressBar.setMax(mTotalProgress);
        progressBar.setProgress(mCurrentProgress);
        playProgressThread = new Thread(new PlayProgressRunable());
        playProgressThread.start();
    }

    // 自动化100秒更新进度(播放进度条)
    class PlayProgressRunable implements Runnable {
        @Override
        public void run() {

            while (mCurrentProgress < mTotalProgress) {
                try {
                    mCurrentProgress += 1;
                    progressBar.setProgress(mCurrentProgress);
                    handler.sendEmptyMessage(1001);
                    Thread.sleep(1000);
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

    /** ============================以上部分是进度条====================================== **/
}
