package com.las.desktop.minsides.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.las.desktop.minsides.GdxMain;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.las.desktop.minsides")
public class DesktopLauncher {

	public static void main (String[] arg) {
		AnnotationConfigApplicationContext beanContext = new AnnotationConfigApplicationContext(DesktopLauncher.class);
		GdxMain main = beanContext.getBean(GdxMain.class);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(main, config);
	}
}
