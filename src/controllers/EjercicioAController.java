package controllers;

import javafx.scene.Node;
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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Controlador principal de la ventana EjercicioA
 * 
 */
public class EjercicioAController {
	
	private Scene mainScene;
	private TextField profesionTxtf,nHermanosTxtf;
	private ComboBox<String> comboEdades;
	private RadioButton[] radiobuttons = new RadioButton[3];
	private CheckBox practicaDeporte;
	private ListView<String> deportesLst;
	private Slider compras,cine,tele;
	private Button aceptarButton, cancelarButton;
	
	/**
	 * Constructor de la clase controladora. Instancia todos los nodos con los que interactúa el usuario
	 * 
	 * @param root Escena principal de EjercicioA
	 */
	public EjercicioAController(Scene root) {
		mainScene = root;
		cancelarButton = (Button) root.lookup("#cancelarButton");
		aceptarButton = (Button) root.lookup("#aceptarButton");
		profesionTxtf = (TextField) root.lookup("#profesionTextfield");
		nHermanosTxtf = (TextField) root.lookup("#nHermanosTextfield");
		
		/* Array de edades para el dropdown / combobox */
		String[] edades = new String[] {
				"Menores de 18",
				"Entre 18 y 30",
				"Entre 31 y 50",
				"Entre 51 y 70",
				"Mayores de 70"
		};
		/* Inicializar ComboBox de rangos de edad */
		comboEdades = (ComboBox<String>) root.lookup("#edadDropdown");
		/* Añadir rangos de edad al ComboBox */
		comboEdades.getItems().setAll(edades);
		/* Inicializar RadioButtons en un array*/
		radiobuttons[0] = (RadioButton)root.lookup("#radioMale");
		radiobuttons[1] = (RadioButton)root.lookup("#radioFemale");
		radiobuttons[2] = (RadioButton)root.lookup("#radioOther");
		radiobuttons[2].setSelected(true);
		/* Inicializar Slider */
		compras = (Slider) root.lookup("#compraSlider");
		tele = (Slider) root.lookup("#televisionSlider");
		cine = (Slider) root.lookup("#cineSlider");
		/* Inicializar CheckBox practicaDeporte */
		practicaDeporte = (CheckBox) root.lookup("#deporteCheck");
		/* Al hacer click en la edad se activa/desactiva la lista de deportes */
		practicaDeporte.setOnAction(e -> deportesLst.setDisable(!practicaDeporte.isSelected()));
		/* Creamos el ListView con los deportes*/
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
		/* Permitimos selección multiple con CTRL o SHIFT */
		deportesLst.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		/* Buscamos el GridPane de los deportes */
		GridPane deportesGPane = (GridPane) practicaDeporte.getParent();
		deportesGPane.add(deportesLst,1,1);

		/* Establecemos las funciones de los botones */
		setupButtons();
		/* Añadimos ToolTips a los Sliders*/
		setupToolTips();
	}
	/**
	 * Establece las acciones de los botones en la parte inferior de la pantalla. (Aceptar, Cancelar)
	 */
	private void setupButtons() {
		cancelarButton.setOnAction(e -> ((Stage) mainScene.getWindow()).close());
		aceptarButton.setOnAction(e -> crearModalConInfo());
	}
	/**
	 * Itera entre los botones del array (radios) para encontrar el seleccionado.
	 * 
	 * @param radios Array de RadioButtons.
	 * @return RadioButton seleccionado dentro del grupo.
	 */
	private RadioButton selectedButton(RadioButton[] radios) {
		for (RadioButton radio : radios) {
			if (radio.isSelected()) {
				return radio;
			}
		}
		return null;
	}
	
	/**
	 * Genera una ventana modal en la que se dibujan las opciones elegidas por el usuario.
	 * 
	 */
	private void crearModalConInfo() {
		if (whatIsEmpty() != null) {
			showErrorWindow();
			return;
		}
		
		String[] datos = {
			"Profesión: " + profesionTxtf.getText(),
			"Nº Hermanos: " + nHermanosTxtf.getText(),
			"Edad: " + comboEdades.getValue(),
			"Sexo: " + selectedButton(radiobuttons).getText(),
			/* Si practica deporte se añade enseña cuales si no, no. */
			(practicaDeporte.isSelected())?"Deportes que practicas:":"",
			(practicaDeporte.isSelected())?stringDeportesPractica():"",
			"Grado de aficción a las compras: " + (int)compras.getValue(),
			"Grado de aficción a ver la televisión: " + (int)tele.getValue(),
			"Grado de aficción a ir al cine: " + (int)cine.getValue(),
		};
		/* Formatear el String para dejar un salto de linea entre cada valor del array*/
		String datosString="";
		for (String dato:datos) {
			if (dato != null && !dato.isEmpty())
				datosString += dato + "\n";
		}
		/* Creacion de la ventana modal */
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(datosString);
		alert.initModality(Modality.APPLICATION_MODAL);

		alert.showAndWait();
	}
	/**
	 * Toma los datos seleccionados de la lista (deporteLst) y la devuelve formateada.
	 * 
	 * @return String formateado con tabulaciones para enseñar los deportes que sí practica el usuario.
	 */
	private String stringDeportesPractica() {
		String deportes = "";
		for (String deporte : deportesLst.getSelectionModel().getSelectedItems()) {
			deportes += "\t" + deporte + "\n";
		}
		/* Eliminamos el caracter final (\n) para evitar un salto de linea innecesario */
		deportes = deportes.substring(0,deportes.length()-1);
		return deportes;
	}
	/**
	 * Añade tooltips a los sliders.
	 */
	private void setupToolTips() {
		compras.setTooltip(new Tooltip("Indica del 1 al 10 cuanto te gusta ir de compras"));
		cine.setTooltip(new Tooltip("Indica del 1 al 10 cuanto te gusta ir al cine"));
		tele.setTooltip(new Tooltip("Indica del 1 al 10 cuanto te gusta ver la tele"));
	}
	/**
	 * Devuelve el nodo vacío en la ventana.
	 * @return Node nodo vacío en la ventana.
	 */
	private Node whatIsEmpty() {
		if (profesionTxtf.getText() == null) {
			return profesionTxtf;
		} else if(nHermanosTxtf.getText() == null) {
			return nHermanosTxtf;
		} else if(comboEdades.getValue() == null) {
			return comboEdades;
		}
		return null;
	}
	/**
	 * Enseña una ventana de error indicando que falta algo por rellenar.
	 */
	private void showErrorWindow() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR: Missing argument");
		alert.setHeaderText(null);
		alert.setContentText("Falta por rellenar parte del formulario");
		
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.showAndWait();
	}
}
