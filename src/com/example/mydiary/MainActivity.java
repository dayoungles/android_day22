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


public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{
	private Button btnWrite ;
	private Button btnRefresh ;
	private ListView list;
	ArrayList<ListData> listDataArray = new ArrayList<ListData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnWrite = (Button)findViewById(R.id.write);
		btnRefresh = (Button)findViewById(R.id.refresh);
		list = (ListView)findViewById(R.id.lineList);
		
		btnWrite.setOnClickListener(this);
		btnRefresh.setOnClickListener(this);
		list.setOnItemClickListener(this);
		
		for(int i = 0; i <10; i ++){
			ListData data = new ListData(i+"-1:lineTitle",i+"-2:lineText",i+".jpg");
			listDataArray.add(data);
		}
		ListView listView = (ListView)findViewById(R.id.lineList);
		
		ListAdapter listAdapter = new ListAdapter(this, R.layout.list_line1, listDataArray);//에러가 난다면 두번째 인자..
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		try{
		switch (v.getId()){
		case R.id.write:
			Intent writeIntent = new Intent (this, Write.class);
			startActivity(writeIntent);
			break;
		case R.id.refresh:
			Intent refresh = new Intent( this, MainActivity.class);
			startActivity(refresh);
			break;
		case R.id.lineList:
			Intent line1 = new Intent(this,ListLine1.class);
			startActivity(line1);			
			break;
		
			
		}
		}catch(Exception e){
			Log.e("main",e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}


}
