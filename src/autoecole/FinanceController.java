/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoecole;

import ValidateTextfield.custom.NumberTextField;
import dataaccesslayer.DataAccessLayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.shaded.apache.poi.hssf.usermodel.HSSFRow;
import org.shaded.apache.poi.hssf.usermodel.HSSFSheet;
import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author KAMDEM VADECE
 */
public class FinanceController implements Initializable {

    @FXML
    private AnchorPane moneypage;
    @FXML
    private ComboBox<String> ses;
    @FXML
    private ComboBox<String> client;
    @FXML
    private TextField money;
    @FXML
    private Button validate;
    @FXML
    private TableView<tableargent> table_money;
    @FXML
    private TableColumn<tableargent, String> idc;
    @FXML
    private TableColumn<tableargent, String> name;
    @FXML
    private TableColumn<tableargent, String> money_entered;
    @FXML
    private TableColumn<tableargent, String> dates;
    @FXML
    private TableColumn<tableargent, String> reste_to_pay;
    
    ObservableList<String> clist = FXCollections.observableArrayList();
    ObservableList<tableargent> clistt = FXCollections.observableArrayList();
    ObservableList<String> slist = FXCollections.observableArrayList();
    Collections collections;
    @FXML
    private Label print;
    @FXML
    private Button save;
    @FXML
    private NumberTextField total;
    @FXML
    private Button savetotal; 
    private AutoCompletionBinding<String> autocompletebonding;
    private static String aaaa = null;
    //private Set<String> sujestions; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
            client.setEditable(true); 
            ses.setEditable(true);
        try {
            // TODO
            DataAccessLayer dal = new DataAccessLayer();
            ResultSet rs = dal.dbreadsession();
            while(rs.next()){
              
                String a = rs.getString("sessionname");
                
                
                slist.add(a);
                
            }
            collections.sort(slist);
            //sujestions = new HashSet<>(slist);
            TextFields.bindAutoCompletion(ses.getEditor(), slist).setOnAutoCompleted(e -> {
            
            
                try {
                    String sn = ses.getEditor().getText();
                    client.getItems().clear();
                    table_money.getItems().clear();
                    ResultSet rsss = dal.dbreadclient(sn);
                    while(rsss.next()){
                        
                        String a = rsss.getString("nom_client");
                        
                        
                        clist.add(a);
                        
                    }
                    collections.sort(clist);
                    client.setItems(clist);
                    
                    
                    
                    ResultSet rss = dal.dbreadclients(sn);
                    while(rss.next()){
                        
                        String b = rss.getString("id_client");
                        String c = rss.getString("nom_client");
                        String d = rss.getString("argent_percu");
                        String ee = rss.getString("date_de_reception");
                        String f = rss.getString("reste_a_payer");
                        
                        
                        clistt.add(new tableargent(b,c,d,ee,f));
                        
                    }
                    table_money.setItems(clistt);
                    idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
                    name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
                    money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
                    dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
                    reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
                } catch (SQLException ex) {
                    Logger.getLogger(FinanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
          
            
            
            
            });
           
            ses.setItems(slist);
            
        } catch (SQLException ex) {
            Logger.getLogger(ListedesclientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void btnses(ActionEvent event) throws SQLException {
        if(ses.getValue()!=null){
            String sn = ses.getValue();
        
        client.getItems().clear();
        table_money.getItems().clear();
         DataAccessLayer dal = new DataAccessLayer();
         ResultSet rs = dal.dbreadclient(sn);
         while(rs.next()){
              
                String a = rs.getString("nom_client");
                
                
                clist.add(a);
                
            }
            collections.sort(clist);
            
            TextFields.bindAutoCompletion(client.getEditor(), clist).setOnAutoCompleted(e -> {
            if(client.getValue()!=null){
                try {
                    String snn = ses.getValue();
                    String cn = client.getValue();
                    table_money.getItems().clear();
                    ResultSet rss = dal.dbreadclientparticularfinance(snn,cn);
                    while(rss.next()){
                        
                        String b = rss.getString("id_client");
                        String c = rss.getString("nom_client");
                        String d = rss.getString("argent_percu");
                        String ee = rss.getString("date_de_reception");
                        String f = rss.getString("reste_a_payer");
                        
                        
                        clistt.add(new tableargent(b,c,d,ee,f));
                        
                    }
                    table_money.setItems(clistt);
                    idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
                    name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
                    money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
                    dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
                    reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
                } catch (SQLException ex) {
                    Logger.getLogger(FinanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             if(!client.getEditor().getText().isEmpty()){
                try {
                    String snn = ses.getValue();
                    String cn = client.getEditor().getText();
                    table_money.getItems().clear();
                    ResultSet rss = dal.dbreadclientparticularfinance(snn,cn);
                    while(rss.next()){
                        
                        String b = rss.getString("id_client");
                        String c = rss.getString("nom_client");
                        String d = rss.getString("argent_percu");
                        String ee = rss.getString("date_de_reception");
                        String f = rss.getString("reste_a_payer");
                        
                        
                        clistt.add(new tableargent(b,c,d,ee,f));
                        
                    }
                    table_money.setItems(clistt);
                    idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
                    name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
                    money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
                    dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
                    reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
                } catch (SQLException ex) {
                    Logger.getLogger(FinanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
               });
            
            client.setItems(clist); 
            
            
                  
        ResultSet rss = dal.dbreadclients(sn);
            while(rss.next()){
              
                 String b = rss.getString("id_client");
                  String c = rss.getString("nom_client");
                   String d = rss.getString("argent_percu");
                    String e = rss.getString("date_de_reception"); 
                    String f = rss.getString("reste_a_payer");
                     
                
                clistt.add(new tableargent(b,c,d,e,f));
                
            }   
            table_money.setItems(clistt);
            idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
            dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
            reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
        } 
        if(!ses.getEditor().getText().isEmpty()){
         String sn = ses.getEditor().getText();
        
        client.getItems().clear();
        table_money.getItems().clear();
         DataAccessLayer dal = new DataAccessLayer();
         ResultSet rs = dal.dbreadclient(sn);
         while(rs.next()){
              
                String a = rs.getString("nom_client");
                
                
                clist.add(a);
                
            }
            collections.sort(clist);
            
            client.setEditable(true);
            TextFields.bindAutoCompletion(client.getEditor(), clist).setOnAutoCompleted(e -> {
             if(client.getValue()!=null){
                try {
                    String snn = ses.getEditor().getText();
                    String cn = client.getValue();
                    table_money.getItems().clear();
                    ResultSet rss = dal.dbreadclientparticularfinance(snn,cn);
                    while(rss.next()){
                        
                        String b = rss.getString("id_client");
                        String c = rss.getString("nom_client");
                        String d = rss.getString("argent_percu");
                        String ee = rss.getString("date_de_reception");
                        String f = rss.getString("reste_a_payer");
                        
                        
                        clistt.add(new tableargent(b,c,d,ee,f));
                        
                    }
                    table_money.setItems(clistt);
                    idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
                    name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
                    money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
                    dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
                    reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
                } catch (SQLException ex) {
                    Logger.getLogger(FinanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             if(!client.getEditor().getText().isEmpty()){
                try {
                    String snn = ses.getEditor().getText();
                    String cn = client.getEditor().getText();
                    table_money.getItems().clear();
                    ResultSet rss = dal.dbreadclientparticularfinance(snn,cn);
                    while(rss.next()){
                        
                        String b = rss.getString("id_client");
                        String c = rss.getString("nom_client");
                        String d = rss.getString("argent_percu");
                        String ee = rss.getString("date_de_reception");
                        String f = rss.getString("reste_a_payer");
                        
                        
                        clistt.add(new tableargent(b,c,d,ee,f));
                        
                    }
                    table_money.setItems(clistt);
                    idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
                    name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
                    money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
                    dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
                    reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
                } catch (SQLException ex) {
                    Logger.getLogger(FinanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
               });
            
            client.setItems(clist); 
            
            
                  
        ResultSet rss = dal.dbreadclients(sn);
            while(rss.next()){
              
                 String b = rss.getString("id_client");
                  String c = rss.getString("nom_client");
                   String d = rss.getString("argent_percu");
                    String e = rss.getString("date_de_reception"); 
                    String f = rss.getString("reste_a_payer");
                     
                
                clistt.add(new tableargent(b,c,d,e,f));
                
            }   
            table_money.setItems(clistt);
            idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
            dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
            reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
        
        }
        
    }

    @FXML
    private void btnclient(ActionEvent event) throws SQLException {
       if(ses.getValue()!=null){
        if(client.getValue()!=null){ 
        String sn = ses.getValue();
        String cn = client.getValue();
        table_money.getItems().clear();
        DataAccessLayer dal = new DataAccessLayer();
        ResultSet rss = dal.dbreadclientparticularfinance(sn,cn);
            while(rss.next()){
              
                 String b = rss.getString("id_client");
                  String c = rss.getString("nom_client");
                   String d = rss.getString("argent_percu");
                    String e = rss.getString("date_de_reception"); 
                    String f = rss.getString("reste_a_payer");
                     
                
                clistt.add(new tableargent(b,c,d,e,f));
                
            }   
            table_money.setItems(clistt);
            idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
            dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
            reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
    }
       if(!client.getEditor().getText().isEmpty()){
       String sn = ses.getValue();
        String cn = client.getEditor().getText();
        table_money.getItems().clear();
        DataAccessLayer dal = new DataAccessLayer();
        ResultSet rss = dal.dbreadclientparticularfinance(sn,cn);
            while(rss.next()){
              
                 String b = rss.getString("id_client");
                  String c = rss.getString("nom_client");
                   String d = rss.getString("argent_percu");
                    String e = rss.getString("date_de_reception"); 
                    String f = rss.getString("reste_a_payer");
                     
                
                clistt.add(new tableargent(b,c,d,e,f));
                
            }   
            table_money.setItems(clistt);
            idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
            dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
            reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
       }
    }
       
       
        if(!ses.getEditor().getText().isEmpty()){
        if(client.getValue()!=null){ 
        String sn = ses.getEditor().getText();
        String cn = client.getValue();
        table_money.getItems().clear();
        DataAccessLayer dal = new DataAccessLayer();
        ResultSet rss = dal.dbreadclientparticularfinance(sn,cn);
            while(rss.next()){
              
                 String b = rss.getString("id_client");
                  String c = rss.getString("nom_client");
                   String d = rss.getString("argent_percu");
                    String e = rss.getString("date_de_reception"); 
                    String f = rss.getString("reste_a_payer");
                     
                
                clistt.add(new tableargent(b,c,d,e,f));
                
            }   
            table_money.setItems(clistt);
            idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
            dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
            reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
    }
       if(!client.getEditor().getText().isEmpty()){
       String sn = ses.getEditor().getText();
        String cn = client.getEditor().getText();
        table_money.getItems().clear();
        DataAccessLayer dal = new DataAccessLayer();
        ResultSet rss = dal.dbreadclientparticularfinance(sn,cn);
            while(rss.next()){
              
                 String b = rss.getString("id_client");
                  String c = rss.getString("nom_client");
                   String d = rss.getString("argent_percu");
                    String e = rss.getString("date_de_reception"); 
                    String f = rss.getString("reste_a_payer");
                     
                
                clistt.add(new tableargent(b,c,d,e,f));
                
            }   
            table_money.setItems(clistt);
            idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
            dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
            reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
       }
    }
    }
    public String calculeentrer() throws SQLException{
        
        DataAccessLayer dal = new DataAccessLayer();
        String sn = ses.getValue();
        String ssn = ses.getEditor().getText();
        String cn = client.getValue();
        String ccn = client.getEditor().getText();
        String a = money.getText();
        int aa = Integer.parseInt(a);
        if(sn!=null){
        if(cn!=null){
         
    int rest = Integer.parseInt(dal.dbgetresttopay(sn, cn));
    rest = rest-aa;
     aaaa = String.valueOf(rest);
        }
        if(!ccn.isEmpty()){
         
    int rest = Integer.parseInt(dal.dbgetresttopay(sn, ccn));
    rest = rest-aa;
     aaaa = String.valueOf(rest);
        }
        }
        if(!ssn.isEmpty()){
         if(cn!=null){
         
    int rest = Integer.parseInt(dal.dbgetresttopay(ssn, cn));
    rest = rest-aa;
     aaaa = String.valueOf(rest);
        }
        if(!ccn.isEmpty()){
         
    int rest = Integer.parseInt(dal.dbgetresttopay(ssn, ccn));
    rest = rest-aa;
     aaaa = String.valueOf(rest);
        }
        }
        
    return aaaa;
    }
 
    @FXML
    private void btnvalider(ActionEvent event) throws IOException, SQLException {
        DataAccessLayer dal = new DataAccessLayer();
        String sn = ses.getValue();
        String cn = client.getValue();
        String s = money.getText();
        String r = calculeentrer();
        
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String da = format.format(date);
        ResultSet rss = dal.dbreadclientparticularfinance(sn,cn);
        rss.next();
                String b = rss.getString("id_client");
                  String c = rss.getString("nom_client");
                  
        dal.dbstoresumandrest(sn,b,c,s,da,r);
        
        
        table_money.getItems().clear();
        
        ResultSet rsss = dal.dbreadclientparticularfinance(sn,cn);
            while(rsss.next()){
                String bb = rsss.getString("id_client");
                  String cc = rsss.getString("nom_client");
                   String dd = rsss.getString("argent_percu");
                    String ee = rsss.getString("date_de_reception"); 
                    String ff = rsss.getString("reste_a_payer");
                clistt.add(new tableargent(bb,cc,dd,ee,ff));
                
            }   
            table_money.setItems(clistt);
            idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
            dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
            reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
        
    }

    @FXML
    private void btnsave(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        
         print.setText(" ");
          String sn = ses.getValue();
          DataAccessLayer dbl = new DataAccessLayer();
          ResultSet rs = dbl.dbexportsessionfinance(sn);
             int rownum0 = 0;
             int rownum1 = rownum0+1;
             int rownum2 = rownum1+1; 
            
             int index = rownum2+2;
             
             
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
        HSSFRow row1= sheet.createRow(rownum1);
        row1.createCell(0).setCellValue("");
        row1.createCell(1).setCellValue("");
        row1.createCell(2).setCellValue("");
        row1.createCell(3).setCellValue("");
        row1.createCell(4).setCellValue("");
        row1.createCell(5).setCellValue("");
       
        HSSFRow row2= sheet.createRow(rownum2);
        row2.createCell(0).setCellValue("ID");
        row2.createCell(1).setCellValue("NUMERO DE CARTE D'IDENDITE");
        row2.createCell(2).setCellValue("NOM DU CLIENT");
        row2.createCell(3).setCellValue("ARGENT PERCU(fcfa)");
        row2.createCell(4).setCellValue("DATE DE RECEPTION");
        row2.createCell(5).setCellValue("RESTE A PAYER(fcfa)");
        
        while(rs.next()){
        HSSFRow rowindex= sheet.createRow(index);
        rowindex.createCell(0).setCellValue(rs.getString("id"));
        rowindex.createCell(1).setCellValue(rs.getString("id_client"));
        rowindex.createCell(2).setCellValue(rs.getString("nom_client"));
        rowindex.createCell(3).setCellValue(rs.getString("argent_percu"));
        rowindex.createCell(4).setCellValue(rs.getString("date_de_reception"));
        rowindex.createCell(5).setCellValue(rs.getString("reste_a_payer"));
        
         index++;
        rownum0++;
        rownum1++;
        rownum2++;
        
       
        }
        
        DirectoryChooser directorychooser = new DirectoryChooser();
       
        directorychooser.setTitle("SELECTIONNEZ LE FICHIER DANS LE QUEL VOUS VOULEZ SAUVEGARDER");
        File file = directorychooser.showDialog(moneypage.getScene().getWindow());
        
        FileOutputStream f = new FileOutputStream(new File(file.getAbsolutePath())+"/"+sn+"-FINANCE.xls");
        
        workbook.write(f);
        f.close();
        
        
    print.setText("SAUVEGARDER");

        
        
        
        
    }

    @FXML
    private void btnsavetotal(ActionEvent event) throws SQLException, IOException {
      String sn = ses.getValue();
      String cn = client.getValue();
      String t = total.getText();
       DataAccessLayer dal = new DataAccessLayer();
       
        if(dal.dbgetresttopay(sn, cn)==null){
            dal.dbsaveclienttotal(sn,cn,t); 
       ResultSet rss = dal.dbreadclientparticularfinance(sn,cn);
       
        
        table_money.getItems().clear();
        
            while(rss.next()){
              
                 String b = rss.getString("id_client");
                  String c = rss.getString("nom_client");
                   String d = rss.getString("argent_percu");
                    String e = rss.getString("date_de_reception"); 
                    String f = rss.getString("reste_a_payer");
                     
                
                clistt.add(new tableargent(b,c,d,e,f));
                
            }   
            table_money.setItems(clistt);
            idc.setCellValueFactory(new PropertyValueFactory<>("id_client"));
            name.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
            money_entered.setCellValueFactory(new PropertyValueFactory<>("argent_percu"));
            dates.setCellValueFactory(new PropertyValueFactory<>("date_de_reception"));
            reste_to_pay.setCellValueFactory(new PropertyValueFactory<>("reste_a_payer"));
        
         }else{
         print.setText("MONTANT DEJA DEFINI");
        }
        
    }
    
}
