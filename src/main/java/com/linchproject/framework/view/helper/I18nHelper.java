package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.linchproject.framework.i18n.I18n;
import com.linchproject.framework.view.RenderException;

import java.io.IOException;

/**
* @author Georg Schmidl
*/
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
