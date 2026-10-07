package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public List<Todo> findAll() {
		return todoRepository.findAll();
	}

	public Todo findById(Long id) {
		return todoRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Todo not found: " + id));
	}

	@Transactional
	public Todo create(Todo todo) {
		todo.setId(null);
		return todoRepository.save(todo);
	}

	@Transactional
	public Todo update(Long id, Todo request) {
		Todo todo = findById(id);
		todo.setTitle(request.getTitle());
		todo.setContent(request.getContent());
		todo.setCompleted(request.isCompleted());
		return todo;
	}

	@Transactional
	public void delete(Long id) {
		if (!todoRepository.existsById(id)) {
			throw new NoSuchElementException("Todo not found: " + id);
		}
		todoRepository.deleteById(id);
	}
}
