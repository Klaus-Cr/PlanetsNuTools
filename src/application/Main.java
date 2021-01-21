package application;
	
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.zip.GZIPInputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.w3c.dom.Element;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			Rectangle2D screenBounds = Screen.getPrimary().getBounds();
			Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
			Scene scene = new Scene(root,400,400,Color.BEIGE);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			   .uri(URI.create("https://api.planets.nu/games/list?username=Col.%20Sandfurz"))
			   .header("accept", "application/json")
			   .build();
			BodyHandler<byte[]> asByteArray = HttpResponse.BodyHandlers.ofByteArray();
			// use the client to send the request
			HttpResponse<byte[]> response = client.send(request,asByteArray);
			GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(response.body()));
			BufferedReader bf = new BufferedReader(new InputStreamReader(gis));
	        String json = "";
	        String line;
	        
	        while ((line=bf.readLine())!=null) {
	        	json += line;
	        }
	        bf.close();
	        gis.close();
	        
	        JsonReader jsonReader = Json.createReader(new StringReader(json));
			JsonArray object = jsonReader.readArray();
			jsonReader.close();
			JsonObject game = object.getJsonObject(0);
			System.out.println(game);
			/*
			 * final WebView webView = new WebView(); final WebEngine webEngine =
			 * webView.getEngine(); Element e =
			 * webEngine.getDocument().getElementById("name"); e.setNodeValue("test");
			 */
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}