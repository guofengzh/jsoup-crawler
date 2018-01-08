package crawler;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class Page {
    @Selector(".lister__item")
    List<Product> products;
}
