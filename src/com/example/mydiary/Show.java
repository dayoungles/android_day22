package com.example.mydiary;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Show extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
		Intent intent = getIntent();
		int no = intent.getIntExtra("no", -1);
		if (no == -1) {
			finish();
		}
		ListData data = new ListData(no + "-1:lineTitle", no + "-2:lineText",
				(no + 1) + ".jpg");

		TextView title = (TextView) findViewById(R.id.showTitle);
		TextView contents = (TextView) findViewById(R.id.text);
		ImageView image = (ImageView) findViewById(R.id.image);

		title.setText(data.getText1());
		contents.setText(data.getText2());

		InputStream is;
		try {
			is = getAssets().open(data.getImgName());
			Drawable d = Drawable.createFromStream(is, null);
			image.setImageDrawable(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// no 로 어떻게든 해. ㅋㅋㅋ
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show, menu);
		return true;
	}

}
