package ca.jrvs.apps.trading.model.domain;

import io.swagger.models.auth.In;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "symbol",
    "companyName",
    "calculationPrice",
    "open",
    "openTime",
    "close",
    "closeTime",
    "high",
    "low",
    "latestPrice",
    "latestSource",
    "latestTime",
    "latestUpdate",
    "latestVolume",
    "volume",
    "iexRealtimePrice",
    "iexRealtimeSize",
    "iexLastUpdated",
    "delayedPrice",
    "delayedPriceTime",
    "extendedPrice",
    "extendedChange",
    "extendedChangePercent",
    "extendedPriceTime",
    "previousClose",
    "previousVolume",
    "change",
    "changePercent",
    "iexMarketPercent",
    "iexVolume",
    "avgTotalVolume",
    "iexBidPrice",
    "iexBidSize",
    "iexAskPrice",
    "iexAskSize",
    "marketCap",
    "week52High",
    "week52Low",
    "ytdChange",
    "peRatio",
    "lastTradeTime",
    "isUSMarketOpen"
})
public class IexQuote {

  @JsonProperty("symbol")
  private Object symbol;
  @JsonProperty("companyName")
  private Object companyName;
  @JsonProperty("calculationPrice")
  private Object calculationPrice;
  @JsonProperty("open")
  private Object open;
  @JsonProperty("openTime")
  private Object openTime;
  @JsonProperty("close")
  private Object close;
  @JsonProperty("closeTime")
  private Object closeTime;
  @JsonProperty("high")
  private Object high;
  @JsonProperty("low")
  private Object low;
  @JsonProperty("latestPrice")
  private Double latestPrice;
  @JsonProperty("latestSource")
  private Object latestSource;
  @JsonProperty("latestTime")
  private Object latestTime;
  @JsonProperty("latestUpdate")
  private Object latestUpdate;
  @JsonProperty("latestVolume")
  private Object latestVolume;
  @JsonProperty("volume")
  private Object volume;
  @JsonProperty("iexRealtimePrice")
  private Object iexRealtimePrice;
  @JsonProperty("iexRealtimeSize")
  private Object iexRealtimeSize;
  @JsonProperty("iexLastUpdated")
  private Object iexLastUpdated;
  @JsonProperty("delayedPrice")
  private Object delayedPrice;
  @JsonProperty("delayedPriceTime")
  private Object delayedPriceTime;
  @JsonProperty("extendedPrice")
  private Object extendedPrice;
  @JsonProperty("extendedChange")
  private Object extendedChange;
  @JsonProperty("extendedChangePercent")
  private Object extendedChangePercent;
  @JsonProperty("extendedPriceTime")
  private Object extendedPriceTime;
  @JsonProperty("previousClose")
  private Object previousClose;
  @JsonProperty("previousVolume")
  private Object previousVolume;
  @JsonProperty("change")
  private Object change;
  @JsonProperty("changePercent")
  private Object changePercent;
  @JsonProperty("iexMarketPercent")
  private Object iexMarketPercent;
  @JsonProperty("iexVolume")
  private Object iexVolume;
  @JsonProperty("avgTotalVolume")
  private Object avgTotalVolume;
  @JsonProperty("iexBidPrice")
  private Double iexBidPrice;
  @JsonProperty("iexBidSize")
  private Integer iexBidSize;
  @JsonProperty("iexAskPrice")
  private Double iexAskPrice;
  @JsonProperty("iexAskSize")
  private Integer iexAskSize;
  @JsonProperty("marketCap")
  private Object marketCap;
  @JsonProperty("week52High")
  private Object week52High;
  @JsonProperty("week52Low")
  private Object week52Low;
  @JsonProperty("ytdChange")
  private Object ytdChange;
  @JsonProperty("peRatio")
  private Object peRatio;
  @JsonProperty("lastTradeTime")
  private Object lastTradeTime;
  @JsonProperty("isUSMarketOpen")
  private Object isUSMarketOpen;
  @JsonIgnore
  private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("symbol")
  public Object getSymbol() {
    return symbol;
  }

  @JsonProperty("symbol")
  public void setSymbol(Object symbol) {
    this.symbol = symbol;
  }

  @JsonProperty("companyName")
  public Object getCompanyName() {
    return companyName;
  }

  @JsonProperty("companyName")
  public void setCompanyName(Object companyName) {
    this.companyName = companyName;
  }

  @JsonProperty("calculationPrice")
  public Object getCalculationPrice() {
    return calculationPrice;
  }

  @JsonProperty("calculationPrice")
  public void setCalculationPrice(Object calculationPrice) {
    this.calculationPrice = calculationPrice;
  }

  @JsonProperty("open")
  public Object getOpen() {
    return open;
  }

