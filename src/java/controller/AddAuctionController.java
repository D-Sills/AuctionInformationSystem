package controller;

import Utilities.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.AuctionLot;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.Alerts.genericInfo;
import static Utilities.Alerts.genericWarning;
import static com.darrensills.Main.auctionSystem;
import static controller.AuctionMainController.allAuctions;

public class AddAuctionController implements Initializable {
		private String title;
		private LocalDate date;
		private String description;
		private String type;
		private double price;
		private String URL;
		private final ToggleGroup toggleGroup = new ToggleGroup();

		@FXML
		private TextField titleTextField;

		@FXML
		private RadioButton radioButton1;
		@FXML
		private RadioButton radioButton2;
		@FXML
		private RadioButton radioButton3;
		@FXML
		private RadioButton radioButton4;
		@FXML
		private RadioButton radioButton5;
		@FXML
		private RadioButton radioButton6;
		@FXML
		private RadioButton radioButton7;

		@FXML
		private DatePicker datePicker;

		@FXML
		private TextArea descriptionTextArea;

		@FXML
		private TextField URLTextfield;

		@FXML
		private Spinner<Double> priceSpinner;

		@Override
		public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
				for (RadioButton radioButton : Arrays.asList(radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7)) {
						radioButton.setToggleGroup(toggleGroup);
				}
				toggleGroup.selectToggle(toggleGroup.getToggles().get(0));
		}

		/**
		 * Gathers entered data from the textfields and spinner
		 */
		public void enteredInfo() {
				title = titleTextField.getText();
				date = datePicker.getValue();
				RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
				type = Objects.requireNonNull(selectedRadioButton).getText();
				description = descriptionTextArea.getText();
				URL = URLTextfield.getText();
				price = priceSpinner.getValue();
		}

		/**
		 * Validates all entered information. First checks to see if any text-fields are blank,then checks the entered values.
		 * Display alerts for any incorrect information.
		 * if all information is valid, returns true.
		 * @return true if valid, else returns false
		 */
		private boolean validateInformation() {
				enteredInfo();
				if (title.isBlank() || date == null || description.isBlank() || URL.isBlank()) {
						genericWarning("Please fill out all fields");
						return false;
				} else if (!Validation.validPrice(String.valueOf(price))) {
						genericWarning("Please enter a valid price");
						return false;
			  }
				return true;
		}

		/**
		 * Adds a AuctionLot to the system on button press, but only after successful validation.
		 */
		@FXML
		public void onActionAdd() {
				if (validateInformation()) {
						URL url;
						try {
								url = new URL(URL);
						} catch (MalformedURLException e) {
								e.printStackTrace();
								genericWarning("URL is not valid");
								return;
						}
						AuctionLot auctionLot = new AuctionLot(title, description, type, date, price, url);
						auctionSystem.getAuctionLots().add(auctionLot);
						allAuctions.add(auctionLot);
						genericInfo("Successfully added " + title + " to the auction house!");
				}
		}
}
