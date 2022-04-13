package controller;

import Utilities.Alerts;
import Utilities.Comparators.BidDateComparator;
import Utilities.Comparators.BidderAddressComparator;
import Utilities.Comparators.BidderNameComparator;
import Utilities.SceneSwitcher;
import Utilities.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import model.AuctionLot;
import model.Bid;
import model.Bidder;
import model.WinningBid;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static Utilities.Alerts.genericInfo;
import static Utilities.Alerts.genericWarning;
import static Utilities.SortingSearchMethods.SelectionSort;
import static com.darrensills.Main.auctionSystem;
import static controller.AuctionMainController.*;

public class BiddersController implements Initializable {
		private final ObservableList<Bid> allBids = FXCollections.observableArrayList();
		private final ObservableList<String> searchOptions = FXCollections.observableArrayList();
		private String name;
		private String phone;
		private String address;
		private String email;
		private String chosenSearchOption;
		private String searchValue;

		@FXML
		private Label sneaky;

		@FXML
		private TextField nameTextField;

		@FXML
		private TextField phoneTextField;

		@FXML
		private TextArea addressTextArea;

		@FXML
		private TextField emailTextField;

		@FXML
		private TableView<Bidder> bidderTableView;

		@FXML
		private TableColumn<Bidder, String> nameCol;

		@FXML
		private TableColumn<Bidder, String> addressCol;

		@FXML
		private TableColumn<Bidder, String> emailCol;

		@FXML
		private TableColumn<Bidder, String> phoneCol;

		@FXML
		private TableView<Bid> bidsTableView;

		@FXML
		private TableColumn<LocalDateTime, Bid> timeCol;

		@FXML
		private TableColumn<Double, Bid> bidCol;

		@FXML
		private TableColumn<AuctionLot, Bid> lotCol;

		@FXML
		private ComboBox<String> searchComboBox;

		@FXML
		private TextField searchTextField;

		/**
		 * Constructor that initializes various observable lists and comboboxes, as well as adding some simple data to those boxes
		 */
		public BiddersController() {
				searchOptions.addAll("by Name", "by Address");
				searchComboBox = new ComboBox<>();
		}

		@Override
		public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
				setBidderFactory(bidderTableView);
				nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
				addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
				emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
				phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
				Label bidderplaceholder = new Label("Nothing to display!");
				bidderplaceholder.setFont(Font.font("Calibri", 14));
				bidderTableView.setPlaceholder(bidderplaceholder);
				bidderTableView.setItems(allBidders);
				searchComboBox.setItems(searchOptions);
				searchComboBox.setValue("by Name");

				setBidFactory(bidsTableView);
				lotCol.setCellValueFactory(new PropertyValueFactory<>("auctionLot"));
				timeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
				bidCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
				Label bidplaceholder = new Label("Nothing to display!");
				bidplaceholder.setFont(Font.font("Calibri", 14));
				bidsTableView.setPlaceholder(bidplaceholder);

