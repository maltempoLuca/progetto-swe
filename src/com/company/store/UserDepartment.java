package com.company.store;
import java.util.HashMap;

public class UserDepartment {
    private UserDepartment() {

    }

    public static UserDepartment getInstance() {
        if (instance == null)
            instance = new UserDepartment();
        return instance;
    }

    public int registerUser(String email, String password) {
        if (usrLoginInfo.containsKey(email))
            return 0;
        else {
            usrLoginInfo.put(email, password);
            return 1;
        }
    }

    public int loginUser(String email, String password) {
        if (usrLoginInfo.containsKey(email))
            if (password.equals(usrLoginInfo.get(email)))
                return 1;
            else
                return 0;
        else
            return 0;
    }


    private final HashMap<String, String> usrLoginInfo = new HashMap<String, String>();
    private static UserDepartment instance = null;
}
