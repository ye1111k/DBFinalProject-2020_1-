import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	public static Scanner input=new Scanner(System.in);
	public static PreparedStatement pStmt;
	
	
	public static void main(String[] args) throws SQLException, SQLIntegrityConstraintViolationException, SQLSyntaxErrorException {
		// TODO Auto-generated method stub
		
		//userid=testuser, password=testpw, dbname=finalproject
		String userID="testuser";
		String userPW="testpw";
		String dbName="finalproject";
		String url="jdbc:mysql://localhost:3306/"+dbName+"?&serverTimezone=UTC";
		
		Connection myConn=null;
		try {
			myConn=DriverManager.getConnection(url,userID,userPW);//getConnection
			
			while(true) {
				//select option
				System.out.println();
				System.out.println("Select the task to perform.");
				System.out.println("1.song insertion  2.song arrangement 3.album repackaging  4.song deletion");
				System.out.println("5.song filtering  6.print all contents  7.exit");
				int a=input.nextInt();
				if(a==7) {//when user choose exit
					System.out.println("Exit the program.");
					break;
				}
				else if(a==1) {//when user choose insertion
					singers(myConn);//show singers list
					System.out.println("Is there singer of song that you want to insert? 1.yes 2.no");
					int syn=input.nextInt();
					if(syn==1) {//if singer of song already exists
						albums(myConn);
						System.out.println("Is there album of song that you want to insert? 1.yes 2.no");
						int ayn=input.nextInt();
						if(ayn==1) insert3(myConn);//if singer and album of song already exists
						else if(ayn==2) insert2(myConn);//if singer of song already exists but album doesn't
						else System.out.println("Invalid input. Please enter again.");//wrong input
					}
					else if(syn==2) insert1(myConn);//if singer of song doesn't exist
					else System.out.println("Invalid input. Please enter again.");//wrong input
				}
				else if(a==2) {//when user choose arrangement
					songs(myConn);
					arrange(myConn);
				}
				else if(a==3) {//when user choose repackaging
					albums(myConn);
					repackage(myConn);
				}
				else if(a==4) {//when user choose deletion
					songs(myConn);
					delete(myConn);
				}
				else if(a==5) {//when user choose filtering
					//select option of filtering
					System.out.println("Select the option of filtering.");
					System.out.println("1.print all songs according to playtime");
					System.out.println("2.print all songs according to sex of singer and released year");
					System.out.println("3.print all songs according to number of songs in album ");
					int oa=input.nextInt();
					if(oa==1) select1(myConn);
					else if(oa==2) select2(myConn);
					else if(oa==3) select3(myConn);
					else System.out.println("Invalid input. Please enter again.");//wrong input
				}
				else if(a==6) {//when user choose print
					songs(myConn);//print songs
					singers(myConn);//print singers
					albums(myConn);//print albums
					System.out.println("All contents are printed.");
				}
				else System.out.println("Invalid input. Please enter again.");//wrong input
			}
		}
		//exception handling
		catch(SQLSyntaxErrorException e) {
			e.getMessage();
			System.out.println("Invalid input. Exit the program.");
		}
		catch(SQLIntegrityConstraintViolationException e) {
			e.getMessage();
			System.out.println("Invalid input. Exit the program.");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(myConn!=null) {//close connection
				try{
					myConn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	/**
	 * method for print all data in songs table
	 * 
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 */
	public static void songs(Connection myConn) throws SQLException{
		Statement songState=myConn.createStatement();//create statement
		String songSql="select * from songs";//select all data from albums table
		ResultSet songResSet=songState.executeQuery(songSql);//query using statement

		String title, sname, aname;
		int ptm, pts;
		
		System.out.println();
		System.out.println("list of Songs");
		//print all data in songResSet
		while(songResSet.next()) {
			title=songResSet.getString("title");
			sname=songResSet.getString("sname");
			aname=songResSet.getString("aname");
			ptm=songResSet.getInt("ptm");
			pts=songResSet.getInt("pts");
			System.out.println(String.format("title:%40s|singer:%15s|album:%30s|playtime:%4dm%4ds",title, sname,aname,ptm,pts));
		}
		//close Statement
		songResSet.close();
		songState.close();
	}
	/**
	 * method for print all data in singers table
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 */
	public static void singers(Connection myConn) throws SQLException {
		Statement singerState=myConn.createStatement();//create statement
		String singerSql="select * from singers";//select all data from singers table
		ResultSet singerResSet=singerState.executeQuery(singerSql);//query using statement
		
		String name, debutdate, sex;
		int nop;
		
		System.out.println();
		System.out.println("list of Singers");
		//print all data in singerResSet
		while(singerResSet.next()) {
			name=singerResSet.getString("name");
			debutdate=singerResSet.getString("debutdate");
			nop=singerResSet.getInt("nop");
			sex=singerResSet.getString("sex");
			System.out.println(String.format("singer: %15s|debut date: %10s|number of people: %2d|sex of singer: %6s",name, debutdate, nop, sex));
		}
		//close Statement
		singerResSet.close();
		singerState.close();
	}
	/**
	 * method for print all data in albums table
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 */
	public static void albums(Connection myConn) throws SQLException{
		Statement albumState=myConn.createStatement();//create statement
		String albumSql="select * from albums";//select all data from albums table
		ResultSet albumResSet=albumState.executeQuery(albumSql);//query using statement

		String name, sname, releasedate;
		int nos;
		
		System.out.println();
		System.out.println("list of Albums");
		
		//print all data in albumResSet
		while(albumResSet.next()) {
			name=albumResSet.getString("name");
			nos=albumResSet.getInt("nos");
			sname=albumResSet.getString("sname");
			releasedate=albumResSet.getString("releasedate");
			System.out.println(String.format("album: %30s|number of songs: %2d|singer: %15s|released date: %10s",name, nos, sname, releasedate));	
			
		}
		//close Statement
		albumResSet.close();
		albumState.close();
	}
	
	/**
	 * method for insertion singer, album, song
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 */
	public static void insert1(Connection myConn) throws SQLException{//singer,album,song
		String sname, aname, title, debutdate, releasedate, sex; 
		int nos, nop, ptm, pts;

		//get information of singer, album, song
		input.nextLine();
		System.out.print("name of singer:");
		sname=input.nextLine();
		System.out.print("name of album:");
		aname=input.nextLine();
		System.out.print("title of song:");
		title=input.nextLine();
		System.out.print("playtime minute of song:");
		ptm=input.nextInt();
		System.out.print("playtime second of song:");
		pts=input.nextInt();
		input.nextLine();
		System.out.print("debut date of singer:");
		debutdate=input.nextLine();
		System.out.print("released date of album:");
		releasedate=input.nextLine();
		System.out.print("sex of singer:");
		sex=input.nextLine();
		System.out.print("number of people belong to singer:");
		nop=input.nextInt();
		System.out.print("number of song belong to album:");
		nos=input.nextInt();
		
		new singer(sname,debutdate,nop,sex,myConn);
		new album(aname,nos,sname,releasedate,myConn);
		new song(title,sname,aname,ptm,pts,myConn);
		
		singer.insert_singer();//insert singer
		album.insert_album();//insert album
		song.insert_song();//insert song
		
		System.out.println("insertion finished.");
	}
	/**
	 * method for insertion album, song
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 */
	public static void insert2(Connection myConn) throws SQLException {//album,song
		String sname, aname, title, releasedate; 
		int nos, ptm, pts;

		input.nextLine();
		
		//get information of album and song
		System.out.print("name of singer:");
		sname=input.nextLine();
		System.out.print("name of album:");
		aname=input.nextLine();
		System.out.print("title of song:");
		title=input.nextLine();
		
		System.out.print("playtime minute of song:");
		ptm=input.nextInt();
		System.out.print("playtime second of song:");
		pts=input.nextInt();
		input.nextLine();
		System.out.print("released date of album:");
		releasedate=input.nextLine();
		System.out.print("number of song belong to album:");
		nos=input.nextInt();
		
		new album(aname,nos,sname,releasedate,myConn);
		new song(title,sname,aname,ptm,pts,myConn);
		
		album.insert_album();//insert album
		song.insert_song();//insert song
		
		System.out.println("insertion finished.");
	}
	/**
	 * method for insertion song
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 */
	public static void insert3(Connection myConn) throws SQLException{
		String sname, aname, title;
		int ptm, pts;
		
		//get information of song
		input.nextLine();
		System.out.print("name of singer:");
		sname=input.nextLine();
		System.out.print("name of album:");
		aname=input.nextLine();
		System.out.print("title of song:");
		title=input.nextLine();
		System.out.print("playtime minute of song:");
		ptm=input.nextInt();
		System.out.print("playtime second of song:");
		pts=input.nextInt();
		
		new song(title,sname,aname,ptm,pts,myConn);
		song.insert_song();//insert song
		
		System.out.println("insertion finished.");
		
	}
	/**
	 * method for updating playtime of song according to user input
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void arrange(Connection myConn) throws SQLException, SQLSyntaxErrorException {//playtime arrange
		//create prepareStatement
		pStmt=myConn.prepareStatement("update songs set ptm=ptm+? where title=?");
		PreparedStatement pStmt2=myConn.prepareStatement("update songs set pts=pts+? where title=?");
		
		String title;
		int isec, imin, sec;
		
		//get title of song to arrange
		input.nextLine();
		System.out.println("Enter the title of song that you want to arrange.");
		title=input.nextLine();
		//get sec of song to increase
		System.out.println("Enter the second you want to increase.");
		sec=input.nextInt();
		
		//calculate min, sec to increase
		imin=sec/60;
		isec=sec%60;
		//set parameters
		pStmt.setInt(1, imin);
		pStmt2.setInt(1, isec);
		pStmt.setString(2, title);
		pStmt2.setString(2, title);
		//update
		pStmt.executeUpdate();
		pStmt2.executeUpdate();
		//close prepareStatement
		pStmt.close();
		pStmt2.close();
		
		System.out.println("arrangement finished.");
	}
	/**
	 * method for updating album(number of song) and song(playtime)
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void repackage(Connection myConn) throws SQLException{
		Savepoint savepoint1 = null;
		try {
			myConn.setAutoCommit(false);//for using transaction
			
			String aname;
			int sec, imin, isec, nos;
			PreparedStatement p2;
			PreparedStatement p3;
			
			savepoint1 = myConn.setSavepoint("Savepoint1");
			
			//create prepareStatement
			pStmt=myConn.prepareStatement("update albums set nos=? where name=?");
			p2=myConn.prepareStatement("update songs set ptm=ptm+? where aname=?");
			p3=myConn.prepareStatement("update songs set pts=pts+? where aname=?");
			
			//get information of repackaging
			input.nextLine();
			System.out.println("Enter the original name of album that you want to repackage.");
			aname=input.nextLine();
			System.out.println("Enter number of song to contain this album");
			nos=input.nextInt();
			
			//get sec of song to increase
			System.out.println("Enter the second you want to increase.");
			sec=input.nextInt();
			
			//calculate min, sec to increase
			imin=sec/60;
			isec=sec%60;
			
			//set parameter
			pStmt.setInt(1, nos);
			pStmt.setString(2, aname);
			p2.setInt(1, imin);
			p2.setString(2, aname);
			p3.setInt(1, isec);
			p3.setString(2, aname);
			
			//update
			pStmt.executeUpdate();
			p2.executeUpdate();
			p3.executeUpdate();
			
			myConn.commit();//commit
			myConn.setAutoCommit(true);//reset

			//close prepareStatement
			pStmt.close();
			p2.close();
			p3.close();
			
			System.out.println("repackage finished.");
		}catch(SQLException e) {
			myConn.rollback(savepoint1);
		}
	}
	/**
	 * method for deletion song
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void delete(Connection myConn) throws SQLException, SQLSyntaxErrorException{
		String title;
		//get title of song to delete
		input.nextLine();
		System.out.println("Enter the title of song that you want to delete.");
		title=input.nextLine();
		new song(title, myConn);
		song.delete_song();//delete song
		
		System.out.println("deletion finished.");
	}
	/**
	 * method show all song that are more than four minutes long
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void select1(Connection myConn) throws SQLException, SQLSyntaxErrorException {
		pStmt=myConn.prepareStatement("select * from songs where ptm>? or ptm=?");//create prepareStatement
		ResultSet rs;
		int m, ptm, pts;
		String title, sname, aname;
		//get lower bound
		System.out.println("enter the lower bound of minute.");
		m=input.nextInt();
		pStmt.setInt(1, m);
		pStmt.setInt(2, m);
		rs=pStmt.executeQuery();//query
		
		System.out.println();
		System.out.println("list of result data");
		//print all songs that equal or over n minute
		while(rs.next()) {
			title=rs.getString("title");
			sname=rs.getString("sname");
			aname=rs.getString("aname");
			ptm=rs.getInt("ptm");
			pts=rs.getInt("pts");
			
			System.out.println(String.format("title:%40s|singer:%15s|album:%30s|playtime:%4dm%4ds",title, sname,aname,ptm,pts));
		}
		
		//close resultset and prepareStatement
		rs.close();
		pStmt.close();
		
	}
	/**
	 * method show all song according to sex of singer and released date
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void select2(Connection myConn) throws SQLException, SQLSyntaxErrorException  {
		pStmt=myConn.prepareStatement("select title, releasedate "
				+ "from songs inner join albums on songs.aname=albums.name "
				+ "where (releasedate between ? and ?) and "
				+ "(songs.sname=some(select name from singers where sex=?))");//create prepareStatement
		
		ResultSet rs;
		int a;
		String title, releasedate, begindate, enddate, sex = null;
		//get information of sex
		System.out.println("Select sex of singer. 1.female 2.male 3.both");
		a=input.nextInt();
		if(a==1) sex="female";
		else if(a==2) sex="male";
		else if(a==3) sex="both";
		input.nextLine();
		//get year of released date
		System.out.println("Enter the year of released date.");
		releasedate=input.nextLine();
		begindate=releasedate+"-01-01";
		enddate=releasedate+"-12-31";
		//set parameter
		pStmt.setString(1, begindate);
		pStmt.setString(2, enddate);
		pStmt.setString(3, sex);
		
		rs=pStmt.executeQuery();//query
		System.out.println();
		System.out.println("list of result data");
		//print title and released date according to sex of singer and released date
		while(rs.next()) {
			title=rs.getString("title");
			releasedate=rs.getString("releasedate");
			
			System.out.println(String.format("title: %40s|released date: %10s",title, releasedate));
		}
		//close resultSet and prepareStatement
		rs.close();
		pStmt.close();
	}
	/**
	 * method show all song according to the number of songs in the album.
	 * @param myConn connection variable
	 * @throws SQLException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void select3(Connection myConn) throws SQLException, SQLSyntaxErrorException  {
		pStmt=myConn.prepareStatement("select * from aview where nos>? or nos=?");//create prepareStatement
		
		ResultSet rs;
		int nos, nos2;
		String title, aname;
		//get number of song
		System.out.println("enter lower bound of number of song belong to one album.");
		nos=input.nextInt();
		//set parameter
		pStmt.setInt(1, nos);
		pStmt.setInt(2, nos);
		rs=pStmt.executeQuery();//query
		
		System.out.println();
		System.out.println("list of result data");
		//print songs that are belong to album contains songs over n and number of song
		while(rs.next()) {
			title=rs.getString("title");
			nos2=rs.getInt("nos");
			aname=rs.getString("name");
			
			System.out.println(String.format("album: %30s|title: %40s|number of songs: %2d", aname, title, nos2));
		}
		//close resultSet and prepareStatement
		rs.close();
		pStmt.close();
	}
}
