/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoecole;

import dataaccesslayer.DataAccessLayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.shaded.apache.poi.hssf.usermodel.HSSFRow;
import org.shaded.apache.poi.hssf.usermodel.HSSFSheet;
import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author KAMDEM VADECE
 */
public class ListedesclientsController implements Initializable {

    @FXML
    private TableView<tableclients> clientlist;
    @FXML
    private TableColumn<tableclients, String> id;
    @FXML
    private TableColumn<tableclients, String> name;
    @FXML
    private TableColumn<tableclients, String> birthdate;
    @FXML
    private TableColumn<tableclients, String> birthplace;
    @FXML
    private TableColumn<tableclients, String> email;
    @FXML
    private TableColumn<tableclients, String> phone;
    @FXML
    private TableColumn<tableclients, String> address;
    @FXML
    private Button addclient;
    private Label session;
    
    ObservableList<tableclients> clist = FXCollections.observableArrayList();
    ObservableList<String> slist = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> corn;
    @FXML
    private TableColumn<tableclients, String> idcard;
    @FXML
    private Label print;
    Collections collections;
    @FXML
    private Button save;
    @FXML
    private AnchorPane listpage;
    @FXML
    private Button finance;
    @FXML
    private Button home;
    @FXML
    private TableColumn<?, ?> iu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        corn.setEditable(true);
        try {
            // TODO
            DataAccessLayer dal = new DataAccessLayer();
            ResultSet rs = dal.dbreadsession();
            while(rs.next()){
              
                String a = rs.getString("sessionname");
                
                
                slist.add(a);
                
            }
            collections.sort(slist);
            TextFields.bindAutoCompletion(corn.getEditor(), slist).setOnAutoCompleted(e -> {
            
                try {
                    String ses = corn.getEditor().getText();
                    clientlist.getItems().clear();
                    ResultSet rss = dal.dbreadclients(ses);
                    while(rss.next()){
                        
                        String a = rss.getString("id");
                        String aa = rss.getString("identifiant_unique");
                        String b = rss.getString("id_client");
                        String c = rss.getString("nom_client");
                        String d = rss.getString("date_de_naissance_client");
                        String ee = rss.getString("lieu_de_naissance_client");
                        String f = rss.getString("adress_mail_client");
                        String g = rss.getString("telephone_client");
                        String h = rss.getString("adress_client");
                        
                        
                        clist.add(new tableclients(a,aa,b,c,d,ee,f,g,h));
                        
                    }
                    clientlist.setItems(clist);
                    id.setCellValueFactory(new PropertyValueFactory<>("id"));
                    iu.setCellValueFactory(new PropertyValueFactory<>("identifiant_unique"));
                    idcard.setCellValueFactory(new PropertyValueFactory<>("id_client"));
                    name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
                    birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance_client"));
                    birthplace.setCellValueFactory(new PropertyValueFactory<>("lieu_de_naissance_client"));
                    email.setCellValueFactory(new PropertyValueFactory<>("adress_mail_client"));
                    phone.setCellValueFactory(new PropertyValueFactory<>("telephone_client"));
                    address.setCellValueFactory(new PropertyValueFactory<>("adress_client"));
                } catch (SQLException ex) {
                    Logger.getLogger(ListedesclientsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            });
            corn.setItems(slist); 
        } catch (SQLException ex) {
            Logger.getLogger(ListedesclientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }    

    @FXML
    private void btnaddclient(ActionEvent event) throws IOException {
     String s = corn.getValue();
     String ss = corn.getEditor().getText();
     if(corn.getValue()!=null){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customerinfo.fxml"));
        
        
        
        
        Parent cuspage = loader.load();
 
        Scene cuspagescene = new Scene(cuspage);
        CustomerinfoController select = loader.getController();             
        select.sessions(s);
        Stage appstage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appstage2.setScene(cuspagescene);
        appstage2.setTitle("AJOUTEZ CLIENT");
        appstage2.show();     
     }else{
     print.setText("CHOISISEZ D'ABORD UNE SESSION");
     }
      if(!corn.getEditor().getText().isEmpty()){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customerinfo.fxml"));
        
        
        
        
        Parent cuspage = loader.load();
 
        Scene cuspagescene = new Scene(cuspage);
        CustomerinfoController select = loader.getController();             
        select.sessions(ss);
        Stage appstage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appstage2.setScene(cuspagescene);
        appstage2.setTitle("AJOUTEZ CLIENT");
        appstage2.show();     
     }else{
     print.setText("CHOISISEZ D'ABORD UNE SESSION");
     }
    }

    @FXML
    private void btncorn(ActionEvent event) throws SQLException, IOException {
        String ses = corn.getValue();
        clientlist.getItems().clear();
        DataAccessLayer dal = new DataAccessLayer();        
        ResultSet rs = dal.dbreadclients(ses);
            while(rs.next()){
              
                String a = rs.getString("id");
                String aa = rs.getString("identifiant_unique");
                 String b = rs.getString("id_client");
                  String c = rs.getString("nom_client");
                   String d = rs.getString("date_de_naissance_client");
                    String e = rs.getString("lieu_de_naissance_client"); 
                    String f = rs.getString("adress_mail_client");
                     String g = rs.getString("telephone_client");
                     String h = rs.getString("adress_client");
                    
                
                clist.add(new tableclients(a,aa,b,c,d,e,f,g,h));
                
            }   
            clientlist.setItems(clist);
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            iu.setCellValueFactory(new PropertyValueFactory<>("identifiant_unique"));
            idcard.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance_client"));
            birthplace.setCellValueFactory(new PropertyValueFactory<>("lieu_de_naissance_client"));
            email.setCellValueFactory(new PropertyValueFactory<>("adress_mail_client"));
            phone.setCellValueFactory(new PropertyValueFactory<>("telephone_client"));
            address.setCellValueFactory(new PropertyValueFactory<>("adress_client"));
            
            
    }

    @FXML
    private void btnsave(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        print.setText(" ");
          String sn = corn.getValue();
          String ssn = corn.getEditor().getText();
          if(sn!=null){
          DataAccessLayer dbl = new DataAccessLayer();
          ResultSet rs = dbl.dbexportsession(sn);
             int rownum0 = 0;
             int rownum1 = rownum0+1;
             int rownum2 = rownum1+1;
            
             int index = rownum2+1;
             
             
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=  workbook.createSheet();;
        HSSFRow row0= sheet.createRow(rownum0);
        row0.createCell(0).setCellValue("SESSION");
        row0.createCell(1).setCellValue("");
        row0.createCell(2).setCellValue("");
        row0.createCell(3).setCellValue(sn.toUpperCase());
        row0.createCell(4).setCellValue("");
        row0.createCell(5).setCellValue("");
        row0.createCell(6).setCellValue("");
        row0.createCell(7).setCellValue("");
        row0.createCell(8).setCellValue("");
        HSSFRow row1= sheet.createRow(rownum1);
        row1.createCell(0).setCellValue("");
        row1.createCell(1).setCellValue("");
        row1.createCell(2).setCellValue("");
        row1.createCell(3).setCellValue("");
        row1.createCell(4).setCellValue("");
        row1.createCell(5).setCellValue("");
        row1.createCell(6).setCellValue("");
        row1.createCell(7).setCellValue("");
        HSSFRow row2= sheet.createRow(rownum2);
        row2.createCell(0).setCellValue("ID");
        row2.createCell(1).setCellValue("IDENTIFIANT UNIQUE");
        row2.createCell(2).setCellValue("NUMERO DE CARTE D'IDENDITE");
        row2.createCell(3).setCellValue("NOM DU CLIENT");
        row2.createCell(4).setCellValue("DATE DE NAISSANCE");
        row2.createCell(5).setCellValue("LIEU DE NAISSANCE");
        row2.createCell(6).setCellValue("ADRESSE E-MAIL");
        row2.createCell(7).setCellValue("NUMERO DE TELEPHONE");
        row2.createCell(8).setCellValue("ADRESSE");
        
        while(rs.next()){
        HSSFRow rowindex= sheet.createRow(index);
        rowindex.createCell(0).setCellValue(rs.getString("id"));
        rowindex.createCell(1).setCellValue(rs.getString("identifiant_unique"));
        rowindex.createCell(2).setCellValue(rs.getString("id_client"));
        rowindex.createCell(3).setCellValue(rs.getString("nom_client"));
        rowindex.createCell(4).setCellValue(rs.getString("date_de_naissance_client"));
        rowindex.createCell(5).setCellValue(rs.getString("lieu_de_naissance_client"));
        rowindex.createCell(6).setCellValue(rs.getString("adress_mail_client"));
        rowindex.createCell(7).setCellValue(rs.getString("telephone_client"));
        rowindex.createCell(8).setCellValue(rs.getString("adress_client"));
        
        index++;
        rownum0++;
        rownum1++;
        rownum2++;
        
       
        }
        
        DirectoryChooser directorychooser = new DirectoryChooser();
       
        directorychooser.setTitle("SELECTIONNEZ LE FICHIER DANS LE QUEL VOUS VOULEZ SAUVEGARDER");
        File file = directorychooser.showDialog(listpage.getScene().getWindow());
        
        FileOutputStream f = new FileOutputStream(new File(file.getAbsolutePath())+"/"+sn+".xls");
        
        workbook.write(f);
        f.close();
        
        
    print.setText("SAUVEGARDER");
          }
        
          if(!ssn.isEmpty()){
          DataAccessLayer dbl = new DataAccessLayer();
          ResultSet rs = dbl.dbexportsession(ssn);
             int rownum0 = 0;
             int rownum1 = rownum0+1;
             int rownum2 = rownum1+1;
            
             int index = rownum2+1;
             
             
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=  workbook.createSheet();;
        HSSFRow row0= sheet.createRow(rownum0);
        row0.createCell(0).setCellValue("SESSION");
        row0.createCell(1).setCellValue("");
        row0.createCell(2).setCellValue("");
        row0.createCell(3).setCellValue(sn.toUpperCase());
        row0.createCell(4).setCellValue("");
        row0.createCell(5).setCellValue("");
        row0.createCell(6).setCellValue("");
        row0.createCell(7).setCellValue("");
        row0.createCell(8).setCellValue("");
        HSSFRow row1= sheet.createRow(rownum1);
        row1.createCell(0).setCellValue("");
        row1.createCell(1).setCellValue("");
        row1.createCell(2).setCellValue("");
        row1.createCell(3).setCellValue("");
        row1.createCell(4).setCellValue("");
        row1.createCell(5).setCellValue("");
        row1.createCell(6).setCellValue("");
        row1.createCell(7).setCellValue("");
        HSSFRow row2= sheet.createRow(rownum2);
        row2.createCell(0).setCellValue("ID");
        row2.createCell(1).setCellValue("IDENTIFIANT UNIQUE");
        row2.createCell(2).setCellValue("NUMERO DE CARTE D'IDENDITE");
        row2.createCell(3).setCellValue("NOM DU CLIENT");
        row2.createCell(4).setCellValue("DATE DE NAISSANCE");
        row2.createCell(5).setCellValue("LIEU DE NAISSANCE");
        row2.createCell(6).setCellValue("ADRESSE E-MAIL");
        row2.createCell(7).setCellValue("NUMERO DE TELEPHONE");
        row2.createCell(8).setCellValue("ADRESSE");
        
        while(rs.next()){
        HSSFRow rowindex= sheet.createRow(index);
        rowindex.createCell(0).setCellValue(rs.getString("id"));
        rowindex.createCell(1).setCellValue(rs.getString("identifiant_unique"));
        rowindex.createCell(2).setCellValue(rs.getString("id_client"));
        rowindex.createCell(3).setCellValue(rs.getString("nom_client"));
        rowindex.createCell(4).setCellValue(rs.getString("date_de_naissance_client"));
        rowindex.createCell(5).setCellValue(rs.getString("lieu_de_naissance_client"));
        rowindex.createCell(6).setCellValue(rs.getString("adress_mail_client"));
        rowindex.createCell(7).setCellValue(rs.getString("telephone_client"));
        rowindex.createCell(8).setCellValue(rs.getString("adress_client"));
        
        index++;
        rownum0++;
        rownum1++;
        rownum2++;
        
       
        }
        
        DirectoryChooser directorychooser = new DirectoryChooser();
       
        directorychooser.setTitle("SELECTIONNEZ LE FICHIER DANS LE QUEL VOUS VOULEZ SAUVEGARDER");
        File file = directorychooser.showDialog(listpage.getScene().getWindow());
        
        FileOutputStream f = new FileOutputStream(new File(file.getAbsolutePath())+"/"+ssn+".xls");
        
        workbook.write(f);
        f.close();
        
        
    print.setText("SAUVEGARDER");
          }
        
        
    }

    @FXML
    private void btnfinance(ActionEvent event) throws IOException {
         
        Parent root = FXMLLoader.load(getClass().getResource("finance.fxml"));
       
       Scene scene = new Scene(root);
       Stage primaryStage = new Stage();
       primaryStage.setScene(scene);
       primaryStage.setTitle("FINANCE");
       primaryStage.setResizable(true);
       primaryStage.show();
    }

    @FXML
    private void btnhome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeFXML.fxml"));
   
        Parent cuspage = loader.load();
 
        Scene cuspagescene = new Scene(cuspage);
        Stage appstage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appstage2.setScene(cuspagescene);
        appstage2.setTitle("AUTO ECOLE");
        appstage2.show();      
    }
    
}
