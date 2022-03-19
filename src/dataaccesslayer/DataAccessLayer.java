/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccesslayer;


import com.sun.rowset.internal.Row;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import org.shaded.apache.poi.hssf.usermodel.HSSFCell;
import org.shaded.apache.poi.hssf.usermodel.HSSFRow;
import org.shaded.apache.poi.hssf.usermodel.HSSFSheet;
import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.shaded.apache.poi.ss.usermodel.DataFormatter;

/**
 *
 * @author KAMDEM VADECE
 */
public class DataAccessLayer {
     Connection con;
     DBconnect dbcon = new DBconnect();
     static int size;
     static String cnames ="";
     static String lectname="";
      
   public DataAccessLayer(){
     
    }
  
   public void dbaddcourse() throws SQLException{
   
       con = dbcon.getConnection(); 
       ResultSet coursename = con.createStatement().executeQuery("select id from course where coursename=\""+cnames+"\"");
       
    if(!coursename.next()){
       String sql1 = "insert into course (coursename,lecturer) values (?,?)";
       PreparedStatement addcourse = con.prepareStatement(sql1);
       addcourse.setString(1,cnames);
       addcourse.setString(2,lectname);
       addcourse.execute();
    }
   }
   
   public void dbdeletecourse(String course) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "delete from course where coursename=?";
       PreparedStatement deletecourse = con.prepareStatement(sql);
       deletecourse.setString(1,course);
       deletecourse.execute();
   }
   
   public ResultSet[] dbreadcourse() throws SQLException{
       con = dbcon.getConnection();
       ResultSet coursename = con.createStatement().executeQuery("select coursename from course");
      ResultSet lecturer  = con.createStatement().executeQuery("select lecturer from course");
        return new ResultSet[]{coursename,lecturer};
   
   }
    public ResultSet dbreadstudent(String course) throws SQLException{
       con = dbcon.getConnection();
       ResultSet coursename = con.createStatement().executeQuery("select sname from "+course+"");
      
        return coursename;
   
   }
   
    public void dbaddcoursetable() throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "CREATE table if not EXISTS "+cnames+"(id int AUTO_INCREMENT primary KEY, sname varchar(100), smatricule varchar(100), se_mail varchar(100), sphonenumber varchar(100), sgrade varchar(20))";
       PreparedStatement addcoursetable = con.prepareStatement(sql);
       addcoursetable.execute();
   }
   
    public void dbdeletecoursetable(String course) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "DROP table if exists "+course+"";
       PreparedStatement deletecoursetable = con.prepareStatement(sql);
       deletecoursetable.execute();
   }
    
    public void dbaddexcelsheet (File file) throws SQLException, FileNotFoundException, IOException{
     con = dbcon.getConnection(); 
       String sql = "insert into "+cnames+" (sname,smatricule,se_mail,sphonenumber,sgrade) values (?,?,?,?,?)";
       PreparedStatement addexcelsheet = con.prepareStatement(sql);
       FileInputStream f = new FileInputStream(new File(file.getAbsolutePath()));
       HSSFWorkbook hssfWorkbook=new HSSFWorkbook(f);
        HSSFSheet hssfSheet=  hssfWorkbook.getSheetAt(0);
        HSSFRow firstRow;
       
       //String defaultsgrade = "PENDING";
       Iterator rowIter = hssfSheet.rowIterator();
       String data;
       HSSFRow r = (HSSFRow)rowIter.next(); 
       short lastCellNum = r.getLastCellNum();
        int[] dataCount = new int[lastCellNum];
        int col = 0;
        
        while(rowIter.hasNext()) {
            Iterator cellIter = ((HSSFRow)rowIter.next()).cellIterator();
            while(cellIter.hasNext()) {
                HSSFCell cell = (HSSFCell)cellIter.next();
                col = cell.getColumnIndex();
                dataCount[col] += 1;
                DataFormatter df = new DataFormatter();
                data = df.formatCellValue(cell);
                System.out.println("Data: " + data);
            }
        }
        f.close();
  
        for(int x = 0; x < dataCount.length; x++) {
           size = dataCount[x];
        }
       for(int i=3; i<=size; i++){
        firstRow = hssfSheet.getRow(i);
       
        addexcelsheet.setString(1,firstRow.getCell(1).getStringCellValue());
        addexcelsheet.setString(2,firstRow.getCell(2).getStringCellValue());
        addexcelsheet.setString(3,firstRow.getCell(3).getStringCellValue());
        addexcelsheet.setString(4,firstRow.getCell(4).getStringCellValue());
        addexcelsheet.setString(5,firstRow.getCell(5).getStringCellValue());
        //addexcelsheet.setString(5,defaultsgrade);
      
       addexcelsheet.execute();
       }
   
    }
     public ResultSet[] dbreadcoursedata(String coursen) throws SQLException{
       con = dbcon.getConnection(); 
       String sqln = "SELECT sname FROM "+coursen;
       PreparedStatement readcoursedatan = con.prepareStatement(sqln);
      
       
       String sqlm = "SELECT smatricule FROM "+coursen;
       PreparedStatement readcoursedatam = con.prepareStatement(sqlm);
       
       String sqls = "SELECT sgrade FROM "+coursen;
       PreparedStatement readfeestatus = con.prepareStatement(sqls);
       
       String sqli = "SELECT id FROM "+coursen;
       PreparedStatement readid = con.prepareStatement(sqli);
     
         
       ResultSet coursename = readcoursedatan.executeQuery();
       ResultSet coursematricule = readcoursedatam.executeQuery();
       ResultSet grade = readfeestatus.executeQuery();
       ResultSet id = readid.executeQuery();
      
        return new ResultSet[]{coursename,coursematricule,grade,id};
   
   }
   
      public ResultSet[] dbreadcoursedataparticularstudent(String coursen,String sname) throws SQLException{
       con = dbcon.getConnection(); 
      
       String sqlm = "SELECT smatricule FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readcoursedatam = con.prepareStatement(sqlm);
       
       String sqls = "SELECT sgrade FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readfeestatus = con.prepareStatement(sqls);
       
       String sqli = "SELECT id FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readid = con.prepareStatement(sqli);
     
         
       
       ResultSet smatricule = readcoursedatam.executeQuery();
       ResultSet feestatus = readfeestatus.executeQuery();
       ResultSet id = readid.executeQuery();
      
        return new ResultSet[]{smatricule,feestatus,id};
   
   }
   
     public void dbstudentpaymentstatus(String coursen ,String matricule,String grade) throws SQLException{
       con = dbcon.getConnection();
       String sql = "UPDATE "+coursen+" SET sgrade=\""+grade+"\" WHERE smatricule=\""+matricule+"\"";
       PreparedStatement studentpaymentstatus = con.prepareStatement(sql);
     
       studentpaymentstatus.execute();
   }
     
   
      public ResultSet dbmfchecker(String coursen) throws SQLException{
       con = dbcon.getConnection(); 
       String sql = "SELECT sgrade FROM "+coursen;
       PreparedStatement mfchecker = con.prepareStatement(sql);
      
       ResultSet coursename = mfchecker.executeQuery();

      
        return coursename;
   
   }
     
       public void dbgetcoursenameandlecturerfromexcelsheet(File file) throws SQLException, IOException{
       con = dbcon.getConnection();
       
       FileInputStream f = new FileInputStream(new File(file.getAbsolutePath()));
       HSSFWorkbook hssfWorkbook=new HSSFWorkbook(f);
        HSSFSheet hssfSheet=  hssfWorkbook.getSheetAt(0);
        HSSFRow Row0;
        HSSFRow Row1;

        Row0 = hssfSheet.getRow(0);
        Row1 = hssfSheet.getRow(1);
       
       cnames= Row0.getCell(1).getStringCellValue();
       lectname= Row1.getCell(1).getStringCellValue();
       
        }
       public ResultSet[] dbgetmatriculeemailgrade(String cn) throws SQLException{
       con = dbcon.getConnection(); 
       String sqlm = "SELECT smatricule FROM "+cn;
       PreparedStatement readcoursedatam = con.prepareStatement(sqlm);
       
       String sqls = "SELECT sgrade FROM "+cn;
       PreparedStatement readfsgrade = con.prepareStatement(sqls);
       
       String sqli = "SELECT se_mail FROM "+cn;
       PreparedStatement reademail = con.prepareStatement(sqli);
     
         
       
       ResultSet smatricule = readcoursedatam.executeQuery();
       ResultSet sgrade = readfsgrade.executeQuery();
       ResultSet email = reademail.executeQuery();
      
        return new ResultSet[]{smatricule,sgrade,email};
   
       
       }
       
       public void dbaddclient(String session,String aa,String bb,String cc,String dd,String ee,String ff,String gg,String hh) throws SQLException{
       con = dbcon.getConnection(); 
      String sql = "insert into "+session+" (id_client,nom_client,date_de_naissance_client,lieu_de_naissance_client,adress_mail_client,telephone_client,adress_client) values (?,?,?,?,?,?,?)";
       PreparedStatement addclient = con.prepareStatement(sql); 
        addclient.setString(1,aa);
        addclient.setString(2,bb);
        addclient.setString(3,cc);
        addclient.setString(4,dd);
        addclient.setString(5,ee);
        addclient.setString(6,ff);
        addclient.setString(7,gg);
         addclient.execute();
         
         ResultSet rs = con.createStatement().executeQuery("SELECT id FROM "+session+" where nom_client=\""+bb+"\"");
              rs.next();
             
              String h = rs.getString("id");
              String hhh = hh+h;
              
       
        String sql1 = "UPDATE "+session+" SET identifiant_unique=\""+hhh+"\" WHERE nom_client=\""+bb+"\"";
        PreparedStatement adduniqueid= con.prepareStatement(sql1); 
       
       adduniqueid.execute();
       
       
   }
       
        public void dbaddclientfinance(String session,String aa,String bb) throws SQLException{
       con = dbcon.getConnection(); 
      String sql = "insert into "+session+"finance (id_client,nom_client) values (?,?)";
       PreparedStatement addclientfinance = con.prepareStatement(sql); 
        addclientfinance.setString(1,aa);
        addclientfinance.setString(2,bb);
        
      
       addclientfinance.execute();
   }
       
   
       public ResultSet dbreadclients(String session) throws SQLException{
       con = dbcon.getConnection();
       ResultSet clientdata = con.createStatement().executeQuery("select * from "+session);
        return clientdata;
   
   }  
     
       public void dbaddsession(String sname) throws SQLException{
   
       con = dbcon.getConnection(); 
       ResultSet sessionname = con.createStatement().executeQuery("select id from session where sessionname=\""+sname+"\"");
       
    if(!sessionname.next()){
       String sql1 = "insert into session (sessionname) values (?)";
       PreparedStatement addsession = con.prepareStatement(sql1);
       addsession.setString(1,"S"+sname);
       addsession.execute();
    }
   }
       
        public ResultSet dbreadsession() throws SQLException{
       con = dbcon.getConnection();
       ResultSet sessiondata = con.createStatement().executeQuery("select sessionname from session");
        return sessiondata;
   
   }  
        
        public ResultSet dbexportsession(String sessionn) throws SQLException{
       con = dbcon.getConnection(); 
       String sql = "SELECT * FROM "+sessionn;
       PreparedStatement courselist = con.prepareStatement(sql);
      
       ResultSet session = courselist.executeQuery();

      
        return session;
     
}
        
        public ResultSet dbexportsessionfinance(String sessionn) throws SQLException{
       con = dbcon.getConnection(); 
       String sql = "SELECT * FROM "+sessionn+"finance";
       PreparedStatement courselist = con.prepareStatement(sql);
      
       ResultSet session = courselist.executeQuery();

      
        return session;
     
}
        
        public void dbaddsessiontable(String snames) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "CREATE table if not EXISTS S"+snames+"(id int AUTO_INCREMENT primary KEY, id_client varchar(100),identifiant_unique varchar(100), nom_client varchar(100), date_de_naissance_client varchar(100), lieu_de_naissance_client varchar(100), adress_mail_client varchar(100), telephone_client varchar(100), adress_client varchar(100),argent_percu varchar(100),date_de_reception varchar(100),reste_a_payer varchar(100))";
       PreparedStatement addcoursetable = con.prepareStatement(sql);
       addcoursetable.execute();
   }
        
         public void dbaddsessiontablefinance(String snames) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "CREATE table if not EXISTS S"+snames+"finance(id int AUTO_INCREMENT primary KEY, id_client varchar(100), nom_client varchar(100),argent_percu varchar(100),date_de_reception varchar(100),reste_a_payer varchar(100))";
       PreparedStatement addcoursetable = con.prepareStatement(sql);
       addcoursetable.execute();
   }
        
        public void dbdeletesession(String sessions) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "delete from session where sessionname=?";
       PreparedStatement deletesession = con.prepareStatement(sql);
       deletesession.setString(1,sessions);
       deletesession.execute();
   }
    
        public void dbdeletesessiontable(String session) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "DROP table if exists "+session+"";
       PreparedStatement deletesessiontable = con.prepareStatement(sql);
       deletesessiontable.execute();
   }
        
        public void createsessiontable() throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "CREATE table if not EXISTS session (id int AUTO_INCREMENT primary KEY, sessionname varchar(100))";
       PreparedStatement addcoursetable = con.prepareStatement(sql);
       addcoursetable.execute();
   }
        
         public ResultSet dbreadclient(String sn) throws SQLException{
       con = dbcon.getConnection();
       ResultSet clientdata = con.createStatement().executeQuery("select nom_client from "+sn);
        return clientdata;
   
   }  
         
          public ResultSet dbreadclientparticularfinance(String sn, String cn) throws SQLException{
       con = dbcon.getConnection();
       ResultSet clientdata = con.createStatement().executeQuery("select * from "+sn+"finance where nom_client=\""+cn+"\"");
        return clientdata;
   
   }  
          
          public void dbstoresumandrest(String sn,String cid,String cn,String ap,String ddr,String rap) throws SQLException{
       con = dbcon.getConnection(); 
       
      String sql = "insert into "+sn+"finance(id_client,nom_client,argent_percu,date_de_reception,reste_a_payer) values (?,?,?,?,?)";
       PreparedStatement addclientfinance = con.prepareStatement(sql); 
        addclientfinance.setString(1,cid);
        addclientfinance.setString(2,cn);
        addclientfinance.setString(3,ap);
        addclientfinance.setString(4,ddr);
        addclientfinance.setString(5,rap);
         addclientfinance.execute();
       
      
         String sql1 = "UPDATE "+sn+" SET reste_a_payer=\""+rap+"\" WHERE nom_client=\""+cn+"\"";
        PreparedStatement addclient= con.prepareStatement(sql1); 
       
       addclient.execute();
       
       
   }
          
          public String dbgetresttopay(String sname, String cn) throws SQLException{
              con = dbcon.getConnection();
              ResultSet rs = con.createStatement().executeQuery("SELECT reste_a_payer FROM "+sname+"finance where nom_client=\""+cn+"\"");
              ResultSet rss = con.createStatement().executeQuery("SELECT reste_a_payer FROM "+sname+" where nom_client=\""+cn+"\"");
              rs.next();
              rss.next();
              String a = rss.getString("reste_a_payer");
              return a;
          }
        
      public void dbsaveclienttotal(String sn,String cn,String total) throws SQLException{
       con = dbcon.getConnection(); 
       String sql = "UPDATE "+sn+"finance SET reste_a_payer=\""+total+"\" WHERE nom_client=\""+cn+"\"";
       PreparedStatement settotal = con.prepareStatement(sql);
      settotal.execute();
      String sql1 = "UPDATE "+sn+" SET reste_a_payer=\""+total+"\" WHERE nom_client=\""+cn+"\"";
       PreparedStatement settotal1 = con.prepareStatement(sql1);
      settotal1.execute();
      
      }
      
          
}
