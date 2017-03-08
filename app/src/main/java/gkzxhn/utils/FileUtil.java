package gkzxhn.utils;

import java.io.File;

/**
 * Author: Huang ZN
 * Date: 2017/3/8
 * Email:943852572@qq.com
 * Description:
 */

public class FileUtil {

    /**
     * 获取图片所属文件夹路径
     * @param path
     * @return
     */
    public static String getFilePath(String path) {
        if (path.contains(File.separator)){
            return path.substring(0, path.lastIndexOf(File.separator));
        }
        return path;
    }

}
