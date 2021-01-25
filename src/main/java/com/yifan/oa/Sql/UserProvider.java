package com.yifan.oa.Sql;

public class UserProvider {

    public String userList(String where ,String order){
        return " select * from user where "+where+"  order by "+order;
    }
}
