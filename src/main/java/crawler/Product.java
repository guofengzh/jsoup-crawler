package crawler;

import com.univocity.parsers.annotations.NullString;

import java.util.List;

public class Product {
    public static final String[] header = {"code", "title", "details", "price", "sizes", "no_stock_size", "product_url", "slug",
                                              "on_shelf", "off_shelf", "new_product", "sale_off", "short_in_size", "complement" } ;
    private String code ;
    private String title ;
    private String lister__item__details ;
    private String lister__item__price ;
    private String lister__item__slug ;
    private List<String> sizes ;
    private List<String> noStock ;
    private String productUrl ;
    private String on_shelf ;
    private String off_shelf ;
    private Boolean new_product ;
    private Double sale_off ;
    private List<String> short_in_size ;
    private List<String> complement ;

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

    public List<String> getNoStock() {
        return noStock;
    }

    public void setNoStock(List<String> noStock) {
        this.noStock = noStock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOn_shelf() {
        return on_shelf;
    }

    public void setOn_shelf(String on_shelf) {
        this.on_shelf = on_shelf;
    }

    public String getOff_shelf() {
        return off_shelf;
    }

    public void setOff_shelf(String off_shelf) {
        this.off_shelf = off_shelf;
    }

    public Boolean getNew_product() {
        return new_product;
    }

    public void setNew_product(Boolean new_product) {
        this.new_product = new_product;
    }

    public Double getSale_off() {
        return sale_off;
    }

    public void setSale_off(Double sale_off) {
        this.sale_off = sale_off;
    }

    public List<String> getShort_in_size() {
        return short_in_size;
    }

    public void setShort_in_size(List<String> short_in_size) {
        this.short_in_size = short_in_size;
    }

    public List<String> getComplement() {
        return complement;
    }

    public void setComplement(List<String> complement) {
        this.complement = complement;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", lister__item__details='" + lister__item__details + '\'' +
                ", lister__item__price='" + lister__item__price + '\'' +
                ", lister__item__slug='" + lister__item__slug + '\'' +
                ", sizes=" + sizes +
                ", noStock=" + noStock +
                ", productUrl='" + productUrl + '\'' +
                ", on_shelf='" + on_shelf + '\'' +
                ", off_shelf='" + off_shelf + '\'' +
                ", new_product=" + new_product +
                ", sale_off=" + sale_off +
                ", short_in_size=" + short_in_size +
                ", complement=" + complement +
                '}';
    }
}
