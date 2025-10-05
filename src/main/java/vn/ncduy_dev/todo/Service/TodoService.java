package vn.ncduy_dev.todo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.ncduy_dev.todo.Model.Todo;
import vn.ncduy_dev.todo.Repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void handleCreateTodo(Todo todo) {
        this.todoRepository.save(todo);
    }

    public List<Todo> getAllTodo() {
        return this.todoRepository.findAll();
    }

    public Optional<Todo> getOneTodoByID(Long id) {
        return this.todoRepository.findById(id);
    }

    public void handleUpdateTodoById(Long id) {
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo currentTodo = optionalTodo.get();
            currentTodo.setComplete(true);
            currentTodo.setName("update name");

            this.todoRepository.save(currentTodo);
        }
    }

    public void handleDeleteTodoById(Long id) {
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            this.todoRepository.save(optionalTodo.get());
        }
    }

}
