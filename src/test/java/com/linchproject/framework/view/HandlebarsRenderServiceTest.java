package com.linchproject.framework.view;

import com.linchproject.core.Route;
import com.linchproject.framework.i18n.I18n;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Georg Schmidl
 */
public class HandlebarsRenderServiceTest {
    @Test
    public void testRender() throws Exception {
        HandlebarsRenderService handlebarsRenderService = new HandlebarsRenderService();

        String result;

        result = handlebarsRenderService.render("name", null);
        assertEquals("", result);

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("name", "John");

        result = handlebarsRenderService.render("name", context);
        assertEquals("John", result);
    }

    @Test
    public void testPathHelper() throws Exception {
        HandlebarsRenderService handlebarsRenderService = new HandlebarsRenderService();

        String result;

        Route route = mock(Route.class);
        when(route.changePath(anyString())).thenReturn(route);
        when(route.getUrl()).thenReturn("testUrl");
        when(route.isDefaultController()).thenReturn(true);

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("route", route);

        result = handlebarsRenderService.render("route", context);
        assertEquals("testUrl", result);
    }

    @Test
    public void testI18nHelper() throws Exception {
        HandlebarsRenderService handlebarsRenderService = new HandlebarsRenderService();

        String result;

        I18n i18n = mock(I18n.class);
        when(i18n.getText("test.key")).thenReturn("Test Value");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("i18n", i18n);

        result = handlebarsRenderService.render("i18n", context);
        assertEquals("Test Value", result);
    }
}
