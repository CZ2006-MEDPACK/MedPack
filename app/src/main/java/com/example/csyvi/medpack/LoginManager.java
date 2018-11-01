package com.example.csyvi.medpack;

import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Login manager.
 */
public class LoginManager {

    public Boolean validateRegistration(String email, String password)
    {
        Boolean result;

        if(password.isEmpty() || email.isEmpty())
        {
            result = false;
        }

        else
        {
            result = true;
        }

        return result;
    }

    public Boolean validateLogin(String email, String password)
    {
        Boolean result = false;

        if(password.isEmpty() || email.isEmpty())
        {
            return result;
        }

        else
        {
            return true;
        }
    }

    public Boolean validateEmailFormat(String email)
    {
        Boolean result;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches())
        {
            result = true;
        }

        else
        {
            result = false;
        }

        return result;
    }

}
