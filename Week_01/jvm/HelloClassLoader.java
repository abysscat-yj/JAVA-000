package jvm;

import java.io.UnsupportedEncodingException;
import java.util.Base64;


public class HelloClassLoader extends ClassLoader {

	public static void main(String[] args) throws Exception {

		new HelloClassLoader().findClass("Hello").newInstance();

	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String helloBase64 = "yv66vgAAADQAHwoABgARCQASABMIABQKABUAFgcAFwcAGAEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2N" +
				"hbFZhcmlhYmxlVGFibGUBAAR0aGlzAQALTGp2bS9IZWxsbzsBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZhDAAHAAgHABkMABoAGwEAGEhlb" +
				"GxvIENsYXNzIEluaXRpYWxpemVkIQcAHAwAHQAeAQAJanZtL0hlbGxvAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2" +
				"YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgAhAAUABgAAAAAAAgABAAcACA" +
				"ABAAkAAAAvAAEAAQAAAAUqtwABsQAAAAIACgAAAAYAAQAAAAMACwAAAAwAAQAAAAUADAANAAAACAAOAAgAAQAJAAAAJQACAAAAAAAJsgACEgO2AASxAAAAAQAK" +
				"AAAACgACAAAABgAIAAcAAQAPAAAAAgAQ";

		byte[] bytes = decode(helloBase64);
		try {
			System.out.println(new String(bytes, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return defineClass(name, bytes, 0, bytes.length);

	}

	public byte[] decode(String base64) {
		return Base64.getDecoder().decode(base64);
	}


}
