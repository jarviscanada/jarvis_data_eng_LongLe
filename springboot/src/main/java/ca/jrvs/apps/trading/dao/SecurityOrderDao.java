package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

  private final String TABLE_NAME = "security_order";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public SecurityOrderDao(DataSource dataSource) {
    setJdbcTemplate(new JdbcTemplate(dataSource));
    setSimpleJdbcInsert(new SimpleJdbcInsert(dataSource)
        .withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN));
  }

  @Override
  public int updateOne(SecurityOrder securityOrder) {
    String updateSQL = "UPDATE " + getTABLE_NAME() + " SET account_id=?, status=?, ticker=?, size=?, price=?, "
        + "notes=? WHERE id=?";
    return getJdbcTemplate().update(updateSQL, makeUpdateValues(securityOrder));
  }

  private Object[] makeUpdateValues(SecurityOrder securityOrder) {
    List<Object> values = new ArrayList<>();
    values.add(securityOrder.getAccountId());
    values.add(securityOrder.getStatus());
    values.add(securityOrder.getTicker());
    values.add(securityOrder.getSize());
    values.add(securityOrder.getPrice());
    values.add(securityOrder.getNotes());
    values.add(securityOrder.getId());
    return values.toArray();
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented...");
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
  Class<SecurityOrder> getEntityClass() {
    return SecurityOrder.class;
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