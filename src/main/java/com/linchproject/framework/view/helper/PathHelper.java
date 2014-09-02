package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.linchproject.core.Route;
import com.linchproject.framework.view.RenderException;

import java.io.IOException;
import java.util.Map;

/**
* @author Georg Schmidl
*/
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

            if (options.hash.size() > 0) {
                url = url + "?" + getQueryString(options.hash);
            }

            return url;

        } catch (ClassCastException e) {
            throw new RenderException("route is not a route" , e);
        }
    }

    private String getQueryString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }
}
