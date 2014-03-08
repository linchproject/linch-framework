package com.linchproject.framework.view;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Georg Schmidl
 */
public class MustacheRenderServiceTest {
    @Test
    public void testRender() throws Exception {
        MustacheRenderService mustacheRenderService = new MustacheRenderService();

        String result;

        result = mustacheRenderService.render("name", null);
        assertEquals("", result);

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("name", "John");

        result = mustacheRenderService.render("name", context);
        assertEquals("John", result);
    }
}