  @JsonProperty("open")
  public void setOpen(Object open) {
    this.open = open;
  }

  @JsonProperty("openTime")
  public Object getOpenTime() {
    return openTime;
  }

  @JsonProperty("openTime")
  public void setOpenTime(Object openTime) {
    this.openTime = openTime;
  }

  @JsonProperty("close")
  public Object getClose() {
    return close;
  }

  @JsonProperty("close")
  public void setClose(Object close) {
    this.close = close;
  }

  @JsonProperty("closeTime")
  public Object getCloseTime() {
    return closeTime;
  }

  @JsonProperty("closeTime")
  public void setCloseTime(Object closeTime) {
    this.closeTime = closeTime;
  }

  @JsonProperty("high")
  public Object getHigh() {
    return high;
  }

  @JsonProperty("high")
  public void setHigh(Object high) {
    this.high = high;
  }

  @JsonProperty("low")
  public Object getLow() {
    return low;
  }

  @JsonProperty("low")
  public void setLow(Object low) {
    this.low = low;
  }

  @JsonProperty("latestPrice")
  public Double getLatestPrice() {
    return latestPrice;
  }

  @JsonProperty("latestPrice")
  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  @JsonProperty("latestSource")
  public Object getLatestSource() {
    return latestSource;
  }

  @JsonProperty("latestSource")
  public void setLatestSource(Object latestSource) {
    this.latestSource = latestSource;
  }

  @JsonProperty("latestTime")
  public Object getLatestTime() {
    return latestTime;
  }

  @JsonProperty("latestTime")
  public void setLatestTime(Object latestTime) {
    this.latestTime = latestTime;
  }

  @JsonProperty("latestUpdate")
  public Object getLatestUpdate() {
    return latestUpdate;
  }

  @JsonProperty("latestUpdate")
  public void setLatestUpdate(Object latestUpdate) {
    this.latestUpdate = latestUpdate;
  }

  @JsonProperty("latestVolume")
  public Object getLatestVolume() {
    return latestVolume;
  }

  @JsonProperty("latestVolume")
  public void setLatestVolume(Object latestVolume) {
    this.latestVolume = latestVolume;
  }

  @JsonProperty("volume")
  public Object getVolume() {
    return volume;
  }

  @JsonProperty("volume")
  public void setVolume(Object volume) {
    this.volume = volume;
  }

  @JsonProperty("iexRealtimePrice")
  public Object getIexRealtimePrice() {
    return iexRealtimePrice;
  }

  @JsonProperty("iexRealtimePrice")
  public void setIexRealtimePrice(Object iexRealtimePrice) {
    this.iexRealtimePrice = iexRealtimePrice;
  }

  @JsonProperty("iexRealtimeSize")
  public Object getIexRealtimeSize() {
    return iexRealtimeSize;
  }

  @JsonProperty("iexRealtimeSize")
  public void setIexRealtimeSize(Object iexRealtimeSize) {
    this.iexRealtimeSize = iexRealtimeSize;
  }

  @JsonProperty("iexLastUpdated")
  public Object getIexLastUpdated() {
    return iexLastUpdated;
  }

  @JsonProperty("iexLastUpdated")
  public void setIexLastUpdated(Object iexLastUpdated) {
    this.iexLastUpdated = iexLastUpdated;
  }

  @JsonProperty("delayedPrice")
  public Object getDelayedPrice() {
    return delayedPrice;
  }

  @JsonProperty("delayedPrice")
  public void setDelayedPrice(Object delayedPrice) {
    this.delayedPrice = delayedPrice;
  }

  @JsonProperty("delayedPriceTime")
  public Object getDelayedPriceTime() {
    return delayedPriceTime;
  }

  @JsonProperty("delayedPriceTime")
  public void setDelayedPriceTime(Object delayedPriceTime) {
    this.delayedPriceTime = delayedPriceTime;
  }

  @JsonProperty("extendedPrice")
  public Object getExtendedPrice() {
    return extendedPrice;
  }

