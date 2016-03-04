package com.example.weijunpeng.infiniteindicatorlayout.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by weijunpeng on 16/3/4.
 */
public class UiHelper {
    public static void shortToast(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


}
