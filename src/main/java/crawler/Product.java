import com.univocity.parsers.annotations.NullString;

import java.util.List;

public class Product {
    private String code ;
    private String title ;
    private String lister__item__details ;
    private String lister__item__price ;
    private String lister__item__slug ;
    private List<String> sizes ;
    private String productUrl ;

    public Product() {
    }

    public Product(String title, String lister__item__details, String lister__item__price, String lister__item__slug) {
        this.title = title;
        this.lister__item__details = lister__item__details;
        this.lister__item__price = lister__item__price;
        this.lister__item__slug = lister__item__slug;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLister__item__details() {
        return lister__item__details;
    }

    public void setLister__item__details(String lister__item__details) {
        this.lister__item__details = lister__item__details;
    }

    public String getLister__item__price() {
        return lister__item__price;
    }

    public void setLister__item__price(String lister__item__price) {
        this.lister__item__price = lister__item__price;
    }

    public String getLister__item__slug() {
        return lister__item__slug;
    }

    public void setLister__item__slug(String lister__item__slug) {
        this.lister__item__slug = lister__item__slug;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", lister__item__details='" + lister__item__details + '\'' +
                ", lister__item__price='" + lister__item__price + '\'' +
                ", lister__item__slug='" + lister__item__slug + '\'' +
                ", code='" + code + '\'' +
                ", sizes=" + sizes +
                ", productUrl='" + productUrl + '\'' +
                '}';
    }
}
