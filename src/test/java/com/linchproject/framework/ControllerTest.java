package com.linchproject.framework;

import com.linchproject.core.Result;
import com.linchproject.core.Route;
import com.linchproject.core.results.Success;
import com.linchproject.framework.view.MustacheRenderService;
import org.junit.Test;

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
        controller.setRenderService(new MustacheRenderService());

        Result result;

        Route route = mock(Route.class);
        when(route.copy()).thenReturn(route);
        when(route.getUrl()).thenReturn("testUrl");
        when(route.isDefaultController()).thenReturn(true);
        controller.setRoute(route);

        result = controller.render("route", controller.context());
        assertTrue(result instanceof Success);
        assertEquals("testUrl", ((Success) result).getContent());
    }

    public class MyController extends Controller {

    }
}
