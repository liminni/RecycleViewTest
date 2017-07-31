package com.lixiaoming.recycleviewtest.voice;

import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import com.lixiaoming.recycleviewtest.utils.Base64Util;

import java.io.IOException;

/**
 * Created by lixiaoming on 17/5/7.
 */

public class VoiceUtil {
    public static void playVoice(String base64){
        MediaPlayer  player = new MediaPlayer();
        try {
            player.reset();// 资源重置
            String fileString = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/data/files/player35.amr";
            try {
//                String base64Str = Base64Util.ioToBase64(fileStr);
                Base64Util.base64ToIo(base64, fileString);
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
    }
}
