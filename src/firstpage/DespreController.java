/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstpage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DespreController {


    @FXML
    private Button closeDespre;
    
     @FXML
    private TextArea text;

    @FXML
    void closeDespre(ActionEvent event) {
        Platform.exit();
    }

}
