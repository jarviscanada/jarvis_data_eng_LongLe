package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private PositionDao positionDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
      SecurityOrderDao securityOrderDao, PositionDao positionDao){
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.positionDao = positionDao;
  }

  /**
   * Create a new trader and initialize a new account with 0 amount
   *
   * @param trader that cannot be null except id.
   * @return traderAccountView
   * @throws IllegalArgumentException if a trader property is null except id property
   */
  public TraderAccountView createTraderAndAccount(Trader trader) throws IllegalArgumentException{
    checkInputTrader(trader);
    trader.setId(traderDao.save(trader).getId());
    Account account = new Account();
    account.setTrader_id(trader.getId());
    account.setAmount(0d);
    account.setId(accountDao.save(account).getId());
    TraderAccountView traderAccountView = new TraderAccountView();
    traderAccountView.setTrader(trader);
    traderAccountView.setAccount(account);
    return traderAccountView;
  }

  private void checkInputTrader(Trader trader){
    if (trader == null){ throw new IllegalArgumentException("Trader is null.");}
    if (trader.getCountry() == null){ throw new IllegalArgumentException("Trader's Country is null.");}
    if (trader.getDob() == null){ throw new IllegalArgumentException("Trader's Date of birth is null.");}
    if (trader.getEmail() == null){ throw new IllegalArgumentException("Trader's Email is null.");}
    if (trader.getFirstName() == null){ throw new IllegalArgumentException("Trader's First Name is null.");}
    if (trader.getLastName() == null){ throw new IllegalArgumentException("Trader's Last Name is null.");}
    if (trader.getId() != null){ throw new IllegalArgumentException("Trader's ID should be null.");}
  }

  /**
   * Delete a trader and all SecurityOrder and account associated with this trader
   * Note: A trader can be deleted iff there is no open position and cash balance is 0
   *
   * @param traderId
   * @throws IllegalArgumentException is traderId is null or not found or unable to delete
   */
  public void deleteTraderById(Integer traderId) throws IllegalArgumentException{
    Account account = accountDao.findRowByColumnId(traderId, "trader_id").get(0);
    List<SecurityOrder> securityOrders = securityOrderDao.findRowByColumnId(account.getId(), "account_id");
    List<Position> positions = positionDao.findRowByColumnId(account.getId(), "account_id");
    checkBalance(account);
    if (positions.size() != 0){
      checkPositions(positions);
    }
    if (securityOrders.size() != 0){
      for (SecurityOrder securityOrder : securityOrders) {
        securityOrderDao.deleteById(securityOrder.getId());
      }
    }
    accountDao.deleteById(account.getId());
    traderDao.deleteById(traderId);
  }

  private void checkBalance(Account account){
    if (account.getAmount() != 0) {
      throw new IllegalArgumentException("Deletion failure: The balance is not 0.");
    }
  }

  private void checkPositions(List<Position> positions){
    for (Position position : positions){
      if (position.getPosition() != 0){
        throw new IllegalArgumentException("Deletion failure: The position for " +
            position.getTicker() + " is not 0.");
      }
    }
  }

  public Account deposit(Integer traderId, Double fund){
    checkTraderId(traderId);
    checkFundFormat(fund);
    Account account = accountDao.findRowByColumnId(traderId, "trader_id").get(0);
    return accountDao.updateAmountById(account, fund);
  }

  /**
   * Withdraw fund from an account identified by a trader ID
   *
   * @param traderId
   * @param fund
   * @return updated account
   * @throws IllegalArgumentException if traderID is null or not found, or fund>balance or fund<=0
   */
  public Account withdraw(Integer traderId, Double fund){
    checkTraderId(traderId);
    checkFundFormat(fund);
    Account account = accountDao.findRowByColumnId(traderId, "trader_id").get(0);
    return accountDao.updateAmountById(account, fund*-1d);
  }

  private void checkTraderId(Integer traderId){
    Trader trader = traderDao.findById(traderId).get();
    if (trader == null){
      throw new IllegalArgumentException("Trader with trader ID " + traderId + " is not found.");
    }
  }

  private void checkFundFormat(Double fund){
    if (fund <= 0){
      throw new IllegalArgumentException("Fund to deposit/withdraw should not be less or equal to 0.");
    }
  }

  public TraderDao getTraderDao() {
    return traderDao;
  }

  public void setTraderDao(TraderDao traderDao) {
    this.traderDao = traderDao;
  }

  public AccountDao getAccountDao() {
    return accountDao;
  }

  public void setAccountDao(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  public SecurityOrderDao getSecurityOrderDao() {
    return securityOrderDao;
  }

  public void setSecurityOrderDao(SecurityOrderDao securityOrderDao) {
    this.securityOrderDao = securityOrderDao;
  }

  public PositionDao getPositionDao() {
    return positionDao;
  }

  public void setPositionDao(PositionDao positionDao) {
    this.positionDao = positionDao;
  }
}