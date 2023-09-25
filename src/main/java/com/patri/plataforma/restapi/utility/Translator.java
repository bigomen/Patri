package com.patri.plataforma.restapi.utility;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class Translator
{
	private static ResourceBundleMessageSource messageSource;

	@Autowired
	Translator(ResourceBundleMessageSource messageSource)
	{
		Translator.messageSource = messageSource;
		Translator.messageSource.setBasenames("messages");
	}

	public static String toLocale(String msg)
	{
		Locale locale = LocaleContextHolder.getLocale();
		return toLocale(msg, locale);
	}

	public static String toLocale(String msg, @Nullable Object[] args)
	{
		Locale locale = LocaleContextHolder.getLocale();
		return toLocale(msg, locale, args);
	}

	public static String toLocale(String msg, @Nullable String... args)
	{
		Locale locale = LocaleContextHolder.getLocale();
		return toLocale(msg, locale, args);
	}

	public static String toLocale(String msg, Locale locale)
	{
		return messageSource.getMessage(msg, null, locale);
	}

	public static String toLocale(String msg, Locale locale, @Nullable Object[] args)
	{
		return messageSource.getMessage(msg, args, locale);
	}
}