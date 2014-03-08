package com.linchproject.framework;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.TemplateFunction;
import com.linchproject.core.Result;
import com.linchproject.core.Route;

import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Georg Schmidl
 */
public abstract class Controller extends com.linchproject.core.Controller {

    protected ClassLoader classLoader;

    protected Result render(String template) {
        return render(template, context());
    }

    protected Result render(String template, Map<String, Object> context) {
        if (context == null) {
            context = new HashMap<String, Object>();
        }
        context.put("path", new TemplateFunction() {
            @Override
            public String apply(String input) {

                String url = null;
                if (route != null) {
                    Route newRoute = route.copy();
                    if (input.length() > 0) {
                        newRoute.setPath(input);
                    }
                    url = newRoute.getUrl();
                }
                return url;
            }
        });
        context.put("route", route);

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

        return success(writer.toString());
    }

    protected Context context() {
        return new Context();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
