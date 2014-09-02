package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockHelperTest {

    @Test
    public void testApply() throws Exception {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("block", new BlockHelper());

        Context context = Context.newContext(null);

        Template template;
        String result;

        template = handlebars.compileInline(
                "{{#block \"test\"}}one{{/block}}"
        );
        result = template.apply(context);
        assertEquals("one", result);

        template = handlebars.compileInline(
                "{{#partial \"test\"}}two{{/partial}}" +
                "{{#block \"test\"}}one{{/block}}"
        );
        result = template.apply(context);
        assertEquals("two", result);

        template = handlebars.compileInline(
                "{{#partial \"test\"}}{{name}}{{/partial}}" +
                        "{{#block \"test\" name=\"three\"}}one{{/block}}"
        );
        result = template.apply(context);
        assertEquals("three", result);

        template = handlebars.compileInline(
                "{{#partial \"test\"}}{{.}}{{/partial}}" +
                        "{{#block \"test\"}}one{{/block}}"
        );
        result = template.apply(context);
        assertEquals("one", result);
    }
}