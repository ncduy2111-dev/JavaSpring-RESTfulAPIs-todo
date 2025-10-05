package vn.ncduy_dev.todo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vn.ncduy_dev.todo.Model.Todo;
import vn.ncduy_dev.todo.Service.TodoService;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/create")
    public String create() {
        Todo myTodo = new Todo("Learn Spring Boot", false);
        this.todoService.handleCreateTodo(myTodo);
        return "create ok";
    }

    @GetMapping("/all-todos")
    public ResponseEntity<List<Todo>> allTodo() {
        List<Todo> todos = this.todoService.getAllTodo();

        return ResponseEntity.ok().body(todos);
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Optional<Todo>> getToDoById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.todoService.getOneTodoByID(id));
    }

    @GetMapping("/update")
    public String updateTodo() {
        Long id = 2L;
        this.todoService.handleUpdateTodoById(id);
        return "update ok";
    }

    @GetMapping("/delete")
    public String deleteTodo() {
        Long id = 2L;
        this.todoService.handleDeleteTodoById(id);
        return "update ok";
    }

}
