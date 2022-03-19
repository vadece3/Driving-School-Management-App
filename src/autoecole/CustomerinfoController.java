/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoecole;

import dataaccesslayer.DataAccessLayer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author KAMDEM VADECE
 */
public class CustomerinfoController implements Initializable {

    @FXML
    private Button submit;
    @FXML
    private Label print;
    @FXML
    private TextField cid;
    @FXML
    private TextField cname;
    @FXML
    private TextField cbl;
    @FXML
    private TextField cam;
    @FXML
    private TextField cnt;
    @FXML
    private TextField car;
    @FXML
    private DatePicker cbdate;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem CustemerOders;
    @FXML
    private Label session;
    @FXML
    private Button retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnsubmit(ActionEvent event) throws SQLException, IOException {
        if(!cid.getText().isEmpty()){
        if(!cname.getText().isEmpty()){
        if(cbdate.getValue()!=null){
        if(!cbl.getText().isEmpty()){
        if(!cnt.getText().isEmpty()){
        if(!car.getText().isEmpty()){
        DataAccessLayer dal = new DataAccessLayer();
       String a = cid.getText();
       String b = cname.getText();
       String c = cbdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
       String d = cbl.getText();
       String e = cam.getText();
       String f = cnt.getText();
       String g = car.getText();
       String cn = session.getText();
       String h = cn+"AS";
       dal.dbaddclient(cn,a,b,c,d,e,f,g,h);
       dal.dbaddclientfinance(cn,a,b);
       
       FXMLLoader loader = new FXMLLoader(getClass().getResource("listedesclients.fxml"));
        Parent cuspage = loader.load();
 
        Scene cuspagescene = new Scene(cuspage); 
        Stage appstage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appstage2.setScene(cuspagescene);
        appstage2.setTitle("AUTO ECOLE");
        appstage2.show();     
       
        }else{
        print.setText("ENTREZ ADRESSE DE RESIDENCE");
        }
        }else{
        print.setText("ENTREZ NUMERO DE TELEPHONE");
        }
        }else{
        print.setText("ENTREZ LIEU DE NAISSANCE");
        } 
        }else{
        print.setText("SELECTIONNEZ DATE DE NAISSANCE");
        }
        }else{
        print.setText("ENTREZ NOM DU CLIENT");
        }
        }else{
        print.setText("IDENTITE VIDE");
        }
    }

    @FXML
    private void btnclose(ActionEvent event) {
    }

    @FXML
    private void btncusor(ActionEvent event) {
    }
    
    public void sessions(String aa){
        
        session.setText(aa);
        }   

    @FXML
    private void btnretour(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("listedesclients.fxml"));
        Parent cuspage = loader.load();
 
        Scene cuspagescene = new Scene(cuspage); 
        Stage appstage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appstage2.setScene(cuspagescene);
        appstage2.setTitle("AUTO ECOLE");
        appstage2.show();  
    }
    
}
