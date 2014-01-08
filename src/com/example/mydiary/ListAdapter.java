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
import android.widget.Toast;

public class ListAdapter extends ArrayAdapter<ListData> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<ListData> listData;

	public ListAdapter(Context context, int layoutResourceId,
			ArrayList<ListData> listData) {
		super(context, layoutResourceId, listData);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.listData = listData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		try {
			Log.i("in check", "djkfldjs");
			if (row == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);
			}

			TextView tvText1 = (TextView) row.findViewById(R.id.lineTitle);
			TextView tvText2 = (TextView) row.findViewById(R.id.lineText);
			String contents = listData.get(position).getText2();
			contents = contents.substring(0,  20);
			tvText1.setText(listData.get(position).getText1());
			tvText2.setText(contents);

			ImageView imageView = (ImageView) row.findViewById(R.id.lineImage);
			InputStream is = context.getAssets().open(
					listData.get(position).getImgName());
			Drawable d = Drawable.createFromStream(is, null);
			imageView.setImageDrawable(d);
			Log.i("check", tvText1.getText().toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("ListAdapter", e.getMessage());
		}

		return null;

	}
}
