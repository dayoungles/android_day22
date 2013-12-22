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

			String sql = "Create table if not exists Articles (id integer primary key autoincrement,"
					+ "ArticleNumber integer unique not null,"
					+ "Title text not null,"
					+ "WriterName text not null,"
					+ "WriterID text not null,"
					+ "Content text not null,"
					+ "WriteDate text not null," + "ImgName text not null);";
			database.execSQL(sql);

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("test", "create table failed " + e);
		}

	}

	/**
	 * JSON파싱을 위한 테스트 문자열입니다. 각 데이터는 다음과 같습니다. ArticleNumber - 글번호 중복X 숫자 Title
	 * - 글제목 문자열 Writer - 작성자 Id - 작성자ID Content - 글내용 WriteDate - 작성일 ImgName -
	 * 사진명
	 */
	public String getJsonTestData() {//삭제 예정 
		StringBuilder sb = new StringBuilder();
		sb.append("");
		try {

			sb.append("[");

			sb.append("      {");
			sb.append("         'ArticleNumber':'1',");
			sb.append("         'Title':'오늘도 좋은 하루',");
			sb.append("         'Writer':'학생1',");
			sb.append("         'Id':'6613d02f3e2153283f23bf621145f877',");
			sb.append("         'Content':'하지만 곧 기말고사지...',");
			sb.append("         'WriteDate':'2013-09-23-10-10',");
			sb.append("         'ImgName':'photo1.jpg'");
			sb.append("      },");
			sb.append("      {");
			sb.append("         'ArticleNumber':'2',");
			sb.append("         'Title':'대출 최고 3000만원',");
			sb.append("         'Writer':'김미영 팀장',");
			sb.append("         'Id':'6326d02f3e2153266f23bf621145f734',");
			sb.append("         'Content':'김미영팀장입니다. 고갱님께서는 최저이율로 최고 3000만원까지 30분 이내 통장입금가능합니다.',");
			sb.append("         'WriteDate':'2013-09-24-11-22',");
			sb.append("         'ImgName':'photo2.jpg'");
			sb.append("      },");
			sb.append("      {");
			sb.append("         'ArticleNumber':'3',");
			sb.append("         'Title':'MAC등록신청',");
			sb.append("         'Writer':'학생2',");
			sb.append("         'Id':'8426d02f3e2153283246bf6211454262',");
			sb.append("         'Content':'1a:2b:3c:4d:5e:6f',");
			sb.append("         'WriteDate':'2013-09-25-12-33',");
			sb.append("         'ImgName':'photo3.jpg'");
			sb.append("      }");

			sb.append("]");

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("getJsonTestData", e.getMessage());
		}
		return sb.toString();
	}

	public void insertJsonData(String jsonData) {

		FileDownloader fileDownLoader = new FileDownloader(context);
		String articleNumber;
		String title;
		String writer;
		String id;
		String content;
		String writedate;
		String imgName;

		try {
			JSONArray jArr = new JSONArray(jsonData);//이녀석이 제이슨을 중괄호 단위로 잘라주었네.

			for (int i = 0; i < jArr.length(); i++) {
				JSONObject jObj = jArr.getJSONObject(i);
				articleNumber = jObj.getString("ArticleNumber");
				title = jObj.getString("Title");
				writer = jObj.getString("Writer");
				id = jObj.getString("Id");
				content = jObj.getString("Content");
				writedate = jObj.getString("WriteDate");
				imgName = jObj.getString("ImgName");

				Log.i("test", "ArticleNumber :::" + articleNumber + "Title : "
						+ title);
				// String sql =
				// "INSERT INTO Articles (ArticleNumber, Title, WriterName, WriterID, Content, WriterDate,ImgName) Values("
				// +article+
				String sql = "insert into Articles(ArticleNumber, Title, WriterName, WriterID, Content, WriteDate,ImgName) values(?,?,?,?,?,?,?)";
				String[] array = { articleNumber, title, writer, id, content,
						writedate, imgName };

				try {
					database.execSQL(sql, array);
				} catch (Exception e) {
					Log.e("test", "DB error" + e);
					e.printStackTrace();
				}
				fileDownLoader.downFile("http://10.73.44.93/~stu09/image/"+imgName, imgName);
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
		String writer;
		String id;
		String content;
		String writedate;
		String imgName;

		String sql = "select * from Articles;";
		Cursor cursor = database.rawQuery(sql, null);
		try {
			while (cursor.moveToNext()) {
				articleNumber = cursor.getString(1);
				title = cursor.getString(2);
				writer = cursor.getString(3);
				id = cursor.getString(4);
				content = cursor.getString(5);
				writedate = cursor.getString(6);
				imgName = cursor.getString(7);

				articleList.add(new Article(articleNumber, title, writer, id,
						content, writedate, imgName));
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
		String writer;
		String id;
		String content;
		String writedate;
		String imgName;

		String sql = "select * from Articles where ArticleNumber ="
				+ articleNumber + ";";
		Cursor cursor = database.rawQuery(sql, null);
		try {
			if (cursor.moveToNext()) {// 커서는 원래 첫번째 레코드가 아니라 desc를 가리키고 있으니까.
										// 넘겨줘야지.
				// articleNumber = cursor.getString(1);
				title = cursor.getString(2);
				writer = cursor.getString(3);
				id = cursor.getString(4);
				content = cursor.getString(5);
				writedate = cursor.getString(6);
				imgName = cursor.getString(7);

				article = new Article(articleNumber + "", title, writer, id,
						content, writedate, imgName);
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
