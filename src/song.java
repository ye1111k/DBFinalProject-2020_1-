import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
/**
 * for songs table
 */
public class song {
	public static String title;
	public static String sname;
	public static String aname;
	public static int ptm;
	public static int pts;
	
	public static Connection myConn;
	public static PreparedStatement songPS;
	
	/**
	 * constructor of song class for delete action
	 * @param title title of song
	 * @param myConn connection variable
	 */
	song(String title,Connection myConn){
		song.title=title;
		song.myConn=myConn;
	}
	/**
	 * constructor of song class for insert action
	 * @param title title of song
	 * @param sname singer
	 * @param aname album
	 * @param ptm playtime - min
	 * @param pts playtime - sec
	 * @param myConn connection variable
	 */
	song(String title, String sname, String aname, int ptm, int pts,Connection myConn){
		song.title=title;
		song.sname=sname;
		song.aname=aname;
		song.ptm=ptm;
		song.pts=pts;
		song.myConn=myConn;
	}
	/**
	 * operate insert action using prepareStatement
	 * @throws SQLException parameter is null or empty
	 * @throws SQLIntegrityConstraintViolationException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void insert_song() throws SQLException, SQLIntegrityConstraintViolationException, SQLSyntaxErrorException {
		songPS=myConn.prepareStatement("insert into songs values(?,?,?,?,?)");
		//set parameter
		songPS.setString(1, title);
		songPS.setString(2, sname);
		songPS.setString(3, aname);
		songPS.setInt(4, ptm);
		songPS.setInt(5, pts);
		songPS.executeUpdate();//insert song
		songPS.close();//close prepareStatement
	}
	/**
	 * operate delete action using prepareStatement
	 * @throws SQLException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void delete_song() throws SQLException, SQLSyntaxErrorException{
		songPS=myConn.prepareStatement("delete from songs where title=?");
		songPS.setString(1, title);//set parameter
		songPS.executeUpdate();//delete song
		songPS.close();//close prepareStatement
	}
	
}
