package controller;

import Utilities.Alerts;
import Utilities.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.AuctionLot;
import model.SoldAuctionLot;

import java.io.IOException;

import static com.darrensills.Main.auctionSystem;
import static controller.AuctionMainController.*;
import static controller.AuctionMainController.allAuctions;
import static controller.AuctionMainController.df;

public class AuctionLotCellController extends ListCell<AuctionLot> {
		private AuctionLot auc;

		@FXML
		private Label statusLabel;

		@FXML
		private ImageView lotImage;

		@FXML
		private Label titleLabel;

		@FXML
		private Label typeLabel;

		@FXML
		private Label priceLabel;

		@FXML
		private Button binButton;

		@FXML
		private Button editButton;

		public AuctionLotCellController() {
				loadFXML();
		}

		private void loadFXML() {
				try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AuctionLotCell.fxml"));
						loader.setRoot(this);
						loader.setController(this);
						loader.load();
				}
				catch (IOException e) {
						throw new RuntimeException(e);
				}

		}

		@Override
		protected void updateItem(AuctionLot auctionLot, boolean empty) {
				super.updateItem(auctionLot, empty);

				if(empty || auctionLot == null) {
						setText(null);
						setContentDisplay(ContentDisplay.TEXT_ONLY);
				}
				else {
						auc = auctionLot;
						Image image = null;
						try {
								image = new Image(auctionLot.getImageURL().openStream());
						} catch (IOException e) {
								e.printStackTrace();
						}

						lotImage.setImage(image);
						titleLabel.setText(auctionLot.getName());
						priceLabel.setText("€" + df.format(auctionLot.getBids().isEmpty() ? auctionLot.getStartingPrice() : auctionLot.getLargestBid().getAmount()));
						typeLabel.setText(auctionLot.getType());

						if (auctionLot instanceof SoldAuctionLot) {
								lotImage.setOpacity(0.6);
								statusLabel.setStyle("-fx-border-color: red; -fx-rotate: 345;-fx-background-radius: 7px;-fx-border-radius: 7px;" +
												"-fx-background-color: white;-fx-border-width: 3px;-fx-text-alignment: center;");
								statusLabel.setText("SOLD!");
								editButton.setDisable(true);
								binButton.setDisable(true);
						} else {
								lotImage.setOpacity(1);
								statusLabel.setStyle(null);
								statusLabel.setText(null);
								editButton.setDisable(false);
								binButton.setDisable(false);
						}

						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				}
		}

		@FXML
		public void onActionBin(ActionEvent actionEvent) {
				if (Alerts.genericConfirm("Are you sure you want to withdraw " + auc.getName() + "?")) {
						auctionSystem.getAuctionLots().remove(auc);
						allAuctions.remove(auc);
				}
		}

		@FXML
		public void onActionView(ActionEvent actionEvent) throws IOException {
				setSelectedLot(auc);
				SceneSwitcher.SceneSwitchNewWindow(actionEvent, "ViewAuctionLot.fxml", (getSelectedLot().getName() +" Auction Lot"));
		}

		/**
		 * Switches scenes to EditAuctionLot.fxml on button press
		 * @param actionEvent handler that reacts to the javafx root event
		 */
		@FXML
		public void onActionEdit(ActionEvent actionEvent) throws IOException {
				setSelectedLot(auc);
				SceneSwitcher.SceneSwitchNewWindow(actionEvent, "EditAuctionLot.fxml", "Edit Auction Lot");
		}
}
