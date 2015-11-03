package com.example.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "tUser")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int")
    private Integer id;

    @Column(columnDefinition = "varchar(20)")
    @NotNull
    private String phone;

    @Column(columnDefinition = "char(200)")
    @NotNull
    private String password;

    @Column(columnDefinition = "varchar(100)")
    @NotNull
    private String email;

    @Column(columnDefinition = "varchar(20)")
    private String nickname;

    @Column(name = "realname", columnDefinition = "varchar(20)")
    private String realName;

    @Column(name = "idcard", columnDefinition = "varchar(18)")
    private String idCard;

    private boolean enabled;

    @Column(columnDefinition = "enum('male','female','secret')")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "college", referencedColumnName = "id")
    private College college;

    @ManyToOne
    @JoinColumn(name = "academy", referencedColumnName = "id")
    private Academy academy;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private Blob avatar;

    @Column(columnDefinition = "date")
    private Instant birthday;
    @Column(columnDefinition = "timestamp")
    private Instant regTime;

    @Column(columnDefinition = "varchar(39)")
    private String regIp;

    @Column(columnDefinition = "varchar(200)")
    private String regGps;

    @ManyToMany
    @JoinTable(name = "tFriendship",
            joinColumns = {
                    @JoinColumn(name = "user_a", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_b", referencedColumnName = "id")
            })
    private List<User> friends;

    @ManyToMany(mappedBy = "friends")
    private List<User> friendsOf;

    @ManyToMany
    @JoinTable(name = "tSubscription",
            joinColumns =
                    {
                            @JoinColumn(name = "broadcaster", referencedColumnName = "id")
                    },
            inverseJoinColumns =
                    {
                            @JoinColumn(name = "subscriber", referencedColumnName = "id")
                    }
            )
    private List<User> subscriptions;

    @ManyToMany(mappedBy = "subscriptions")
    private List<User> broadcaster;

    @OneToMany(mappedBy = "promoter")
    private List<Activity> hostActivities;

    protected User() {
    }

    public User(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, phone='%s', password='%s']",
                id, phone, password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public Academy getAcademy() {
        return academy;
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Instant getRegTime() {
        return regTime;
    }

    public void setRegTime(Instant regTime) {
        this.regTime = regTime;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getRegGps() {
        return regGps;
    }

    public void setRegGps(String regGps) {
        this.regGps = regGps;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFriendsOf() {
        return friendsOf;
    }

    public void setFriendsOf(List<User> friendsOf) {
        this.friendsOf = friendsOf;
    }

    public List<User> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<User> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<User> getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(List<User> broadcaster) {
        this.broadcaster = broadcaster;
    }

    public enum Gender {
        MALE, FEMALE, SECRET
    }

}
