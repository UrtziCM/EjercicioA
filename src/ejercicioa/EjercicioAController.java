package ejercicioa;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EjercicioAController {
	
	private Scene mainScene;
	private TextField profesionTxtf,nHermanosTxtf;
	private ComboBox<String> comboEdades;
	private RadioButton[] radiobuttons = new RadioButton[3];
	private CheckBox practicaDeporte;
	private ListView<String> deportesLst;
	private Slider compras,cine,tele;
	private Button aceptarButton, cancelarButton;
	
	public EjercicioAController(Scene root) {
		mainScene = root;
		cancelarButton = (Button) root.lookup("#cancelarButton");
		aceptarButton = (Button) root.lookup("#aceptarButton");
		profesionTxtf = (TextField) root.lookup("#profesionTextfield");
		nHermanosTxtf = (TextField) root.lookup("#nHermanosTextfield");
		
		String[] edades = new String[] {
				"Menores de 18",
				"Entre 18 y 30",
				"Entre 31 y 50",
				"Entre 51 y 70",
				"Mayores de 70"
			};
		comboEdades = (ComboBox<String>) root.lookup("#edadDropdown");
		comboEdades.getItems().setAll(edades);
		
		radiobuttons[0] = (RadioButton)root.lookup("#radioMale");
		radiobuttons[1] = (RadioButton)root.lookup("#radioFemale");
		radiobuttons[2] = (RadioButton)root.lookup("#radioOther");
		radiobuttons[2].setSelected(true);
		compras = (Slider) root.lookup("#compraSlider");
		tele = (Slider) root.lookup("#televisionSlider");
		cine = (Slider) root.lookup("#cineSlider");
		practicaDeporte = (CheckBox) root.lookup("#deporteCheck");
		
		practicaDeporte.setOnAction(e -> deportesLst.setDisable(!practicaDeporte.isSelected()));
		
		deportesLst = new ListView<String>();
		deportesLst.getItems().setAll(
			"Tenis",
			"Fútbol",
			"Baloncesto",
			"Natación",
			"Ciclismo",
			"Otro"
		);
		deportesLst.setDisable(true);
		deportesLst.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		GridPane deportesGPane = (GridPane) practicaDeporte.getParent();
		deportesGPane.add(deportesLst,1,1);

		this.setupButtons();
	}
	
	private void setupButtons() {
		cancelarButton.setOnAction(e -> ((Stage) mainScene.getWindow()).close());
		aceptarButton.setOnAction(e -> crearModalConInfo());
	}
	
	private RadioButton selectedButton(RadioButton[] radios) {
		for (RadioButton radio : radios) {
			if (radio.isSelected()) {
				return radio;
			}
		}
		return null;
	}
	
	private void crearModalConInfo() {
		String[] datos = {
			"Profesión: " + profesionTxtf.getText(),
			"Nº Hermanos: " + nHermanosTxtf.getText(),
			"Edad: " + comboEdades.getValue(),
			"Sexo: " + selectedButton(radiobuttons).getText(),
			(practicaDeporte.isSelected())?"Deportes que practicas:":"",
			stringDeportesPractica(),
			"Grado de aficción a las compras: " + (int)compras.getValue(),
			"Grado de aficción a ver la televisión: " + (int)tele.getValue(),
			"Grado de aficción a ir al cine: " + (int)cine.getValue(),
		};
		String datosString="";
		for (String dato:datos) {
			if (dato != null && !dato.isEmpty())
				datosString += dato + "\n";
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(datosString);

		alert.showAndWait();
	}

	private String stringDeportesPractica() {
		String deportes = "";
		for (String deporte : deportesLst.getSelectionModel().getSelectedItems()) {
			deportes += "\t" + deporte + "\n";
		}
		deportes = deportes.substring(0,deportes.length()-1);
		return deportes;
	}
}
