package com.linchproject.framework;

import com.github.mustachejava.TemplateFunction;
import com.linchproject.core.Result;
import com.linchproject.core.Route;
import com.linchproject.core.results.Binary;
import com.linchproject.framework.assets.Asset;
import com.linchproject.framework.assets.AssetService;
import com.linchproject.framework.db.ConnectionService;
import com.linchproject.framework.i18n.I18n;
import com.linchproject.framework.i18n.I18nService;
import com.linchproject.framework.view.RenderService;
import com.linchproject.http.CookieService;
import com.linchproject.http.LocaleService;
import com.linchproject.http.SessionService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Georg Schmidl
 */
public abstract class Controller extends com.linchproject.core.Controller {

    protected ConnectionService connectionService;
    protected RenderService renderService;
    protected I18nService i18nService;
    protected AssetService assetService;

    protected SessionService sessionService;
    protected CookieService cookieService;
    protected LocaleService localeService;

    private I18n i18n;

    protected Result render() {
        return render(route.getAction(), context());
    }

    protected Result render(Map<String, Object> context) {
        return render(route.getAction(), context);
    }

    protected Result render(String action) {
        return render(action, context());
    }

    protected Result render(String action, Map<String, Object> context) {
        String template = route.getBeforeAction() + "/" + action;
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

    protected Result asset() {
        String fileName = route.getAfterController();

        Asset asset = assetService.getAsset(fileName);

        Binary binary = new Binary();
        binary.setFileName(asset.getFileName());
        binary.setInputStream(asset.getInputStream());
        binary.setLength(asset.getLength());
        binary.setLastModified(asset.getLastModified());
        binary.setPublic(true);

        return binary;
    }

    protected I18n getI18n() {
        if (i18n == null) {
            Locale locale = localeService.getLocale();
            i18n = locale != null? i18nService.getI18n(locale): i18nService.getI18n(Locale.getDefault());
        }
        return i18n;
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

    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void setCookieService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

}
