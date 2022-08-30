package ca.jrvs.apps.trading.model.domain;

public class Position implements Entity<Integer>{

  private Integer accountId;
  private String ticker;
  private Integer position;

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  @Override
  public Integer getId() {
    return accountId;
  }

  @Override
  public void setId(Integer account_id) {
    this.accountId = account_id;
  }
}