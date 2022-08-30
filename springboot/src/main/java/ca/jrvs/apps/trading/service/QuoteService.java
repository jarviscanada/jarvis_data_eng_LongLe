package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao){
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  /**
   * Find an IexQuote
   *
   * @param ticker id
   * @return IexQuote object
   * @throws IllegalArgumentException if tikcer is invalid
   */
  public IexQuote findIexQuoteByTicker(String ticker){
    return marketDataDao.findById(ticker)
        .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid."));
  }

  /**
   * Update quote table against IEX source
   *  - get all quotes from the db
   *  - foreach ticker get IexQuote
   *  - convert iexQuote to quote entity
   *  - persist quote to db
   *
   * //@throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public void updateMarketData(){
    List<Quote> quotes = findAllQuotes();
    IexQuote iexQuote;
    Quote quoteTemp;
    for (Quote quote : quotes){
      String ticker = quote.getTicker();
      iexQuote = marketDataDao.findById(ticker).get();
      quoteTemp = buildQuoteFromIexQuote(iexQuote);
      quoteDao.save(quoteTemp);
    }
  }

  /**
   * Validate (against IEX) and save given tickers to quote table
   *
   * - Get iexQuotes
   * - convert each iexQuote to Quote entity
   * - persist the quote to db
   *
   * @param tickers
   * @throws IllegalArgumentException if ticker is not found
   */
  public List<Quote> saveQuotes(List<String> tickers){
    List<Quote> quotes = new ArrayList<>();
    for (String ticker : tickers){
      quotes.add(saveQuote(ticker));
    }
    return quotes;
  }

  /**
   * helper for saveQuotes
   * @param ticker
   * @return Quote found
   */
  public Quote saveQuote(String ticker){
    IexQuote iexQuote = new IexQuote();
    iexQuote = marketDataDao.findById(ticker).get();
    Quote quote = buildQuoteFromIexQuote(iexQuote);
    return saveQuote(quote);
  }

  /**
   * Update a quote to quote table without validation
   * @param quote
   */
  public Quote saveQuote(Quote quote){
    return quoteDao.save(quote);
  }

  /**
   * find all quotes from the quote table
   * @return list of quotes
   */
  public List<Quote> findAllQuotes(){
    return quoteDao.findAll();
  }

  /**
   * Helper method: Map a IexQuote to a Quote entity
   * Note: `iexQuote.getLastestPrice() == null` if the stock market is closed.
   * Make sure set a default value for number fields.
   */
  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
    Quote quote = new Quote();
    quote.setTicker((String) iexQuote.getSymbol());
    quote.setLastPrice(iexQuote.getLatestPrice());
    quote.setAskPrice(iexQuote.getIexAskPrice());
    quote.setAskSize(iexQuote.getIexAskSize());
    quote.setBidPrice(iexQuote.getIexBidPrice());
    quote.setBidSize(iexQuote.getIexBidSize());
    return quote;
  }
}