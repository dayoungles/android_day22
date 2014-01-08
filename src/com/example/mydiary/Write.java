package com.example.mydiary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Write extends Activity implements OnClickListener{
	private ProgressDialog progressDialog;
	private static final int REQUEST_PHOTO_ALBUM = 1;
	private TextView writer;
	private EditText title;
	private EditText content;
	private ImageButton photo;
	private Button upload;
	
	private String filePath;
	private String fileName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);
		
		writer = (TextView) findViewById(R.id.writer);
		title = (EditText)findViewById(R.id.inputTitle);
		content = (EditText)findViewById(R.id.inputText);
		photo = (ImageButton)findViewById(R.id.image);
		photo.setOnClickListener(this);
		upload = (Button) findViewById(R.id.send);
		upload.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
		case R.id.image:
			
			Intent intent = new Intent (Intent.ACTION_PICK);
			intent.setType(Images.Media.CONTENT_TYPE);
			intent.setData(Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
			break;
		case R.id.send:
			final Handler handler = new Handler();
			new Thread(){
				public void run(){
					handler.post(new Runnable(){

						@Override
						public void run() {
							progressDialog = ProgressDialog.show(Write.this,"","uploading");
						}
						
					});
					String ID = Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
					//String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA).format(new Date());
					Article article = new Article("0",
							title.getText().toString(),
							writer.getText().toString(),
							content.getText().toString(),
							fileName
							);
				ProxyUP proxyUP = new ProxyUP();
				proxyUP.uploadArticle(article, filePath);
				
				handler.post(new Runnable(){

					@Override
					public void run() {
						progressDialog.cancel();
						finish();
					}
				});
				}
			}.start();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		try{
		if(requestCode == REQUEST_PHOTO_ALBUM){
			Uri uri = getRealPathUri(data.getData());
			filePath = uri.toString();
			fileName = uri.getLastPathSegment();
			
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			photo.setImageBitmap(bitmap);
		}
		}catch(Exception e){
			Log.e("test", "onActivityResult Error"+e);
		}
	}
	
	private Uri getRealPathUri(Uri uri){
		try{
		Uri filePathUri = uri;
		if(uri.getScheme().toString().compareTo("content")==0){
			Cursor cursor = getApplicationContext().getContentResolver().query(uri, null,null,null,null);
			if(cursor.moveToFirst()){
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				filePathUri = uri.parse(cursor.getString(column_index));
			}
		
		}
		
		return filePathUri;
		}catch(Exception e){
			e.printStackTrace();
			Log.i("test", e.getMessage());
		}
		return null;
	}

}
