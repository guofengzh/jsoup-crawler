package crawler.page.net;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class NetBrandListPage {
    public static final String url = "https://www.net-a-porter.com" ;

    //@Selector(value = ".sf-nav__bar.sf-nav__section .sf-nav__section.sf-nav__long-list.sf-nav__with-all li:not(:first-child):not(:last-child) a", attr = "href")
    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(3) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__long-list.sf-nav__with-all > ul :not(:first-child):not(:last-child) a", attr = "href")
    public List<String> clothing ;

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(4) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> shoes ;

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(5) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> bags ;

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(6) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> accessories ;

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(7) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> jewellery ;

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(8) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> lingerie ;

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(9) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> beauty ;

    @Override
    public String toString() {
        return "NetBrandListPage{" +
                "clothing=" + clothing +
                ", \nshoes=" + shoes +
                ", \nbags=" + bags +
                ", \naccessories=" + accessories +
                ", \njewellery=" + jewellery +
                ", \nlingerie=" + lingerie +
                ", \nbeauty=" + beauty +
                '}';
    }
}
