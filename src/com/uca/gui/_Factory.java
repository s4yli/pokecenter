package com.uca.gui;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
public class _Factory {
    static Configuration configuration = _FreeMarkerInitializer.getContext();

    public static String genericGUI(Map<String, Object> input, String templateFile) throws IOException, TemplateException
    {
        Writer output = new StringWriter();
        Template template = configuration.getTemplate(templateFile);
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }
}

