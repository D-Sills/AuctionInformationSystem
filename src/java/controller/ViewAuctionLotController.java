package controller;

import Utilities.Alerts;
import Utilities.Comparators.BidAmountComparator;
import Utilities.SceneSwitcher;
import Utilities.SortingSearchMethods;
import Utilities.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import model.Bid;
import model.Bidder;
import model.SoldAuctionLot;
import model.WinningBid;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static Utilities.Alerts.genericWarning;
import static com.darrensills.Main.auctionSystem;
import static controller.AuctionMainController.*;

public class ViewAuctionLotController implements Initializable {
		private final ObservableList<Bid> allBids = FXCollections.observableArrayList();
		private Double newBid;
		private Bidder bidder;

		@FXML
		private Label statusLabel;

		@FXML
		private Button sellButton;

		@FXML
		private Button addBidButton;

		@FXML
		private Button withdrawButton;

		@FXML
		private Label nameLabel;

		@FXML
		private Label priceLabel;

		@FXML
		private Label dateLabel;

		@FXML
		private Label typeLabel;

		@FXML
		private Label descriptionLabel;

		@FXML
		private ImageView lotImage;

		@FXML
		private Label startingPriceLabel;

		@FXML
		private Spinner<Double> bidSpinner;

		@FXML
		private ComboBox<Bidder> bidderComboBox;

		@FXML
		private TableColumn<Bidder, Bid> bidderCol;

		@FXML
		private TableColumn<LocalDateTime, Bid> timeCol;

		@FXML
		private TableColumn<Double, Bid> bidCol;

		@FXML
		private TableView<Bid> bidsTableView;
		private Bid selectedBid;

		public ViewAuctionLotController() {
				bidderComboBox = new ComboBox<>();
		}

