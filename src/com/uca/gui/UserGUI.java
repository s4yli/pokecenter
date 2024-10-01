package com.uca.gui;
import com.uca.core.UserCore;
import com.uca.entity.UserEntity;
import com.uca.exceptions.ServiceException;
import freemarker.template.TemplateException;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGUI
{

    public static String getAllUsers(UserEntity connectedUser) throws IOException, TemplateException
    {
        Map<String, Object> input = new HashMap<>();
        input.put("connectedUser", connectedUser);

        try
        {
            input.put("users", UserCore.getAll());
        }
        catch (ServiceException e)
        {
            input.put("error", e.getMessage());
        }

        return _Factory.genericGUI(input, "users/users.ftl");
    }

    public static String getRegistration(UserEntity connectedUser) throws IOException, TemplateException
    {
        Map<String, Object> input = new HashMap<>();
        input.put("connectedUser", connectedUser);

        return _Factory.genericGUI(input, "registration/registration.ftl");
    }


}
