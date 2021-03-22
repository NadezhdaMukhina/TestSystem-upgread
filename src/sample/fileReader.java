package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class fileReader {

    public fileReader() {
    }

    public String fileRead(ActionEvent event) {// возвращает путь выбранного файла
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("DataBase files (*.db)", "*.db");
        fileChooser.getExtensionFilters().add(fileFilter);
        fileChooser.getExtensionFilters().addAll(fileFilter);
        fileChooser.setTitle("Выбор файла");

        File file = fileChooser.showOpenDialog(primaryStage);
        System.out.println("Выбран файл для загрузки данных: " + file.getPath() + "\n"); // пишем путь выбранного файла в лог

        return file.getPath();
    }

    public String fileCreate(ActionEvent event) {// возвращает путь созданного файла
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("DataBase files (*.db)", "*.db");
        fileChooser.getExtensionFilters().add(fileFilter);
        fileChooser.getExtensionFilters().addAll(fileFilter);
        fileChooser.setTitle("Выбор файла");

        File file = fileChooser.showSaveDialog(primaryStage);
        try {
            if (file.createNewFile()) {
                System.out.println("Создан файл базы данных: " + file.getPath() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания файла БД. Файл не создан.");
        }

        return file.getPath();
    }
}
