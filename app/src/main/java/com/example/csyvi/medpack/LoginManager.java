package com.example.csyvi.medpack;

import android.widget.Toast;

/**
 * The type Login manager.
 */
public class LoginManager {

    public Boolean validateRegistration(String name, String email, String password)
    {
        Boolean result = false;

        if(name.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            return result;
        }

        else
        {
            return true;
        }
    }

}
