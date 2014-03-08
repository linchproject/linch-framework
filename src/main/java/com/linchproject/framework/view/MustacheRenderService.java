package com.linchproject.framework.view;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.Reader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author Georg Schmidl
 */
public class MustacheRenderService implements RenderService {

    protected ClassLoader classLoader;

    @Override
    public String render(String template, Map<String, Object> context) {
        StringWriter writer = new StringWriter();

        MustacheFactory mf = new DefaultMustacheFactory() {
            @Override
            public Reader getReader(String resourceName) {
                ClassLoader contextClassLoader = null;
                if (classLoader != null) {
                    contextClassLoader = Thread.currentThread().getContextClassLoader();
                    Thread.currentThread().setContextClassLoader(classLoader);
                }

                Reader reader = super.getReader(resourceName);

                if (contextClassLoader != null) {
                    Thread.currentThread().setContextClassLoader(contextClassLoader);
                }
                return reader;
            }
        };
        Mustache mustache = mf.compile("templates/" + template + ".mustache");
        mustache.execute(writer, context);

        return writer.toString();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
