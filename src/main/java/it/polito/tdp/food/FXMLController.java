package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Ingredienti;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCalorie;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Condiment> boxIngrediente;

    @FXML
    private Button btnDietaEquilibrata;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaDieta(ActionEvent event) {
    	txtResult.clear();
    	
    	Condiment i = this.boxIngrediente.getValue();
    	
    	if(i==null) {
    		txtResult.appendText("Scegliere un vertice di partenza!");
    		return;
    	}
    	
    	for(String s: this.model.ricorsiva(i)) {
    		txtResult.appendText(s+"\n");
    	}
    	

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer calorie;
    	String c = this.txtCalorie.getText();
    	
    	if(c == null) {
    		txtResult.appendText("Inserire il valore delle calorie!");
    	}
    	
    	try {
    		calorie = Integer.parseInt(c);
    		
    	}catch (IllegalArgumentException e) {
        	txtResult.appendText("Inserire valore numerico!");
        	return;
        }
    	
    	this.model.creaGrafo(calorie);
    	this.boxIngrediente.getItems().clear();
    	this.boxIngrediente.getItems().addAll(this.model.getVertex());
    	txtResult.appendText("Grafo creato!\n#Vertici = "+this.model.getVertex().size()+" #Archi = "+this.model.getEdge().size()+"\n");
    	
    	for(Ingredienti i : this.model.getIngredienti()) {
    		txtResult.appendText(i.toString()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;
	}
}

