//package com.example.mydiary;
//
//import com.actionbarsherlock.app.SherlockActivity;
//import com.actionbarsherlock.app.SherlockListActivity;
//import com.devspark.sidenavigation.ISideNavigationCallback;
//
//import android.widget.Toast;
//
//public class SherlockSideNavigation extends SherlockActivity implements ISideNavigationCallback {
//	@Override
//	public void onSideNavigationItemClick(int itemId)
//	{
//		String text="";
//		switch(itemId){
//		case R.id.side_navigation_menu_add:
//			text="add";
//			break;
//		case R.id.side_navigation_menu_call:
//			text="call";
//			break;
//		case R.id.side_navigation_menu_delete:
//			text="delete";
//			break;
//		case R.id.side_navigation_menu_text:
//			text="text";
//			break;
//		default:
//				text="";
//				
//			
//		}
//		Toast.makeText(getApplicationContext(), "side menu: "+text, Toast.LENGTH_SHORT).show();
//	}
//}
