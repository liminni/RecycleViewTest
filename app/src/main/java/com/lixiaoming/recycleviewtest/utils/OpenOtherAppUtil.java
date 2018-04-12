package com.lixiaoming.recycleviewtest.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by lixiaoming on 2017/12/6.
 */

public class OpenOtherAppUtil {
    /** 根据包名和Activity名打开另一个应用，参数启动时传的值 */
    public static void openOtherApp(Context context, String packageName, String classname) {

        // 获取包名 activityPackageName = 参数packname
        String activityPackageName = packageName;
        // 获取Activity名
        // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
        String className = classname;
        // LAUNCHER Intent
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 新建一个task来启动应用
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 清除task中已经存在的应用Activity信息

        // 设置ComponentName参数1:packagename参数2:MainActivity路径
        ComponentName cn = new ComponentName(activityPackageName, className);

        intent.setComponent(cn);
        String userName = DESUtils.encrypt("anlun");
        String password = DESUtils.encrypt("anl@1234");
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "无法打开该应用！", Toast.LENGTH_SHORT).show();
        }

    }

}
