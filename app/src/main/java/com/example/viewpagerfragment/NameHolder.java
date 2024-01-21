package com.example.viewpagerfragment;

public class NameHolder {
    private static NameHolder instance = new NameHolder();
    private String sharedValue;

    private String uid;

    private String file_address="123";
    private String release_content;

    private NameHolder() {}
    public void setRelease_content(String msg){
        release_content=msg;
    }
    public String getRelease_content(){
        return release_content;
    }

    public void setFile(String file){
        file_address=file;
    }
    public String getFile_address(){
        return file_address;
    }//用户头像地址

    public void setUid(String recuid) {
        uid = recuid;
    }//用户uid


    public static NameHolder getInstance() {
        return instance;
    }

    public String getSharedValue() {
        return sharedValue;
    }//用户名字

    public void setSharedValue(String value) {
        sharedValue = value;
    }

    public String getUid(){
        return uid;
    }
}
