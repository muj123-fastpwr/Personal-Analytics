package midFidelty;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class Window {
	private String[] apps;
	private String[] wins;
	private GridBagConstraints gbc_label;

	
	
	public void openWindows(){
		apps = null;
		wins = null;
		String[] a = new String[100];
		String[] w = new String[100];
		
		try {
			String process;
			
			int i=0;
			Process p = Runtime.getRuntime().exec("tasklist /v /fo csv /nh");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		//	System.out.println(input.read());// total number of lines
			while ((process = input.readLine()) != null) {
				process = process.replaceAll("\"", "");
				String[]str = process.split(",");
				if(str.length == 11){
					if(!str[10].equals("N/A")){
			//			System.out.println(str[0]+"__"+str[10]);
						a[i] = str[0];
						w[i] = str[10];
					}	
				}
				else if(str.length == 10){
					if(!str[9].equals("N/A")){
			//			System.out.println(str[0]+"__"+str[9]);
						a[i] = str[0];
						w[i] = str[9];
					}	
				}
				else if(str.length==9){
					if(!str[8].equals("N/A")){
			//			System.out.println(str[0]+"__"+str[8]);
						a[i] = str[0];
						w[i] = str[8];
					}
				}
					i++;				
			}
			input.close();
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		int count=0;
		for(int j=0;j<a.length;j++){
			if(a[j]!=null){
				count++;
			}
		}
		String[]ap = new String[count];
		String[]win = new String[count];
		count=0;
		for(int j=0;j<a.length;j++){
			if(a[j]!=null){
				if(w[j].matches("(.*) - (.*)")){	
					ap[count]=a[j];
					win[count]=w[j];
					count++;
				}
			}
		}
		
		apps = ap;
		wins = win;
	}

	
	
	public void currentApps(JTextArea activeWindow, JPanel CurrentApps, GridBagLayout gbl_CurrentApps){
		CurrentApps.removeAll();
		gbl_CurrentApps.columnWidths = new int[]{89, 0};
		gbl_CurrentApps.rowHeights = new int[]{14, 14, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_CurrentApps.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_CurrentApps.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		CurrentApps.setLayout(gbl_CurrentApps);
		
		
		for(int i=0;i<apps.length-1;i++){
			
			JLabel appLabel = new JLabel(apps[i]);
			appLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			appLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					String temp="";
					String label = appLabel.getText();
					for(int j=0;j<apps.length;j++){
						if(label.equals(apps[j])){
							temp = wins[j]+"\n";
							break;
						}
					}
					activeWindow.setText(temp);
					
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					appLabel.setForeground(Color.GRAY);
					appLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					appLabel.setForeground(Color.BLACK);
				}
			});
			
			gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.WEST;
			gbc_label.insets = new Insets(0, 0, 5, 0);
			gbc_label.gridx = 0;
			gbc_label.gridy = i;
			CurrentApps.add(appLabel, gbc_label);
		}
		
		
	}
	
	
	
	
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
	        Thread.sleep(5000);
	        
	        GregorianCalendar gcalendar1 = new GregorianCalendar();
			h1 = gcalendar1.get(Calendar.HOUR);
			m1 = gcalendar1.get(Calendar.MINUTE);
			s1 = gcalendar1.get(Calendar.SECOND);
			time = h1 * 3600 + m1 *60 + s1;
	        com.autoUpdate(windowTitle=windowTitle.trim(),time);
	        gcalendar1 = null;
	        System.gc();
		}
	}
	
	

}
