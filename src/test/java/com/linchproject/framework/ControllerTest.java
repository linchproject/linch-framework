package com.linchproject.framework;

import com.linchproject.core.Route;
import com.linchproject.framework.i18n.I18n;
import com.linchproject.framework.i18n.I18nService;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Georg Schmidl
 */
public class ControllerTest {
    @Test
    public void testContext() throws Exception {
        Controller controller = new MyController();

        Route route = mock(Route.class);
        controller.setRoute(route);

        I18n i18n = mock(I18n.class);
        I18nService i18nService = mock(I18nService.class);
        when(i18nService.getI18n(any(Locale.class))).thenReturn(i18n);
        controller.setI18nService(i18nService);

        Controller.Context context = controller.context();
        assertEquals(route, context.get("route"));
        assertEquals(i18n, context.get("i18n"));
    }

    public class MyController extends Controller {

    }
}
