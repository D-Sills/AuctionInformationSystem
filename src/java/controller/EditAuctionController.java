package controller;

import Utilities.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.Alerts.genericInfo;
import static Utilities.Alerts.genericWarning;
import static controller.AuctionMainController.*;

public class EditAuctionController implements Initializable {
		private final ToggleGroup toggleGroup = new ToggleGroup();
		private String title;
		private LocalDate date;
		private String description;
		private String type;
		private Double price;
		private String URL;

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
				updateFields();
		}

		/**
		 * Gathers entered data from the textfields and spinner
		 */
		public void enteredInfo() {
				title = titleTextField.getText();
				RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
				type = selectedRadioButton.getText();
				date = datePicker.getValue();
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
				if (title.isBlank() || date == null ||  description.isBlank() || URL.isBlank()) {
						genericWarning("Please fill out all fields");
						return false;
				} else if (!Validation.validPrice(String.valueOf(price))) {
						genericWarning("Please enter a valid price");
						return false;
				}
				return true;
		}

		public void updateFields() {
				for (Toggle toggle : toggleGroup.getToggles()) {
						RadioButton radioButton = (RadioButton) toggle;
						if (Objects.equals(radioButton.getText(), getSelectedLot().getType())) {
								toggleGroup.selectToggle(radioButton);
						}
				}
				titleTextField.setText(getSelectedLot().getName());
				URLTextfield.setText(getSelectedLot().getImageURL().toString());
				descriptionTextArea.setText(getSelectedLot().getDescription());
				priceSpinner.getValueFactory().setValue(getSelectedLot().getStartingPrice());
				datePicker.setValue(getSelectedLot().getDateOfOrigin());
		}

		/**
		 * Adds a Auction to the system on button press, but only after successful validation.
		 */
		@FXML
		public void onActionUpdate() {
				if (validateInformation()) {
						URL url;
						try {
								url = new URL(URL);
						} catch (MalformedURLException e) {
								e.printStackTrace();
								genericWarning("URL is not valid");
								return;
						}
						getSelectedLot().setName(title);
						getSelectedLot().setType(type);
						getSelectedLot().setDateOfOrigin(date);
						getSelectedLot().setStartingPrice(price);
						getSelectedLot().setDescription(description);
						getSelectedLot().setType(type);
						getSelectedLot().setImageURL(url);
						allAuctions.set(allAuctions.indexOf(getSelectedLot()), getSelectedLot());
						genericInfo("Successfully updated " + title + "!");
				}
		}
}
