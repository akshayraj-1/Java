import java.io.*;
import java.util.Base64;
import java.util.Random;

public class TodoModel implements Serializable {

    private final long id;
    private final String title;
    private boolean completed;

    public TodoModel(String title) {
        this.id = System.currentTimeMillis() % 1000 + new Random().nextInt(100);
        this.title = title;
        this.completed = false;
    }

    public TodoModel(long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public static String serialize(TodoModel todoModel) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(todoModel);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TodoModel deserialize(String serializedTodo) {
        try {
            byte[] data = Base64.getDecoder().decode(serializedTodo);
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            TodoModel todoModel = (TodoModel) ois.readObject();
            ois.close();
            return todoModel;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
