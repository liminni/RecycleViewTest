package com.lixiaoming.recycleviewtest.timeselector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lixiaoming.recycleviewtest.R;

import org.xwalk.core.XWalkView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lixiaoming on 17/2/8.
 */

public class DialogUtil {
    public void showTimeDialog(final Context context, Activity activity,final XWalkView webView) {

        LayoutInflater inflater1 = LayoutInflater.from(context);
        final View timepickerview1 = inflater1.inflate(R.layout.timepicker, null);
        TextView curDate = (TextView) timepickerview1.findViewById(R.id.timePickerTextTv);

        ScreenInfo screenInfo1 = new ScreenInfo(activity);
        final WheelMain wheelMain = new WheelMain(timepickerview1, true, true);
        wheelMain.screenheight = screenInfo1.getHeight();
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time1 = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"// 月份是从0开始编号的
                + calendar.get(Calendar.DAY_OF_MONTH) + "";
        Calendar calendar1 = Calendar.getInstance();
        if (JudgeDate.isDate(time1, "HH:mm")) {// yyyy-MM-dd
            try {
                calendar1.setTime(dateFormat.parse(time1));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        int year1 = calendar1.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH);
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
        int hour1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int minutes1 = calendar1.get(Calendar.MINUTE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(calendar1.getTime());
        curDate.setText(dateStr);
        // wheelMain.initDateTimePicker(year1, month1, day1,hour1,minutes1);
        wheelMain.initDateTimePicker(hour1, minutes1);

        final MyAlertDialog dialog = new MyAlertDialog(context).builder().setTitle("请选择活动开始时间")
                .setView(timepickerview1);
        dialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

        dialog.setPositiveButton("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context, wheelMain.getHoursAndMins(), Toast.LENGTH_LONG).show();
               final String hoursAndMins = wheelMain.getHoursAndMins();
               webView.post(new Runnable() {
                   @Override
                   public void run() {
                       webView.load("javascript:selectStr('"+hoursAndMins+"')",null);
//                       webView.evaluateJavascript("selectStr('"+hoursAndMins+"'）", null);
                   }
               });
            }

        });
        dialog.show();
    }
}
