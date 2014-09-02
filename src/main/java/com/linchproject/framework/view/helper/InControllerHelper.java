package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.linchproject.core.Route;
import com.linchproject.framework.view.RenderException;

import java.io.IOException;

/**
* @author Georg Schmidl
*/
public class InControllerHelper implements Helper<String> {

    @Override
    public CharSequence apply(String controller, Options options) throws IOException {
        try {
            Route route = (Route) options.context.get("route");

            if (route != null && route.getController().equals(controller)) {
                return options.fn();
            } else {
                return options.inverse();
            }

        } catch (ClassCastException e) {
            throw new RenderException("route is not a route" , e);
        }
    }
}
