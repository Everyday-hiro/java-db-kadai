package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement insertstatement = null;
		Statement selectstatement = null;
				
		String[][] userList = {
				{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！", "12"},
				{"1003", "2023-02-09", "今日も頑張ります！", "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
				{"1002", "2023-02-10", "明日から連休ですね！", "20"}
				
		};
		
		try {
			//データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					""
					);
			
			System.out.println("データベース接続成功："+ con);
			
			//（INSERT）SQLクエリを準備
			String insql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
			insertstatement = con.prepareStatement(insql);
			
			System.out.println("レコード追加を実行します");
			int rowCnt = 0;
			for(int i =0; i < userList.length; i++) {
				insertstatement.setString(1, userList[i][0]);
				insertstatement.setString(2, userList[i][1]);
				insertstatement.setString(3, userList[i][2]);
				insertstatement.setString(4, userList[i][3]);
				
				//SQLクエリを実行
				rowCnt += insertstatement.executeUpdate();
				
			}
			System.out.println(rowCnt + "件のレコードが追加されました");
			
			//(SELECT)SQLクエリの準備
			selectstatement = con.createStatement();
			String sesql ="SELECT * FROM posts WHERE user_id = 1002";
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			
			ResultSet result = selectstatement.executeQuery(sesql);
			
			while(result.next()) {
				Date postedAt = result.getDate("posted_at");
				String postContent =result.getString("post_content");
				int likes = result.getInt("likes");
				System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "/投稿内容=" + postContent + "/いいね数=" + likes);
			}
 		}catch(SQLException e) {
 			System.out.println("エラー発生：" + e.getMessage());
 		}finally {
 			try {
 				if(insertstatement != null) insertstatement.close();
 				if(selectstatement != null) selectstatement.close();
 				if(con != null) con.close();
 			}catch(SQLException e) {
 				System.out.println("");
 			}
 		}

	}

}
