package com.libgo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Muat file FXML utama (primary.fxml)
        Parent root = loadFXML("primary");
        scene = new Scene(root, 600, 500); // Menggunakan root yang di-load dari FXML
        stage.setScene(scene);
        stage.setTitle("Pengelola Stok Buku");
        stage.show();
    }

    // Metode untuk mengganti tampilan (antara primary dan secondary)
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml)); // Gunakan parameter fxml untuk menentukan file yang akan dimuat
    }
    
    // Metode untuk memuat file FXML
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Gagal memuat file FXML: " + fxml + ".fxml", e);
        }
    }
    

    public static void main(String[] args) {
        launch(); // Memulai aplikasi JavaFX
    }
}
