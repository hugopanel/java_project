package com.gesto;

import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.control.TextField;
import gesto.api.Database;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
    private Stage stage;
    private Database db;

    public LoginController(Database db, Stage stage) {
        this.db = db;
        this.stage = stage;
    }

    @FXML
    private SimpleObjectProperty<Icon> InfoButton = new SimpleObjectProperty<Icon>(this, "infoButton");

    @FXML
    private SimpleObjectProperty<Icon> closeButton = new SimpleObjectProperty<Icon>(this, "closeButton");

    @FXML
    private Button loginButton;

    @FXML
    private TextField mailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Text wrongCredentialsText;

    @FXML
    void convertLoginButtonListener() {
        String userID = mailInput.getText() ;
        String pw = passwordInput.getText() ;

        //vérifier que le les données de l'utilisateur existent dans la base de données
        if (userID.equals("user") && pw.equals("password"))
        {
            // envoyer sur la page principale de l'application
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(GestoApplication.class.getResource("app-view.fxml"));
                fxmlLoader.setController(new AppController(db, stage));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        else {
            // Afficher une message d'erreur ; rendre visible un textfield
            //il faut créer un warning label
            wrongCredentialsText.setVisible(true);
        }

    }

}
