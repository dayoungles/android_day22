package com.example.mydiary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Article>{
	private Context context;
	private int layoutResurceId;
	private ArrayList<Article> listData;

	public CustomAdapter(Context context, int layoutResourceId, ArrayList<Article> articleList) {
		super(context, layoutResourceId, articleList);
		this.context = context;
		this.layoutResurceId = layoutResourceId;
		this.listData = articleList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;//이게 각 칸이라는데?
		
		if(row == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResurceId, parent, false);
		}
		
		TextView tvText1 = (TextView) row.findViewById(R.id.lineTitle);
		TextView tvText2 = (TextView) row.findViewById(R.id.lineText);
		
		tvText1.setText(listData.get(position).getTitle());
		tvText2.setText(listData.get(position).getContent());
		
		ImageView imageView = (ImageView) row.findViewById(R.id.lineImage);
		
		try{
			InputStream is = context.getAssets().open(listData.get(position).getImgName());
			Drawable d = Drawable.createFromStream(is, null);
			imageView.setImageDrawable(d);
		}catch(IOException e){
			Log.e("Error", "Error:" + e);
		}
		return row;
		
	}

}