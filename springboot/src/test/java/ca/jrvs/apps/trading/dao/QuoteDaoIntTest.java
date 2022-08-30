package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote;

  @Before
  public void insertion(){
    savedQuote = new Quote();
    insertOne();
    insertTwo();
  }

  public void insertOne(){
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("SDG");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);
  }

  public void insertTwo(){
    List<Quote> quotes = new ArrayList<>();
    Quote quote1 = new Quote();
    quote1.setAskPrice(9d);
    quote1.setAskSize(9);
    quote1.setBidPrice(9.2d);
    quote1.setBidSize(9);
    quote1.setId("jrvs");
    quote1.setLastPrice(9.1d);
    quotes.add(quote1);
    Quote quote2 = new Quote();
    quote2.setAskPrice(11d);
    quote2.setAskSize(11);
    quote2.setBidPrice(11.2d);
    quote2.setBidSize(11);
    quote2.setId("jar");
    quote2.setLastPrice(11.1d);
    quotes.add(quote2);
    quoteDao.saveAll(quotes);
  }

  @Test
  public void findById(){
    String ticker = savedQuote.getId();
    Optional<Quote> quote = quoteDao.findById(ticker);
    assertEquals(quote.get().getAskPrice(), savedQuote.getAskPrice());
    assertEquals(quote.get().getAskSize(), savedQuote.getAskSize());
    assertEquals(quote.get().getBidPrice(), savedQuote.getBidPrice());
    assertEquals(quote.get().getBidSize(), savedQuote.getBidSize());
    assertEquals(quote.get().getId(), savedQuote.getId());
    assertEquals(quote.get().getLastPrice(), savedQuote.getLastPrice());
  }

  @Test
  public void findAll(){
    String ticker = savedQuote.getId();
    List<Quote> quotes = quoteDao.findAll();
    assertEquals(quotes.get(0).getAskPrice(), savedQuote.getAskPrice());
    assertEquals(quotes.get(0).getAskSize(), savedQuote.getAskSize());
    assertEquals(quotes.get(0).getBidPrice(), savedQuote.getBidPrice());
    assertEquals(quotes.get(0).getBidSize(), savedQuote.getBidSize());
    assertEquals(quotes.get(0).getId(), savedQuote.getId());
    assertEquals(quotes.get(0).getLastPrice(), savedQuote.getLastPrice());
  }

  @Test
  public void existById(){
    assertTrue(quoteDao.existsById(savedQuote.getId()));
    assertFalse(quoteDao.existsById("AAAA"));
  }

  @Test
  public void count(){
    assertEquals(3, quoteDao.count());
  }

  @Test
  public void unimplemented(){
    try{
      quoteDao.delete(savedQuote);
    } catch (UnsupportedOperationException e){
      assertTrue(true);
    }
    try{
      quoteDao.deleteAll(new ArrayList<Quote>());
    } catch (UnsupportedOperationException e){
      assertTrue(true);
    }
    try{
      quoteDao.findAllById(new ArrayList<String>());
    } catch (UnsupportedOperationException e){
      assertTrue(true);
    }
  }

  @After
  public void deletion(){
    quoteDao.deleteById(savedQuote.getId());
    assertEquals(2, quoteDao.count());
    quoteDao.deleteAll();
    assertEquals(0, quoteDao.count());
    try{
      quoteDao.existsById("AAPL");
    } catch (NullPointerException e) {
      assertTrue(true);
    } catch (Exception e){
      Assert.fail();
    }
  }
}