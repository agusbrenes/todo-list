package com.example.todolist.todolists.service;

import com.example.todolist.exceptions.types.ItemNotFoundException;
import com.example.todolist.exceptions.types.LimitException;
import com.example.todolist.exceptions.types.TodoListNotFoundException;
import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.dto.ItemInfoDto;
import com.example.todolist.items.entity.Item;
import com.example.todolist.items.mapper.ItemMapper;
import com.example.todolist.items.service.ItemService;
import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.todolists.dto.TodoListInfoDto;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.users.entity.User;
import com.example.todolist.users.mapper.UserMapper;
import com.example.todolist.utils.response.PageResponse;
import com.example.todolist.todolists.entity.TodoList;
import com.example.todolist.todolists.mapper.TodoListMapper;
import com.example.todolist.todolists.repository.TodoListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TodoListServiceImpl implements TodoListService {

    private final TodoListRepository todoListRepository;

    private final TodoListMapper todoListMapper;

    private final ItemMapper itemMapper;

    private final ItemService itemService;

    private final UserMapper userMapper;

    private static final int MAX_TODO_LISTS_PER_USER = 5;

    private static final int MAX_ITEMS_PER_TODO_LIST = 10;

    public TodoListDto getTodoList(UserDto userDto, Integer id) throws TodoListNotFoundException {
        log.info("Getting To-Do List with id: '{}' from the database", id);
        User currentUser = userMapper.convertToEntity(userDto);
        TodoList todoList = todoListRepository
                .findByCreatorAndId(currentUser, id)
                .orElseThrow(() -> new TodoListNotFoundException(id));
        log.info("To-Do List with id: '{}' found", id);
        return todoListMapper.convertToDto(todoList);
    }

    public PageResponse getAllTodoLists(UserDto userDto, Pageable paging) {
        log.info("Getting all the To-Do Lists from the database");
        User currentUser = userMapper.convertToEntity(userDto);
        Page<TodoList> orderedTodoLists = todoListRepository
                .findAllByCreatorOrderByCreatedAtAsc(currentUser, paging);
        List<TodoListDto> results = todoListMapper
                .convertNEntitiesToDto(orderedTodoLists.getContent());
        return PageResponse
                .builder()
                .page(paging.getPageNumber())
                .size(results.size())
                .totalPages(orderedTodoLists.getTotalPages())
                .totalResults(orderedTodoLists.getTotalElements())
                .results(results)
                .build();
    }

    public TodoListDto createTodoList(UserDto userDto, TodoListInfoDto todoListInfoDto)
            throws LimitException {
        log.info("Creating a new To-Do List with name: '{}'", todoListInfoDto.getTitle());
        User currentUser = userMapper.convertToEntity(userDto);
        if (todoListRepository.countAllByCreator(currentUser) >= MAX_TODO_LISTS_PER_USER) {
            throw new LimitException("To-Do Lists", "User", MAX_TODO_LISTS_PER_USER);
        }
        TodoList todoList = todoListMapper.convertToEntity(todoListInfoDto);
        todoList.setCreator(currentUser);
        todoList.setCreatedAt(LocalDateTime.now());
        todoList.setUpdatedAt(LocalDateTime.now());
        return todoListMapper.convertToDto(todoListRepository.save(todoList));
    }

    public TodoListDto updateTodoList(
            UserDto userDto,
            Integer id,
            TodoListInfoDto todoListInfoDto) throws TodoListNotFoundException {
        log.info("Updating To-Do List with id: '{}'", id);
        User currentUser = userMapper.convertToEntity(userDto);
        TodoList todoList = todoListRepository
                .findByCreatorAndId(currentUser, id)
                .orElseThrow(() -> new TodoListNotFoundException(id));
        todoList.setTitle(todoListInfoDto.getTitle());
        todoList.setUpdatedAt(LocalDateTime.now());
        return todoListMapper.convertToDto(todoListRepository.save(todoList));
    }

    public void deleteTodoList(UserDto userDto, Integer id) throws TodoListNotFoundException {
        User currentUser = userMapper.convertToEntity(userDto);
        TodoList todoList = todoListRepository
                .findByCreatorAndId(currentUser, id)
                .orElseThrow(() -> new TodoListNotFoundException(id));
        log.info("Deleting To-Do List with id: '{}'", id);
        todoListRepository.delete(todoList);
    }

    /* To-Do List Items */

    public List<ItemDto> getTodoListItems(UserDto userDto, Integer id)
            throws TodoListNotFoundException {
        log.info("Getting All Items from To-Do List with id: '{}' from the database", id);
        User currentUser = userMapper.convertToEntity(userDto);
        TodoList todoList = todoListRepository
                .findByCreatorAndId(currentUser, id)
                .orElseThrow(() -> new TodoListNotFoundException(id));
        log.info("To-Do List with id: '{}' found. Retrieving all Items from To-Do List", id);
        return itemMapper.convertNEntitiesToDto(todoList.getItems().stream().toList());
    }

    public ItemDto addTodoListItem(
            UserDto userDto,
            Integer id,
            ItemInfoDto itemInfoDto) throws TodoListNotFoundException, LimitException {
        log.info("Adding Item to To-Do List with id: '{}'", id);
        User currentUser = userMapper.convertToEntity(userDto);
        TodoList todoList = todoListRepository
                .findByCreatorAndId(currentUser, id)
                .orElseThrow(() -> new TodoListNotFoundException(id));
        if (todoList.getItems().size() >= MAX_ITEMS_PER_TODO_LIST) {
            throw new LimitException("Items", "To-Do List", MAX_ITEMS_PER_TODO_LIST);
        }
        log.info("To-Do List with id: '{}' found. Adding Item with description: '{}'",
                id,
                itemInfoDto.getDescription());
        Item item = itemMapper.convertToEntity(itemService.createItem(itemInfoDto));
        item.setTodoList(todoList);
        todoList.addItem(item);
        todoList.setUpdatedAt(LocalDateTime.now());
        todoListRepository.save(todoList);
        return itemMapper.convertToDto(item);
    }

    public ItemDto updateTodoListItem(
            UserDto userDto,
            Integer todoListId,
            Integer itemId,
            ItemInfoDto itemInfoDto) throws TodoListNotFoundException, ItemNotFoundException {
        log.info("Updating Item with id: '{}', from To-Do List with id: '{}'", itemId, todoListId);
        User currentUser = userMapper.convertToEntity(userDto);
        TodoList todoList = todoListRepository
                .findByCreatorAndId(currentUser, todoListId)
                .orElseThrow(() -> new TodoListNotFoundException(todoListId));
        log.info("To-Do List with id: '{}' found", todoListId);
        return itemService.updateItem(todoList, itemId, itemInfoDto);
    }

    public void deleteTodoListItem(
            UserDto userDto,
            Integer todoListId,
            Integer itemId) throws TodoListNotFoundException, ItemNotFoundException {
        User currentUser = userMapper.convertToEntity(userDto);
        TodoList todoList = todoListRepository
                .findByCreatorAndId(currentUser, todoListId)
                .orElseThrow(() -> new TodoListNotFoundException(todoListId));
        log.info("Deleting To-Do List with id: '{}'", todoListId);
        itemService.deleteItem(todoList, itemId);
    }

}
