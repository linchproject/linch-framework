package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IncludeHelperTest {

    @Test
    public void testApply() throws Exception {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("include", new IncludeHelper());

        Context context = Context.newContext(null);

        Template template;
        String result;

        template = handlebars.compileInline(
                "{{#include \"templates/include\"}}{{/include}}"
        );
        result = template.apply(context);
        assertEquals("test", result);


    }
}