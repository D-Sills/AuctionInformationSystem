package Utilities;

import com.darrensills.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class Alerts {
		/**
		 * Reusable warning popup
		 * @param desc content of the alert
		 */
		public static void genericWarning(String desc) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText(desc);

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				DialogPane dialogPane = alert.getDialogPane();
				stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/auctionicon.png")))); //set the icon
				stage.getScene().getStylesheets().add(
								Objects.requireNonNull(Alerts.class.getResource("/style.css")).toExternalForm());
				dialogPane.getStyleClass().add("alert");

				alert.showAndWait();
		}

		/**
		 * Reusable information popup
		 * @param desc content of the alert
		 */
		public static void genericInfo(String desc) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(desc);

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				DialogPane dialogPane = alert.getDialogPane();
				stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/auctionicon.png")))); //set the icon
				stage.getScene().getStylesheets().add(
								Objects.requireNonNull(Alerts.class.getResource("/style.css")).toExternalForm());
				dialogPane.getStyleClass().add("alert");

				alert.showAndWait();
		}

		/**
		 * Reusable confirmation popup
		 * @param desc content of the alert
		 * @return the result of the popup, either yes or no
		 */
		public static boolean genericConfirm(String desc) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(desc);

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				DialogPane dialogPane = alert.getDialogPane();
				stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/auctionicon.png")))); //set the icon
				stage.getScene().getStylesheets().add(
								Objects.requireNonNull(Alerts.class.getResource("/style.css")).toExternalForm());
				dialogPane.getStyleClass().add("alert");
				Optional<ButtonType> result = alert.showAndWait();

				return result.orElse(null) == ButtonType.OK;
		}
}
