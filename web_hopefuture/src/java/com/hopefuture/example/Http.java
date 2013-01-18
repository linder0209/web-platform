package com.hopefuture.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Http {
	public static void main(String[] args) {
		try {
			URL url = new URL(
					"http://maps.google.com/maps/api/js?sensor=false&language=en_US");
			URLConnection connection = url.openConnection();
			connection.getContent();
			InputStream input = connection.getInputStream();
			StringBuilder data = new StringBuilder();
			String str;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			while ((str = reader.readLine()) != null) {
				data.append(str);
			}
			System.out.println(data);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
