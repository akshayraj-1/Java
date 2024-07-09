import java.io.*;
import java.util.ArrayList;

public class TodoController {

    private static final String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "todos.txt";
    private final ArrayList<TodoModel> todos;

    public TodoController(){
        this.todos = new ArrayList<>();
        loadTodosFromFile();
    }

    private void loadTodosFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                TodoModel todoModel = TodoModel.deserialize(line);
                todos.add(todoModel);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTodoToFile(TodoModel todoModel) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(TodoModel.serialize(todoModel) + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveTodosToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (TodoModel todoModel : todos) {
                writer.write(TodoModel.serialize(todoModel) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<TodoModel> getTodos() {
        return todos;
    }

    public void addTodo(String title) {
        TodoModel todoModel = new TodoModel(title);
        todos.add(todoModel);
        addTodoToFile(todoModel);
    }

    public boolean toggleStatus(long id) {
        for (TodoModel todoModel : todos) {
            if (todoModel.getId() == id) {
                todoModel.setCompleted(!todoModel.getCompleted());
                saveTodosToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteTodo(long id) {
        for (TodoModel todoModel : todos) {
            if (todoModel.getId() == id) {
                todos.remove(todoModel);
                saveTodosToFile();
                return true;
            }
        }
        return false;
    }
}
