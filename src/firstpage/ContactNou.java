/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstpage;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author maria
 */
public class ContactNou {

    SimpleStringProperty nume;
    SimpleStringProperty prenume;
    SimpleStringProperty sex;
    SimpleStringProperty telefon;
    SimpleStringProperty email;
    SimpleStringProperty favorit;
    
    
     public ContactNou(String nume, String prenume, String sex, String telefon, String email, String favorit)
    {
        this.nume = new SimpleStringProperty(nume);
        this.prenume = new SimpleStringProperty(prenume);
        this.sex = new SimpleStringProperty(sex);
        this.telefon = new SimpleStringProperty(telefon);
        this.email = new SimpleStringProperty(email);
        this.favorit = new SimpleStringProperty(favorit);
    }

}