  @JsonProperty("extendedPrice")
  public void setExtendedPrice(Object extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  @JsonProperty("extendedChange")
  public Object getExtendedChange() {
    return extendedChange;
  }

  @JsonProperty("extendedChange")
  public void setExtendedChange(Object extendedChange) {
    this.extendedChange = extendedChange;
  }

  @JsonProperty("extendedChangePercent")
  public Object getExtendedChangePercent() {
    return extendedChangePercent;
  }

  @JsonProperty("extendedChangePercent")
  public void setExtendedChangePercent(Object extendedChangePercent) {
    this.extendedChangePercent = extendedChangePercent;
  }

  @JsonProperty("extendedPriceTime")
  public Object getExtendedPriceTime() {
    return extendedPriceTime;
  }

  @JsonProperty("extendedPriceTime")
  public void setExtendedPriceTime(Object extendedPriceTime) {
    this.extendedPriceTime = extendedPriceTime;
  }

  @JsonProperty("previousClose")
  public Object getPreviousClose() {
    return previousClose;
  }

  @JsonProperty("previousClose")
  public void setPreviousClose(Object previousClose) {
    this.previousClose = previousClose;
  }

  @JsonProperty("previousVolume")
  public Object getPreviousVolume() {
    return previousVolume;
  }

  @JsonProperty("previousVolume")
  public void setPreviousVolume(Object previousVolume) {
    this.previousVolume = previousVolume;
  }

  @JsonProperty("change")
  public Object getChange() {
    return change;
  }

  @JsonProperty("change")
  public void setChange(Object change) {
    this.change = change;
  }

  @JsonProperty("changePercent")
  public Object getChangePercent() {
    return changePercent;
  }

  @JsonProperty("changePercent")
  public void setChangePercent(Object changePercent) {
    this.changePercent = changePercent;
  }

  @JsonProperty("iexMarketPercent")
  public Object getIexMarketPercent() {
    return iexMarketPercent;
  }

  @JsonProperty("iexMarketPercent")
  public void setIexMarketPercent(Object iexMarketPercent) {
    this.iexMarketPercent = iexMarketPercent;
  }

  @JsonProperty("iexVolume")
  public Object getIexVolume() {
    return iexVolume;
  }

  @JsonProperty("iexVolume")
  public void setIexVolume(Object iexVolume) {
    this.iexVolume = iexVolume;
  }

  @JsonProperty("avgTotalVolume")
  public Object getAvgTotalVolume() {
    return avgTotalVolume;
  }

  @JsonProperty("avgTotalVolume")
  public void setAvgTotalVolume(Object avgTotalVolume) {
    this.avgTotalVolume = avgTotalVolume;
  }

  @JsonProperty("iexBidPrice")
  public Double getIexBidPrice() {
    return iexBidPrice;
  }

  @JsonProperty("iexBidPrice")
  public void setIexBidPrice(Double iexBidPrice) {
    this.iexBidPrice = iexBidPrice;
  }

  @JsonProperty("iexBidSize")
  public Integer getIexBidSize() {
    return iexBidSize;
  }

  @JsonProperty("iexBidSize")
  public void setIexBidSize(Integer iexBidSize) {
    this.iexBidSize = iexBidSize;
  }

  @JsonProperty("iexAskPrice")
  public Double getIexAskPrice() {
    return iexAskPrice;
  }

  @JsonProperty("iexAskPrice")
  public void setIexAskPrice(Double iexAskPrice) {
    this.iexAskPrice = iexAskPrice;
  }

  @JsonProperty("iexAskSize")
  public Integer getIexAskSize() {
    return iexAskSize;
  }

  @JsonProperty("iexAskSize")
  public void setIexAskSize(Integer iexAskSize) {
    this.iexAskSize = iexAskSize;
  }

  @JsonProperty("marketCap")
  public Object getMarketCap() {
    return marketCap;
  }

  @JsonProperty("marketCap")
  public void setMarketCap(Object marketCap) {
    this.marketCap = marketCap;
  }

  @JsonProperty("week52High")
  public Object getWeek52High() {
    return week52High;
  }

  @JsonProperty("week52High")
  public void setWeek52High(Object week52High) {
    this.week52High = week52High;
  }

  @JsonProperty("week52Low")
  public Object getWeek52Low() {
    return week52Low;
  }

  @JsonProperty("week52Low")
  public void setWeek52Low(Object week52Low) {
    this.week52Low = week52Low;
  }

  @JsonProperty("ytdChange")
  public Object getYtdChange() {
    return ytdChange;
  }

  @JsonProperty("ytdChange")
  public void setYtdChange(Object ytdChange) {
    this.ytdChange = ytdChange;
  }

  @JsonProperty("peRatio")
  public Object getPeRatio() {
    return peRatio;
  }

  @JsonProperty("peRatio")
  public void setPeRatio(Object peRatio) {
    this.peRatio = peRatio;
  }

  @JsonProperty("lastTradeTime")
  public Object getLastTradeTime() {
    return lastTradeTime;
  }

  @JsonProperty("lastTradeTime")
  public void setLastTradeTime(Object lastTradeTime) {
    this.lastTradeTime = lastTradeTime;
  }

  @JsonProperty("isUSMarketOpen")
  public Object getIsUSMarketOpen() {
    return isUSMarketOpen;
  }

  @JsonProperty("isUSMarketOpen")
  public void setIsUSMarketOpen(Object isUSMarketOpen) {
    this.isUSMarketOpen = isUSMarketOpen;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}