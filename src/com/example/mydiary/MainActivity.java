package com.example.mydiary;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

//import com.actionbarsherlock.view.MenuItem;
//import com.actionbarsherlock.view.Menu;

import android.view.*;


import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;



public class MainActivity extends Activity implements OnClickListener, OnItemClickListener 
{
	
	
	private Handler handler = new Handler();
	private Button btnWrite;
	private Button btnRefresh;
	private ListView list;
	private SideNavigationView sideNavigationView;
	
	
	
	ArrayList<ListData> listDataArray = new ArrayList<ListData>();
	ArrayList<Article> articleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {

		
//			btnWrite = (Button) findViewById(R.id.write);
//			btnRefresh = (Button) findViewById(R.id.refresh);
			
			
//			btnWrite.setOnClickListener(this);
//			btnRefresh.setOnClickListener(this);

			sideNavigationView = (SideNavigationView)findViewById(R.id.side_navigation_view);
			sideNavigationView.setMenuItems(R.menu.side_menu);
			sideNavigationView.setMenuClickCallback(sideNavigationCallback);
			sideNavigationView.setMode(Mode.LEFT);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		
			
			
			list = (ListView) findViewById(R.id.lineList);
			
			refreshData();
			listView();
			
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
	public boolean onCreateOptionsMenu(Menu menu) {
		try {
			// Inflate the menu; this adds items to the action bar if it is
			// present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;

		} catch (Exception e) {
			Log.i("onCreateOptionsMenu", e.getMessage());
		}
		return false;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

            
                    String text = "";
                    boolean sbviewflag = false;
                    
                    switch (item.getItemId()) {
                    case R.id.action_write :
                    	Intent writeIntent = new Intent(this, Write.class);
        				startActivity(writeIntent);
                        break;
                            
                    case R.id.action_refresh :
        				//Intent refresh = new Intent(this, MainActivity.class);
        				//startActivity(refresh);
                    	refreshData();
        				break;
                    
                    case R.id.action_item3 :
                            text = "Normal menu item";
                            break;
                    
                    case android.R.id.home:
                 
                        Log.i("sidebar","home touched!!!");
                    	
                    	sideNavigationView.toggleMenu(); 
                    			
                       break;
                    
                    default :
                    	return super.onOptionsItemSelected(item);
                            
                    }
                    
                    if (text != null) {
                            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();}
                    
                    return true;
           
    
	}


	@Override
	public void onClick(View v) {
		try {
//			switch (v.getId()) {
//			case R.id.write:
//				Intent writeIntent = new Intent(this, Write.class);
//				startActivity(writeIntent);
//				break;
//			case R.id.refresh:
//				Intent refresh = new Intent(this, MainActivity.class);
//				startActivity(refresh);
//				break;
//			// case R.id.lineList:
//			// Intent line1 = new Intent(this, Show.class);
//			// startActivity(line1);
//			// break;
//			}

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

	private void refreshData(){
		new Thread(){
			public void run(){
				Proxy proxy = new Proxy();
				Dao dao = new Dao (getApplicationContext());
				dao.insertJsonData(proxy.getJSON());
				
				handler.post(new Runnable(){
					public void run(){
						listView();
					}
				});
			}
		}.start();
	}

	private void listView() {
		Dao dao = new Dao(getApplicationContext());
		articleList = dao.getArticleList();
		CustomAdapter customAdapter = new CustomAdapter(this,
				R.layout.list_line1, articleList);
		list.setAdapter(customAdapter);
		list.setOnItemClickListener(this);
	}
	
	public void onResume(){
		super.onResume();
		refreshData();
		listView();
	}
	
	
	ISideNavigationCallback sideNavigationCallback = new ISideNavigationCallback()
	{
		@Override
		public void onSideNavigationItemClick(int itemId)
		{
			try
			{
				String text="";
				switch(itemId)
				{
					case R.id.side_navigation_menu_add:
						text="add";
						break;
						
					case R.id.side_navigation_menu_call:
						text="call";
						break;
						
					case R.id.side_navigation_menu_delete:
						text="delete";
						break;
						
					case R.id.side_navigation_menu_text:
						text="text";
						break;
					default:
							text="";
				}		
			}	
			catch(Exception e){
				Log.i("onSideNavigationItemClick",e.getMessage());
			}
				
		}
	};
}
