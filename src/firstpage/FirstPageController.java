package firstpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author maria
 */
public class FirstPageController {

    Stage addStage = new Stage();
    AddContactController ctrlAdd;

    Stage despreStage = new Stage();
    DespreController ctrlDespre;

    //**************************************
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    //**************** Meniu *********************
    @FXML
    private MenuItem add;

    @FXML
    private MenuItem deleteSelected;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem refresh;
    //**************** Tabel **********************
    @FXML
    private TableView<ContactNou> tabel;

    @FXML
    private TableColumn<ContactNou, String> nume;

    @FXML
    private TableColumn<ContactNou, String> prenume;

    @FXML
    private TableColumn<ContactNou, String> sex;

    @FXML
    private TableColumn<ContactNou, String> telefon;

    @FXML
    private TableColumn<ContactNou, String> email;

    @FXML
    private TableColumn<ContactNou, String> favoriti;

    @FXML
    private MenuItem despre;

    //********** Functii **********************
    @FXML
    void despre(ActionEvent event) {
        despreStage.showAndWait();
    }

    @FXML
    void refreshFile(ActionEvent event) {
        incarc();
    }

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void deleteSelected(ActionEvent event) {

        // Preiau contactul curent, selectat in TableView
        ContactNou c = (ContactNou) tabel.getSelectionModel().getSelectedItem();
        String nInitial = c.nume.get();
        try {
            File fXmlFile = new File("/home/maria/NetBeansProjects/FirstPage/src/firstpage/Contacte.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            // Construiesc lista de noduri de tip <contact>
            NodeList lista = doc.getElementsByTagName("contact");
            // Parcurg lista de elem. si caut prep. nInitial
            for (int contor = 0; contor < lista.getLength(); contor++) {
                Element contact = (Element) lista.item(contor);
                String t = contact.getElementsByTagName("nume").item(0).getTextContent();
                // Verific daca este nInitial. Daca da, sterg elementul.
                if (t.equals(nInitial)) {
                    // Sterg elementul curent
                    contact.getParentNode().removeChild(contact);
                    salvez(doc);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        incarc();
    }

    @FXML
    void addContact(ActionEvent event) {
        addStage.showAndWait();
    }

    @FXML
    public void initialize() {

        nume.setCellValueFactory(cellData -> cellData.getValue().nume);
        prenume.setCellValueFactory(cellData -> cellData.getValue().prenume);
        sex.setCellValueFactory(cellData -> cellData.getValue().sex);
        telefon.setCellValueFactory(cellData -> cellData.getValue().telefon);
        email.setCellValueFactory(cellData -> cellData.getValue().email);
        favoriti.setCellValueFactory(cellData -> cellData.getValue().favorit);
        incarc();

        try {
            //1. încarc fisierul fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddContact.fxml"));
            //2. creez un container care va conţine fereastra
            AnchorPane container = (AnchorPane) loader.load();
            //3. preiau obiectul controller al ferestrei de dialog
            ctrlAdd = loader.getController();
            //4. impun titlul ferestrei adaugate
            addStage.setTitle("Adaugati contact nou");
            //5. impun tipul ferestrei (modalã sau nemodalã)
            addStage.initModality(Modality.APPLICATION_MODAL);
            //6. creez scena care contine interfata ferestrei
            Scene scena = new Scene(container);
            //7. ataşez obiectului addContact (Stage) scena
            addStage.setScene(scena);
            AddContactController B = loader.getController();
            B.setA(this);
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

        try {
            //1. încarc fisierul fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Despre.fxml"));
            //2. creez un container care va conţine fereastra
            AnchorPane container = (AnchorPane) loader.load();
            //3. preiau obiectul controller al ferestrei de dialog
            ctrlDespre = loader.getController();
            //4. impun titlul ferestrei adaugate
            despreStage.setTitle("About");
            //5. impun tipul ferestrei (modalã sau nemodalã)
            despreStage.initModality(Modality.APPLICATION_MODAL);
            //6. creez scena care contine interfata ferestrei
            Scene scena = new Scene(container);
            //7. ataşez obiectului addContact (Stage) scena
            despreStage.setScene(scena);
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

    }

    public void incarc() {
        try {
            File fXmlFile = new File("/home/maria/NetBeansProjects/FirstPage/src/firstpage/Contacte.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            Element radacina = doc.getDocumentElement();
            // Construiesc lista de noduri de tip <preparat>
            NodeList lista = doc.getElementsByTagName("contact");
            // Golesc continutul din TableView
            tabel.getItems().clear();
            // Parcurg lista de elemente de tip 'contact'
            for (int contor = 0; contor < lista.getLength(); contor++) {
                Element contact = (Element) lista.item(contor);

                // Preiau valorile si creez un obiectul contact (ContactNou) 
                String cnume
                        = contact.getElementsByTagName("nume").item(0).getTextContent();
                String cprenume
                        = contact.getElementsByTagName("prenume").item(0).getTextContent();
                String csex
                        = contact.getElementsByTagName("sex").item(0).getTextContent();
                String ctel
                        = contact.getElementsByTagName("telefon").item(0).getTextContent();
                String cemail
                        = contact.getElementsByTagName("email").item(0).getTextContent();
                String cfavorit
                        = contact.getElementsByTagName("favorit").item(0).getTextContent();
                ContactNou contactNou;
                contactNou = new ContactNou(cnume, cprenume, csex, ctel, cemail, cfavorit);
                // Adaug in TableView
                tabel.getItems().add(contactNou);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
