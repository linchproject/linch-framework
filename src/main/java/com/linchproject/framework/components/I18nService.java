package com.linchproject.framework.components;

import com.linchproject.framework.I18n;

import java.util.Locale;

/**
 * @author Georg Schmidl
 */
public interface I18nService {

    I18n getI18n(Locale locale);
}
