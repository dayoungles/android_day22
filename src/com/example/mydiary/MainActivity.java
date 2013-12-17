package com.example.mydiary;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private Button btnWrite;
	private Button btnRefresh;
	private ListView list;
	ArrayList<ListData> listDataArray = new ArrayList<ListData>();
	ArrayList<Article> articleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {

			Dao dao = new Dao(getApplicationContext());

			// String testJsonData = dao.getJsonTestData();
			// dao.insertJsonData(testJsonData);

			btnWrite = (Button) findViewById(R.id.write);
			btnRefresh = (Button) findViewById(R.id.refresh);
			list = (ListView) findViewById(R.id.lineList);

			btnWrite.setOnClickListener(this);
			btnRefresh.setOnClickListener(this);
			articleList = dao.getArticleList();

			CustomAdapter customAdapter = new CustomAdapter(this,
					R.layout.list_line1, articleList);
			list.setAdapter(customAdapter);
			list.setOnItemClickListener(this);

			// for (int i = 0; i < 10; i++) {
			// ListData data = new ListData(i + "-1:lineTitle", i +
			// "-2:lineText",
			// "photo"+(i + 1)+ ".jpg");
			// listDataArray.add(data);
			// }
			// ListView listView = (ListView) findViewById(R.id.lineList);
			//
			// ListAdapter listAdapter = new ListAdapter(this,
			// R.layout.list_line1,
			// listDataArray);// 에러가 난다면 두번째 인자..
			// listView.setAdapter(listAdapter);
			// listView.setOnItemClickListener(this);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("oncreate", e.getMessage());
		}
	}

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.write:
				Intent writeIntent = new Intent(this, Write.class);
				startActivity(writeIntent);
				break;
			case R.id.refresh:
				Intent refresh = new Intent(this, MainActivity.class);
				startActivity(refresh);
				break;
			// case R.id.lineList:
			// Intent line1 = new Intent(this, Show.class);
			// startActivity(line1);
			// break;
			}

		} catch (Exception e) {
			Log.e("main", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Intent intent = new Intent(this, Show.class);
		intent.putExtra("ArticleNumber", articleList.get(position)
				.getArticleNumber() + "");
		startActivity(intent);
		// 지금부터 해야 할 것. 클릭하면 show 페이지로 아이디랑 포지션을 넘겨주어야 할 것입니다.

	}

}
