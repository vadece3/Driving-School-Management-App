/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoecole;

import dataaccesslayer.DataAccessLayer;
import java.awt.Checkbox;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.shaded.apache.poi.hssf.usermodel.HSSFRow;
import org.shaded.apache.poi.hssf.usermodel.HSSFSheet;
import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author KAMDEM VADECE
 */
public class HomeFXMLController implements Initializable {

    @FXML
    private Button sendall;
    @FXML
    private TableColumn<tableviewwithcheckbox, String> coursename;
    @FXML
    private CheckBox selectallcourse_checkbox;
    @FXML
    private Button addcourse;
    @FXML
    private Button deletecourse;
    @FXML
    private Label labelnotification;
    @FXML
    private TextField textnewcourse;
    @FXML
    private TableView<tableviewwithcheckbox> tableview;
    @FXML
    private TableColumn<tableviewwithcheckbox, CheckBox> cb;
    
    ObservableList<tableviewwithcheckbox> sessionnamelist = FXCollections.observableArrayList();
    @FXML
    private Button work;
    @FXML
    private MenuItem refresh;
    @FXML
    private AnchorPane home;
    @FXML
    private Button savebutton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            // TODO
             
           
           DataAccessLayer dbl = new DataAccessLayer();
           dbl.createsessiontable();
           ResultSet rs = dbl.dbreadsession();
            while(rs.next()){
               
                CheckBox cb = new CheckBox();
                String sname = rs.getString("sessionname");
               
                sessionnamelist.add(new tableviewwithcheckbox(sname,cb));
                
            }   
            tableview.setItems(sessionnamelist);
            coursename.setCellValueFactory(new PropertyValueFactory<>("sessionname"));
            cb.setCellValueFactory(new PropertyValueFactory<>("cb"));
        } catch (SQLException ex) {
            Logger.getLogger(HomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }
    @FXML
    private void btnsendall(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
           
        DirectoryChooser directorychooser = new DirectoryChooser();
       
        directorychooser.setTitle("CHOISIR OU SAUVEGARDER LE FICHIER ");
        File file = directorychooser.showDialog(home.getScene().getWindow());
        
        FileOutputStream f = new FileOutputStream(new File(file.getAbsolutePath())+"/.xls");
        
             int rownum0 = 0;
             int rownum1 = rownum0+1;
            
             int index = rownum1+1;
             HSSFWorkbook workbook=new HSSFWorkbook();
             HSSFSheet sheet=  workbook.createSheet();
        for(tableviewwithcheckbox table : sessionnamelist) {
          table.cb.setSelected(true);selectallcourse_checkbox.setSelected(true); 
         if(selectallcourse_checkbox.isSelected())
         {
       labelnotification.setText(" ");
       String sn = table.getSessionname();    
        DataAccessLayer dbl = new DataAccessLayer();
        
        ResultSet rs = dbl.dbexportsession(sn);
          
         HSSFRow row0= sheet.createRow(rownum0);
        row0.createCell(0).setCellValue("SESSION");
        row0.createCell(1).setCellValue("");
        row0.createCell(2).setCellValue("");
        row0.createCell(3).setCellValue(sn.toUpperCase());
        row0.createCell(4).setCellValue("");
        row0.createCell(5).setCellValue("");
        row0.createCell(6).setCellValue("");
        row0.createCell(7).setCellValue("");
        HSSFRow row1= sheet.createRow(rownum1);
        row1.createCell(0).setCellValue("ID");
        row1.createCell(1).setCellValue("NUMERO DE CARTE D'IDENDITE");
        row1.createCell(2).setCellValue("NOM DU CLIENT");
        row1.createCell(3).setCellValue("DATE DE NAISSANCE");
        row1.createCell(4).setCellValue("LIEU DE NAISSANCE");
        row1.createCell(5).setCellValue("ADRESS E-MAIL");
        row1.createCell(6).setCellValue("NUMERO DE TELEPHONE");
        row1.createCell(7).setCellValue("ADRESS");
        
        while(rs.next()){
         HSSFRow rowindex= sheet.createRow(index);
        rowindex.createCell(0).setCellValue(rs.getString("id"));
        rowindex.createCell(1).setCellValue(rs.getString("id_client"));
        rowindex.createCell(2).setCellValue(rs.getString("nom_client"));
        rowindex.createCell(3).setCellValue(rs.getString("date_de_naissance_client"));
        rowindex.createCell(4).setCellValue(rs.getString("lieu_de_naissance_client"));
        rowindex.createCell(5).setCellValue(rs.getString("adress_mail_client"));
        rowindex.createCell(6).setCellValue(rs.getString("telephone_client"));
        rowindex.createCell(7).setCellValue(rs.getString("adress_client"));
        
        index++;
        rownum0++;
        rownum1++;
        
       
        }
        
        
         } table.cb.setSelected(false);selectallcourse_checkbox.setSelected(false);
         }
        workbook.write(f); 
        
       
        f.close();
         
        
    }


    @FXML
    private void btnselectallcourse_checkbox(ActionEvent event) {
         for(tableviewwithcheckbox checkbox : sessionnamelist) {
         if(selectallcourse_checkbox.isSelected())
         {
         checkbox.cb.setSelected(true);
         }
         else{
         checkbox.cb.setSelected(false);
         }
         }
        
        
    }

    @FXML
    private void btnaddcourse(ActionEvent event) throws SQLException, IOException {
        String sessionn = textnewcourse.getText();
        if(!sessionn.isEmpty()){
            labelnotification.setText(" ");
        DataAccessLayer dbaccesslayer = new DataAccessLayer();
     
        dbaccesslayer.dbaddsessiontable(sessionn);
        dbaccesslayer.dbaddsessiontablefinance(sessionn);
        dbaccesslayer.dbaddsession(sessionn);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeFXML.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("AUTO ECOLE");
        stage.show();
        }else{
         labelnotification.setText("ENTREZ LE NOM DE LA NOUVELLE SESSION");
        }
    }

    @FXML
    private void btndeletecourse(ActionEvent event) throws SQLException, IOException {
       
        DataAccessLayer dbaccesslayer = new DataAccessLayer();
        //code to get name to be deleted
       for(tableviewwithcheckbox table : sessionnamelist) 
       {
       if(table.getCb().isSelected()){
           labelnotification.setText(" ");
       String sname = table.getSessionname();
       dbaccesslayer.dbdeletesession(sname);
       dbaccesslayer.dbdeletesessiontable(sname);
       
       }
       else{
       labelnotification.setText("SELLECTIONEZ UNE SESSION");
       }
       }
     FXMLLoader loader = new FXMLLoader(getClass().getResource("homeFXML.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("AUTO ECOLE");
        stage.show();
}

    @FXML
    private void btnwork(ActionEvent event) throws SQLException, IOException {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listedesclients.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("AUTO ECOLE");
        stage.show();
    }

    @FXML
    private void btnrefresh(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("homeFXML.fxml"));
        home.getChildren().setAll(pane);
    }

    
}
