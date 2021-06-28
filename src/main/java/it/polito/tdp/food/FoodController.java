/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...\n");
    	if(this.model.getGrafo() == null) {
    		this.txtResult.appendText("Creare prima il grafo!");
    		return;
    	}
    	String porzione = this.boxPorzioni.getValue();
    	if(porzione == null) {
    		this.txtResult.appendText("Selezionare una porzione!");
    		return;
    	}
    	String n = this.txtCalorie.getText();
    	try{
    		int passi = Integer.parseInt(n);
    		if(passi < 0) {
    			this.txtResult.appendText("Inserire un intero positivo!");
        		return;
    		}
    		List<Adiacenza> cammino = this.model.getCammino(porzione, passi);
    		this.txtResult.appendText("CAMMINO:\n");
    		for(Adiacenza s : cammino) {
        		this.txtResult.appendText(s.getP2() + "\n");
        	}
    		this.txtResult.appendText("PESO TOTALE: " + this.model.getPesoCammino());
    	}
    	catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire un intero!");
    		return;
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    	if(this.model.getGrafo() == null) {
    		this.txtResult.appendText("Creare prima il grafo!");
    		return;
    	}
    	String porzione = this.boxPorzioni.getValue();
    	if(porzione == null) {
    		this.txtResult.appendText("Selezionare una porzione!");
    		return;
    	}
    	List<Adiacenza> vicini = this.model.getCorrelati(porzione);
    	for(Adiacenza a : vicini) {
    		this.txtResult.appendText(a.getP2() + " - " + a.getPeso() + "\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	boxPorzioni.getItems().clear();
    	txtResult.appendText("Creazione grafo...\n");
    	
    	String c = this.txtCalorie.getText();
    	try{
    		int cal = Integer.parseInt(c);
    		if(cal < 0) {
    			this.txtResult.appendText("Inserire un intero positivo!");
        		return;
    		}
    		String msg = this.model.creaGrafo(cal);
    		this.txtResult.appendText(msg);
    		this.boxPorzioni.getItems().addAll(this.model.getVertici());
    	}
    	catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire un intero!");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
