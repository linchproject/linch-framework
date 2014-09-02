package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.linchproject.core.Route;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PathHelperTest {

    @Test
    public void testApply() throws Exception {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("path", new PathHelper());

        Route route = mock(Route.class);
        when(route.changePath(anyString())).thenReturn(route);
        when(route.getUrl()).thenReturn("test");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("route", route);

        Context context = Context.newContext(model);

        Template template;
        String result;

        template = handlebars.compileInline(
                "{{path \"one\"}}"
        );
        result = template.apply(context);
        assertEquals("test", result);

        template = handlebars.compileInline(
                "{{path \"one\" param=\"two\"}}"
        );
        result = template.apply(context);
        assertEquals("test?param=two", result);
    }
}