		@Override
		public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
				if (!getSelectedLot().getBids().isEmpty()) for (Bid bid : getSelectedLot().getBids()) allBids.add(bid);
				if (!auctionSystem.getBidders().isEmpty()) bidderComboBox.setValue(auctionSystem.getBidders().get(0));
				sortByPrice();
				updateFields();
				bidderComboBox.setItems(allBidders);
				bidSpinner.setPromptText("€" + getSelectedLot().getStartingPrice().toString());
				setFactory(bidsTableView);
				bidderCol.setCellValueFactory(new PropertyValueFactory<>("bidder"));
				timeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
				bidCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
				Label placeholder = new Label("Nothing to display!");
				placeholder.setFont(Font.font("Calibri", 14));
				bidsTableView.setPlaceholder(placeholder);
				bidsTableView.setItems(allBids);
				addListListener(allBids);
				if (getSelectedLot() instanceof SoldAuctionLot) soldLot();
		}

		private void setFactory(TableView<Bid> view) {
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
								if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
										selectedBid = bidsTableView.getSelectionModel().getSelectedItem();
								}
								if(row.isEmpty()) {
										selectedBid = null;
								}
								if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
										try {
												setSelectedBidder(bidsTableView.getSelectionModel().getSelectedItem().getBidder());
												SceneSwitcher.SceneSwitch(nameLabel,"Bidders.fxml","Manage Bidders");
										} catch (IOException e) {
												e.printStackTrace();
										}
								}

						});

						return row;
				});
		}

		public <T> void addListListener(ObservableList<T> list) {
				list.addListener((ListChangeListener<? super T>) c -> {
						while (c.next()) {
								if (c.wasAdded() || c.wasUpdated() || c.wasRemoved()) {
										bidsTableView.refresh();
										sortByPrice();

										allAuctions.add(null); //hack to fire the list change listener
										allAuctions.remove(null);
								}
						}
				});
		}

		/**
		 * Gathers entered data from the textfields and spinner
		 */
		public void enteredInfo() {
				bidder = bidderComboBox.getValue();
				newBid = bidSpinner.getValue();
		}

		/**
		 * Validates all entered information. First checks to see if any text-fields are blank,then checks the entered values.
		 * Display alerts for any incorrect information.
		 * if all information is valid, returns true.
		 * @return true if valid, else returns false
		 */
		private boolean validateInformation() {
				enteredInfo();
				if (bidder == null) {
						genericWarning("Please fill out all fields");
						return false;
				} else if (!Validation.validPrice(String.valueOf(newBid))) {
						genericWarning("Please enter a valid price");
						return false;
				}
				return true;
		}

		public void updateFields() {
				nameLabel.setText(getSelectedLot().getName());
				typeLabel.setText(getSelectedLot().getType());
				descriptionLabel.setText(getSelectedLot().getDescription());
				priceLabel.setText("€" + df.format(getSelectedLot().getStartingPrice()));
				dateLabel.setText(String.valueOf(getSelectedLot().getDateOfOrigin()));
				Image image = null;
				try {
						image = new Image(getSelectedLot().getImageURL().openStream());
				} catch (IOException e) {
						e.printStackTrace();
				}
				lotImage.setImage(image);
		}

		private void sortByPrice() {
				SortingSearchMethods.SelectionSort(allBids, new BidAmountComparator().reversed());
		}

		public void onActionAddBid() {
				if (validateInformation()) {
						if (newBid < getSelectedLot().getStartingPrice()) { //move to validate
								genericWarning("Entered bid must be higher than the starting price!");
								return;
						}
						if (!getSelectedLot().getBids().isEmpty()) {
								Bid bigBid = getSelectedLot().getLargestBid();
								if (bigBid.getAmount() < newBid) {
										Bid bid = new Bid(bidder, getSelectedLot(), newBid, LocalDateTime.now());
										allBids.add(bid);
										getSelectedLot().getBids().add(bid);
								} else genericWarning("Only a higher bid than the current highest bid can be added!");
						} else {
								Bid bid = new Bid(bidder, getSelectedLot(), newBid, LocalDateTime.now());
								getSelectedLot().getBids().add(bid);
								allBids.add(bid);
						}
				}
		}

		public void onActionWithdraw() {
				if (selectedBid == null) {
						genericWarning("Please select a bid first!");
				} else {
						if (Alerts.genericConfirm("Are you sure you want to withdraw" + getSelectedLot().getLargestBid().toString() + " ?")) {
								getSelectedLot().getBids().remove(selectedBid);
								allBids.remove(selectedBid);
								selectedBid = null;
								bidsTableView.getSelectionModel().select(null);
						}
				}
		}

		public void onActionAcceptBid() {
				if (getSelectedLot().getBids().isEmpty()) {
						genericWarning("Please make a bid first!");
				} else {
						if (Alerts.genericConfirm("Are you sure you want to accept" + getSelectedLot().getLargestBid().toString() + " ?")) {
								WinningBid winningBid = new WinningBid(getSelectedLot().getLargestBid().getBidder(),getSelectedLot(),getSelectedLot().getLargestBid().getAmount(),getSelectedLot().getLargestBid().getDateTime());

								allBids.set(allBids.indexOf(getSelectedLot().getLargestBid()), winningBid);
								getSelectedLot().getBids().set(getSelectedLot().getBids().indexOf(getSelectedLot().getLargestBid()), winningBid);

								SoldAuctionLot soldAuctionLot = new SoldAuctionLot(getSelectedLot().getName(), getSelectedLot().getDescription(), getSelectedLot().getType()
																																				, getSelectedLot().getDateOfOrigin(), getSelectedLot().getStartingPrice(), getSelectedLot().getImageURL(),
																																								winningBid.getAmount(), LocalDateTime.now());
								for (Bid bid:getSelectedLot().getBids()) soldAuctionLot.getBids().add(bid);

								auctionSystem.getAuctionLots().set(auctionSystem.getAuctionLots().indexOf(getSelectedLot()), soldAuctionLot);
								allAuctions.set(allAuctions.indexOf(getSelectedLot()),soldAuctionLot);
								setSelectedLot(soldAuctionLot);

								soldLot();
						}
				}
		}

		public void soldLot() {
				bidderComboBox.setDisable(true);
				bidSpinner.setDisable(true);
				sellButton.setDisable(true);
				addBidButton.setDisable(true);
				withdrawButton.setDisable(true);
				startingPriceLabel.setText("Sold for - ");
				priceLabel.setText("€"  + getSelectedLot().getLargestBid().getAmount().toString() + " at " +
								getSelectedLot().getLargestBid().getDateTime().format(DateTimeFormatter.ofPattern("h:mm a")));
				lotImage.setOpacity(0.6);
				statusLabel.setStyle("-fx-border-color: red; -fx-rotate: 345;-fx-background-radius: 7px;-fx-border-radius: 7px;" +
								"-fx-background-color: white;-fx-border-width: 3px;-fx-text-alignment: center;");
				statusLabel.setText("SOLD!");
		}
}
