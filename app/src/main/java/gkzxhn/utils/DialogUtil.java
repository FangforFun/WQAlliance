package gkzxhn.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import gkzxhn.wqalliance.R;

/**
 * Author: Huang ZN
 * Date: 2017/3/6
 * Email:943852572@qq.com
 * Description:
 */

public class DialogUtil {

    /**
     * 显示签约对话框
     * @param context
     * @param underLineClick
     * @param onLineClick
     * @return
     */
    public static AlertDialog showSignDialog(Context context, View.OnClickListener
            underLineClick, View.OnClickListener onLineClick){
        if (!(context instanceof Activity)){
            throw new IllegalStateException("show dialog must use activity context");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.layout_sign_dialog, null);
        TextView tv_under_line_sign = (TextView) view.findViewById(R.id.tv_under_line_sign);
        TextView tv_on_line_sign = (TextView) view.findViewById(R.id.tv_on_line_sign);
        tv_under_line_sign.setOnClickListener(underLineClick);
        tv_on_line_sign.setOnClickListener(onLineClick);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * dismiss
     * @param dialogs
     */
    public static void dismissDialog(AlertDialog... dialogs){
        if (dialogs == null || dialogs.length == 0)
            return;
        for (AlertDialog dialog : dialogs){
            if (dialog != null){
                if (dialog.isShowing())
                    dialog.dismiss();
                dialog = null;
            }
        }
    }

}
