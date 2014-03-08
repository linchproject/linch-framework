package com.linchproject.framework;

import com.linchproject.core.Result;
import com.linchproject.core.Route;
import com.linchproject.core.results.Success;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Georg Schmidl
 */
public class ControllerTest {
    @Test
    public void testRender() throws Exception {
        Controller controller = new MyController();

        Result result;

        result = controller.render("name", null);
        assertTrue(result instanceof Success);
        assertEquals("", ((Success) result).getContent());

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("name", "John");

        result = controller.render("name", context);
        assertTrue(result instanceof Success);
        assertEquals("John", ((Success) result).getContent());


        Route route = mock(Route.class);
        when(route.copy()).thenReturn(route);
        when(route.getUrl()).thenReturn("testUrl");
        controller.setRoute(route);

        result = controller.render("route", null);
        assertTrue(result instanceof Success);
        assertEquals("testUrl", ((Success) result).getContent());

    }

    public class MyController extends Controller {

    }
}
