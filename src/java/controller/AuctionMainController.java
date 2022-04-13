package controller;

import Utilities.Alerts;
import Utilities.Comparators.LotDateComparator;
import Utilities.Comparators.LotNameComparator;
import Utilities.Comparators.LotPriceComparator;
import Utilities.CustomList;
import Utilities.NoSelect;
import Utilities.SceneSwitcher;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import model.AuctionLot;
import model.Bidder;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

import static Utilities.Alerts.genericInfo;
import static Utilities.Alerts.genericWarning;
import static Utilities.SortingSearchMethods.SelectionSort;
import static com.darrensills.Main.auctionSystem;

/**
 * @author Darren Sills
 * MainMenu class which shows the MainMenu.Fxml screen.
 * Responsible for swapping between the main scenes and also for saving and loading data via xstream.
 */
public class AuctionMainController implements Initializable {
    public static final DecimalFormat df = new DecimalFormat("0.00");
    protected static final ObservableList<AuctionLot> allAuctions = FXCollections.observableArrayList();
    protected static final ObservableList<Bidder> allBidders = FXCollections.observableArrayList();
    protected static Bidder selectedBidder;
    protected static AuctionLot selectedLot;

    private String chosenSearchOption;
    private String searchValue;
    private Double priceFilter;
    ObservableList<String> searchOptions = FXCollections.observableArrayList();
    private final ToggleGroup toggleGroup = new ToggleGroup();

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
    private RadioButton radioButton8;

    @FXML
    private Label maxPrice;

    @FXML
    private ComboBox<String> searchComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private Slider priceSlider;

    @FXML
    private ListView<AuctionLot> auctionListView;

    /**
     * Constructor that initializes various observable lists and comboboxes, as well as adding some simple data to those boxes
     */
    public AuctionMainController() {
        searchOptions.addAll("by Name", "by Description Keyword", "by Year of Origin");
        searchComboBox = new ComboBox<>();
    }

