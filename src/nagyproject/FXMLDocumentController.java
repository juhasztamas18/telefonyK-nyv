package nagyproject;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TableView tablazat;
    @FXML
    private Button send;
    @FXML
    private Button pdfButton;
    @FXML
    private Pane errorPane;
    @FXML
    private Button errorButton;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField pdfTextField;
    @FXML
    private TextField inputNumber;
    @FXML
    private StackPane menu;
    @FXML
    private Pane exportPane;
    @FXML
    private Pane contactPane;
    @FXML
    private Label infoMasssige;

    private String ERRORR_MES = "Message"; //hiba gomb hiv�sa miatt vezettem be
    private final String MENU_CONTACTS = "Menü";
    private final String MENU_DATA = "Adatok";
    private final String MENU_EXPORT = "Exportalas";
    private final String MENU_EXIT = "Kilepes";
    DataBase db = new DataBase();

    private final ObservableList<Person> data = FXCollections.observableArrayList();

    @FXML
    private void addContact(ActionEvent event) {
        String email = inputEmail.getText();
        String name = inputName.getText();
        String number = inputNumber.getText();
        if (email.length() > 3 && name.length() != 0 && number.length() != 0 && number.length()==12 && email.contains("@") && email.contains(".")) {
            Person newPerson = new Person(inputName.getText(), inputNumber.getText(), inputEmail.getText());
            data.add(newPerson);
            db.addContact(newPerson);
            inputEmail.clear();
            inputName.clear();
            inputNumber.clear();
        } else {
            errorPane.setVisible(true);
            contactPane.setDisable(true);
            contactPane.setVisible(false);
            ERRORR_MES = "Message1";
        }
    }

    @FXML
    private void pdfGeneration(ActionEvent event) {
        String inputData = pdfTextField.getText();
        inputData = inputData.replaceAll("\\s+", ""); //kicser�li a az �sszes sz�kozt
        if (inputData.length() != 0 && !inputData.equals("")) {
            PdfGen pdfGeneralo = new PdfGen();
            pdfGeneralo.pdfGeneration(inputData, data);
            infoMasssige.setText("Sikeres generálás");
            errorPane.setVisible(true);
            exportPane.setDisable(true);
            exportPane.setVisible(false);
            ERRORR_MES = "Message2";
            pdfTextField.clear();

        } else {
            infoMasssige.setText("Adjon meg értékeket!");
            errorPane.setVisible(true);
            exportPane.setDisable(true);
            exportPane.setVisible(false);
            ERRORR_MES = "Message2";
        }

    }

    @FXML
    private void errorMessinge(ActionEvent event) {
        if (ERRORR_MES.equals("Message1")) {
            errorPane.setVisible(false);
            contactPane.setDisable(false);
            contactPane.setVisible(true);
        } else if (ERRORR_MES.equals("Message2")) {
            errorPane.setVisible(false);
            exportPane.setDisable(false);
            exportPane.setVisible(true);
        }

    }

    public void setDataBase() {
        TableColumn nameCol = new TableColumn("Nevek");
        nameCol.setMinWidth(150);
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setName(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn phonenumberCol = new TableColumn("Telefon szám");
        phonenumberCol.setMinWidth(100);
        phonenumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phonenumberCol.setCellValueFactory(new PropertyValueFactory<Person, String>("phonenumber"));
        phonenumberCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setNumber(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        TableColumn emailCol = new TableColumn("Email cím");
        emailCol.setMinWidth(185);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

        emailCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setEmail(t.getNewValue());
                db.updateContact(actualPerson);
            }
        }
        );

        tablazat.getColumns().addAll(nameCol, phonenumberCol, emailCol);
        data.addAll(db.getAllContacts());
        tablazat.setItems(data);

    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menű");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);
        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);
        nodeItemA.setExpanded(true);

        Node dataNode = new ImageView(new Image(getClass().getResourceAsStream("/kep1.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/kep4.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_EXPORT, exportNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_DATA, dataNode);
        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);

        menu.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            try {
                                selectedItem.setExpanded(true);
                            } catch (Exception ex) {
                            }
                            break;
                        case MENU_DATA:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;

                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDataBase();
        setMenuData();

    }

}
