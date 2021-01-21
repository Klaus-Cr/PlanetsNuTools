package application;


	import java.io.BufferedReader;
	import java.io.ByteArrayInputStream;
	import java.io.InputStreamReader;
	import java.io.StringReader;
	import java.net.URI;
	import java.net.http.HttpClient;
	import java.net.http.HttpRequest;
	import java.net.http.HttpResponse;
	import java.net.http.HttpResponse.BodyHandler;
	import java.util.zip.GZIPInputStream;

	import javax.json.Json;
	import javax.json.JsonArray;
	import javax.json.JsonObject;
	import javax.json.JsonReader;

	import javafx.application.Application;
    import javafx.stage.Stage;


	public class Main extends Application {
		@Override
		public void start(Stage primaryStage) {
			try {

				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder()
				   .uri(URI.create("https://api.planets.nu/games/list?username=deinName"))
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
				
			} catch(Exception e) {
				e.printStackTrace();
				
			}
		}
		
		public static void main(String[] args) {
			launch(args);
		}
	}
