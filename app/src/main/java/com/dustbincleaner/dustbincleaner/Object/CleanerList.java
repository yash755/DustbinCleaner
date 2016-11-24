package com.dustbincleaner.dustbincleaner.Object;



public class CleanerList {

    public String id;
    public String username;
    public String name;

    public CleanerList(String id, String username,String name) {
        super();
        this.id = id;
        this.username = username;
        this.name = name;
    }
    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id = id;
    }
    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
}
