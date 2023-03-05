import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToDo {
    /*
    {
    "userId": 1,
    "id": 1,
    "title": "delectus aut autem",
    "completed": false
  }
     */
    private int userId;
    private int id;
    private String title;
    private boolean completed;
    public static List<ToDo> readToDos(){
            Gson GSON = new Gson();
            List<ToDo> todos = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("D://goit//todos.json"))) {
                JsonArray array = GSON.fromJson(br, JsonArray.class);
                for (JsonElement element : array) {
                    ToDo todo = GSON.fromJson(element, ToDo.class);
                    todos.add(todo);
                }
                return todos;
            } catch (IOException e) {
                System.out.println("Error");
                return null;
            }
    }
    public static void makeFile()throws IOException, InterruptedException, URISyntaxException {
        String uri = "https://jsonplaceholder.typicode.com/users/1/todos";
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(uri))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String text = httpResponse.body();
        try(FileOutputStream fileOutputStream = new FileOutputStream("D://goit/todos.json")) {
            byte[] buffer = text.getBytes();
            fileOutputStream.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static List<ToDo> openedToDos(List<ToDo> todos) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = java.net.http.HttpClient.newHttpClient();
        String uri = "https://jsonplaceholder.typicode.com/users/1/todos";
        HttpRequest request =  HttpRequest.newBuilder()
                .uri(new URI(uri))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println("Status Code: " + response.statusCode());

        return  todos.stream()
                .filter(user->user.userId == user.userId & !user.completed)
                .collect(Collectors.toList());

    }

    @Override
    public String toString() {
        return "ToDo{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
