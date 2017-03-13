package gkzxhn.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

    private static String defaultMsg = "请稍候...";

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
     * 显示未签约对话框
     * @param context
     * @param cancel
     * @param comfirm
     * @return
     */
    public static AlertDialog showUnSignedDialog(Context context, View.OnClickListener
            cancel, View.OnClickListener comfirm){
        if (!(context instanceof Activity)){
            throw new IllegalStateException("show dialog must use activity context");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.layout_nosign_dialog, null);
        TextView tv_under_line_sign = (TextView) view.findViewById(R.id.tv_cancer_sign);
        TextView tv_on_line_sign = (TextView) view.findViewById(R.id.tv_comfirm_sign);
        tv_under_line_sign.setOnClickListener(cancel);
        tv_on_line_sign.setOnClickListener(comfirm);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 显示正在加载对话框
     * @param context
     * @return
     */
    public static ProgressDialog showLoadingDialog(Context context, String msg){
        if (!(context instanceof Activity)){
            throw new IllegalStateException("show dialog must use activity context");
        }
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 显示正在加载对话框
     * @param context
     * @return
     */
    public static ProgressDialog showLoadingDialog(Context context){
        return showLoadingDialog(context, defaultMsg);
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

    /**
     * dismiss
     * @param dialogs
     */
    public static void dismissProgressDialog(ProgressDialog... dialogs){
        if (dialogs == null || dialogs.length == 0)
            return;
        for (ProgressDialog dialog : dialogs){
            if (dialog != null){
                if (dialog.isShowing())
                    dialog.dismiss();
                dialog = null;
            }
        }
    }

}
