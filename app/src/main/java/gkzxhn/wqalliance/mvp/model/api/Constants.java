package gkzxhn.wqalliance.mvp.model.api;

import android.os.Environment;

/**
 * Created by 方 on 2017/3/14.
 */

public class Constants {
    public static final String SD_ROOT_PATH= Environment.getExternalStorageDirectory().getPath()+"/WQAlliance";
    public static final String SD_FILE_CACHE_PATH = SD_ROOT_PATH+"/cache/";

    public static boolean notificationIsOpen = true;
}
