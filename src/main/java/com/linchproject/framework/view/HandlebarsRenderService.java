package com.linchproject.framework.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.linchproject.core.Route;
import com.linchproject.framework.i18n.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Georg Schmidl
 */
public class HandlebarsRenderService implements RenderService {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsRenderService.class);

    protected ClassLoader classLoader;

    private Handlebars handlebars;

    public HandlebarsRenderService() {
        TemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setPrefix("/templates");

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

            return template.apply(context);

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