    /**
     * Gathers the Appointment's data from the system and populates the table fields
     * @param url used to resolve relative paths for root object
     * @param resourceBundle localizes the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        auctionListView.setCellFactory(new AuctionLotCellFactory());
        auctionListView.setSelectionModel(new NoSelect<>());
        auctionListView.setItems(allAuctions);
        Label placeholder = new Label("Nothing to display!");
        placeholder.setFont(Font.font("Calibri", 14));
        auctionListView.setPlaceholder(placeholder);
        addListListener(allAuctions);
        searchComboBox.setItems(searchOptions);
        searchComboBox.setValue("by Name");

        for (RadioButton radioButton : Arrays.asList(radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8)) {
            radioButton.setToggleGroup(toggleGroup);
        }
        toggleGroup.selectToggle(radioButton8);
        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> filterByType());

        priceSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            maxPrice.textProperty().setValue(
                    "€%s".formatted(df.format(newValue)));
            priceFilter = (Double) newValue;
        });
    }

    public void fillSystem() {
        if (!auctionSystem.getAuctionLots().isEmpty()) {
            for (AuctionLot auctionLot : auctionSystem.getAuctionLots()) {
                allAuctions.add(auctionLot);
            }
        }
        if (!auctionSystem.getBidders().isEmpty()) {
            for (Bidder bidder : auctionSystem.getBidders()) {
                allBidders.add(bidder);
            }
        }
    }

    public void clearSystem() {
        allAuctions.clear();
        allBidders.clear();
        auctionSystem.getAuctionLots().clear(); //clear the two main lists to wipe all data
        auctionSystem.getBidders().clear();
    }

    public <T> void addListListener(ObservableList<T> list) {
        list.addListener((ListChangeListener<? super T>) c -> {
            while (c.next()) {
                if (c.wasPermutated() || c.wasUpdated() || c.wasRemoved() || c.wasAdded()) {
                    auctionListView.refresh();
                }
            }
        });
    }

    /**
     * Gathers entered data from the textfields and spinner
     */
    public void enteredInfo() {
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
    private boolean validateInformation() {
        enteredInfo();
        if (searchValue.isBlank() || chosenSearchOption.isBlank()) {
            genericWarning("Please fill out all fields");
            return false;
        }
        return true;
    }
    //---------------------------------------------------------------//
    //Button Methods                                                 //
    //---------------------------------------------------------------//
    /**
     * Switches scenes to AddAuctionLot.fxml on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionAddAuction(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.SceneSwitchNewWindow(actionEvent, "AddAuctionLot.fxml", "Add Auction Lot");
    }

    /**
     * Switches scenes to Bidders.fxml on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionBidders(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.SceneSwitchNewWindow(actionEvent, "Bidders.fxml", "Manager Bidders");
    }

    //---------------------------------------------------------------//
    //Sorting/Filter Methods                                         //
    //---------------------------------------------------------------//
    private void sortByName() {
        SelectionSort(allAuctions, new LotNameComparator());
    }

    private void sortByDate() {
        SelectionSort(allAuctions, new LotDateComparator());
    }

    private void sortByPrice() {
        SelectionSort(allAuctions, new LotPriceComparator().reversed());
    }

    /**
     * Searches through things entered on button press
     * @param actionEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onActionSearch(ActionEvent actionEvent) {
        if (auctionSystem.getAuctionLots().isEmpty()) genericWarning("Please add an Auction Lot first!");
        if (validateInformation()) {
            allAuctions.clear();
            if ("by Name".equals(chosenSearchOption)) {
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                    if (auctionLot.getName().toLowerCase().contains(searchValue.toLowerCase())) {
                        allAuctions.addAll(auctionLot);
                    }
                sortByName();
            }
            if ("by Description Keyword".equals(chosenSearchOption)) {
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                    if (auctionLot.getDescription().toLowerCase().contains(searchValue.toLowerCase())) {
                        allAuctions.add(auctionLot);
                    }
                sortByName();
            }
            if ("by Year of Origin".equals(chosenSearchOption)) {
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                    if (auctionLot.getDateOfOrigin().toString().toLowerCase().contains(searchValue.toLowerCase())) {
                        allAuctions.add(auctionLot);
                    }
                sortByDate();
            }
        }
    }

    private void filterByType() {
        if (!auctionSystem.getAuctionLots().isEmpty()) {
            if (auctionSystem.getAuctionLots().isEmpty()) genericWarning("Please add an Auction Lot first!");
            allAuctions.clear();
            if (radioButton1.isSelected())
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                    if (auctionLot.getType().equals(radioButton1.getText())) allAuctions.add(auctionLot);
            if (radioButton2.isSelected())
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                    if (auctionLot.getType().equals(radioButton2.getText())) allAuctions.add(auctionLot);
            if (radioButton3.isSelected())
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                    if (auctionLot.getType().equals(radioButton3.getText())) allAuctions.add(auctionLot);
            if (radioButton4.isSelected())
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                 if (auctionLot.getType().equals(radioButton4.getText())) allAuctions.add(auctionLot);
            if (radioButton5.isSelected())
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                    if (auctionLot.getType().equals(radioButton5.getText())) allAuctions.add(auctionLot);
            if (radioButton6.isSelected())
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                  if (auctionLot.getType().equals(radioButton6.getText())) allAuctions.add(auctionLot);
            if (radioButton7.isSelected())
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots())
                    if (auctionLot.getType().equals(radioButton7.getText())) allAuctions.add(auctionLot);
            if (radioButton8.isSelected())
                for (AuctionLot auctionLot : auctionSystem.getAuctionLots()) allAuctions.add(auctionLot);
        }
    }

    /**
     * Bids adjusted depending on the price put by the slider
     * @param mouseEvent handler that reacts to the javafx root event
     */
    @FXML
    public void onSliderDone(MouseEvent mouseEvent) {
        if (!auctionSystem.getAuctionLots().isEmpty()) {
            allAuctions.clear();
            for (AuctionLot auctionLot : auctionSystem.getAuctionLots()) {
                if (priceFilter == 0) allAuctions.add(auctionLot);
                if (auctionLot.getBids().isEmpty()) {
                    if (auctionLot.getStartingPrice() <= priceFilter)
                        allAuctions.add(auctionLot);
                } else if (auctionLot.getLargestBid().getAmount() <= priceFilter)
                    allAuctions.add(auctionLot);
            }
            sortByPrice();
        }
    }

    //---------------------------------------------------------------//
    //Persistence                                                    //
    //---------------------------------------------------------------//
    /**
     * Uses the XStream library to save the system data to a .xml file in the project
     */
    @FXML
    public void onActionSave() throws Exception {
        if (Alerts.genericConfirm("Are you sure you want to save all data to AuctionSystem.xml?")) {
            XStream xstream = new XStream(new DomDriver()); //initialise the xstream object
            ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("AuctionSystem.xml")); //initialise an objectoutputsteam to a specific file
            out.writeObject(auctionSystem.getAuctionLots()); //write out the objects you want saved
            out.writeObject(auctionSystem.getBidders());
            out.close();

            genericInfo("Saved to AuctionSystem.xml successful!");
        }
    }

    /**
     * Uses the XStream library to load the system data from a .xml file in the project
     */
    @FXML
    @SuppressWarnings("unchecked")
    public void onActionLoad() throws Exception {
        File xml = new File("AuctionSystem.xml");
        if (!xml.isFile()) {
            genericInfo("Please save some data first!");
        } else {
            if (Alerts.genericConfirm("Are you sure you want to load all data from AuctionSystem.xml?")) {
                XStream xstream = new XStream(new DomDriver());
                ObjectInputStream is =  xstream.createObjectInputStream(new FileReader("AuctionSystem.xml"));  //initialise an objectoutputsteam from a specific file
                auctionSystem.setAuctionLots((CustomList<AuctionLot>) is.readObject()); //tell it what object to assign values to
                auctionSystem.setBidders((CustomList<Bidder>) is.readObject());
                is.close();

                allBidders.clear();
                allAuctions.clear();
                fillSystem();
                genericInfo("Loaded from AuctionSystem.xml successful!");
            }
        }
    }

    /**
     * Completely resets all data in the System
     */
    @FXML
    public void onActionReset() {
        if (Alerts.genericConfirm("Are you sure you want to reset all data in the system?")) {
            clearSystem();

            genericInfo("System reset successful!");
        }
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    public static Bidder getSelectedBidder() {
        return selectedBidder;
    }

    public static void setSelectedBidder(Bidder selectedBidder) {
        AuctionMainController.selectedBidder = selectedBidder;
    }

    public static AuctionLot getSelectedLot() {
        return selectedLot;
    }

    public static void setSelectedLot(AuctionLot selectedLot) {
        AuctionMainController.selectedLot = selectedLot;
    }
}
