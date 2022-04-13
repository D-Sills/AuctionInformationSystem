package controller;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.AuctionLot;

public class AuctionLotCellFactory implements Callback<ListView<AuctionLot>, ListCell<AuctionLot>> {

		@Override
		public ListCell<AuctionLot> call(ListView<AuctionLot> param) {
				return new AuctionLotCellController();
		}
}
