package com.example.mydiary;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListLine1 extends Activity implements OnItemClickListener {

	ArrayList<ListData> listDataArray = new ArrayList<ListData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.list_line1);

			for (int i = 0; i < 10; i++) {
				ListData data = new ListData(i + "-1:lineTitle", i
						+ "-2:lineText", i + ".jpg");
				listDataArray.add(data);
			}
			ListView listView = (ListView) findViewById(R.id.lineList);

			ListAdapter listAdapter = new ListAdapter(this,
					R.layout.list_line1, listDataArray);// 에러가 난다면 두번째 인자..
			listView.setAdapter(listAdapter);
			listView.setOnItemClickListener(this);
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("onCreate", e.getMessage());
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Log.i("test", position + " 빈리스트 선택");
		Log.i("test", "리스트 내용 " + listDataArray.get(position).getText1());

	}

}
