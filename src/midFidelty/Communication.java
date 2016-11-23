package midFidelty;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

public class Communication {
	private Connection cn;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement ps;
	private int time;
	private String date;
	
	public Communication(Connection cn){
		this.cn = cn;
		GregorianCalendar gcalendar1 = new GregorianCalendar();
		int h1,m1,s1;
		h1 = gcalendar1.get(Calendar.HOUR);
		m1 = gcalendar1.get(Calendar.MINUTE);
		s1 = gcalendar1.get(Calendar.SECOND);
		time = h1 * 3600 + m1 *60 + s1;
		
		Date date2 = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		date = ft.format(date2);
		
	}
	
	public void updateData(String title,int newTime) throws HeadlessException, SQLException{
		int oldTime=0,winId=0;
		//String titleName=null;
		String query="select winId from window where winName='"+title+"';";
		boolean titleExists = false;
		try {
			st=cn.createStatement();
			rs=st.executeQuery(query);
			while(rs.next()){
			//	titleName = rs.getString(2);
				winId = Integer.parseInt(rs.getString(1));
				titleExists = true;
			}
		} catch (SQLException e) {
		 	// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Unable to retrieve from database\n"+e);
		}
			
		int id=0;
		if(!titleExists){
			
			query = "select count(winId) from window;";
			try{
				
				st=cn.createStatement();
				rs=st.executeQuery(query);
				while(rs.next()){
					id = Integer.parseInt(rs.getString(1))+1;
				}
				query = "insert into window values("+id+",'"+title+"');"; //auto-increment for pk, fk doesn't work
				ps = cn.prepareStatement(query);
				ps.executeUpdate();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unable to insert into Window Table\n"+e);
			}
			
			query = "insert into dateAndTime values("+id+",'"+date+"', "+(newTime-time)+")";
			try{
				ps = cn.prepareStatement(query);
				ps.executeUpdate();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Unable to insert into dateAndTime Table\n"+e);
			}
		}
		
		else if(titleExists){
			query = "select time from dateAndTime where winId="+winId+" and date='"+date+"'";
			try{
				st=cn.createStatement();
				rs=st.executeQuery(query);
				while(rs.next()){
					oldTime = Integer.parseInt(rs.getString(1));
				}
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Unable to retrieve from dateAndTime\n"+e);
			}
			if(oldTime!=0){
				query = "update dateAndTime set time ="+(newTime-time+oldTime)+" where winId="+winId+" and date='"+date+"'";
				try{
					ps = cn.prepareStatement(query);
					ps.executeUpdate();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unable to update time\n"+e);
				}
			}
			else if(oldTime==0){
				query = "insert into dateAndTime values("+winId+",'"+date+"',"+(newTime-time)+")";
				try{
					ps = cn.prepareStatement(query);
					ps.executeUpdate();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unable to update time for new day\n"+e);
				}
			}
			
		}
		
		time = newTime;
	}
	
	
}



