package com.goodtechsystem.mypwd.vo;

public class UserVO {
    private String id;
    private String name;
    private String password;

    public UserVO(){ }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }

    public void setID(String id){ this.id = id; }

    public void setName(String name){this.name = name;}

    public void setPassword(String password){ this.password = password; }
}
