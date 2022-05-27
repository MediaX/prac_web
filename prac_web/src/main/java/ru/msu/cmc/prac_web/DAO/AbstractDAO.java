package ru.msu.cmc.prac_web.DAO;

import ru.msu.cmc.prac_web.classes.CommonEntity;

import java.util.List;

/* all DAO classes have same methods */
public interface AbstractDAO<T extends CommonEntity> {
    T getById(Integer id);

    T getById(T id);

    List<T> getAll();

    void update(T entity);
    void delete(T entity);
    void save(T entity);

}