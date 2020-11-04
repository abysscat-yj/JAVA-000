package jvm;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


public class JvmAppClassLoaderAddURL {

	public static void main(String[] args) {

		String appPath = "file:/d/app/";

		URLClassLoader urlClassLoader = (URLClassLoader) JvmAppClassLoaderAddURL.class.getClassLoader();

		try {
			Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			addURL.setAccessible(true);
			URL url = new URL(appPath);
			addURL.invoke(urlClassLoader, url);
			Class.forName("jvm.Hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
