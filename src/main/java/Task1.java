import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Task1 {
    public static final HttpClient CLIENT = java.net.http.HttpClient.newHttpClient();
    public static final Gson GSON = new Gson();
    public static List<User> users = new LinkedList<>();

    public static List<User> createUserList() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("D://goit//users.json"))) {
            JsonArray array = GSON.fromJson(bufferedReader, JsonArray.class);
            for (JsonElement element : array) {
                User user = GSON.fromJson(element, User.class);
                users.add(user);
            }
            return users;
        } catch (IOException e) {
            System.out.println("Error");
            return null;
        }

    }
    public static User newUser(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(createUserList().toString()))
                .build();
        //HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
        User user = users.get(0);
        return user;
    }
    public static User getUser(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
        User user = users.get(0);
        return user;
    }
    public static int deleteUser(User user, URI uri) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
            users.remove(user);
            return response.statusCode();
    }
    public static List<User> allUsers(URI uri) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
            return users;
    }
    public static List<User> getUser(URI uri, int id) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
            return  users.stream()
                    .filter(user-> id == user.getId())
                    .collect(toList());
    }

    public static List<User> getUser(URI uri, String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
        return  users.stream()
                .filter(user-> username.equals(user.getUsername()))
                .collect(toList());
    }
}
