package org.team10424102.blackserver.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.jetbrains.annotations.Nullable;
import org.team10424102.blackserver.config.json.Views;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "t_notification")
public class Notification {
    public enum Type {
        FRIEND_ADD("好友申请"),
        FRIEND_REMOVE("友尽通知"),
        GROUP_JOIN("入群申请"),
        ACTIVITY_JOIN("参加活动申请"),
        GROUP_QUIT("退群通知"),
        ACTIVITY_QUIT("退出活动通知");

        private final static Map<String, Type> map = new HashMap<>();

        static {
            for (Type t: Type.values()) {
                map.put(t.key, t);
            }
        }

        public static Type fromKey(String key) {
            return map.get(key);
        }

        private String key;

        Type(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return key;
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne
    private User target;

    @Transient
    @JsonView(Views.Notification.class)
    private Object data;

    @Transient
    private String[] contentParts;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getContent() {
        return content;
    }

    @JsonGetter
    @JsonProperty("type")
    public Type getType() {
        if (contentParts == null) {
            contentParts = content.split("-");
        }
        return Type.fromKey(contentParts[0]);
    }

    public String getExtra() {
        if (contentParts == null) {
            contentParts = content.split("-");
        }
        if (contentParts.length == 1) return null;
        return contentParts[1];
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent(Type type, String extra) {
        if (extra == null) content = type + "";
        content = type + "-" + extra;
    }

    @JsonIgnore
    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
