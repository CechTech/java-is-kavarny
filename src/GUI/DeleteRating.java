package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Database;

/** Potvrzovaci obrazovka pri smazani hodnoceni
 * @author cecj02
 * @version ZS 2018
 */
public class DeleteRating {
    private Stage deleteStage;

    /**Constructor
     * @param database databaze alikace
     * @param idRating id ratingu, ktery chceme smazat
     */
    public DeleteRating(Database database, int idRating) {

        //TITLE
        Text title = new Text();
        title.setText("Opravdu chcete smazat hodnocení");
        title.getStyleClass().add("title-small");

        //BUTTON - potvrzeni smazani
        Button confirmButton = new Button();
        confirmButton.getStyleClass().add("deleteButton");
        confirmButton.setText("Potvrdit smazání");
        confirmButton.setOnAction(event -> {

            //Smazani kavarny z databaze
            String sql = "DELETE FROM sql11216990.rating WHERE rating.id='" + idRating + "'";
            boolean databaseOperation = database.operate("DELETE", sql);

            //Vysledek pozadavku smazani
            if (databaseOperation) {
                String titleAlert = "Hodnocení bylo smazáno";
                String textAlert = "Úspěšně jste smazali hodnocení";
                database.alert(titleAlert, textAlert);
                deleteStage.hide();

                //aktualizace dat
                String sql1 = "SELECT * FROM sql11216990.cafe";
                database.operate("SEARCH", sql1);
            } else {
                String titleAlert = "Hodnocení nebylo smazáno";
                String textAlert = "Došlo k chybě";
                database.alert(titleAlert, textAlert);
            }
        });

        //BUTTON - zpet na minulou obrazovku
        Button cancelButton = new Button();
        cancelButton.setText("Zpět");
        cancelButton.setOnAction(event -> {
            deleteStage.hide();
        });

        //GridPane - rozlozeni okna pro potvrzeni smazani
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(20);
        gridPane.add(title, 0, 0,2,1);
        gridPane.add(confirmButton, 0, 1);
        gridPane.add(cancelButton, 1, 1);

        //Window - setup
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("panePadding");
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 350, 150);
        scene.getStylesheets().add("styles/styles.css");

        deleteStage = new Stage();
        deleteStage.setTitle("Aplikace káva - Smazání hodnocení");
        deleteStage.setScene(scene);
        deleteStage.show();
    }
}
