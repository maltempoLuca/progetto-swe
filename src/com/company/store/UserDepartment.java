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
        String lowerCaseEmail = email.toLowerCase();
        if (usrLoginInfo.containsKey(lowerCaseEmail))
            return 0;
        else {
            usrLoginInfo.put(lowerCaseEmail, password);
            return 1;
        }
    }

    public int loginUser(String email, String password) {
        String lowerCaseEmail = email.toLowerCase();
        if (usrLoginInfo.containsKey(lowerCaseEmail))
            if (password.equals(usrLoginInfo.get(lowerCaseEmail)))
                return 1;
            else
                return 0;
        else
            return 0;
    }


    private final HashMap<String, String> usrLoginInfo = new HashMap<String, String>();
    private static UserDepartment instance = null;
}
