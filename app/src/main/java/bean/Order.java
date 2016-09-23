package bean;

/**
 * Created by Jam on 2016/8/4.
 */
public class Order {
    private int id,price,opinion;
    private String imgnm,title;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOpinion() {
        return opinion;
    }

    public void setOpinion(int opinion) {
        this.opinion = opinion;
    }

    public String getImgnm() {
        return imgnm;
    }

    public void setImgnm(String imgnm) {
        this.imgnm = imgnm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