				if (getSelectedBidder() != null) {
						bidderTableView.getSelectionModel().select(getSelectedBidder());
						updateFields();
				}
		}

		private void setBidderFactory(TableView<Bidder> view) {
				view.setRowFactory( tv -> {
						TableRow<Bidder> row = new TableRow<>();
						row.setOnMouseClicked(event -> {
								if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
										setSelectedBidder(bidderTableView.getSelectionModel().getSelectedItem());
										updateFields();
								}
								if(row.isEmpty()) {
										setSelectedBidder(null);
								}
						});
						return row ;
				});
		}

		private void setBidFactory(TableView<Bid> view) {
				view.setRowFactory(tv -> {
						TableRow<Bid> row = new TableRow<>() {
								@Override
								protected void updateItem(Bid bid, boolean empty) {
										super.updateItem(bid, empty);

										if (bid instanceof WinningBid) { //if the row is a record, change the colour
												setStyle("-fx-background-color: #ff6961");
										} else { //reset if not
												setStyle(null);
										}
								}
						};

						row.setOnMouseClicked(event -> {
								if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
										try {
												Bid selectedBid = bidsTableView.getSelectionModel().getSelectedItem();
												for (AuctionLot auctionLot: auctionSystem.getAuctionLots()) {
														if (auctionLot.getBids().contains(selectedBid)) {
																setSelectedLot(auctionLot);
														}
												}
												SceneSwitcher.SceneSwitch(sneaky,"ViewAuctionLot.fxml",(getSelectedBidder().getName() + " Auction Lot"));
										} catch (IOException e) {
												e.printStackTrace();
										}
								}
						});

						return row;
				});
		}

		/**
		 * Gathers entered data from the textfields and spinner
		 */
		public void enteredInfo() {
				name = nameTextField.getText();
				address = addressTextArea.getText();
				email = emailTextField.getText();
				phone = phoneTextField.getText();
		}

		/**
		 * Validates all entered information. First checks to see if any text-fields are blank,then checks the entered values.
		 * Display alerts for any incorrect information.
		 * if all information is valid, returns true.
		 *
		 * @return true if valid, else returns false
		 */
		private boolean validateInformation() {
				enteredInfo();
				if (name.isBlank() || address.isBlank() || email.isBlank() || phone.isBlank()) {
						genericWarning("Please fill out all fields");
						return false;
				} else if (!Validation.validPhone(phone)) {
						Alerts.genericWarning("Invalid phone number, must be 10 numbers long");
						return false;
				} else if (!Validation.validEmail(email)) {
						Alerts.genericWarning("Invalid email, must contain a @ and .");
						return false;
				}
				return true;
		}

		/**
		 * Gathers entered data from the textfields and spinner
		 */
		public void enteredInfoSearch() {
				searchValue = searchTextField.getText();
				chosenSearchOption = searchComboBox.getValue();
		}

		/**
		 * Validates all entered information. First checks to see if any text-fields are blank,then checks the entered values.
		 * Display alerts for any incorrect information.
		 * if all information is valid, returns true.
		 *
		 * @return true if valid, else returns false
		 */
		private boolean validateInformationSearch() {
				enteredInfoSearch();
				if (searchValue.isBlank() || chosenSearchOption.isBlank()) {
						genericWarning("Please fill out all fields");
						return false;
				}
				return true;
		}

		public void updateFields() {
				allBids.clear();
				if (!auctionSystem.getAuctionLots().isEmpty())
						for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
								if (!auctionLot.getBids().isEmpty())
										for (Bid bid : auctionLot.getBids())
												if (bid.getBidder().equals((getSelectedBidder()))) {
														allBids.add(bid);
												}
				bidsTableView.setItems(allBids);
				sortByDate();
				nameTextField.setText(getSelectedBidder().getName());
				addressTextArea.setText(getSelectedBidder().getAddress());
				emailTextField.setText(getSelectedBidder().getEmail());
				phoneTextField.setText(getSelectedBidder().getPhone());
		}


		private void sortByName() {
				SelectionSort(allBidders, new BidderNameComparator());
		}

		private void sortByAddress() {
				SelectionSort(allBidders, new BidderAddressComparator());
		}

		private void sortByDate() {
				SelectionSort(allBids, new BidDateComparator());
		}

		/**
		 * Searches through things entered on button press
		 * @param actionEvent handler that reacts to the javafx root event
		 */
		@FXML
		public void onActionSearch(ActionEvent actionEvent) {
				if (auctionSystem.getBidders().isEmpty()) genericWarning("Please add a Bidder first!");
				if (validateInformationSearch()) {
						setSelectedBidder(null);
						bidderTableView.getSelectionModel().select(null);
						if ("by Name".equals(chosenSearchOption)) {
								allBidders.clear();
								for (Bidder bidder : auctionSystem.getBidders())
										if (bidder.getName().toLowerCase().contains(searchValue.toLowerCase())) {
												allBidders.add(bidder);
										}
								if (allBidders.isEmpty()) allBids.clear();
								sortByName();
						}
						if ("by Address".equals(chosenSearchOption)) {
								allBidders.clear();
								for (Bidder bidder : auctionSystem.getBidders())
										if (bidder.getAddress().toLowerCase().contains(searchValue.toLowerCase())) {
												allBidders.add(bidder);
										}
								if (allBidders.isEmpty()) allBids.clear();
								sortByAddress();
						}
				}
		}

		/**
		 * Adds a Bidder to the system on button press, but only after successful validation.
		 */
		@FXML
		public void onActionAdd(ActionEvent actionEvent) {
				if (validateInformation()) {
						Bidder bidder = new Bidder(name, address, email, phone);
						auctionSystem.getBidders().add(bidder);
						allBidders.add(bidder);
						bidderTableView.getSelectionModel().select(null);
						genericInfo("Successfully added " + name + " to the system!");
				}
		}

		/**
		 * Updates a Bidder on button press, but only after successful validation.
		 */
		@FXML
		public void onActionUpdate() {
				if (getSelectedBidder() == null) genericWarning("No Bidder selected!");
				else if (validateInformation()) {
						getSelectedBidder().setName(name);
						getSelectedBidder().setAddress(address);
						getSelectedBidder().setEmail(email);
						getSelectedBidder().setPhone(phone);

						setSelectedBidder(null);
						bidderTableView.getSelectionModel().select(null);
						bidderTableView.refresh();
						genericInfo("Successfully updated " + name + "!");
				}
		}
}