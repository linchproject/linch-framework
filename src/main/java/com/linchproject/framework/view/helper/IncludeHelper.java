package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;

import java.io.IOException;

/**
* @author Georg Schmidl
*/
public class IncludeHelper implements Helper<String> {

    @Override
    public CharSequence apply(String path, Options options) throws IOException {
        Template template = options.handlebars.compile(path);

        Context childContext = Context.newContext(options.context, options.hash);
        return options.apply(template, childContext);
    }
}
