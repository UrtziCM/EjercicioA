package ejercicioa;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class EjercicioAController {
	private TextField profesionTxtf,nHermanosTxtf;
	private ComboBox<String> comboEdades;
	private RadioButton radioMale,radioFemale,radioOther;
	private CheckBox practicaDeporte;
	private ListView<String> deportesLst;
	private Slider compras,tele,cine;
	
	public EjercicioAController(Scene root) {
		profesionTxtf = (TextField) root.lookup("#profesionTextfield");
	}
	
	
}
