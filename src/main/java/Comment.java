import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    /*
    {
    "postId": 10,
    "id": 46,
    "name": "name",
    "email": "Jeremy.Harann@waino.me",
    "body": "body"
  }
     */
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;


    public int getId() {
        return id;
    }
    public static List<Comment> readComments() {
        Gson GSON = new Gson();
        List<Comment> comments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D://goit//comments.json"))) {
            JsonArray array = GSON.fromJson(br, JsonArray.class);
            for (JsonElement element : array) {
                Comment comment = GSON.fromJson(element, Comment.class);
                comments.add(comment);
            }
            return comments;
        } catch (IOException e) {
            System.out.println("Error");
            return null;
        }
    }
    public static void makeCommentFile() throws IOException, InterruptedException, URISyntaxException {
        String uri = "https://jsonplaceholder.typicode.com/posts/10/comments";
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(uri))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String text = httpResponse.body();
        try(FileOutputStream fileOutputStream = new FileOutputStream("D://goit/comments.json")) {
            byte[] buffer = text.getBytes();
            fileOutputStream.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void findLastPostComments(Post post) throws IOException {
        // user-X-post-Y-comments.json
        // Х - id користувача
        // Y - номер посту
        List<Comment> comments = readComments();
        List<Comment> fileComments = new ArrayList<>();
        int postId = post.getId();
        int userId = post.getUserId();
        String nameFile = "user-" + userId + "-post-" + postId + "-comments.json";

        File file = new File(nameFile);
        if (!file.exists())
            file.createNewFile();

        try (FileWriter writer = new FileWriter(file)) {
            Gson gs = new GsonBuilder().setPrettyPrinting()
                    .create();
            for (Comment comment : comments) {
                //System.out.println(comment);
                if (postId == comment.postId)
                    fileComments.add(comment);
            }
            String stringGS = gs.toJson(fileComments);
            writer.write(stringGS);
            writer.flush();
            System.out.println("File " + nameFile + "  was created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Comment{" +
                "postId=" + postId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
