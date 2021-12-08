
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
/**
 * for singers table
 */
public class singer {
	public static String name;
	public static String debutdate;
	public static int nop;
	public static String sex;
	
	public static Connection myConn;
	public static PreparedStatement singerPS;
	
	/**
	 * constructor of singer class
	 * @param name name of singer
	 * @param debutdate debut date of singer
	 * @param nop number of person of singer
	 * @param sex sex of singer
	 * @param myConn connection variable
	 */
	singer(String name, String debutdate, int nop, String sex, Connection myConn){
		singer.name=name;
		singer.debutdate=debutdate;
		singer.nop=nop;
		singer.sex=sex;
		singer.myConn=myConn;
	}
	/**
	 * operate insert action using prepareStatement
	 * @throws SQLException parameter is null or empty
	 * @throws SQLIntegrityConstraintViolationException parameter is null or empty
	 * @throws SQLSyntaxErrorException parameter is null or empty
	 */
	public static void insert_singer() throws SQLException, SQLIntegrityConstraintViolationException, SQLSyntaxErrorException {
		singerPS=myConn.prepareStatement("insert into singers values(?,?,?,?)");
		//set parameter
		singerPS.setString(1, name);
		singerPS.setString(2, debutdate);
		singerPS.setInt(3, nop);
		singerPS.setString(4, sex);
		singerPS.executeUpdate();//insert singer
		singerPS.close();//close prepareStatement
	}
}
