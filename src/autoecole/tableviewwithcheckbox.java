/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoecole;

import java.awt.Checkbox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 *
 * @author KAMDEM VADECE
 */
public class tableviewwithcheckbox {
    String sessionname = new String();
    CheckBox cb = new CheckBox();

    tableviewwithcheckbox(String sessionname, CheckBox cb) {
        this.sessionname = sessionname;
        this.cb = cb;
        
    }

    public String getSessionname() {
        return sessionname;
    }

    public void setSessionname(String sessionname) {
        this.sessionname = sessionname;
    }

    public CheckBox getCb() {
        return cb;
    }

    public void setCh(CheckBox cb) {
        this.cb = cb;
    }
    
}
