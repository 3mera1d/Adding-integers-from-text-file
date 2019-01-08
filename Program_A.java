import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Reads abc.txt file of integer lines, writes sum of each line at the end of
 * each of the lines into abc_out.txt file. Resources are listed throughout
 * program. First source for the fileChooser is from
 * http://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
 * 
 * @author Paty
 *
 */
public final class Program_A extends Application {

	private Desktop desktop = Desktop.getDesktop();
	static String[] addtoArray = new String[10];

	@Override
	public void start(final Stage stage) throws IOException {
		stage.setTitle("Program A");

		final FileChooser fileChooser = new FileChooser();

		final Button openButton = new Button("Open");
		final Button cancelButton = new Button("Cancel");

		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				configureFileChooser(fileChooser);
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					openFile(file);
				}
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				stage.close();
			}

		});

		final GridPane inputGridPane = new GridPane();

		GridPane.setConstraints(openButton, 0, 0);
		GridPane.setConstraints(cancelButton, 1, 0);
		inputGridPane.setHgap(6);
		inputGridPane.setVgap(6);
		inputGridPane.getChildren().addAll(openButton, cancelButton);

		final Pane rootGroup = new VBox(12);
		rootGroup.getChildren().addAll(inputGridPane);
		rootGroup.setPadding(new Insets(12, 12, 12, 12));

		stage.setScene(new Scene(rootGroup));
		stage.show();

		// Source: https://stackoverflow.com/questions/37769481/
		// javafx-gui-that-opens-a-text-file-how-to-read-whats-in-text-file-and-edit-save
		BufferedReader textfile = new BufferedReader(new FileReader("abc.txt"));
		process(textfile);

	}

	/**
	 * Reads each line from text, adds the integers and puts the sum at the end
	 * of the line. Saves into new output file.
	 * 
	 * @param textfile
	 * @throws IOException
	 */
	static void process(BufferedReader textfile) throws IOException {
		int sum = 0;
		String line, everything;

		// Source:
		// https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
		BufferedReader readFile = new BufferedReader(new FileReader("abc.txt"));

		try {
			StringBuilder sb = new StringBuilder();
			line = readFile.readLine();

			while (line != null) {

				sb.append(line);
				sum = num(line);
				sb.append(" " + sum);
				sb.append(System.lineSeparator());

				line = readFile.readLine();

			}
			everything = sb.toString();

		} finally {
			readFile.close();

		}

		System.out.println(everything);

		PrintWriter writer = new PrintWriter("abc_out.txt");
		writer.println(everything);

		writer.close();
	}

	/**
	 * Splits each line read, turns them into integers and adds them.
	 * Source: https://stackoverflow.com/questions/27457846/multiple-integer-read-in-java-with-bufferedreader-in-java
	 * 
	 * @param lines
	 * @return sum of each line of integers
	 */
	public static int num(String lines) {
		int intNum = 0;
		int sum = 0;
		String[] numbers;
		List<String> eachLineList = new ArrayList<String>();
		eachLineList.add(lines.trim());
		for (String line : eachLineList) {
			numbers = line.split("\\s+");
			if (numbers.length <= 1) {
				break;
			} else {

				for (String num : numbers) {
					intNum = Integer.parseInt(num);
					sum += intNum;
				}
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Select text file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

	}

	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(Program_A.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
