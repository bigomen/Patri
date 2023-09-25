package com.patri.plataforma.restapi.banner;

import java.io.PrintStream;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class CustomBanner implements Banner
{
	@Override
	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out)
	{
		out.println(0);

	}

}
