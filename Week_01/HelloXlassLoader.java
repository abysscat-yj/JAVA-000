
import java.io.*;

public class HelloXlassLoader extends ClassLoader {

	public static void main(String[] args) throws Exception {

		Class<?> hello = new HelloXlassLoader().findClass(null);
		hello.getDeclaredMethod("hello").invoke(hello.newInstance());

	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] bytes = getXclassByteArray("Week_01/Hello.xlass");

		return defineClass(name, bytes, 0, bytes.length);
	}


	private byte[] getXclassByteArray(String filePath) {
		byte[] bytes = new byte[1024];
		try(InputStream in = new FileInputStream(filePath);
			ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			byte[] buffer = new byte[1024 * 4];
			int n;
			while ((n = in.read(buffer)) != -1) {
				out.write(buffer, 0, n);
			}
			bytes = out.toByteArray();
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte)(255 - bytes[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}


}
