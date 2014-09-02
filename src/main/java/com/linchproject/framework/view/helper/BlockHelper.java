package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;

import java.io.IOException;

/**
* @author Georg Schmidl
*/
public class BlockHelper implements Helper<String> {

    @Override
    public CharSequence apply(String path, Options options) throws IOException {
        Context blockContext = Context.newContext(options.context, options.hash);

        CharSequence block = options.fn(blockContext);

        Template partialTemplate = options.partial(path);

        CharSequence partial;

        if (partialTemplate == null) {
            partial = block;
        } else {
            Context partialContext =  Context.newContext(blockContext, block);
            partial = options.apply(partialTemplate, partialContext);
        }

        return partial;
    }
}
