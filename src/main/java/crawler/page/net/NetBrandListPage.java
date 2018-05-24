package crawler.page.net;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class NetBrandListPage {
    public static final String url = "https://www.net-a-porter.com" ;

    @Selector(value = ".sf-nav__bar.sf-nav__section .sf-nav__section.sf-nav__long-list sf-nav__with-all li:not(:first-child) a", attr = "href")
    public List<String> clothing ;

    /*
    @Selector(value = ".shop__cols__wrapper__womens .bags li:not(:first-child) a", attr = "href")
    public List<String> bags ;

    @Selector(value = ".shop__cols__wrapper__womens .shoes li:not(:first-child) a", attr = "href")
    public List<String> shoes ;

    @Selector(value = ".shop__cols__wrapper__womens .jewellery li:not(:first-child) a", attr = "href")
    public List<String> jewellery ;

    @Selector(value = ".shop__cols__wrapper__womens .fine__jewellery li:not(:first-child) a", attr = "href")
    public List<String> fine__jewellery ;

    @Selector(value = ".shop__cols__wrapper__womens .accessories li:not(:first-child) a", attr = "href")
    public List<String> accessories ;

    @Override
    public String toString() {
        return "BrandListPage{" +
                "clothing=" + clothing + "\n"+
                ", bags=" + bags + "\n"+
                ", shoes=" + shoes + "\n"+
                ", jewellery=" + jewellery + "\n"+
                ", fine__jewellery=" + fine__jewellery + "\n"+
                ", accessories=" + accessories +
                '}';
    }
    */
}
