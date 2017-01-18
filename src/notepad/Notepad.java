/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.FileStore;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author fatma
 */
public class Notepad extends Application {

    FileChooser fileChooser;
    TextArea TA = new TextArea();
    boolean flag = false;

    @Override
    public void start(Stage primaryStage) {

        MenuBar bar = new MenuBar();

        Menu file = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem exitItem = new MenuItem("Exit");

        Menu edit = new Menu("Edit");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        MenuItem deleteItem = new MenuItem("Delete");
        //SeparatorMenuItem deleteItem = new SeparatorMenuItem();
        Menu help = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About Notepad");

        file.getItems().addAll(newItem, openItem, saveItem, exitItem);

        edit.getItems().addAll(cutItem, copyItem, pasteItem, deleteItem);

        help.getItems().addAll(aboutItem);

        bar.getMenus().addAll(file, edit, help);

        newItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() { // flag was false
            @Override
            public void handle(ActionEvent event) {

                if (!TA.getText().isEmpty()) {
                    check(primaryStage);
                    /*
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("New Notepad");
                    alert.setHeaderText("Do you want to save the changes?");
                    //    alert.setContentText("Choose your option.");

                    ButtonType buttonTypeOne = new ButtonType("Yes");
                    ButtonType buttonTypeTwo = new ButtonType("No");

                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne) {
                        fileChooser = new FileChooser();

                        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

                        String text = TA.getText();
                        byte[] b = text.getBytes();

                        // while(b.length>0){ 
                        //}
                        File file = fileChooser.showSaveDialog(primaryStage);
                        if (file != null) {
                            try (OutputStream out = new BufferedOutputStream(
                                    Files.newOutputStream(file.toPath(), CREATE, APPEND))) {
                                out.write(b);
                            } catch (IOException x) {
                                System.err.println(x);
                            }
                        }
   
                    } else if (result.get() == buttonTypeTwo) {
                        TA.clear();
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                     */
                }
            }
        });

        openItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

                File file = fileChooser.showOpenDialog(primaryStage);
                InputStream in;
                if (file != null) {
                    try {
                        in = Files.newInputStream(file.toPath());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String data = reader.readLine();
                        TA.setText(data);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!TA.getText().isEmpty()){
                    System.out.println(" Text Found");
                    save(primaryStage);
                }else{
                    System.out.println("No Text Found");
                }
            }
        });

        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!TA.getText().isEmpty()) {
                    check(primaryStage);
                }
                Platform.exit();
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                if (!TA.getText().isEmpty()) {
                    check(primaryStage);
                    //save(primaryStage);
                }
                Platform.exit();
            }
        });

        cutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TA.cut();
            }
        });
        copyItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TA.copy();
            }
        });
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TA.deleteNextChar();
            }
        });
        pasteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TA.paste();
            }
        });

        aboutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("About!");
                alert.setHeaderText(null);
                alert.setContentText("Notepad Task");
                alert.setContentText("By: Fatma Ramadan");
                alert.show();
            }
        });

     //   TA.setPromptText("ENTER YOUR TEXT HERE");

        BorderPane pane = new BorderPane();
        pane.setTop(bar);
        pane.setCenter(TA);

        Scene scene = new Scene(pane, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void save(Stage primaryStage) {

        flag= true;
        fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

        String text = TA.getText();
        byte[] b = text.getBytes();

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try {
                OutputStream out = new BufferedOutputStream(Files.newOutputStream(file.toPath(), CREATE, APPEND));
                out.write(b);
                flag = true;

                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * create an alert which is used to check either to save the note or not if
     * yes then choose location and name
     *
     */
    public void check(Stage primaryStage) {

      //  if (!flag) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("New Notepad");
            alert.setHeaderText("Do you want to save the changes?");
            //    alert.setContentText("Choose your option.");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                save(primaryStage);
               /*
                fileChooser = new FileChooser();

                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

                String text = TA.getText();
                byte[] b = text.getBytes();
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(file.toPath(), CREATE, APPEND))) {
                        out.write(b);
                    } catch (IOException x) {
                        System.err.println(x);
                    }
                }
*/
            } else if (result.get() == buttonTypeTwo) {
                TA.clear();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        //}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}