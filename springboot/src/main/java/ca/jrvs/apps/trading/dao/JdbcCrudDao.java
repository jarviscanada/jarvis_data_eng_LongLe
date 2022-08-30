package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

  abstract public JdbcTemplate getJdbcTemplate();

  abstract public SimpleJdbcInsert getSimpleJdbcInsert();

  abstract public String getTableName();

  abstract public String getIdColumnName();

  abstract Class<T> getEntityClass();

  @Override
  public <S extends T> S save(S entity){
    if (existsById(entity.getId())){
      int updateRowNum = updateOne(entity);
      if (updateRowNum != 1){
        throw new DataRetrievalFailureException("Unable to update quote.");
      }
    } else {
      addOne(entity);
    }
    return entity;
  }

  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    List<S> out = new ArrayList<>();
    for (S q : entities) {
      out.add((S) save(q));
    }
    return out;
  }

  private <S extends T> void addOne(S entity){
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
    Object newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setId((Integer) newId);
  }

  abstract public int updateOne(T entity);

  @Override
  public Optional<T> findById(Integer id){
    Optional<T> entity = Optional.empty();
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    try {
      entity = Optional.ofNullable((T) getJdbcTemplate().
          queryForObject(selectSql,
              BeanPropertyRowMapper.newInstance(getEntityClass()), id));
    } catch (IncorrectResultSizeDataAccessException e ){
      logger.debug("Can't find trader id: " + id, e);
    }
    return entity;
  }

  @Override
  public boolean existsById(Integer id){
    if (id == null){return false;}
    long count;
    String countSql = "SELECT COUNT(*) FROM " + getTableName() +
        " WHERE "+ getIdColumnName() + "=?";
    try {
      count = getJdbcTemplate().queryForObject(countSql,
          Long.class, id);
    } catch (NullPointerException e){
      count = 0;
    }
    return count != 0;
  }

  @Override
  public List<T> findAll(){
    String selectSql = "SELECT * FROM " + getTableName();
    return getJdbcTemplate()
        .query(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()));
  }

  @Override
  public List<T> findAllById(Iterable<Integer> ids){
    List<T> entities = new ArrayList<>();
    for (Integer id : ids){
      entities.add(findById(id).get());
    }
    return entities;
  }

  public List<T> findRowByColumnId(Integer id, String column){
    List<T> entityOut = new ArrayList<>();
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + column + "=?";
    try {
      entityOut = getJdbcTemplate().
          query(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()), id);
    } catch (IncorrectResultSizeDataAccessException e ){
      logger.debug("Can't find column with id: " + id, e);
    }
    return entityOut;
  }


  @Override
  public void delete(T entity) {
    deleteById(entity.getId());
  }

  @Override
  public void deleteById(Integer id){
    String deleteSql = "DELETE FROM " + getTableName() + " WHERE "+ getIdColumnName() + "=?";
    getJdbcTemplate().update(deleteSql, id);
  }

  @Override
  public void deleteAll(Iterable<? extends T> entities) {
    for (T entity : entities){
      delete(entity);
    }
  }

  @Override
  public long count(){
    long count;
    String countSql = "SELECT COUNT(*) FROM " + getTableName();
    try {
      count = getJdbcTemplate().queryForObject(countSql, Long.class);
    } catch (NullPointerException e){
      count = 0;
    }
    return count;
  }

  @Override
  public void deleteAll(){
    String deleteSql = "DELETE FROM " + getTableName();
    getJdbcTemplate().update(deleteSql);
  }

}