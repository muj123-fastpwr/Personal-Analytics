package midFidelty;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class Window {
	
	
	
	public void focusedWindow(Connection cn) throws InterruptedException, HeadlessException, SQLException{
		char[] buffer = new char[100];
		String windowTitle=null;
		Communication com = new Communication(cn);
		int time=0;
		int h1,m1,s1;
		
		while(true){
	        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
	        User32.INSTANCE.GetWindowText(hwnd, buffer, 100);
	        windowTitle = Native.toString(buffer);
	        System.out.println("Active window title: " + Native.toString(buffer));
	        Thread.sleep(5000);
	        
	        GregorianCalendar gcalendar1 = new GregorianCalendar();
			h1 = gcalendar1.get(Calendar.HOUR);
			m1 = gcalendar1.get(Calendar.MINUTE);
			s1 = gcalendar1.get(Calendar.SECOND);
			time = h1 * 3600 + m1 *60 + s1;
	        com.autoUpdate(windowTitle,time);
	        gcalendar1 = null;
	        System.gc();
		}
	}
	
	
	

}
