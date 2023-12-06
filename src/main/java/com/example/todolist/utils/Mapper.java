package com.example.todolist.utils;

import java.util.List;

public abstract class Mapper<E, D> {

    public abstract D convertToDto(E entity);

    public abstract E convertToEntity(D dto);

    public List<D> convertNEntitiesToDto(List<E> entitiesList) {
        return entitiesList.stream().map(this::convertToDto).toList();
    }

    public List<E> convertNDtoToEntities(List<D> listOfDto) {
        return listOfDto.stream().map(this::convertToEntity).toList();
    }

}

