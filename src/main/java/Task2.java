
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Task2 {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Post.makePostFile();
        Comment.makeCommentFile();
        List<Post> posts = Post.readPosts();
        List<Comment> comments = Comment.readComments();
        //System.out.println(posts);
        //System.out.println(comments);
        Post post = Post.findLastPost(posts);
        Comment.findLastPostComments(post);

    }



}
