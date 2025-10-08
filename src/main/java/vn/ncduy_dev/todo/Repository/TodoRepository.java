package vn.ncduy_dev.todo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ncduy_dev.todo.Entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
