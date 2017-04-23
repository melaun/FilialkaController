/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filialkacontroller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author Podzimek Vojtěch
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private VBox boxName;

    @FXML
    private VBox boxIP;

    @FXML
    private VBox boxIndikator;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private ProgressIndicator indikator;

    ArrayList<Filialka> filialky = new ArrayList();

    @FXML
    private void handleButtonAction(ActionEvent event) {
        refreshButton.setDisable(true);
        indikator.setVisible(true);
        Task task = new Task() {
            
            @Override
            protected Object call() throws Exception {
                refreshAvaibleFilialky();
                
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            refreshButton.setDisable(false);
            indikator.setVisible(false);
            boxesRefresh();
        });
        new Thread(task).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boxIndikator.setSpacing(15);
        boxName.setSpacing(10);
        boxIP.setSpacing(10);
        initFilialky();
    }

    /**
     * add fililkz to filialkz
     */
    private void initFilialky() {
        filialky.add(new Filialka("CENTRALA", "10.8.0.1"));
        filialky.add(new Filialka("Milín", "10.8.0.18"));
        filialky.add(new Filialka("Nemocnice", "10.8.0.30"));
        filialky.add(new Filialka("Kozárovice", "10.8.0.42"));
        filialky.add(new Filialka("Cukrárna Pražská", "10.8.0.50"));
        filialky.add(new Filialka("Cukrárna Sídliště", "10.8.0.58"));
        filialky.add(new Filialka("Zdice", "10.8.0.38"));
        filialky.add(new Filialka("Vysoka Pec", "10.8.0.34"));
        filialky.add(new Filialka("Stará Huť", "10.8.0.10"));
        filialky.add(new Filialka("Velkoobchod", "10.8.0.54"));
    }

    /**
     * refresh components in boxes clear boxes add filiaky from filialky
     */
    private void boxesRefresh() {
        System.out.println("Start boxes refresh on click");
        boxIP.getChildren().clear();
        boxIndikator.getChildren().clear();
        boxName.getChildren().clear();
        
        for (Filialka filialka : filialky) {
            addFilialka(filialka);
        }
    }
    /**
     * Refresf all filialky and set progress indikator
     */
    private void refreshAvaibleFilialky() {
        double counter = 0;
        double addValue = (1/(double)filialky.size());
        for (Filialka f : filialky) {
            counter+=addValue;
            f.refreshAvaible();
            indikator.setProgress(counter);
        }
    }

    /**
     * add row with filialka to boxes
     *
     * @param filialka
     */
    private void addFilialka(Filialka filialka) {
        Label name = new Label(filialka.getName());
        boxName.getChildren().add(name);
        Label ip = new Label(filialka.getIpv4Adress());
        boxIP.getChildren().add(ip);
        Circle indicator = new Circle();
        if (filialka.getIsAvaible()) {
            indicator.setRadius(8.0);
            indicator.setFill(Color.GREEN);
        } else {
            indicator.setRadius(8.0);
            indicator.setFill(Color.RED);
        }
        boxIndikator.getChildren().add(indicator);

    }

}
