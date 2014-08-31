package com.linchproject.framework.view;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.linchproject.core.Route;
import com.linchproject.framework.i18n.I18n;
import com.linchproject.ioc.Initializing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * @author Georg Schmidl
 */
public class HandlebarsRenderService implements RenderService, Initializing {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsRenderService.class);

    protected ClassLoader classLoader;

    private Handlebars handlebars;

    /*
     * This JavaBeanValueResolver, by default, is a singleton which uses a cache. To ensure that
     * the cache is cleared every time this service is created, we create a new instance, which
     * will be given to the context.
     */
    private JavaBeanValueResolver javaBeanValueResolver = new JavaBeanValueResolver();

    @Override
    public void init() {
        TemplateLoader templateLoader = new ClassPathTemplateLoader() {
            @Override
            protected URL getResource(String location) {
                if (classLoader != null) {
                    log.debug("using {}", classLoader);
                    return classLoader.getResource(location);
                } else {
                    log.debug("using {}", Thread.currentThread().getContextClassLoader());
                    return Thread.currentThread().getContextClassLoader().getResource(location);
                }
            }
        };
        templateLoader.setPrefix("templates");

        this.handlebars = new Handlebars(templateLoader);
        this.handlebars.registerHelper("path", new PathHelper());
        this.handlebars.registerHelper("i18n", new I18nHelper());
    }

    @Override
    public String render(String templateName, Map<String, Object> context) {
        if (templateName.startsWith(File.separator)) {
            templateName = templateName.substring(1, templateName.length());
        }
        log.debug("rendering {}", templateName);

        try {
            log.debug("compiling {}", templateName);
            Template template = this.handlebars.compile(templateName);

            Context handlebarsContext = Context.newBuilder(context)
                    .resolver(MapValueResolver.INSTANCE, javaBeanValueResolver)
                    .build();
            return template.apply(handlebarsContext);

        } catch (IOException e) {
            throw new RenderException("template '" + templateName + "' cannot be loaded", e);
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public class PathHelper implements Helper<String> {

        @Override
        public CharSequence apply(String path, Options options) throws IOException {
            try {
                Route route = (Route) options.context.get("route");

                if (route == null) {
                    throw new RenderException("path helper was called without a route");
                }

                String url;
                if (path.length() > 0) {
                    url = route.changePath(path).getUrl();
                } else {
                    url = route.getUrl();
                }
                return url;

            } catch (ClassCastException e) {
                throw new RenderException("route is not a route" , e);
            }
        }
    }

    public class I18nHelper implements Helper<String> {

        @Override
        public CharSequence apply(String key, Options options) throws IOException {
            try {
                I18n i18n = (I18n) options.context.get("i18n");

                if (i18n == null) {
                    throw new RenderException("i18n helper was called without an i18n object");
                }

                return i18n.getText(key);

            } catch (ClassCastException e) {
                throw new RenderException("i18n is not an i18n object" , e);
            }
        }
    }
}
