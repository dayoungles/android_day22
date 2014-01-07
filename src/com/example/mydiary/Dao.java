package com.example.mydiary;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {// Data ( Db, File 또는 다른 서비스 )에 접근을 제어

	private Context context;
	private SQLiteDatabase database;

	public Dao(Context context) {
		try {
			this.context = context;
			// db초기화
			database = context.openOrCreateDatabase("LocalDATA.db",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);

			String sql = "Create table if not exists Articles ("
					+ "id integer not null,"
					+ "title text not null,"
					+ "user text,"
					+ "content text,"
					+ "fileName text);";
			database.execSQL(sql);

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("test", "create table failed " + e);
		}

	}

	public void insertJsonData(String jsonData) {
		FileDownloader fileDownLoader = new FileDownloader(context);
		String title;
		String writer;
		String id;
		String contents;
		String fileName;

		try {
//			JSONArray jArr = new JSONArray(jsonData);//이녀석이 제이슨을 중괄호 단위로 잘라주었네.
			JSONObject temp = new JSONObject(jsonData);
			JSONArray jArr = temp.getJSONArray("list");

			for (int i = 0; i < jArr.length(); i++) {
				JSONObject jObj = jArr.getJSONObject(i);
				title = jObj.getString("title");
				writer = jObj.getString("user");
				id = jObj.getString("id");
				contents = jObj.getString("contents");
				fileName = jObj.getString("fileName");

				Log.i("test", "ArticleNumber :::" + id + "Title : "
						+ title);
				// String sql =
				// "INSERT INTO Articles (ArticleNumber, Title, WriterName, WriterID, Content, WriterDate,ImgName) Values("
				// +article+
				String sql = "insert into Articles(id, title, user, content, fileName) values(?,?,?,?,?)";
				String[] array = { id, title, writer, contents, fileName };

				try {
					database.execSQL(sql, array);
				} catch (Exception e) {
					Log.e("test", "DB error" + e);
					e.printStackTrace();
				}
				fileDownLoader.downFile("http://10.73.43.76:8080/images/"+fileName, fileName);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("text", "json error " + e);
		}
	}

	public ArrayList<Article> getArticleList() {

		ArrayList<Article> articleList = new ArrayList<Article>();
		String articleNumber;
		String title;
		String user;
		String id;
		String contents;
		String fileName;

		String sql = "select * from Articles;";
		Cursor cursor = database.rawQuery(sql, null);
		try {
			while (cursor.moveToNext()) {
				id = cursor.getString(0);
				title = cursor.getString(1);
				user = cursor.getString(2);
				contents = cursor.getString(3);
				fileName = cursor.getString(4);

				articleList.add(new Article(id, title, user, contents, fileName));
			}
			cursor.close();

			return articleList;
		} catch (Exception e) {
			Log.i("getArticleList", e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public Article getArticleByArticleNumber(String articleNumber) {
		Article article = null;

		// String articleNumber;
		String title;
		String user;
		String id;
		String contents;
		String fileName;

		String sql = "select * from Articles where id ="+ articleNumber + ";";
		Cursor cursor = database.rawQuery(sql, null);
		try {
			if (cursor.moveToNext()) {// 커서는 원래 첫번째 레코드가 아니라 desc를 가리키고 있으니까.
										// 넘겨줘야지.
				// articleNumber = cursor.getString(1);
				articleNumber = cursor.getString(0);
				title = cursor.getString(1);
				user = cursor.getString(2);
				contents = cursor.getString(3);
				fileName = cursor.getString(4);

				article = new Article(articleNumber+ "", title, user,contents, fileName);
			}
			cursor.close();

			return article;
		} catch (Exception e) {
			Log.i("getArticleByArticleNumber", e.getMessage());
			e.printStackTrace();
		}
		return null;//  try에서 return을 만나면 그냥 끝나서 상관없지만 캐치는 지금 리턴이 없기 때문에 리턴을 잘못주면 망함.
	}
}
