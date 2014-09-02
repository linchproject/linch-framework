package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.linchproject.core.Route;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InActionHelperTest {

    @Test
    public void testApply() throws Exception {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("inAction", new InActionHelper());

        Route route = mock(Route.class);
        when(route.getAction()).thenReturn("test");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("route", route);

        Context context = Context.newContext(model);

        Template template;
        String result;

        template = handlebars.compileInline(
                "{{#inAction \"test\"}}one{{/inAction}}"
        );
        result = template.apply(context);
        assertEquals("one", result);

        template = handlebars.compileInline(
                "{{#inAction \"test2\"}}one{{/inAction}}"
        );
        result = template.apply(context);
        assertEquals("", result);

        template = handlebars.compileInline(
                "{{#inAction \"test2\"}}onw{{else}}two{{/inAction}}"
        );
        result = template.apply(context);
        assertEquals("two", result);
    }
}