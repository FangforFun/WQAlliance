package gkzxhn.wqalliance.mvp.model.entities;

/**
 * Author: Huang ZN
 * Date: 2017/3/11
 * Email:943852572@qq.com
 * Description:
 */

public class InfoChangedEvent {

    private String faceUrl;
    private String userName;

    public InfoChangedEvent(String url, String name){
        this.faceUrl = url;
        this.userName = name;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
