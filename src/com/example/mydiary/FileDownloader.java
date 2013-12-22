package com.example.mydiary;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.util.Log;

public class FileDownloader {
	private final Context context;
	public FileDownloader(Context context){
		this.context = context;
	}
	public void downFile (String fileUrl, String fileName){
		File filePath = new File (context.getFilesDir().getPath()+"/"+fileName);
		
		if(!filePath.exists() ){
			try{
				URL url = new URL(fileUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				//서버의 헤더와 데이터 중 헤더를 세팅하는 작업.
				conn.setConnectTimeout(10*1000);
				conn.setReadTimeout(10*1000);
				conn.setRequestMethod("GET");
				conn.setRequestProperty("connection", "Keep-Alive");//connection을 keep alive 해라.
				conn.setRequestProperty("Accept-Charset", "UTF-8");// 캐릭터 셋을  utf-8로.
				conn.setRequestProperty("Cache-Control", "no-cache");//캐시는 안받아. 
				conn.setRequestProperty("Accept", "*/*");// json형태로 받아. 
				conn.setDoInput(true);
				
				conn.connect();
				
				int status = conn.getResponseCode();
				Log.i("test","ProxyResponseCode: "+status);
				
				switch(status){
				//200번대가 걸리면 서버에서 정보를 가지고 와서 스트링으로 바꿔주는 작업. 
				case 200:
				case 201:
					InputStream is = conn.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					ByteArrayBuffer baf = new ByteArrayBuffer(50);
					int current  =0;
					
					while((current = bis.read()) != -1){
						baf.append((byte) current);
					}
					
					FileOutputStream fos = context.openFileOutput(fileName, 0);
					fos.write(baf.toByteArray());
					fos.close();
					bis.close();
					is.close();
				}
				
			}catch(Exception e){
				e.printStackTrace();
				Log.i("test","fileDown error" + e.getMessage());
			}
		}
	}
}
