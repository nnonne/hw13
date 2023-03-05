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
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Post {
    /*
    {
    "userId": 1,
    "id": 1,
    "title": "title",
    "body": "body"
  }
     */
    private int userId;
    private int id;
    private String title;
    private String body;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }
    public static void makePostFile() throws IOException, InterruptedException, URISyntaxException {
        String uri = "https://jsonplaceholder.typicode.com/users/1/posts";
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(uri))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String text = httpResponse.body();
        try(FileOutputStream fileOutputStream = new FileOutputStream("D://goit/posts.json")) {
            byte[] buffer = text.getBytes();
            fileOutputStream.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static List<Post> readPosts() {
        Gson GSON = new Gson();
        List<Post> posts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D://goit/posts.json"))) {
            JsonArray array = GSON.fromJson(br, JsonArray.class);
            for (JsonElement element : array) {
                Post post = GSON.fromJson(element, Post.class);
                posts.add(post);
            }
            return posts;
        } catch (IOException e) {
            System.out.println("Error");
            return null;
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
    public static Post findLastPost(List<Post> posts){
        //int maxId = posts.get(0).id;
        Post res = posts.stream()
                .max(Comparator.comparing(post -> post.id))
                //.filter(post-> username.equals(user.getUsername()))
                .get();
                //.collect(toList());
        return res;
    }
}
