package common.im;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ScreenUtils;
import com.jess.arms.utils.UiUtils;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

import common.SuperApplication;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.ui.activity.LoginActivity;
import gkzxhn.wqalliance.mvp.ui.activity.MainActivity;

/**
 * Author: Huang ZN
 * Date: 2017/3/3
 * Email:943852572@qq.com
 * Description:网易云信SDK相关初始化及监听操作
 */

public class NimController {

    public static final String TAG = "NimController";

    public static void init(final Context context, LoginInfo loginInfo){
        // sdk初始化
        NIMClient.init(context, loginInfo, options(context));
        if (inMainProcess()) {
            NimUIKit.init(context);
            NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                    new Observer<StatusCode>() {
                        @Override
                        public void onEvent(StatusCode statusCode) {
                            LogUtils.i(TAG, "User status changed to: " + statusCode);
                            if (statusCode == StatusCode.KICKOUT){
                                // 被挤下线
                                UiUtils.makeText(context.getString(R.string.kickout));
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }else if(statusCode == StatusCode.NET_BROKEN){
                                UiUtils.makeText(context.getString(R.string.net_broken));
                            }
                        }
                    }
                    , true);
        }
    }

    /**
     * 主进程
     * @return
     */
    private static boolean inMainProcess() {
        String packageName = SuperApplication.getContext().getPackageName();
        String processName = getProcessName(SuperApplication.getContext());
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     * @param context
     * @return 进程名
     */
    public static String getProcessName(Context context) {
        String processName = null;
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }
        }
    }

    // 如果返回值为 null，则全部使用默认参数。
    private static SDKOptions options(Context context) {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.drawable.logo_login;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        options.sdkStorageRootPath = Environment.getExternalStorageDirectory() + "/" + context.getPackageName() + "/nim";

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = ScreenUtils.getScreenWidth() / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.drawable.logo_login;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }

    /**
     * 云信sdk登录
     * @param account  账号
     * @param token  账号token(密码)
     * @param callback  登录回调
     */
    public static void login(String account, String token, RequestCallback<LoginInfo> callback){
        LoginInfo loginInfo = new LoginInfo(account, token);
        NIMClient.getService(AuthService.class).login(loginInfo).setCallback(callback);
    }

    /**
     * 检查结果码  非成功登录情况下调用
     *   201	客户端版本不对，需升级sdk
         301	被封禁
         302	用户名或密码错误
         315	IP限制
         403	非法操作或没有权限
         404	对象不存在
         405	参数长度过长
         406	对象只读
         408	客户端请求超时
         413	验证失败(短信服务)
         414	参数错误
         415	客户端网络问题
         416	频率控制
         417	重复操作
         418	通道不可用(短信服务)
         419	数量超过上限
         422	账号被禁用
         431	HTTP重复请求
         500	服务器内部错误
         503	服务器繁忙
         509	无效协议
         514	服务不可用
         998	解包错误
         999	打包错误
         1000	本地操作异常
     * @param code  结果码
     */
    public static void checkLoginResultCode(int code) {
        switch (code){
            case 201:UiUtils.makeText(UiUtils.getString(R.string.code_201)); break;
            case 301:UiUtils.makeText(UiUtils.getString(R.string.code_301)); break;
            case 302:UiUtils.makeText(UiUtils.getString(R.string.code_302)); break;
            case 315:UiUtils.makeText(UiUtils.getString(R.string.code_315)); break;
            case 403:UiUtils.makeText(UiUtils.getString(R.string.code_403)); break;
            case 404:UiUtils.makeText(UiUtils.getString(R.string.code_404)); break;
            case 405:UiUtils.makeText(UiUtils.getString(R.string.code_405)); break;
            case 406:UiUtils.makeText(UiUtils.getString(R.string.code_406)); break;
            case 408:UiUtils.makeText(UiUtils.getString(R.string.code_408)); break;
            case 414:UiUtils.makeText(UiUtils.getString(R.string.code_414)); break;
            case 415:UiUtils.makeText(UiUtils.getString(R.string.code_415)); break;
            case 416:UiUtils.makeText(UiUtils.getString(R.string.code_416)); break;
            case 417:UiUtils.makeText(UiUtils.getString(R.string.code_417)); break;
            case 422:UiUtils.makeText(UiUtils.getString(R.string.code_422)); break;
            case 431:UiUtils.makeText(UiUtils.getString(R.string.code_431)); break;
            case 500:UiUtils.makeText(UiUtils.getString(R.string.code_500)); break;
            case 503:UiUtils.makeText(UiUtils.getString(R.string.code_503)); break;
            case 509:UiUtils.makeText(UiUtils.getString(R.string.code_509)); break;
            case 514:UiUtils.makeText(UiUtils.getString(R.string.code_514)); break;
            case 998:UiUtils.makeText(UiUtils.getString(R.string.code_998)); break;
            case 999:UiUtils.makeText(UiUtils.getString(R.string.code_999)); break;
            case 1000:UiUtils.makeText(UiUtils.getString(R.string.code_1000)); break;
        }
    }
}
