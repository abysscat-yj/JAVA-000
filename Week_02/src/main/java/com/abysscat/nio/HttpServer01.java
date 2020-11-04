package com.abysscat.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class HttpServer01 {

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(8801);
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				service(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static void service(Socket socket) {
		try {

			System.out.println("HttpServer01监听到请求：" + LocalDateTime.now().toString());


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
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}



}
