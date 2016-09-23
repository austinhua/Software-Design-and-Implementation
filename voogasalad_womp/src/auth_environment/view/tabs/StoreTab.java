package auth_environment.view.tabs;

import java.util.*;
import auth_environment.Models.StoreTabModel;
import auth_environment.Models.Interfaces.IAuthModel;
import auth_environment.delegatesAndFactories.NodeFactory;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class StoreTab extends Tab implements IWorkspace {
    private static final String NAMES_PACKAGE = "auth_environment/properties/names";
    private ResourceBundle myNamesBundle = ResourceBundle.getBundle(NAMES_PACKAGE);

    private static final String DIMENSIONS_PACKAGE = "auth_environment/properties/dimensions";
    private ResourceBundle myDimensionsBundle = ResourceBundle.getBundle(DIMENSIONS_PACKAGE);

    private BorderPane myRoot;
    private GridPane myGrid;
    private StoreTabModel myStoreTabModel;
    private IAuthModel myAuthModel;
    private List<ComboBox<String>> unitList;
    private List<TextField> costList;
    private NodeFactory myNodeFactory;
    private Button done;
    private List<NameAffectorCostSet> nameAffectorCostSets = new ArrayList<>();

    public StoreTab (String name, IAuthModel authModel) {
        super(name);
        myAuthModel = authModel;
        unitList = new ArrayList<ComboBox<String>>();
        costList = new ArrayList<>();
        myNodeFactory = new NodeFactory();
        init();
    }

    private void init () {
        myRoot = new BorderPane();
        myGrid = new GridPane();
        myStoreTabModel = new StoreTabModel(myAuthModel);
        setRefresh();
        int index = 0;
        Button dummyButton = new Button("");
        createProductList(index, myGrid, dummyButton);
        myRoot.setLeft(myGrid);
        done = new Button(myNamesBundle.getString("storeDoneButton"));
        doneAction();
        myRoot.setBottom(done);
        setContent(myRoot);
    }

    private void setRefresh () {
        this.setOnSelectionChanged(e -> refresh());
    }

    private void refresh () {
        unitList.stream().forEach(s -> {
            s.getItems().clear();
            s.getItems()
                    .addAll(this.myAuthModel.getIAuthEnvironment().getUnitFactory().getUnitLibrary()
                            .getUnitNames());
        });
        myStoreTabModel.getStoreFactory().setAffectorLibrary(this.myAuthModel.getIAuthEnvironment().getAffectorFactory().getAffectorLibrary());
        myStoreTabModel.getStoreFactory().setUnitLibrary(this.myAuthModel.getIAuthEnvironment().getUnitFactory().getUnitLibrary());
    }

    private void createProductList (int index, GridPane newTableInfo, Button dButton) {
        newTableInfo.getChildren().remove(dButton);

        NameAffectorCostSet n = new NameAffectorCostSet();

        ComboBox<String> newCBox = n.getComboBox();
        newCBox.getItems()
                .addAll(this.myAuthModel.getIAuthEnvironment().getUnitFactory().getUnitLibrary()
                        .getUnitNames());
        newTableInfo.add(createCostBox(n), 2, index);
        index++;
        Button newProductButton = new Button(myNamesBundle.getString("storeAddNewProduct"));
        int num = index;
        newProductButton.setOnAction(e -> createProductList(num, newTableInfo, newProductButton));
        newTableInfo.add(newProductButton, 2, index);
        unitList.add(newCBox);
        nameAffectorCostSets.add(n);
    }

    private Node createCostBox (NameAffectorCostSet n) {
        ComboBox<String> cBox = n.getComboBox();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(Double
                .parseDouble(myDimensionsBundle.getString("storeBoxPadding"))));
        hbox.setSpacing(Double.parseDouble(myDimensionsBundle.getString("storeBoxPadding")));
        cBox.setMinWidth(Double.parseDouble(myDimensionsBundle.getString("storeBoxWidth")));
        hbox.getChildren().add(cBox);
        TextField input =
                this.myNodeFactory
                        .buildTextFieldWithPrompt(myNamesBundle.getString("storePromptCost"));
        n.setTextFieldCost(input);
        input.setMaxWidth(Double.parseDouble(myDimensionsBundle.getString("storeInputWidth")));
        input.setMinHeight(Double.parseDouble(myDimensionsBundle.getString("storeInputHeight")));
        hbox.setMinWidth(Double.parseDouble(myDimensionsBundle.getString("storeBoxMinWidth")));
        // hbox.getChildren().add(input);
        hbox.getChildren().add(createEditButton(n));
        costList.add(input);
        return hbox;
    }

    private Node createEditButton (NameAffectorCostSet n) {
        Button edit = new Button(myNamesBundle.getString("storeEditAffectors"));
        edit.setOnAction(e -> checkContent(n));
        return edit;
    }

    private void checkContent (NameAffectorCostSet n) {
        if (n.getName() != null) {
            new EditUpgradeWindow(myAuthModel, n);
        }
    }

    private void doneAction () {
        done.setOnAction(e -> updateBuyables());
    }

    private void updateBuyables () {
        for (NameAffectorCostSet nacs : nameAffectorCostSets) {
            if (nacs.getName() == null)
                continue;

            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < nacs.affectorCost.size(); i++)
                nameList.add(nacs.getName());
            myStoreTabModel.addBuyableUpgrades(nameList, nacs.affectorNames, nacs.affectorCost);
        }
    }

    public Node getRoot () {
        return myRoot;
    }

    public static class NameAffectorCostSet {
        private ComboBox<String> comboBox = new ComboBox<>();
        private TextField cost;
        private List<String> affectorNames = new ArrayList<>();
        private List<Integer> affectorCost = new ArrayList<>();

        public List<String> getAffectorNames () {
            return affectorNames;
        }

        public List<Integer> getAffectorCost () {
            return affectorCost;
        }

        public void setTextFieldCost (TextField c) {
            cost = c;
        }

        public TextField getTextFieldCost () {
            return cost;
        }

        public ComboBox<String> getComboBox () {
            return comboBox;
        }

        public String getName () {
            return comboBox.getValue();
        }

    }
}
