import java.util.ArrayList;

public class Main {

    public static final int MAX_TODO_LENGTH = 30;
    public static TodoController todoController = new TodoController();
    public static void main(String[] args) {

        System.out.println("1 -> Add Todo \n2 -> Toggle Status \n3 -> Delete Todo \n4 -> Show Todos \n5 -> Exit");
        while (true) {
            System.out.print("Enter your choice (1-5): ");
            String choice = System.console().readLine();
            switch (choice) {
                case "1":
                    addTodo();
                    break;
                case "2":
                    toggleStatus();
                    break;
                case "3":
                    deleteTodo();
                    break;
                case "4":
                    showTodos();
                    break;
                case "5":
                    System.exit(0);
                    break;
                default:
            }
        }
    }

    public static void addTodo() {
        System.out.print("Enter Todo (Max " + MAX_TODO_LENGTH + " characters): ");
        String todo = System.console().readLine();
        if (todo.length() > MAX_TODO_LENGTH) {
            System.out.println("Todo cannot be more than 60 characters");
            return;
        }
        todoController.addTodo(todo);
        System.out.println("Todo added successfully");
    }

    public static void toggleStatus() {
        System.out.print("Enter Todo ID to toggle: ");
        long id = Long.parseLong(System.console().readLine());
        if (todoController.toggleStatus(id)) {
            System.out.println("Todo toggled successfully");
        } else {
            System.out.println("Todo not found");
        }
    }

    public static void deleteTodo() {
        System.out.print("Enter Todo ID to delete: ");
        long id = Long.parseLong(System.console().readLine());
        if (todoController.deleteTodo(id)) {
            System.out.println("Todo deleted successfully");
        } else {
            System.out.println("Todo not found");
        }
    }

    public static void showTodos() {
        int col1Width = 10;
        int col2Width = MAX_TODO_LENGTH + 5;
        int col3Width = 10;
        int totalWidth = col1Width + col2Width + col3Width;
        // Separator
        System.out.printf("%-" + totalWidth + "s%n", "=".repeat(totalWidth));
        // Header
        System.out.printf("%-" + col1Width + "s %-" + col2Width + "s %-" + col3Width + "s%n", "ID", "Description", "Status");
        // Separator
        System.out.printf("%-" + totalWidth + "s%n", "-".repeat(totalWidth));
        // Todos
        ArrayList<TodoModel> todos = todoController.getTodos();
        if (todos.isEmpty()) {
            System.out.println("No todos found\nStart adding todos using '1'");
            System.out.printf("%-" + totalWidth + "s%n", "=".repeat(totalWidth));
            return;
        }
        for (TodoModel todo : todos) {
            System.out.printf("%-" + col1Width + "s %-" + col2Width + "s %-" + col3Width + "s%n", todo.getId(), todo.getTitle(), todo.getCompleted() ? "Done" : "Pending");

        }
        // Separator
        System.out.printf("%-" + totalWidth + "s%n", "=".repeat(totalWidth));
    }
}