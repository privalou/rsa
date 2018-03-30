package view.controllers;

import files.KeyGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;

import javafx.event.ActionEvent;
import org.apache.commons.codec.binary.Hex;
import rsa.PrivateKey;
import rsa.PublicKey;
import services.NetServiceClient;
import utils.ESignatureUtils;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
    public Button transferButton;
    public TextField ipField;
    public TextField fileField;
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private Button findFileButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void findFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        fileField.clear();
        if (file == null) {
            throwAlert(event, "Error", "Error", "You havent selected any file");
        } else {
            fileField.appendText(file.getName());
        }
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void transferFile(ActionEvent event) {
        String serverIp = ipField.getText();
        try {
            InetAddress inetAddress = InetAddress.getByName(serverIp);
            Socket socket = new Socket(inetAddress, 9999);
            KeyGenerator.generate("eSignature");
            PublicKey publicKey = KeyGenerator.loadPublicKey("eSignature.public");
            PrivateKey privateKey = KeyGenerator.loadPrivateKey("eSignature.private");
            NetServiceClient netServiceClient = new NetServiceClient();
            PublicKey aeskey = netServiceClient.sendKeyRequest();
            if (fileField.getText() != "") {
                String fileName = fileField.getText();
                byte[] hash = ESignatureUtils.signFile(fileName);
                byte[] enchHash = privateKey.encrypt(hash);
                netServiceClient.sendBytes(enchHash, publicKey, fileName, aeskey, "Test");
                throwAlert(event, "Congratulations", "Wow", "You've just sent encrypted file." +
                        " Its hash is " + Hex.encodeHexString(hash));
            } else {
                throwAlert(event, "Error", "Error", "Please select file to transfer");
            }
        } catch (UnknownHostException e) {
            throwAlert(event, "Error", "Error", "Unknown host");
        } catch (IOException e) {
            throwAlert(event, "Error", "Error", "IO exception");
        }
    }

    private void throwAlert(ActionEvent event, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setTitle(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
