package crawler.model;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class ProductPage {
    @Selector(value=".redefine__right__pager .next a", attr = "href")
    public transient String nextPage ;

    @Selector(".lister__item")
    public List<Product> products;
}