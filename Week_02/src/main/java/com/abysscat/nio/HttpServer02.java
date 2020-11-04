package com.abysscat.nio;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;


public class HttpServer02 {
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = new ServerSocket(8802);
		while (true) {
			try {
				final Socket socket = serverSocket.accept();
				new Thread(() -> {
					service(socket);
				}).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void service(Socket socket) {
		try {

			System.out.println("HttpServer02监听到请求：" + LocalDateTime.now().toString());

			Thread.sleep(20);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

			String body = "hello,nio";
			printWriter.println("HTTP/1.1 200 OK");
			printWriter.println("Content-Type:text/html;charset=utf-8");
			printWriter.println("Content-Length:" + body.length());
			printWriter.println();
			printWriter.write(body);
			printWriter.close();
			socket.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
