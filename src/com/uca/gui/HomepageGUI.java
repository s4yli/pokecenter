package com.uca.gui;

import com.uca.entity.UserEntity;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomepageGUI
{
    public static String getHomepage(UserEntity connectedUser) throws IOException, TemplateException
    {
        Map<String, Object> input = new HashMap<>();
        input.put("connectedUser", connectedUser);

        return _Factory.genericGUI(input, "homepage/homepage.ftl");
    }
}
