package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource){
    setJdbcTemplate(new JdbcTemplate(dataSource));
    setSimpleJdbcInsert(new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME));
  }

  @Override
  public Quote save(Quote quote) {
    if (existsById(quote.getTicker())){
      int updateRowNum = updateOne(quote);
      if (updateRowNum != 1){
        throw new DataRetrievalFailureException("Unable to update quote.");
      }
    } else {
      addOne(quote);
    }
    return quote;
  }

  private void addOne(Quote quote){
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int rowNo = getSimpleJdbcInsert().execute(parameterSource);
    if (rowNo != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, rowNo);
    }
  }

  private int updateOne(Quote quote){
    String updateSQL = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, "
        + "ask_price=?, ask_size=? WHERE ticker=?";
    return getJdbcTemplate().update(updateSQL, makeUpdateValues(quote));
  }

  private Object[] makeUpdateValues(Quote quote){
    List<Object> values = new ArrayList<>();
    values.add(quote.getLastPrice());
    values.add(quote.getBidPrice());
    values.add(quote.getBidSize());
    values.add(quote.getAskPrice());
    values.add(quote.getAskSize());
    values.add(quote.getTicker());
    return values.toArray();
  }

  @Override
  public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
    List<S> quotesOut = new ArrayList<>();
    for (Quote q : quotes) {
      quotesOut.add((S) save(q));
    }
    return quotesOut;
  }

  @Override
  public Optional<Quote> findById(String ticker) {
    Optional<Quote> quote = null;
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE ticker=? for UPDATE";
    try {
      quote = Optional.ofNullable(
          getJdbcTemplate().queryForObject(selectSql,
              BeanPropertyRowMapper.newInstance(Quote.class), ticker)
      );
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Can't find trader id: " + ticker, e);
    }
    if (quote == null) {
      throw new ResourceNotFoundException("Resource not found");
    }
    return quote;
  }

  @Override
  public boolean existsById(String ticker) {
    long count;
    String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME +
        " WHERE ticker='" + ticker.toUpperCase() + "'";
    try {
      count = getJdbcTemplate().queryForObject(countSql, Long.class);
    } catch (NullPointerException e){
      count = 0;
    }
    return count != 0;
  }

  @Override
  public List<Quote> findAll() throws DataAccessException {
    String selectSql = "SELECT * FROM " + TABLE_NAME;
    return getJdbcTemplate()
        .query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public long count() {
    long count;
    String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME;
    try {
      count = getJdbcTemplate().queryForObject(countSql, Long.class);
    } catch (NullPointerException e){
      count = 0;
    }
    return count;
  }

  @Override
  public void deleteById(String ticker) {
    String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE ticker=?";
    getJdbcTemplate().update(deleteSql, ticker);
  }

  @Override
  public void delete(Quote quote) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public void deleteAll() {
    String deleteSql = "DELETE FROM " + TABLE_NAME;
    getJdbcTemplate().update(deleteSql);
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  public void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
    this.simpleJdbcInsert = simpleJdbcInsert;
  }
}