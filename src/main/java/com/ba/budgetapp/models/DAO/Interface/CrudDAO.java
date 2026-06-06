package com.ba.budgetapp.models.DAO.Interface;

import java.util.List;
import java.util.Optional;

/**
 * Interface générique CRUD.
 *
 * @param <T> Entité
 * @param <ID> Type de l'identifiant
 *
 * @author Etudiant
 */
public interface CrudDAO<T, ID> {

    boolean create(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(ID id);
}