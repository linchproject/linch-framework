package com.linchproject.framework.view;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author Georg Schmidl
 */
public class MustacheRenderService implements RenderService {

    private static final Logger log = LoggerFactory.getLogger(MustacheRenderService.class);

    protected ClassLoader classLoader;

    @Override
    public String render(String template, Map<String, Object> context) {
        log.debug("rendering {}");

        StringWriter writer = new StringWriter();

        MustacheFactory mf = new DefaultMustacheFactory() {
            @Override
            public Reader getReader(String resourceName) {
                ClassLoader contextClassLoader = null;
                if (classLoader != null) {
                    log.debug("using {}", classLoader);
                    contextClassLoader = Thread.currentThread().getContextClassLoader();
                    Thread.currentThread().setContextClassLoader(classLoader);
                } else {
                    log.debug("using {}", Thread.currentThread().getContextClassLoader());
                }

                Reader reader = super.getReader(resourceName);

                if (contextClassLoader != null) {
                    Thread.currentThread().setContextClassLoader(contextClassLoader);
                }
                return reader;
            }
        };

        String fileName = "templates/" + template + ".mustache";
        log.debug("compiling {}", fileName);
        Mustache mustache = mf.compile(fileName);
        mustache.execute(writer, context);

        return writer.toString();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
