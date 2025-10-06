package vn.ncduy_dev.todo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.ncduy_dev.todo.Model.Todo;
import vn.ncduy_dev.todo.Service.TodoService;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> create(@RequestBody Todo myTodo) {
        this.todoService.handleCreateTodo(myTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(myTodo);
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

    @PutMapping("/todo/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id, @RequestBody Todo updateTodo) {
        this.todoService.handleUpdateTodoById(id, updateTodo);
        return ResponseEntity.ok().body("update ok");
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        this.todoService.handleDeleteTodoById(id);
        return ResponseEntity.ok().body("delete id = " + id);
    }

}
