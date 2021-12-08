import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
/**
 * for albums table
 */
public class album {
	public static String name;
	public static int nos;
	public static String sname;
	public static String releasedate;
	
	public static Connection myConn;
	public static PreparedStatement albumPS;
	
	/**
	 * constructor of album class
	 * @param name name of album
	 * @param nos number of song in album
	 * @param sname name of singer
	 * @param releasedate released date
	 * @param myConn connection variable
	 */
	album(String name, int nos, String sname, String releasedate,Connection myConn){
		album.name=name;
		album.nos=nos;
		album.sname=sname;
		album.releasedate=releasedate;
		album.myConn=myConn;
	}
	/**
	 * operate insert action using prepareStatement 
	 * @throws SQLException parameter is null or empty
	 * @throws SQLIntegrityConstraintViolationException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void insert_album() throws SQLException, SQLIntegrityConstraintViolationException, SQLSyntaxErrorException {
		albumPS=myConn.prepareStatement("insert into albums values(?,?,?,?)");
		//set parameter
		albumPS.setString(1, name);
		albumPS.setInt(2, nos);
		albumPS.setString(3, releasedate);
		albumPS.setString(4, sname);
		albumPS.executeUpdate();//insert album
		albumPS.close();//close prepareStatement
	}
}
