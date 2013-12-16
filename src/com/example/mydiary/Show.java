package com.example.mydiary;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Show extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
		
		TextView title = (TextView) findViewById(R.id.showTitle);
		TextView contents = (TextView) findViewById(R.id.text);
		ImageView image = (ImageView) findViewById(R.id.image);
		//이것말고도 글쓴 사람, 날짜....뭐 이런 것까지 모두 만들어줘야된다;;
		
		String articleNumber = getIntent().getExtras().getString("ArticleNumber");
		Log.i("articleNumber",articleNumber);
		//Intent intent = getIntent();
		Dao dao = new Dao(getApplicationContext());
		
		Article article = dao.getArticleByArticleNumber(articleNumber);
		
		title.setText(article.getTitle());
		contents.setText(article.getContent());
		
//		
//		int no = intent.getIntExtra("ArticleNumber", -1);
//		if (no == -1) {
//			finish();
//		}
//		ListData data = new ListData(no + "-1:lineTitle", no + "-2:lineText",
//				(no + 1) + ".jpg");
//
//
//		title.setText(data.getText1());
//		contents.setText(data.getText2());
//
		try {
			InputStream ims;
			ims = getApplicationContext().getAssets().open(article.getImgName());
			Drawable d = Drawable.createFromStream(ims, null);
			image.setImageDrawable(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("inputStream error", "error" + e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show, menu);
		return true;
	}

}
