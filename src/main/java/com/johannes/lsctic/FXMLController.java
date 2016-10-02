package com.johannes.lsctic;

import com.johannes.lsctic.amiapi.Amiapi;
import com.johannes.lsctic.amiapi.ServerConnectionHandler;
import com.johannes.lsctic.fields.InternField;
import com.johannes.lsctic.fields.HistoryField;
import com.johannes.lsctic.fields.LDAPField;
import com.johannes.lsctic.settings.AsteriskSettingsField;
import com.johannes.lsctic.settings.DeploymentSettingsField;
import com.johannes.lsctic.settings.LDAPSettingsField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private VBox panelA;
    @FXML
    private VBox panelB;
    @FXML
    private VBox panelC;
    @FXML
    private VBox panelD;
    @FXML
    private Button optionAccept;
    @FXML
    private Button optionReject;
    @FXML
    private TextField paneATextIn;
    @FXML
    private TextField paneBTextIn;
    @FXML
    private TabPane tabPane;
    @FXML
    private ScrollPane scrollPaneA;

    private OptionsStorage storage;
    private Map<Integer, PhoneNumber> internNumbers;
    private String quickdialString;
    private Tooltip customTooltip;
    private ServerConnectionHandler somo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Muss vor dem erstellen des Optionsstorage sein, da ggf. die Datenbank nicht existiert
        SqlLiteConnection s = new SqlLiteConnection("settingsAndData.db", "dataLocal.db");
        // Optionsstorage erstellen und Daten aus Settingsdatabase laden
        storage = new OptionsStorage(optionAccept, optionReject);
        // Die Lizenz überprüfen, wenn nicht lizensiert beenden.
        //  startUpLicenseCheck(storage);
        customTooltip = new Tooltip();
        panelA.setSpacing(3);
        panelB.setSpacing(3);
        panelC.setSpacing(3);
        
        // AmiApi initialisieren
        //api = new Amiapi("192.168.178.195", "Johannes", "johannesAusTirol1232", 5038);
        
        internNumbers = new TreeMap<>();
        internNumbers.put(702, new PhoneNumber(true, 702, "Johannes Engler", 19));
        internNumbers.put(703, new PhoneNumber(true, 703, "Michael Engler", 2));
        internNumbers.put(704, new PhoneNumber(true, 704, "Fabian Engler", 1));
        internNumbers.put(705, new PhoneNumber(true, 705, "Fabian Englert", 5));
        internNumbers.put(706, new PhoneNumber(true, 706, "Manuel Englert", 9));
        internNumbers.put(707, new PhoneNumber(true, 707, "Stefan Englert", 5));
        internNumbers.put(708, new PhoneNumber(true, 708, "Felix Englert", 5));
        internNumbers.put(709, new PhoneNumber(true, 709, "Marcel Englert", 5));
        internNumbers.put(710, new PhoneNumber(true, 710, "Dirk Englert", 5));
        internNumbers.put(711, new PhoneNumber(true, 711, "Olliver Englert", 15));
        internNumbers.put(712, new PhoneNumber(true, 712, "Victor Englert", 5));
        internNumbers.put(713, new PhoneNumber(true, 713, "Marco Englert", 5));
        internNumbers.put(714, new PhoneNumber(true, 714, "Gregor Englert", 5));

        /*internNumbers.put("JoZannes Engler", new PhoneNumber(true, 0157, "JohZnnes Engler", 12));
        internNumbers.put("MiZhael Engler", new PhoneNumber(true, 0157, "MichZel Engler", 2));
        internNumbers.put("FaZian Engler", new PhoneNumber(true, 0157, "FabiaZ Engler", 1));
        internNumbers.put("FaZian Englert", new PhoneNumber(true, 0157, "FabiZn Englert", 5));
        internNumbers.put("MaZuel Englert", new PhoneNumber(true, 0157, "ManuZl Englert", 9));
        internNumbers.put("StZfan Englert", new PhoneNumber(true, 0157, "StefZn Englert", 5));
        internNumbers.put("FeZix Englert", new PhoneNumber(true, 0157, "FelixZEnglert", 5));
        internNumbers.put("MaZcel Englert", new PhoneNumber(true, 0157, "MarcZl Englert", 5));
        internNumbers.put("DiZk Englert", new PhoneNumber(true, 0157, "Dirk EZglert", 5));
        internNumbers.put("OlZiver Englert", new PhoneNumber(true, 0157, "OllZver Englert", 15));
        internNumbers.put("ViZtor Englert", new PhoneNumber(true, 0157, "VictZr Englert", 5));
        internNumbers.put("MaZco Englert", new PhoneNumber(true, 0157, "MarcoZEnglert", 5));*/
        
        /*   tabPane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {   NICHT LÖSCHEN ERSTER ANSATZ FÜR WEITERE BESCHLEUNIGUNG DES ARBEITENS
        

         @Override
         public void handle(javafx.scene.input.KeyEvent event) {
         if (event.getCode() == KeyCode.I && event.isControlDown()) {
         selectTab(0);
         System.out.println(0);
         event.consume(); 
         } if(event.getCode() == KeyCode.L && event.isControlDown()) {
         selectTab(1);
         System.out.println(1);

         event.consume();
         }  if(event.getCode() == KeyCode.A && event.isControlDown()) {
         selectTab(2);
         System.out.println(2);

         event.consume();
         }  if (event.getCode() == KeyCode.O && event.isControlDown()) {
         selectTab(3);
         System.out.println(3);

         event.consume();
         }
         }
         });*/
        HashMap<Integer, InternField> i = new HashMap();
        internNumbers.entrySet().stream().forEach((g) -> {
            i.put(g.getKey(),new InternField(g.getValue().getName(), g.getValue().getCount(), g.getKey(), somo));
        });
        try {
            somo = new ServerConnectionHandler(i);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<InternField> interns = new ArrayList<>(i.values());
        updateAnzeige(interns);
        paneATextIn.addEventFilter(KeyEvent.KEY_PRESSED, (javafx.scene.input.KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println(quickdialString);
                try {
                    long l = Long.valueOf(quickdialString);
                    System.out.println("Wähle " + l);
                } catch (Exception e) {

                }

                event.consume();
            }
        });

        paneATextIn.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                quickdialString = newValue;
                long quickdial = Long.valueOf(newValue);
                customTooltip.hide();
                customTooltip.setText("Nummer erkannt. Enter zum wählen");
                paneATextIn.setTooltip(customTooltip);
                customTooltip.setAutoHide(true);
                Point2D p = paneATextIn.localToScene(0.0, 0.0);
                customTooltip.show(paneATextIn, p.getX()
                        + paneATextIn.getScene().getX() + paneATextIn.getScene().getWindow().getX(), p.getY()
                        + paneATextIn.getScene().getY() + paneATextIn.getScene().getWindow().getY() + paneATextIn.getHeight());

            } catch (Exception e) {
                customTooltip.hide();
            }
            updateAnzeige(generiereReduziertesSet(internNumbers, newValue));
        });
        LDAPController l = new LDAPController("server", 389, "server", "people", storage);
        ArrayList<LDAPEntry> ld = l.getN("", storage.getLdapSearchAmount());
        updateLdapFields(ld);
        paneBTextIn.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            ArrayList<LDAPEntry> ld1 = l.getN(newValue, storage.getLdapSearchAmount());
            updateLdapFields(ld1);
        });

        ArrayList<HistoryField> hFields = new ArrayList();
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", true));
        hFields.add(new HistoryField("Johannes Engler", "12.12.16 15:30", "10 min", true));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", true));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", false));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", true));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", true));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", true));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", false));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", true));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", true));
        hFields.add(new HistoryField("Johannes Engler", "Gestern", "10 min", true));

        panelC.getChildren().addAll(hFields);

        panelD.getChildren().addAll(new AsteriskSettingsField(storage), new LDAPSettingsField(storage), new DeploymentSettingsField(storage));

    }

    private void selectTab(int i) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(i); //select by index starting with 0
        tabPane.setSelectionModel(selectionModel);
        selectionModel.clearSelection(); //clear your selection
    }

    private ArrayList<InternField> generiereReduziertesSet(Map<Integer, PhoneNumber> internNumbers, String val) {
        ArrayList<InternField> out = new ArrayList<>();
        internNumbers.entrySet().stream().filter((g) -> (g.getValue().getName().toLowerCase().contains(val.toLowerCase()))).forEach((g) -> {
            out.add(new InternField(g.getValue().getName(), g.getValue().getCount(), g.getKey(), somo));
        });
        return out;
    }

    private void updateAnzeige(ArrayList<InternField> i) {
        scrollPaneA.setVvalue(0);
        
        Collections.sort(i, (InternField o1, InternField o2) -> o2.getCount() - o1.getCount() //Sortiert nach Count
        /*  int i = 0;
         while(o1.getName().charAt(i) == o2.getName().charAt(i)) {
         ++i;
         if(i>=o1.getName().length() || i>=o2.getName().length()) {
         return 0;
         }
         }
         return o1.getName().charAt(i) - o2.getName().charAt(i);*/
        // Nach namen sortieren
        );
        panelA.getChildren().clear();
        panelA.getChildren().addAll(i);
    }

    private void updateLdapFields(ArrayList<LDAPEntry> i) {
        panelB.getChildren().clear();
        ArrayList<LDAPField> ldapFields = new ArrayList<>();
        i.stream().forEach((ent) -> {
            ldapFields.add(new LDAPField(ent.get(0), 2, 123123, ent, storage));
        });
        panelB.getChildren().addAll(ldapFields);
    }

    public void startUpLicenseCheck(OptionsStorage storage) {
        LicenseChecker lcheck = new LicenseChecker(storage);

        if (lcheck.checkIsValid()) {
            Stage dialogStage = new Stage();
            dialogStage.setOnCloseRequest((WindowEvent event) -> {
                System.exit(0);
            });
            dialogStage.setAlwaysOnTop(true);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            final Button b = new Button("Beenden");
            b.setOnAction((ActionEvent event) -> {
                System.exit(0);
            });
            dialogStage.setScene(new Scene(VBoxBuilder.create().
                    children(new Text("Ihre Testlizenz ist leider abgelaufen. Bitte besuchen sie www.cti.eu um das Produkt zu lizenzieren"), b).
                    alignment(Pos.CENTER).padding(new Insets(10)).build()));
            dialogStage.show();

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int seconds = 21;
                int i = 0;

                @Override
                public void run() {
                    i++;

                    if (i % seconds == 0) {
                        System.exit(0);
                    } else {
                        Platform.runLater(() -> {
                            b.setText("Beenden (" + (20 - i) + ")");
                        });
                    }
                }

            }, 1000, 1000);
        }
    }

}
