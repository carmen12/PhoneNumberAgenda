/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstpage;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class AddContactController implements Initializable {

    private String tabel;

    Stage firstPgStage = new Stage();
    FirstPageController ctrl;

    private FirstPageController A = new FirstPageController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        rb.getObject();
    }

    @FXML
    private Label labelNume;

    @FXML
    private Label titlu;

    @FXML
    private Label labelTelefon;

    @FXML
    private Label labelPrenume;

    @FXML
    private Label labelEmail;

    @FXML
    private CheckBox favorit;

    @FXML
    private Button anuleaza;

    @FXML
    private RadioButton femeie;

    @FXML
    private RadioButton barbat;

    @FXML
    private Button adauga;

    @FXML
    private TextField prenume;

    @FXML
    private TextField nume;

    @FXML
    private TextField telefon;

    @FXML
    private TextField email;

    @FXML
    private ToggleGroup tg1;

    private String sex = "F";
    
    private String favor = "-";

    //********** Functii **********************
    public void setA(FirstPageController A) {
        this.A = A;
    }

    @FXML
    void markFavorit(ActionEvent event) {
        if (favorit.isSelected()){
            favor = "favorit";
        }else{
            favor = "-";
        }
        
    }

    @FXML
    void setSex(ActionEvent event) {
        if (barbat.isSelected()) {
            sex = "M";
        } else if (femeie.isSelected()) {
            sex = "F";
        } else {
            sex = "F";
        }
    }

    @FXML
    void anuleaza(ActionEvent event) {
        Stage stage = (Stage) anuleaza.getScene().getWindow();
        stage.hide();
    }

    @FXML
    void showEmailInput(ActionEvent event) {

    }

    @FXML
    void adauga(ActionEvent event) {

        try {
            File fXmlFile = new File("/home/maria/NetBeansProjects/FirstPage/src/firstpage/Contacte.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Element radacina = doc.getDocumentElement();

            // Adaug in fisierul .xml un nou contact
            Text valoare; // Folosit la incarcarea valorilor
            Element contactNou = doc.createElement("contact");

            Element numeContact = doc.createElement("nume");
            contactNou.appendChild(numeContact);
            valoare = doc.createTextNode(nume.getText());
            numeContact.appendChild(valoare);

            Element prenumeContact = doc.createElement("prenume");
            contactNou.appendChild(prenumeContact);
            valoare = doc.createTextNode(prenume.getText());
            prenumeContact.appendChild(valoare);

            Element sexContact = doc.createElement("sex");
            contactNou.appendChild(sexContact);
            valoare = doc.createTextNode(sex);
            sexContact.appendChild(valoare);
            Element telContact = doc.createElement("telefon");
            contactNou.appendChild(telContact);
            valoare = doc.createTextNode(telefon.getText());
            telContact.appendChild(valoare);

            Element emailContact = doc.createElement("email");
            contactNou.appendChild(emailContact);
            valoare = doc.createTextNode(email.getText());
            emailContact.appendChild(valoare);
            
            if (favorit.isSelected()){
                Element favoritContact = doc.createElement("favorit");
                contactNou.appendChild(favoritContact);
                valoare = doc.createTextNode(favor);
                favoritContact.appendChild(valoare);
            }else{
                Element favoritContact = doc.createElement("favorit");
                contactNou.appendChild(favoritContact);
                valoare = doc.createTextNode(favor);
                favoritContact.appendChild(valoare);
            }

            radacina.appendChild(contactNou);
            // Salvez fisierul .xml
            salvez(doc);
            A.incarc();
            // Golesc controalele
            nume.setText("");
            prenume.setText("");
            telefon.setText("");
            email.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) anuleaza.getScene().getWindow();
        stage.close();
//        loadScene();

    }

    private void salvez(Document doc) {
        // Salvez pe disc
        try {
            TransformerFactory transformerFactory
                    = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                    "4");
            DOMSource sursa = new DOMSource(doc);
            FileOutputStream fo = new FileOutputStream("/home/maria/NetBeansProjects/FirstPage/src/firstpage/Contacte.xml");
            StreamResult rezultat = new StreamResult(fo);
            transformer.transform(sursa, rezultat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
