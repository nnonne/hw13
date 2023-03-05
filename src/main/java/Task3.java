import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Task3 {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ToDo.makeFile();
        int id = 1;
        List<ToDo> todos = ToDo.readToDos();
        List<ToDo> tasks = ToDo.openedToDos(todos,id );
        System.out.println(tasks);
    }
}
