package gkzxhn.wqalliance.mvp.model;

/**
 * Author: Huang ZN
 * Date: 2017/3/4
 * Email:943852572@qq.com
 * Description:消息bean
 */

public class Message {

    private String avatar_url;
    private String username;
    private String content;
    private long received_time;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getReceived_time() {
        return received_time;
    }

    public void setReceived_time(long received_time) {
        this.received_time = received_time;
    }
}
