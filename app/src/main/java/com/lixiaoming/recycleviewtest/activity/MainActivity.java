package com.lixiaoming.recycleviewtest.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lixiaoming.recycleviewtest.R;
import com.lixiaoming.recycleviewtest.aconstant.GloableGonfig;
import com.lixiaoming.recycleviewtest.voice.MediaRecorderActivity;
import com.lixiaoming.recycleviewtest.voice.VoiceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import static com.lixiaoming.recycleviewtest.R.id.btn_linear_1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private Activity activity;

    // WheelMain wheelMain;
    //
    // EditText txttime;
    //
    // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent.getAction().equals(Intent.ACTION_MAIN)) {
            GloableGonfig.USERNAME = intent.getStringExtra("userName");
            GloableGonfig.PASSWORD = intent.getStringExtra("password");
        }
        mContext = this;
        activity = this;
        initView();
    }

    private void initView() {
        // txttime = new EditText(mContext);
        // Calendar calendar = Calendar.getInstance();
        // txttime.setText(calendar.get(Calendar.YEAR) + "-" +
        // (calendar.get(Calendar.MONTH) + 1) + "-"// 月份是从0开始编号的
        // + calendar.get(Calendar.DAY_OF_MONTH) + "");

        Button btn_linear_1 = ((Button) findViewById(R.id.btn_linear_1));
        Button btn_linear_2 = ((Button) findViewById(R.id.btn_linear_2));
        Button btn_grid_1 = ((Button) findViewById(R.id.btn_grid_1));
        Button btn_grid_2 = ((Button) findViewById(R.id.btn_grid_2));
        Button btn_staggerted_1 = ((Button) findViewById(R.id.btn_staggered_1));
        Button btn_staggerted_2 = ((Button) findViewById(R.id.btn_staggered_2));
        Button btn_time_seletor = ((Button) findViewById(R.id.btn_time_selector));
        Button btn_finger = ((Button) findViewById(R.id.btn_finger));
        Button btn_voice = ((Button) findViewById(R.id.btn_voice));
        Button btn_playvoice = ((Button) findViewById(R.id.btn_playvoice));
        Button btn_indexBar = ((Button) findViewById(R.id.btn_indexBar));
        Button btn_playAudio = ((Button) findViewById(R.id.btn_playAudio));
        Button btn_open = ((Button) findViewById(R.id.btn_open));

        btn_linear_1.setOnClickListener(this);
        btn_linear_2.setOnClickListener(this);
        btn_grid_1.setOnClickListener(this);
        btn_grid_2.setOnClickListener(this);
        btn_staggerted_1.setOnClickListener(this);
        btn_staggerted_2.setOnClickListener(this);
        btn_finger.setOnClickListener(this);
        btn_voice.setOnClickListener(this);
        btn_playvoice.setOnClickListener(this);
        btn_indexBar.setOnClickListener(this);
        btn_playAudio.setOnClickListener(this);
        btn_open.setOnClickListener(this);

        btn_time_seletor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = "com.dxmap.shuili#@#com.dxmap.shuili.MainActivity#@#0";
                String[] split = string.split("#@#");
                Log.d("fate", "split[0]--" + split[0]);
                Log.d("fate", "split[1]--" + split[1]);
                Log.d("fate", "split[2]--" + split[2]);
                String url = "{\"url\":\"http://www.wandoujia.com/apps/com.zhihu.daily.android/binding?source=wandoujia-web_direct_binded\"}";
                String downUrl = url.substring(url.indexOf(":") + 1, url.length() - 1);
                Log.d("fate", "downUrl = " + downUrl);
                try {
                    JSONObject object = new JSONObject(url);
                    String URL = object.getString("url");
                    Log.d("fate", "==URL = " + downUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
        case btn_linear_1:
            intent = new Intent(mContext, LinearLayoutManageActivity.class);
            intent.putExtra("orientation", "horizonal");
            startActivity(intent);
            break;
        case R.id.btn_linear_2:
            intent = new Intent(mContext, LinearLayoutManageActivity.class);
            intent.putExtra("orientation", "vertical");
            startActivity(intent);
            break;
        case R.id.btn_grid_1:
            intent = new Intent(mContext, GridLayoutManagerActivity.class);
            intent.putExtra("isUseDivider", "yes");
            startActivity(intent);
            break;
        case R.id.btn_grid_2:
            intent = new Intent(mContext, GridLayoutManagerActivity.class);
            intent.putExtra("isUseDivider", "no");
            startActivity(intent);
            break;
        case R.id.btn_staggered_1:// 固定行或者固定列
            intent = new Intent(mContext, TestPopWindowActivity.class);
            intent.putExtra("fixedHeight", "fixed");
            startActivity(intent);
            break;
        case R.id.btn_staggered_2:// item项的高度随机
            // intent = new Intent(mContext,
            // StaggeredLayoutManagerActivity.class);
            // intent.putExtra("fixedHeight", "unfixed");
            // intent = new Intent(mContext, FingerLoginActivity.class);
            // startActivityForResult(intent, 1008);
            intent = new Intent(mContext, TestJSActivity.class);
            startActivity(intent);
            break;
        case R.id.btn_finger:
            intent = new Intent(mContext, StartFingerActivity.class);
            startActivityForResult(intent, 1009);
            break;
        case R.id.btn_voice:
            intent = new Intent(mContext, MediaRecorderActivity.class);
            startActivity(intent);
            break;
        case R.id.btn_playvoice:
            VoiceUtil.playVoice(base64);
            break;
        case R.id.btn_indexBar:

            Log.d("fate", "====:" + view.getId());
            Log.d("fate", "====:" + getResources().getResourceEntryName(view.getId()));
            Log.d("fate", "====:" + view.getTag());
            intent = new Intent(mContext, XingbiaoActivity.class);
            startActivity(intent);
            break;
        case R.id.btn_playAudio:
            intent = new Intent(mContext, PlayAudioActivity.class);
            // String audioPath =
            // Environment.getExternalStorageDirectory().getAbsolutePath() +
            // File.separator+"CLANNAD-樱花飞雪.mp3";
            // String audioPath =
            // Environment.getExternalStorageDirectory().getAbsolutePath() +
            // File.separator+"playCommit.amr";
            String audioPath = "http://172.20.96.103:8080/bowei_audio_img/GuNsPoAwmBCKteo.m4a";

            intent.putExtra("audioPath", audioPath);
            startActivity(intent);
            break;
        case R.id.btn_open:
              intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse("http://cmccdi.chinamobile.com/cmcc_dism_webapp/theme/testDownload.html");
            Uri content_url = Uri.parse("http://cmccdi.chinamobile.com/cmcc_dism_webapp/component/AttachmentController/download?attachmentId=AaCiiMPWdZzxQFa");
            intent.setData(content_url);
            startActivity(intent);
            break;
        }

    }

    private int RESULT_CANCLE = -101;

    private int RESULT_FAIL = -102;

    private int SUCCESS = 301;

    private int FAIL = 302;

    private String base64 = "IyFBTVIKPJEXFr5meeHgAeev8AAAAIAAAAAAAAAAAAAAAAAAAAA8SHcklmZ54eAB57rwAAAAwAAA\n"
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
            + "AAAAAAAAAAA8RHokSuTr4ciDWnvyMARRQAAFMa63CEgABTViPEOHEDxwZ1dSFgACYUuEDzQkPNCU\n"
            + "/u4JhqcKl3qAZzWZJtvAPER+aRE5QQLBSkkLhdoQ12gztSyq3aqEPuorAb8W31A8eHl/DE94GGB5\n"
            + "KilMLV/heZhZfuIylnyjyIyVkMYAwDzg7ZpdBQKQgH0Em7lJ6ZdBCicv7F0tOduqr0h718aAPJJk\n"
            + "JQAAY0Cg8AS8WNZVWRqDlWg1FjXmy4RAeYVyMIA84PYkWe/YEsFphJosYu2xKwMXshUESxeB34DO\n"
            + "nfPuYDxWVYICNNgnIeGMS9KuzS12+AFmbxg2KC0eB8o/G5ogPOB2b16r8Ath4NN71ZciGl/nH0ZH\n"
            + "oNxC+bTrHpb8JhA8SGQpDGmwCqHDjtyvKhKIET+wObwzuqgSBzIE/YCBIDzYbGkOQnSFoeLXVWiQ\n"
            + "pK+f20gbM+oTBp0MniRBcLlQPFJlkVzizxUh4n5zyMZTMEH4yeXCqAJ/2PVAJnUn35A84pBoD72q\n"
            + "EuDxhxtn62OYfVmvSW+cbI02w9AwAhYY4Dwgc4ce5qANoeEAQRvAHmEnnFGGYo2MKsdSh9+BBHyw\n"
            + "PGB+JAH4VBKB89DGdOpuCEDzJ0CgoDbhqtcyJbjXxxA84HOLXMDsCcHi/XQW843guXO8GZnGzMK+\n"
            + "pK/pqdQD0Dw6eXkKqvkFoeIs1QLP8VYzJVR9Mb0OP6Fnd0rMQonAPEJcFwh8iINA+YolCoaAZEMG\n"
            + "eTMPUttX1Ra9mYfUkCA8QmwgD+fmGSHhhLQrFcYZPcVlbu3bTKynic4KCt3twDxEZ4sIXrQJQeD3\n"
            + "PBIt3KMXt47exrQDe4NBkyC+fvCAPDrIaQujjAKB4NU8vLbp4Q6SRUoeNDW0QrwPDEgc3HA8NFwg\n"
            + "CfOkHCHklcxYg4y3GgRAYVLUL1ZC71t35hsC4DxEctYFLdgegXjXREefeHIVs6IdCqbZYIaoEnfL\n"
            + "oQFQPGBnfxhVLsFB5k4jRDfMiJ36FSHCmo0zUH/99cmJ8rA8RH+LXjsAMEHgd3zHbC+lw9qC6XCy\n"
            + "8cqn0LeBoGSBYDxObYcOOHgIYeOt9b7aIbIUCARMOiY/GFYpMo9J+tmwPEJ/eeickg0B7YRK+Z0s\n"
            + "bdYToXyBpwRXplMOly0uqkA8NG2NGqKoDSHoL7MKquX5QMUMxU6pni6WojXpCH4Q0Dw4el1fR+AN\n"
            + "ofE7Flcq8Z5FC4xc0DCE9gjdqjig2LIwPEBk1lan/DaB4MHlhw35JxidHZW681L3q8qqf0AFkiA8\n"
            + "OHxkXqbKMmHg5cRPnzZF4YBDFhdAyGwtJmKq3b728DzeiYdWujoB4eDLZE+WNp312EKHq8rs7UnQ\n"
            + "hiP2/+hwPPh+IxEqzILB8cjVw1FUnAhAvh2et39sS2ZvnrRFI5A8ILBpCqKKAeHglGJn+eck2bbG\n"
            + "yOKjL88OAIfi1R1CIDxFBX8Wa+gVofHl7VeXLHOBAw8eXHfIZrGvD/mFOb0QPGhtiwgg2gVB6giz\n"
            + "GE8Hnl+crVH7r3fiKWwPhuOCN4A84Ht/X32ADSHg532PNYxjmg2qKixtNABxCAu094ofgDxuch0I\n"
            + "Y94UgeGRNbvZ5r5uHoZ3KaJdg3k2UQPikAvAPN56bQ76dROB4vddds2Kf7JwpVMQOYb7wc7cUwks\n"
            + "8gA8blRpA7OcAyFo1bvtG+b5UEEwsMxcyssycVb1YqdVoDwgiCMLM7IeAfEJnLBuR6/7qCTLJYJL\n"
            + "FQ8goMc03UZgPGphfwg8UAHB8WM7KVkARGu1ijPdRwhbSKL/0g0HhkA8JpbWCuLeA0F5PMx3Ml5K\n"
            + "0xDEtNilMwPMn60efO1zMDw+XX8OUa4J4ejUHSIsip2XuTIoxC5+PpPNO8uRFE9QPCqSJRVllBhh\n"
            + "8SW9h3MYOqaOu6OH4MQ4zBTCoui6pAA8Ql5pD0W9CgHjjF0+yAI2nL3qcREiJ4SFjexC/HdEEDw0\n"
            + "yCQWLDQcIe0ozRFcARqOIw7pYWHTLSlMsg2pzHcAPDpkJA/7zApB4H6d2xbIea2CCor6obpN2q5a\n"
            + "0uYvGTA8TnuLFFH0AYHo9qvSBz6YdBjZrHKTqQV0grukPuTqMDwoZGsORgoJYeGArV9nbymyrUA1\n"
            + "v5wiyFyjnXUNd7uAPDSJfxYGGAUB8YQc1Qs6wAF2clBN+9mYUhN0/0ZOCvA8RHBnFiRYEqH1gl3h\n"
            + "ooOFhXw0HNNUKo9oCDZVwPRPwDw6u1oX5/pBIfEFjMVhIV0LXd0jUscNQv2MQNnAp+qAPEhwaRVP\n"
            + "WwkB6N2Ru028S0/D+BfgrnEi4K1npPg7heA8RGx0J+fLCWHxOceXT3cQFg0OHoFcFXJiVi47xkf8\n"
            + "EDxgfiNc2OAeAeaEEwx/YHiFuoLAhDWz1yZKR0FkUdDgPNhecCbrmgGjhXNjfJh8k3v0JuuPDxWO\n"
            + "6aD+AC0ZiqA8SHpnF0c0EEHwxFVSW2DuHYWGv513Rq/wu729bC1bMDxCcmcU0jyBYfOCMnbFi5DO\n"
            + "xhFF5zsqDEL+SmquD5fgPFZ2aQypXglh4wITLNNFjY6nvnIs1JzFf1waF9w4LCA8Nj5nGrfKEeHl\n"
            + "N9K8JIJNegL0MrBZ5LJPK4Nhm+9T8DxUch9d9TYEwfGZfJtUgG/r1HJ4dUqWNWOgKF38UBxAPCps\n"
            + "cQx7hC0h8NNsxiwvdC5OoFqokLDIPkOPG2d3v3A8TnuGWD7gAeH4frWjEQWGPe5RimURkAD8Mo2l\n"
            + "vZaH4Dw2WmQV+XQFoeTShbM+UvFdeoIMCEWKfi9CDF+yPJhgPCByHAgZygDh8dHRt6lus2ktg1QL\n"
            + "FNBIU5vHxp5/PUA8MEpnF2Y0FOHpLFwJjTIdIDN0/SQm+WxVkRSrndbjkDxCfhwFTJEIweEUzbtx\n"
            + "1NicUviWUXG3PLC5uLwcXAhgPChaaQ5gxInh4NUc3o/PQ/qu8uyShCrSS1CBEI+H4HA8Ro5mU7cN\n"
            + "guH/KzMFpQg3lAs4lHgxeiRC4vGjSPCbsDwSFtwIehQtgfrJUyA7d2qT9YoEDsAMo5T02+cZnfrg\n"
            + "PEZxg1B++fDpZ5xAqY6HWMOFua7RuGQ4r0RhpOaiTgA8HCgdCYAj4AHvh1MBc9jOrqu5s9dKUd02\n"
            + "7u/eJWEF4DwwkGnpgaXD4f2FEFIo3Yf4qkNwWRaOxGTY42ngIDpgPPIiHTmB8Fhh/QrTt+BHtb29\n"
            + "xWcMpgilzAbhzX6ebjA8IHp4UYGDSwH9gEBZeLas5sDNVCn9beDLN8thp2tMsDxEuB0RgY4Nof2R\n"
            + "APHBGe/F/sRvE02HR+kQo6PBtZPwPBJtJFEoql5B9j0jew9ncKGlQTzT86Ls2COa+Nwe/DA8Ph2H\n"
            + "eHLYt2H406CnEf7k3JIAx6ynx+k2+jIIQEe2wDwkPmNIZn9pwf+AhbzOMlEgYEA1/0Ptn0GeevEO\n"
            + "NGrgPNjJLDBtbB1lunaj9H+NAv2uHl4kz3XNRS3xGhNBa/A8YPhnk63KCiH/JEWkz6lhIgDLhR6P\n"
            + "XpUBsAnhB4p14DwWeGcvZQAAw9jRldQJgnmGwg6/bwCZQPDBmcp8W1VgPIp3ZJb4EgNB9Q0ERQHq\n"
            + "i+ak3CX+q2df2SlxKhZhE5A8QPxxmQCCCcH+QlfMeKtsylbdGoZXU14P4vfOuv8rEDxZBIyTOcAC\n"
            + "wf7Ao4Z1EeXm0PanGXa4F6my5pxwvt3wPBA61jA0LSWw5teDhPkwY0qQJllNJVnhTyG7MkQaHFA8\n"
            + "RHtjOGNC8EH3gWNsCHxS1QHnzXafRSN3eFkkSAmaoDweR12gZvsMIf2MNRFu98kJL7KmYJlCbKfc\n"
            + "FuDvc3uwPFWofZf3+ArB/8g0+M1w5OU5XxBuvHxXNotk9QSCmYA8RGJqmrbIBIW5KIbR/kXWQCfe\n"
            + "kOSq0YfLwiIjaFJiwDxluquk+IgJIf5kJSVCdB722cGl3NXQi6kq/Muw2/ZQPAQtiiA3MC5h5nvm\n"
            + "nzxPpkZ+jqtgZFYn7hU6aVKAt6A8Jtp9GGNmWiPBOjIOe+DexkzppOkmI0H6NNTg6cyA0DwGN4AQ\n"
            + "Z4Y9gemHVKtzLMm3tK11JbaCg4HmT3MuATWwPBI0aBBmalwh9YRkMcAtP3xqKrPRxhVRUDLfA+UV\n"
            + "l+A8DiN7CGaYX4PRhxRYiUd+omjP+62ZviXL6RodTO7SQDweUYAAZ9q3QeB/7IZ7zNLNMRriVhDF\n"
            + "LqHqtAH1k3vwPA5DfRB4lPEB9YZC6E77FIi2ERZ4yXi1RG7ubrVl18A8GkeKAnnqGmHpjTzZwNfi\n"
            + "AuycE6XZy2fQEppsQncPMDwGu4EI1d5aIeWFfa3OBHGcON2+Cw6vc0mxk5ftxMFgPA44GwmBzpeA\n"
            + "0ve6r3mwg8Mnt63/8sd7tqMZgsJarXA84CJdEZnLaMHi0F3+6eG+lXN+AF4Na/BmiD6REYkUEDwW\n"
            + "MhcRmzTbYeOI04ZUMi2yKOU3Gk8lk2RFglbCtNBgPNjIiRnid3zh54UgYd7zOk64lcKDNZJNpHgN\n"
            + "81/SVnA8IG1aGeZX0wHnhcB/5JMd1FVnsvkK7TwnJyn0Ih76UDwoNWsX556DQeRKgMXwmG3jVZDR\n"
            + "IzDdrFCKIPqqWQpAPBo+aU/nKh8B9tRgMMwfcdFbdst0k+QqgV39+z5j5RA8RLgnF+Z/DiHlOgC8\n"
            + "7tMQ1OAnDwbD0N/eUz97xZXYsDwauV8X5m1KAfkOMwNrseyPgSEobpZu0rJN+LF2ujVAPOBDkQ/m\n"
            + "SQpB4Gn9aijk06DYChZv+drYylFwwW6rctA8DjWLE+MNCgHgKqstjTBlZzH+/zngF8pp8NHONE1h\n"
            + "kDwIWhcDslcHgceTzETWGMYt/vcoH3UmjBpJ3NPH77fgPAi4JRmbgB4B8a2Uw/nE+ywopOD9bvoI\n"
            + "u1ybAXrPNaA8UHWBZ+wQB8HjYoKzZGeAB9qMiCJLePNUGn8R79CqwDw2gyZwY3QNAe8OcxgJiwKT\n"
            + "Up5eJVG3XlHI61f5bNMwPEp4JFXZKDDD1DnS7K1E/+sCT+QzMigmxm3I9O+/d1A8REUpcNQdLMHr\n"
            + "kXXPL++JBny0uhNihgOwPHGYRvc4UDw6xmQY1WtKAf2TMwk59Mx2/dkMAV4NYWYigzQLBUhAPCCt\n"
            + "YBGLAhOB/TkzYWxPM/jThUJVT8bdQ1tBcYaSgzA84EwbFdx+BsHlyKMbwlWPLBRlzXZD0JPPHecM\n"
            + "R8hXoDwErGkRmdEKQeGAJUfSQ3yqlAxRKpt0kN/KIrC8QQSQPAjIGhGbUMOB4fNVo29DsFYXRyPm\n"
            + "c6VEv0wiCxPYC/A8GrhmheXzXwFrACtM8qm55pDWFSyELqxl2uZ+GycQIDzZwxHRsZ1TkO8olsSF\n"
            + "C7J4Pa9drsKhp0u7YGQBcecwPE8Agq75mhoB/iBbLks2Ob8bqzQIIYFV4LRH2vU28iA8c8EN1TX0\n"
            + "UKH+pJUdOxWijiDzlUUXbFr/g/KM8MWYQDzfGqO5X6BQgf7Rurc0rDTfUY/u4vZPiy/p0cIY9Q/Q\n"
            + "PGslNfrvRAUB/SBa9x6ct+TuPMOOtUOQ6pwopLQTePA8BJ1i7YZOEqH+sIoWWkxJLu9sdX/sCpkk\n"
            + "eHvSpBQ74DwYeWThjLoHge3S+al0kbLOuwwnW81U0pjBL8eBgqUwPASoI7GedUgh7YLbGGONeXJN\n"
            + "+ePJ+Cb+bLjdX6AjCjA8CIF/6cr2HuH2bMqw/+IBtiZubaGx5nFfII+S7PqfYDwIqCXh5hoPoeka\n"
            + "8qDT7J37a5pcv5KwCZwO/N1rYD9gPNjIGmPmuBJh4Q6IetWXITmeMpvNBeR3MPB+25+v+AA8KMrT\n"
            + "We8oMOB9OyxPY94JZTDaUu8txGccI+BpX52TQDwKnGzhKoISgfLFdWbnPfE3CHAwdDDR3A0SOGtl\n"
            + "lj8wPBoiZomNwB9B5+Jj+LlIC5Hyse9WMwa0qVRkm8k1UMA8DrBpmcqePQHoxGEKdVlzlszON3fL\n"
            + "HKOHvTl/aQMC0DxEqGQxyoq4QfvCJnqhB1FqBJOaF47kRhkfmwO81KEAPARVVzHgmlsh5dpBHVK8\n"
            + "mpJ45YM2c7ztvafQ8O7mXLA83skkIfIsHqHozRDzvO7Q+QVjU+7YMX5csKxqgLl80DxEZVpx5HgP\n"
            + "Ae1F0snrmZyWp/bDGs4MEecsPf1xVstQPOBDeHHj0hah4l5hS8TJMX86Wo8bCHMcQhHsiFo70MA8\n"
            + "2GxnEeEAB0FsTI3O2ldXlVvzVhZA1RXPAO3cX1GSwDwgRYB99wgB4ejaDGFmQQTbBkgkdpKUomgC\n"
            + "SIZ1o2ZgPCbIbAgfklzh4YDcHn9+XuJaPjl07cwIHJBkBIGQvmA8ICOFCB+Ay0HlgLwgNA3VB6L0\n"
            + "3LNuOS2FbrVvLaoHwDwuVBwNXzyFISyc24+mxyt2HHrkZAW1vSbcI5HUBVegPESgaQFPgjvB4cC6\n"
            + "DKog4edcWnVb8WisOMqrst7s3xA8JkQaAB+AP6FpgEw0iOLmOxn29oek7ZHQeVc0IaULMDwgt4sA\n"
            + "HtSWYaR/uqaqUtXpMerpXveTglynwQ4NrqSwPDB8E1q/KxrBaNfsJ+JuuClip1//F/BqW9MHiCKR\n"
            + "F4A8GlodAB+NSoDaf+orppK426KcCFLMo8yCQ1ZshCRXcDwmiBdgH4UegeR/tVEJBL5+T1tLByNN\n"
            + "6l1CBNMgJmmwPBq6HQAfiJQBpSk7UzAlyECyDsQThGmJCUB9Cq98QXA8PkQaZV5WvKHhh7qbYBaT\n"
            + "yetO48tSm1hmFM9X5zKGYDwwmh0AvF6DQJ736ylV/TZhYaPZkRNSNDsOPGXMz1oAPEY+HWAfkg5B\n"
            + "8n/MuBEBdUy/e2NEPVDVpqyFQRWAfiA8KKgdAr8qBKGkjWqWR7FHpTBzgd+eotO2cOZSNyOWMDxM\n"
            + "ZB1ZT5gBweB9/OVWowvFJRvGhLlTNmOe46A9JNKAPBhMFwAfOD6haYhrWmnhebhxMcUfpeHkKobi\n"
            + "TllYV2A8KLocCB7YF6GlOlUHpNpPUC8RP4qoHOPp387HNyWMUDweu5EAH260geB+OzEyTS51y/66\n"
            + "bVN628YNb/CCzIjAPDREHQAfaJRhbNerDBlnen6bpOUEbx+kdBw6XyHgaQA8HtAVAB+CnoC1gLsP\n"
            + "q4Kbf76j0IVoudAVAuDDyyvQADw+uZEIH4OSQa0qq1srMql6CVufhm9zFSIA2rzR17SQPCaJiAAf\n"
            + "PhhBpYA69wP2CTgnPApotvn59iDbjW4BG8A8MjXBYr7WEuFodrtsTGGqvMiap4ekqH3+d80UMKd8\n"
            + "ADwoVYgFXyq3geGETHEhl3k9BThxnfwW9D0FH62zclLAPBo5i2K3ohLAtYFLny7Fu3LvMFUOVEBc\n"
            + "72XS5X4WrwA8KFuGDU/GS8Hk1YtQaRLaYcqffO53juUeMJqlcwO7gDworBcAH5LWINOASuX35hfh\n"
            + "LpY61KxKv2tk3rGzU7aQPCjGHQAfgvGB4YBL4mYp9ka1JShWIu1oc9s44LBjWaA8ID4XCr+VhkGm\n"
            + "f6dEkULVOKtreKpve8SYI5fTbP5TgDwmXh0CvFgeAaQ9ygo1Zh7WHqYYa3GvscOO16hkSNDAPBhA\n"
            + "FVgfLAPheH7rdThqIlFsY88c1q5rSSuoDPDbUDA8JqmAC++PJqDxM7uoQ84rUl6OohwT6+iXBEbl\n"
            + "k4ijIDwYWhpau5A2AfEq5zD5OcKlrEB1OZKfd6O41WXMDqCAPCrI3AAffgWhh9S7TyI6fkK8r6L7\n"
            + "2gEUQsdMKQp48YA8HsggAB+lROHTKstzcjxba1tEkygsyWhTI+dAn/39UDwePBwG/5ANINuUywFR\n"
            + "8n1ABBnzHdYNYWwX5yPAGj+APB5tkQ/vwsMBwy/LS0UlKEM9QBU4QaulGEHPZtrKbNA8NEIdBVgc\n"
            + "S0HhoiwtQSueGFTGoZ17hk7SIUAMio+5wDzYWh0KtvKGgeE7ysNdAAa6hMCyBPzEaBXwLzc++FvA\n"
            + "PCAiHQ6+xgWh4IcbID6LaEjWKAJQ66IGxRCqEfddbkA8OseJDhyij8HhLKpL0wYN2CFrhaqa+DNx\n"
            + "zMm3p6WgkDxEQhcMnVkMINMqpb+fINpoOEx79jBvSV9G6Z7HPecQPDC5kQAewAWBl5Cq83V5RMvZ\n"
            + "5BZowZHaAivmjf2EiaA8HogVCr+FHWGk0JumdcVsU9ZuphSnP2TIYLwNHL2rIDw0PhdeHPo8YaSe\n"
            + "GovBpv8nLPB927aisUldjIrubBFgPCJKFVgfKwChwl5ro0DP6FxG8WN0sfVJ/jBVkKHR4KA8RDeI\n"
            + "ApRSG2C9OUqO2BPkIvHSLbT1ANu1iZ4wNBrBwDwqRhpYHygD4WmBZxcPx6Qy7kaFDbzioCfFY+8M\n"
            + "5IOgPCA4IwVP8A8B4NdKoDM+j85u2zZYF2JYWMzhzZ/L6gA8NXodYr5uSSGHmQrXmyt5MOCiwJQB\n"
            + "OdxooFZQq9ZV0Dw0PhMWv9xRoXhsWlKIQ9URXiZ+/KIAwEaEM8MY5n6gPCY5i2VfM0TB4Je68lQR\n"
            + "ki/eZws1UgR7iFquFyngVLA8IEIXDc3ECeDxLFoHzSor3e0WAf/TCy94ROqdeoPfgDw+bB0EpuIW\n"
            + "gPFqTMojOsUnyl44TXKbxJMCGmc1ZzGQPAZsFwq34kmhaapaDMc7R0BxamnZWRsgcDVeD8nGzcA8\n"
            + "NDYdZHz0O0GsgiyRljR7PKWpWhq/hHs47hJoXJzRwDweMBsSuzoOgWnNmFbcB0vBvZOvBty3W6fw\n"
            + "ORmAhaLgPEQ5i2fvyQ7Bp2k7hm+vB0DvIh202roLxUESrGYPTkA8JjYfDjFsFcC1Clo2UnDP93m3\n"
            + "QhOcdeMYHD4fxkKu8DwayG9dniYB4UsoZWensFFBdNKd/RWqBM8Gr8qOAZ0APEhLgxa3ciOA8ABV\n"
            + "LIDSNkP8EeaRKjbJVGb6no/8khA8IKhv66eigeHgOoC2JNTb43EIAxgY8gyUfX+OGpZKYDxObF0P\n"
            + "ZfMQoa0OPCqCveX6ojeuF1cWqBQ1nNXKqHPAPBpbk+6/PpMB8NzkEMWaLCa56I7F2SWNvRDWmQjT\n"
            + "GrA8MomQD/+GQaGGfcqvoTdJ4Cw1mfa1v9w5dRlAudlB0DwmVYthD2qDQeHUtSwubYpEGodWKaxy\n"
            + "zoVhve43fUIAPES6HV99AhwhS4z3ZshbgiA80tuvLPQkoTAcjX2SKMA8KEolZU/QQcFogzwNmcB3\n"
            + "c0pG4ZIiAz/NqctUExTUEDwoNh0FT9YaQPB4Stu4RGbJSDK3uqUG58vnrUl9op2APBo2HQK3sAeB\n"
            + "4lorgDV30VHr5Loin+XnCJwEkrbq70A8JhIOYG93PuHpa0uTy4I/cYMUWxQP1Pw2gCpPT7idYDwo\n"
            + "iGtQfsfbcO+EoNBzAY5Q2kpjeOt4gkqq/d6zSS9gPCQ3avjVcZfJc4eV+nig8h/2/7+Dz/x7adAe\n"
            + "jwbsL0A8INsoIYNM8wH9hmBli+PDZ/Jmk2MFaFR2KBtzd4ZmADwpeGcph6Qd4f+KgzHWRS2xADhb\n"
            + "eqYlaosw0qxezbzgPAiVixGHrQwh5N5Q/R3OomHkm3UKcSKS3itD3W7r16A8TkxkK+h6CSHpwsMo\n"
            + "qPUKQEKK4R9TFfHIS6QbZksacDxpwVcL+ThvZakyEJuu1Zb0oUTjpRMUQG2gIIp9P+wQPGBYJE+n\n"
            + "ygKB7yLFteAMoTOdhezYxnnwI155+TK+BhA82HoUAdNdM8H81AaJaEtLeFzrz0pMMGdRDGgzBW/P\n"
            + "IDxUx4ZZhtDxQfWHEr+hV+/QAZSTqSSBqhOOAeajgwOQPOBxwQGHHj2B54hjXAz/UcYwWDnXXZzI\n"
            + "9FhHNbp0edA8GmQbWYfiHkHm/x3kcerFhzkzploVzWimXeQTwMOKwDzgQh17sgEA4ejXBeGv0eGV\n"
            + "gpyY/bXXKyGOpj2SrGmwPEkEY+glBABB+A8FC9VFSPj1/qzFUkQHoauAkwQaXMA8NFRgMJbrFOH7\n"
            + "m+SypF40SDyE9vM9GhQrTnKQnWJjUDw+fni2t7cCQfbcNWRh8MUIZMrCfPjko0jO9JDzsXKAPE7+\n"
            + "gCuVcgdB76kk+39tEOPaN9otrYwu9OWsOYcu7DA8SGycs+t4ASH7pOXzhXBd0YABpajGIf8s280E\n"
            + "BVVAoDw09l8eWwwJYeJ/Fo7c/hJbOxVW2uis5UygE0Qyxr6APAQ+Xpk6Eweh5v1ULGMmUMK8j/Gn\n"
            + "/xnTY28xvRku4VA8DshrIYJMFYHlIhcdNMrf9WtjMHJ8m0hNo7cEsZYfMDwOVB05lsIdAeKYUhzY\n"
            + "gy7YkFHSeA2FDrjolLnstwagPDQ+JBu6mBTh4eEWNTnMWXCpz55FSkiftH1nKPLadUA8KGxvUwj4\n"
            + "EOF5w+xndUpG7TO7C0yLUJfvReqSPBD0cDw8uiQP/4ADwWmlK+767XRTVHBJiiB24Lvka0tooPEA\n"
            + "PERs1ltLJBNB4H085J+rhxxID14OXe94NmcgzhbSPZA8RF4gB++GEKFO00u9qzmlTWeMzESB3b6t\n"
            + "BXb3LfBywDwo25Nf78YDweDSGsRJv/rcUii7BGcNVaNDsp6BhuNQPNhNgwCWaheBaQi7zppS8GiX\n"
            + "BOClkspn9dj/JiEJ0DA8PlTWB18ujYFpKsvWjJo6WqLH3gaPV4h5kkDwxKC7wDw0Q4hdfQwRQXgW\n"
            + "uhXaCyYZY1WiTTmmGA4BKH5ws4TAPES41ga/lwPhpcdLitplL1GERMQwGEQtgKd3VmTiszA8KEeb\n"
            + "WhxUR0GlrJqyyOZy2vBq+yG+pjoXqHBcra6rQDw0yNYH+4wD4YcuukdEtPn+rFZqd41a2lR4EBBL\n"
            + "35LAPCBUHV9fGI3heQB7lb8C2E/IxnCRBgW0xjNefZm4TeA8GjocXgtIOEGG0PqJXzKMwJTGeGoU\n"
            + "yr9zcx6b0H/I0DwwyB1lT5BYAcJ9y1P8t6ptNZ3es+XQw+gCmksAMSJAPBoo1ggfAUhgva5a4szf\n"
            + "st9NOpqQ0wvE2fQAQYgRxoA8THt7BgHaFaFLasu3eOx1U4jEnDp6bu/N3L66j1AiEDwOKNbwJ5hF\n"
            + "4cOAKvGLm1nVLcglfDJVSo43T+eKLD0APEiIJAOBGA1h4Tu7qeiDyk+UPmvQF8De5qTIfp7HPsA8\n"
            + "IDDWDYIAWKHgVVgAJkrnJVGLPYfrAI6TgdhU07rdgDwykmQN/YhhweJfqhAaj8n/0KSXH0NJiSHD\n"
            + "LdT96dTwPERC1gjHQAWA0jvbnQEU2XYE8vbPY19qvBVu4UEamtA8NKBdF8UahKHhhBvG3AltX1mm\n"
            + "PZagxrtoZbg6HCC1gDw+Qm8U0aQMIPnznIrxASQla+t4KnQwcGBc5SsUwR7APEC2JAwb1DjBpYIl\n"
            + "A8O5VV06MDIHFB5dJOmemW9nnSA8NDhsEh4Ig0HhKovgcQxOS57Z0lSY7sURiFzbPlB+kDw40X8N\n"
            + "oFYcocL2G9tKkdRLqlxHcwIBcX3IVc3HCZNQPCDZhwgfeJIg+Mace/WLTjFqBw9dNe6gcM1m4XWd\n"
            + "8KA8ashpC9QZBODwuFycYrcw9CHJLbBARnrAS0vmW7hPUDwouh8JhKUHAWjFPN/H2NzE4ZI4Glzj\n"
            + "jTnrWk1Iq1oAPEaIJAlPoBBBpDEaFqzqk8I1Byu8dR7lgezFXxqA/SA8cjeNCB+ZgkHg1R1cIJob\n"
            + "tERqSr18BmAb53Hc27oo0DxAyYhYH6MFoeB8dymH3hlIqFshElwNKrTkageKuPrAPES4bA/ebhbh\n"
            + "pT6cR2WrRjbHShT1hGaFEZ9ekaT8PoA8QLgjAB7WGAHg0U3zfeQXvGpRHezbaRRg8jTAdGto8DxA\n"
            + "yGwCvN0XgeF+zApndzjQUEnP3Tgy8OF2rygEOHFAPDRbkAGjnCWB4vFLanCCzru8BG+U/v+WPd7B\n"
            + "k/em5qA8cqmLWB6iiCDwMzLqkHG9n5ku3d1IjxM7I5EzBXEdUDw4u4cMX+SBYeGQPY+WNxW8M6Z9\n"
            + "BlXHLwpcuaMPIPvQPDl4HWQbLQrh4ZMpYhHfmskt8oEcAy5X07ay74jZ8DA8OLmJDJDyieHhlDxl\n"
            + "MbPnDGMby8rSjV6bpwql2OBD4DxoyGvqvcIEoXuCoFzvU3hqH/MWnlI/NDYOya6QBo1APN8DjQVf\n"
            + "IZmB9FXscGG/FvlejdPKQC0Tb6FU0+n5z6A8eFuFUB/MBsHwx10HLX00k6dmtugxuZwGDKXVBIhW\n"
            + "4Dw6hmkBxdgSwePGvWpBYR6gHbuWNoTErBfpClhdGsXAPG7IIxUguBhh9wttMDgayaHykqh3no7T\n"
            + "3HChMTSkIUA8OIhpBITCgeHh0zEPjCRHFLqpqRtaammsvTxV4YRSMDw+PiMXxAoUgfBt3X1LOQy2\n"
            + "CnIdzpCISFnsYoGdTRjAPEC5WgAefoyh54SkyJks/4cO1e3XbI1MKJj+Sjy8MiA8bnuHCB8OR+Ht\n"
            + "iP7o37FxTdQjikIkKGY1+5v5jrriQDxyc4cAHtwYYfOALbp3oZWEYClehO0J5ZLZ3zI35+LQPDih\n"
            + "hwgfhAHhrNRNCzjuC5VS7FzR8xYYj6hWEoa13sA8QH5sYrc+UiHzkMFWvmiQNijMM9os28yM1VFX\n"
            + "T+tKUDxyMiMQNIQjoesa0bGy/J63rWBL+HHo7TNqmE4orW2APECIJVmBwixB4YPCw6n4egN33qBA\n"
            + "lXHE7GMSoXkl/rA8QLgdFJGMSSH9gzKxjhYLi4j4eaaLymlCcKg8OmRiwDxojGsEG4IDwfcurbT/\n"
            + "a42IboMT6ORWvQynWtEww48QPHI/iwgfiBah84AeRs9vckpC5J2Gs7XAWVAzw4HaRZA8cshpAB+E\n"
            + "AeHzgDwF4ykrAPGE5FPJjX34Xu7HhthyADw+yCNaHa0QYceuctTjsB99fv0OgVgjgLDjQsH4iaSg\n"
            + "PHI0awhK2Qjh3iseSXf2zUn+lrys4CiS17kbpRSAJNA8OjgjWB/ZBaHkevuNc/IKWQshf0WHAKFe\n"
            + "adtJdcdvUDxyyGkAHn4aQezUzsERnMxgsmOzvlsLt5uL0sUybD8gPEh+JQGEqiPh5nvMtBSdjQkV\n"
            + "bcW9F8RN+DN+AsAfTkA8QIZsCB+GCkH7gB6kziHhQXFeEiAnIgoPsfukr6RdIDw0qGkEG4wHgeBr\n"
            + "zCdz8UUJSCMEXsgZKBM6xU558ZUAPHJmHRAfjAlh6YBeMTzsUXM5oVuxIw8NTJmLw0GNSPA8Oq2H\n"
            + "AB+SKyHzgT22zkY4icmuhQayyDsBLQL4uJPdUDxyRiMIH4wdQeWKLDhqWoo6emIKrtwcqDSB9A/e\n"
            + "doBAPEA4aXAf6C2h6H1dFhkb4blCm4M1D3LX/yXE5b5eRrA8cnGHCB+WWgHnqy2Y84T/uolQ/Fdy\n"
            + "Cu2LCFamHIx5IDw4rYYBCwAVoeWlTUhx/Bmvfmb3SVEE+Z2FXuWLgWhAPHJoIwgfKAHB64oteHvM\n"
            + "R6UTXkdUV+gJHyCsTX/gQ8A8aKxrCJWhFKHlhN094Baio15NFM5psBQWgLgjd6PAADw4Xm8RZRQJ\n"
            + "weL0DQ8ehjOsgp8QbFqm9L/Wu916wv4gPHIyaQAeRg0h5N/NjQ9nX54niiPltg30DW6f+QPNTRA8\n"
            + "bmYlCB/gROHo0d37cDzCqzYbQLcSumb7BlLH6vxLIDw6qGkLRVgaAeLXnj4MTrN1pGo+mF9rDCzw\n"
            + "TvX8GEPAPHKIJQkJ/ksB7N5dYcIvapCZuvhVMxH4gPhRh+R90FA8csmHAB8uE+H3gD3QO/HMonl2\n"
            + "epvOiTLsaTWy+chfsDw6qG8IH4wWAeB1nV45eV63yez1pxHZPTsi0rdsztAgPEZ7fwiyXABh7N1d\n"
            + "hCoczr6JCr+zVOJhKgRgmCZkkEA8OthvAE7RIeH3Kz0gjeh3gMb4eg6vjV85X0G0mZ8FADxyyGkQ\n"
            + "H4QMAfGoXadLdxiRDs0r8txUdgckauWnPQPQPG6obAEh/Rgh8ws9ILvijJ8BS7M0FL/kRUoErqGM\n"
            + "MQA8QG2ICLUsGCHpo515LX+uklWCtuOlzVKYUx3XW3HlkDxIZGkAhNgHIezQvegfg+y7cyfVOcL5\n"
            + "SYX63AQu/weQPHJtiwAfjJcB7YA9WSuELJQhlgZegx5aB5Gw8E9CqBA8NJppAwngEeHm182oHAVo\n"
            + "kg7+wMkeF3b6XNUS5ra4IDxGyCUJsjgLwemH3eNOhqOOZqEzC1e5KDoM1tH3hl5QPHKIbwRalJyh\n"
            + "+Pm9wc5zsK1dDCsrKhlYoZD8FFfv0cA8QD5pCBzOE8Hjnl1ep8mJiwx2pwnAAbvacy92+Xv14Dw6\n"
            + "k4sGGfo0weX8TVV2vQWAmGvMYw5iCpMf7GA55r0QPGiGaw3QpIlh54EkfEeFlSD9hvvj1pV3nOMh\n"
            + "BlcT+JA8OGwdAYGmFKD5gU1qEfjwsyqHL/zKKLn2Q8SGqsU9QDxGh4cDCbcKweB/Td+IyryseKoN\n"
            + "JHvSk1/ZU4PkwLYwPDpbiwmB5IvB6YEczpocDgLdQ2WuwkJ48IqJVF/rWFA8RshsB+v0GkHk3U4I\n"
            + "dzSabbMrwkzxKbb2MgnBKMORcDxyNh8JC51S4eTVOwlWFwlOlnruznlt7x6T+S6xK4ZAPECUbwBh\n"
            + "qCDB5EpN7aoWLZkEFrbcsVPDqfWHh5X8xFA8brgkAYHmC0DXK7yN4uKuFMtE3PWbCDhbxRbFegKO\n"
            + "UDw4yY0AHn4QwfEpTVYK6K6TzwRloZ0IEDkFYvhmvlHQPDQ/fw1LQAOhy4y7yYt2vVmnjGuMhNCE\n"
            + "59RR1oOfXlA8QKjWAYHoE8lgfbzBPjvt7x+wslLcToTXJ43lmn3MMDw6WmkBC58EoW96TLxXQaA2\n"
            + "TUU+50w/bg8yfdfHsOkAPECJiwVOKpCB4nvNMrnIB6LghKQ9dN2w6Ui99Rhkb6A8OrglCYMEBaD3\n"
            + "L7vYzaZVdSMPM8ncNKIbnx7WIU7TIDxyyB0BgYsA4a2QvALvGDDX6ActhB007goKGZvf7ROAPEKs\n"
            + "aQkr8DHB8/K71PFCg25Pl02RXWTNoS8ri0ZKT1A8RIIdAYnAhaDxgkyvTcyTzjQ1osoi3UNbkIzh\n"
            + "38kTIDw4iGkWgEwIIeGDLel1qlGKp6W3XcCelTHTbqo0RwZgPESpiwDiLGMBeNK7RmS11nDDD4OS\n"
            + "T3+WoTz2oXmt0MA8bjeHDDRgFgHhMTzdazmRyYcYhoMl9Xv7oU5lk5qYoDxANBwINTg5QeB9HJd6\n"
            + "ycb15hirUh4ChchVxZOdfPogPEA4IwoZtTLhp9IcHjAhsyoB3mAJrcNkBoQPuCr568A8OjWLFBug\n"
            + "gSHhOqwkfbUH4nsGFfCC6zsBrVZh/QL+gDw6emkP1SAwYfDRHWLoFESXnPkmVmwLzwooqdMJ/0MA\n"
            + "PDq5WgjFuitByvf9AT63wbqjJt415xUUNe9X4OfWHlA8IMeACujKI+Hpal23tLrrgD34N4YYgKMh\n"
            + "kwRB3yke4DxGk4sAycAFoW2SPezO8TmXKd4WJfYS/nYp0V7Vj78gPDR+ZBaBGhjh4FxdD2/zT6Dd\n"
            + "VY0wuhDcYV7NQEdbuiA8OoYjAYlsA8HhiD3ve4RohMVd9ZHHAQTvMfSADh+IYDxId18TOSgJ4emE\n"
            + "Deopns2uvu3B6zcv0FeA7PDOYU6wPDZ/iwSJagThptY83J7qe8E5/poatzwJTcjB9npCZxA8RnIj\n"
            + "DgGAWwHlgb0cJ5IKpznMfn0ffa8UO5cKDjnsYDwoiX8P85yAIeTXnLU5zqvvr9SKty0apjd34lDI\n"
            + "InpgPGp+HQ9RlBbh4YM8Sot8qO62IsoUMHe2WZgQF6eeT4A82MgjB/OcCWF607tUzv86fudnQMuz\n"
            + "7yBh24KaaNCmsDxyiYsP+ZoUgWrQPXpx636rDRzC0flJYW04KmuafUlAPN65fwWikgzB4NdL99AI\n"
            + "s1ouBsiMwQDYb5zga8Kf3mA8eF4dD/mYE0Hh2bzdj90/FbUGeGBhWbID6uPaiIt9gDxEMmkBo9xw\n"
            + "gcJ6vCOwssNZxAiH1zlgHfoofk7EUENAPGjJfxScXAWh6HDV2ZBLZlZw13gDE/iDjVrRdXOyp4A8\n"
            + "ICOBBrF8EsGsgutiPqJgaOhsh/6XuzvvSPD9NOheMDxGyYsLp1QbYfB6zIHeWmkxu1+YGUmpiWwN\n"
            + "CF3AYmdAPC4/eQPs3UgBx4M7kQ7zeXn9bedq8GdXMZl+XMPUUdA8RIbcDdk4BKHhuL3PU1eckoD3\n"
            + "VGKrHzHc+9U1ieRfEDxAP2MGAZAWwPDWOh54rG4BTdFisA8Jt9iLBXPCiq8APCDIGwu7hhSB4Hk9\n"
            + "0sXvFqIYYD3TnL8UCN+ULFo86SA8QkIlB2GgmeHhIsrLDi4R1RNBSIsbcMNzM6/sq0zKwDw6eiQL\n"
            + "xQ4XweGBPGPewzjj0+lTK0D6QBnx2hT4eEpAPDTI1gAWJAwB4NO7msWhPUqk0xQPEpf8pxPrVjTD\n"
            + "jXA82LgkD/eKB8HhgLv0MLo9R0iTauhkIddg92oIMe97MDw4ktYAkYgZQXidPHfhVThmau94xocy\n"
            + "m9FNj1S2MAtQPDCGJA6y4APhpS07dxh5H1UcvaVvqN9fxixSlaa4iMA8KKDWB7AbQaDTaDzLU6xg\n"
            + "FWPeHFjCu/O7vm3DjV6WMDxAiX8P18INocOHPB30PJEnUP7CbDrOJCKop1EUYqtgPCB7iw8ecw2h\n"
            + "4c08rKbnnfQbzBdTDWHRI90umI+0m2A8MntjEurACmGnJU3+QO95tMMsQEOVfQWlTbGwWHwYgDxA\n"
            + "eiMPWRQVAenkHUi4F6uifPTIsrxVZ1EbBBRBmbkAPCh6aBVxlBJh4N4tR0OSOIMCDgOweYGjqnKY\n"
            + "rcanZHA8OH4cCsvIDkHhjeyEA+yIDUKxGWdqvBRpy8Kgx2I2QDw0k3kLoTQFofGjnBKXxtQHuQFR\n"
            + "APBjC2FEQEkQvtwQPDSIHAmBmBBg2snMaAa2KxEYfptV2xHhVtNlZ3QL+SA8NIlfD6oLCwG1sVwn\n"
            + "i7OwMZbfbHYnU5RslImUMSwIEDzeiCML26IPAeE5TaLn266o+gMdRbuMmTFPYB6obe1wPEZ6ZxGY\n"
            + "4ghB4SwtWnjd1YKblr5sx92gheLmib1TNDA8MJQkC5OQDCGkW5znVuUyDbVgYAt1jNx1qaLOJvza\n"
            + "MDxCqGkM+djCAeFlPNJ1SRE07WpamughjUYUUZTHznOgPEKVgF/kcBDhtfP0IwY+zgB0ztIVyoqG\n"
            + "yDogGZQjWuA8KKmLD8QUgGFLzIr/oyXizZiNFTal2bUF75+ZjAIgUDxSe4sOrZpLAaWWNKOT+YEE\n"
            + "tHXryrJWrpS2CZhAwA9gPCioZgkTxBBh4dOaYGlRHOpjVsDtLV7QL/BKeaEaJ1A8VIYdDND6GkHh\n"
            + "1E1KVpGGlTDiKDlvKEuAttShzXjZ0DwyiCMMgYIpQeGGPQhPMeeQeUFeB2/W54kaYjbVFkIQPEcP\n"
            + "gQnSACkh4ZDdKbaiOJjDvepcIr4I9uYgTDRZU2A8PqmLCQNwEgHhZLxK89XIKRVU/gV9/ZPmyFq9\n"
            + "e5JzkDxSkYAMAnQRocsmHeN8Lf2wC3Psour9J8ESGQHokq9APEjIHAxg/IEh4YILvEx2JGKc/Mev\n"
            + "8Ri0ftI9pVmXMwA8TluLDAJIDCFpJRweVGEZOaxAXqltdQENY3Cix0R18DxK24sOqN4dYeGBfCly\n"
            + "2Ig04GVoo8eaJFKi6MMtUusAPDR7h3dRlB0A8H9cWiD66gncYmlO4oY4Nl0ib5EAvAA8NLolDWc2\n"
            + "UOHlghxxusKSwcE15mscQxytBAHBW4JKwDwoS4Ut4YIQ4eOE7MCpM+/+BS1eyvp1nBgKgilYEfLA\n"
            + "PDqKbw/zzArhbEn9pNAbMYzJ4iEqiJi0XWJ86bdYywA8QCWLGj85I2HxiJRWWTlPxFqLY8AwNBT9\n"
            + "BX1GLvAcADw6P4dZC8wWAeXR9U0MhCEVobERdTFPIMsI04iA52WAPHKhiwyRyIAh6Yx98M0nma4x\n"
            + "qkhdlurXnm7y1A+BioA83kGHEJH3CWHp4oQDUYHOLU9Nlq6cl90PcHYXJaQGUDxGTYINVQIGQPUg\n"
            + "He2VnUmpDjoue7XkywvR/irgY9DQPDRfkwgDGATh8DRdFlhEebxiXI+dXqb4m8xPyOtiOnA8MHGI\n"
            + "CgygEsHwQRwmZ4RhL779hYhW4hXaAFMH2RECYDwwP4sJAlUKweUkzuyins5QrqEAMLM7EillM0iE\n"
            + "gCaQPDR0HQ8qqjwB44cc5YLCrTy19jzpYt2eVjq7laQdQtA8RsgjCYGeAeHhfj5+jIrjaGML6zNX\n"
            + "k+FFSlOgq4IO0DwufX8JgZ8fAdLVTHPfco8eEs4mL9QdDFNSBk1vBiMgPDo0HR3RmkkB+S6AU5R9\n"
            + "Kk9hPYmACgI8J6Tn7N+GwMA8PpBjD6A6FCHth8FZb5gnOe8B3Q2ifyrpxpX/DOyEEDw6j1obqd4F\n"
            + "oe5igKgmnvDglsCGPVo0UOc/cYxH9jtAPEiJeRfREwsh+yug4wMjnG0hMNM6Y78cJSxb1HLog1A8\n"
            + "QpBxFn+cHGH61wEgkbJ8hvTDyUBB7NwrMK+MsC508DxIe4AQQfQQofZN8IzR1NX0+ejbnhoT/BU3\n"
            + "a8NgXahQPFR6dxQauQKB98BTtmFdYLHNrKwih+jmgTe3nPurM5A8+Q+AcECMCYh3DuB8idpL72J5\n"
            + "3ghyIHMrrVS32pY6gDxwhm9X5gQFofTWYIePOCCN9cYZItTOPoE5Bh2Jw4FAPHMDYw3TMIMB/yzB\n"
            + "tc8iPr0YdSyUgfxCCo6V39TBoWA8cJBrCrnuCmH/ANEwWny/RTypNQucBUiQ0oRyA/tYsDxA/GQT\n"
            + "6IgDge+RYHtGxo/b2467N8dP2hyKN9uynaLAPFSRWhK9UIFh+wddRnoaqYljm4989Q5lbjh8Yisa\n"
            + "S6A8Ro9jEquyBKH+DyLkKIadQqY7E+Id15O2wfKwu1y6cDxWkWASq2gJIerBjTqP8umIj7Aulesd\n"
            + "bDrGyT4LTKXQPG8FYQwa4CGh6Nl8ulPq9sX+9lVkEUIyBzcSSqwvt1A8RpBjV1k2CXDlImBkCl6y\n"
            + "61nqJz+UP2F4PiYbg4aCkDw0fiMIQkMEIekgEn+5kXGykH5VNbFQoPUqbiE1juTwPGqQcRVRmALB\n"
            + "9A4AqWvST8QF/tCW4Tc0J6ZTbJKvFiA8UoZnDUZWC0HpMLECkYBdUf3JWZlfI4wKumxWroXLUDw6\n"
            + "h38XYaQwQfPqEvrkBUfTbYXVdkxS4F83ssPpEkOAPDSIYQ/zABBB4Bx96t5FN4eDMnUi7tMJtSsb\n"
            + "iVSapwA8an5pDUS+AeHwcxT2a5BMK0tAtQYLZevOLKrYrmhMADxENBwNUxuAQcsvvZsZPueUqNfn\n"
            + "JugLvo/BjC+4J1YAPGpaJAKyyBThxzG7Da0CKGrSyJVeo9ECH8HvCODggsA8SD2AD1/qWiH01X1Q\n"
            + "K1OQoKiIP2iahE+J9pY/2fj48DxCQYEAGHgRwehKvLKUvNcWgk9uwUjlT0rRH12YlrlAPGo/iwq7\n"
            + "sDUBeSu8+9RGQf96rNu+c8iPuu9EHpnuXKA8SD4jDJzsOEE/O5wmcOYNGlZByG4ieKp6LtGDeanD\n"
            + "IDw0ONZyrVoHIaeZ3Za4ju6MPkBJ8oa+fYopEsmmhyMgPDRAHAuxHQtB4YEcECrP2BlnEwGYPLcj\n"
            + "uQD7VJTBasA8RMgcD/rCVKHgdfzG+0iZJ7aNGpP5YidhNw3665zYADxA24tlSsKDweBfOyscXgJl\n"
            + "vnaNUah9NKGPe02aYU+gPN40HA/gOQ0g8S6cnZqOYjAAw5Wn8DxkSLXu/pKamQA8RkDWD9+9hGGk\n"
            + "1lyLBiM+LfJTylUWzxinqV3XnsPd0DzeuBwHyqI8AWiAvB2rmJooWRmJE9Gz6B3QtfbpRRGQPEjI\n"
            + "HF7ADBrB4JH7aJkeju/Kg8gKuZdmaiKXZ5CiJCA8QjgbAI3WHGGnLsuyDsqZe3oX6ZdSRzCSBCUd\n"
            + "hWhrgDwwyNYP37Yc4eDZ2+LYg1lPg7Rm5wAP1Qpg0nV0cpgAPDR6Iwuj6gKhaYVavzIi/hT9t0hj\n"
            + "3MT54KosZmQZ3sA8NCLWDJr4DwHhZxwgLC6gGEWgPHJPAXTtrzJV9nctEDweQ38OHw0aQaWMOxbm\n"
            + "E3BPt73MeoQho9/UqS0lFO+APEN6HF40MSSBaWj6+rfMBuXGwmWKfDibgja5bhM3RIA8KHolCruc\n"
            + "RsFombstbPZTVPp5zYO0rUQ1eMsmxC/uoDxCNBwEyJ5UwWmSShRNctcM8aiLMcTXxmGlrwpgB1tA\n"
            + "PDRMJAteqDhA8H/aDDS3qB6DBS9Bcjodse61aOC7ylA8RNAdARe4CeFotTq8mWjG6D1F67BRAlh7\n" + "jSDQ+W4WMA==";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case 1008:
            if (resultCode == RESULT_OK) {
                // Toast.makeText(mContext, "指纹验证成功。。。。。。",
                // Toast.LENGTH_LONG).show();
                Log.d("fate", "指纹验证成功");
            } else if (resultCode == RESULT_CANCLE) {
                // Toast.makeText(mContext, "取消了指纹验证",
                // Toast.LENGTH_LONG).show();
                Log.d("fate", "取消了指纹验证");
            } else if (resultCode == RESULT_FAIL) {
                // Toast.makeText(mContext, "指纹验证失败", Toast.LENGTH_LONG).show();
                Log.d("fate", "指纹验证失败");
            }
            break;
        case 1009:
            String info = "";
            if (data != null) {
                info = data.getStringExtra("info");
            }
            if (resultCode == SUCCESS) {
                // 若有指纹，则需要绑定用户信息（用户名，密码）
                Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
            } else if (resultCode == FAIL) {
                Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
    // public void showTimeDialog() {
    // LayoutInflater inflater1 = LayoutInflater.from(MainActivity.this);
    // final View timepickerview1 = inflater1.inflate(R.layout.timepicker,
    // null);
    // TextView curDate = (TextView)
    // timepickerview1.findViewById(R.id.timePickerTextTv);
    //
    // ScreenInfo screenInfo1 = new ScreenInfo(MainActivity.this);
    // wheelMain = new WheelMain(timepickerview1, true, true);
    // wheelMain.screenheight = screenInfo1.getHeight();
    // String time1 = txttime.getText().toString();
    // Calendar calendar1 = Calendar.getInstance();
    // if (JudgeDate.isDate(time1, "HH:mm")) {// yyyy-MM-dd
    // try {
    // calendar1.setTime(dateFormat.parse(time1));
    // } catch (ParseException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // int year1 = calendar1.get(Calendar.YEAR);
    // int month1 = calendar1.get(Calendar.MONTH);
    // int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
    // int hour1 = calendar1.get(Calendar.HOUR_OF_DAY);
    // int minutes1 = calendar1.get(Calendar.MINUTE);
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // String dateStr = sdf.format(calendar1.getTime());
    // curDate.setText(dateStr);
    // // wheelMain.initDateTimePicker(year1, month1, day1,hour1,minutes1);
    // wheelMain.initDateTimePicker(hour1, minutes1);
    // final MyAlertDialog dialog = new
    // MyAlertDialog(MainActivity.this).builder().setTitle("请选择活动开始时间")
    // .setView(timepickerview1).setNegativeButton("取消", new
    // View.OnClickListener() {
    // @Override
    // public void onClick(View v) {
    //
    // }
    // });
    // dialog.setPositiveButton("保存", new View.OnClickListener() {
    // @Override
    // public void onClick(View v) {
    // Toast.makeText(getApplicationContext(), wheelMain.getHoursAndMins(),
    // Toast.LENGTH_LONG).show();
    // }
    // });
    // dialog.show();
    // }
}
