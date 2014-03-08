package com.linchproject.framework;

import com.github.mustachejava.TemplateFunction;
import com.linchproject.core.Result;
import com.linchproject.core.Route;
import com.linchproject.framework.components.ConnectionService;
import com.linchproject.framework.components.I18nService;
import com.linchproject.framework.components.RenderService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Georg Schmidl
 */
public abstract class Controller extends com.linchproject.core.Controller {

    protected ConnectionService connectionService;
    protected RenderService renderService;
    protected I18nService i18nService;

    protected Result render(String template) {
        return render(template, context());
    }

    protected Result render(String template, Map<String, Object> context) {
        return success(renderService.render(template, context));
    }

    protected Context context() {
        return new Context()
                .put("route", route)
                .put("path", new TemplateFunction() {
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
    }

    public class Context extends HashMap<String, Object> {
        @Override
        public Context put(String key, Object value) {
            super.put(key, value);
            return this;
        }
    }

    public void setConnectionService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public void setRenderService(RenderService renderService) {
        this.renderService = renderService;
    }

    public void setI18nService(I18nService i18nService) {
        this.i18nService = i18nService;
    }
}
