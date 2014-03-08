package com.linchproject.framework.components;

import java.util.Map;

/**
 * @author Georg Schmidl
 */
public interface RenderService {

    String render(String template, Map<String, Object> context);
}
