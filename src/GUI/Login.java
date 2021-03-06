package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Database;

/** Panel zobrazujici hodnoceni kavarny
 * @author slav02 / cecj02
 * @version ZS 2018
 */
public class Login {

    private Stage loginStage;
    private Database database;
    private Label userLabel;
    private TextField userField;
    private Label passLabel;
    private PasswordField passField;
    private Button submitButton;

    /**Constructor
     * @param lastStage Stage minuleho okna
     */
    public Login(Stage lastStage){
        database = new Database();
        lastStage.hide();

        //TITLE
        Text title = new Text();
        title.setText("Zadejte přihlašovací údaje");
        title.getStyleClass().add("title");

        //FORM - username
        userLabel = new Label();
        userLabel.setText("Účet:");
        userField = new TextField();
        userField.setText("jizzy");

        //FORM - password
        passLabel = new Label();
        passLabel.setText("Heslo:");
        passField = new PasswordField();
        passField.setText("12345Jizzy");

        //BUTTON - submit
        submitButton = new Button();
        submitButton.getStyleClass().add("submitButton");
        submitButton.setText("Přihlásit");
        submitButton.setOnAction(event -> {
            String name = userField.getText();
            String password = passField.getText();
            String sql = "SELECT * FROM sql11216990.person WHERE (username = '" + name + "' AND password = '" + password + "')";
            boolean databaseOperation = database.operate("LOGIN", sql);
            if (databaseOperation) {
                loginStage.hide();
                Application app = new Application(loginStage, database);
            } else {
                String message = "Přihlášení se nezdařilo";
                String error = "Zadané údaje nejsou správné";
                database.alert(message, error);
            }
        });

        //BUTTON - cancel
        Button cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            loginStage.hide();
            lastStage.show();
        });

        // TilePane - spojeni tlacitek
        HBox boxButtons = new HBox(15);
        boxButtons.getStyleClass().add("registrationBoxButtons");
        boxButtons.getChildren().addAll(submitButton, cancelButton);
        boxButtons.setAlignment(Pos.BASELINE_RIGHT);

        //GridPane - rozlozeni formulare
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(12);
        gridPane.add(title,0,0, 2,1);
        gridPane.add(userLabel,0,1);
        gridPane.add(userField,1,1);
        gridPane.add(passLabel,0,2);
        gridPane.add(passField,1,2);
        gridPane.add(boxButtons,1,3);

        //WINDOW - setup
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 450, 300);
        scene.getStylesheets().add("styles/styles.css");

        loginStage = new Stage();
        loginStage.setTitle("Aplikace káva - Přihlášení");
        loginStage.setScene(scene);
        loginStage.show();

    }
}
