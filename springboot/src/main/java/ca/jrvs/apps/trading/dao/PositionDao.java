package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao extends JdbcCrudDao<Position>{

  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN = "account_id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public PositionDao(DataSource dataSource){
    setJdbcTemplate(new JdbcTemplate(dataSource));
    setSimpleJdbcInsert(new SimpleJdbcInsert(dataSource)
        .withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN));
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN;
  }

  @Override
  Class<Position> getEntityClass() {
    return Position.class;
  }

  @Override
  public <S extends Position> S save(S entity) {
    throw new UnsupportedOperationException("This is a read-Only view, you cannot modify it.");
  }

  @Override
  public <S extends Position> Iterable<S> saveAll(Iterable<S> entities) {
    throw new UnsupportedOperationException("This is a read-Only view, you cannot modify it.");
  }

  @Override
  public void deleteById(Integer id) {
    throw new UnsupportedOperationException("This is a read-Only view, you cannot modify it.");
  }

  @Override
  public void deleteAll(Iterable<? extends Position> entities) {
    throw new UnsupportedOperationException("This is a read-Only view, you cannot modify it.");
  }

  @Override
  public int updateOne(Position entity) {
    throw new UnsupportedOperationException("This is a read-Only view, you cannot modify it.");
  }

  @Override
  public void delete(Position entity) {
    throw new UnsupportedOperationException("This is a read-Only view, you cannot modify it.");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("This is a read-Only view, you cannot modify it.");
  }

  public static Logger getLogger() {
    return logger;
  }

  public String getTABLE_NAME() {
    return TABLE_NAME;
  }

  public String getID_COLUMN() {
    return ID_COLUMN;
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  public void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
    this.simpleJdbcInsert = simpleJdbcInsert;
  }

}