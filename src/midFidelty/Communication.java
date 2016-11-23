package midFidelty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Communication {
	private Connection cn;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement ps;
	
	public Communication(Connection cn){
		this.cn = cn;
	}
	
	public void updateData(String title, int time){
		String query="select winTitle from winInfo where winTitle='"+title+"';";
		String row;
		boolean titleExists = false;
		try {
			st=cn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
				System.out.println("result of query : "+rs.getString(1));
				titleExists = true;
			}
		
			if(!titleExists){
			query = "insert into winInfo ('winTitle','time') values('"+title+"', "+time+");"; //auto-increment for pk, fk doesn't work
			try{
				ps = cn.prepareStatement(query);
				ps.executeUpdate();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unable to connect database\n"+e);
			}
		}
		} catch (SQLException e) {
		 	// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Unable to connect database\n"+e);
		}
		
	}
	
	
}
