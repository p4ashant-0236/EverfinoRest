package com.everfino.everfinorest;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class AppSharedPreferences {
    SharedPreferences sharedPreferences;
    Context context;
    final String prefname="EVERFINO_PREF";
    int restid;
    String restname;
    int userid;
    String username;
    String role;

    public  AppSharedPreferences(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(prefname,context.MODE_PRIVATE);
    }

    public void setPref(int restid,String restname,int userid,String username,String role)
    {
      this.restid=restid;
      this.restname=restname;
      this.userid=userid;
      this.username=username;
      this.role=role;
        sharedPreferences.edit().putInt("restid",this.restid).putString("restname",this.restname).putInt("userid",this.userid).putString("username",this.username).putString("role",this.role).commit();
    }

    public HashMap<String,String> getPref()
    {  HashMap<String,String> map=new HashMap<>();
        map.put("restid",sharedPreferences.getInt("restid",0)+"");
        map.put("restname",sharedPreferences.getString("restname",""));
        map.put("userid",sharedPreferences.getInt("userid",0)+"");
        map.put("username",sharedPreferences.getString("username",""));
        map.put("role",sharedPreferences.getString("role",""));
        return map;
    }

    public  void clearPref()
    {
        sharedPreferences.edit().clear().commit();
    }

}
