package gkzxhn.utils;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ShellUtils;

/**
 * Author: Huang ZN
 * Date: 2017/3/8
 * Email:943852572@qq.com
 * Description:
 */

public class Utils {

    /**
     * 判断网络是否可用
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     *  context 上下文
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isAvailableByPing() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("ping -c 1 -w 1 www.baidu.com", false);
        boolean ret = result.result == 0;
        if (result.errorMsg != null) {
            LogUtils.d("isAvailableByPing errorMsg : " + result.errorMsg);
        }
        if (result.successMsg != null) {
            LogUtils.d("isAvailableByPing successMsg: " + result.successMsg);
        }
        return ret;
    }

}
