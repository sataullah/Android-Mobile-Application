package com.example.tranquillo;

public class dataholder {
    String UserName, UserEmail, UserPassword;

    //Add Constructor

    public dataholder(String userName, String userEmail, String userPassword) {
        UserName = userName;
        UserEmail = userEmail;
        UserPassword = userPassword;
    }


    //Set Getter and Setter

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
