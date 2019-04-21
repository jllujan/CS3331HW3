package src;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;


public class Item {

    private String itemName;// = "LED Monitor";
    private String URL;// = "http://www.bestbuy.com/site/samsun-ue90-series-28-led-4k-uhd-moniotr-black/5484022.p?skuId=5484022";
    private double maxPrice;// = 369.99;
    private double minPrice;// = 61.67;
    private double itemPrice;// = getRandomPrice();
    private double itemChange;// = change();
    private String itemDate;// = "08/25/2018";
    private double previousPrice;

    public Item(){
    }

    public Item(String itemName, String URL, double maxPrice, double minPrice, String itemDate){
        this.itemName = itemName;
        this.URL = URL;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.itemPrice = getRandomPrice();
        this.itemChange = 0;
        this.itemDate = itemDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }


    public double getItemChange() {
        return itemChange;
    }

    public void setItemChange() {
        this.itemChange = change();
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public void setPreviousPrice(double prevPrice){ this.previousPrice = prevPrice; }

    public double getRandomPrice(){
        Random ran = new Random();
        return (new BigDecimal(ran.doubles(minPrice, (maxPrice + 1)).findFirst().getAsDouble())
                .setScale(2,RoundingMode.CEILING).doubleValue());
    }


    public double change(){
        //double increase = ((itemPrice - previousPrice)/ previousPrice) * 100;
        return (new BigDecimal(((itemPrice - previousPrice)/ previousPrice) * 100)
                .setScale(2, RoundingMode.CEILING).doubleValue());
    }

}
