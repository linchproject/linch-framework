package com.linchproject.framework.view.helper;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.linchproject.framework.i18n.I18n;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class I18nHelperTest {

    @Test
    public void testApply() throws Exception {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("i18n", new I18nHelper());

        I18n i18n = mock(I18n.class);
        when(i18n.getText("test")).thenReturn("Test");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("i18n", i18n);

        Context context = Context.newContext(model);

        Template template;
        String result;

        template = handlebars.compileInline(
                "{{#i18n \"test\"}}{{/i18n}}"
        );
        result = template.apply(context);
        assertEquals("Test", result);
    }


}