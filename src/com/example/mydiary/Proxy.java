package com.example.mydiary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class Proxy {
	
	public String getJSON(){
		try{
			//localhost로 어떻게 옮겨야 할런지 모르겠군여. 집에 있으니 접속이 안됨.ㅋㅋ
			URL url = new URL("http://10.73.43.76:8080/board/list.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//서버의 헤더와 데이터 중 헤더를 세팅하는 작업.
			conn.setConnectTimeout(10*1000);
			conn.setReadTimeout(10*1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("connection", "Keep-Alive");//connection을 keep alive 해라.
			conn.setRequestProperty("Accept-Charset", "UTF-8");// 캐릭터 셋을  utf-8로.
			conn.setRequestProperty("Cache-Control", "no-cache");//캐시는 안받아. 
			conn.setRequestProperty("Accept", "application/json");// json형태로 받아. 
			conn.setDoInput(true);
			
			conn.connect();
			
			int status = conn.getResponseCode();
			Log.i("test","ProxyResponseCode: "+status);
			
			switch(status){
			//200번대가 걸리면 서버에서 정보를 가지고 와서 스트링으로 바꿔주는 작업. 
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null){
					sb.append(line+"\n");
				}
				br.close();
				return sb.toString();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			Log.i("test","network error" + e.getMessage());
		}
		return null;
	}
	


}
