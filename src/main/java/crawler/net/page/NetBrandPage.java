package crawler.net.page;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class NetBrandPage {
    public static final String url = "https://www.net-a-porter.com" ;

    //@Selector(value = ".sf-nav__bar.sf-nav__section .sf-nav__section.sf-nav__long-list.sf-nav__with-all li:not(:first-child):not(:last-child) a", attr = "href")
    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(4) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__long-list.sf-nav__with-all > ul :not(:first-child):not(:last-child) a:not([href*=Lingerie])", attr = "href")
    public List<String> clothing ;  // has size

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(5) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> shoes ;  // has size

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(6) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> bags ;  // no size

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(7) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> accessories ;  // no size

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(8) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> jewellery ;  // no size

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(9) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> lingerie ;  // no size

    @Selector(value = "body > div.sf-wrapper > div > nav > ul > li:nth-child(10) > div > div > div.sf-nav__categories > div.sf-nav__section.sf-nav__category-list > ul li:not(:first-child) a", attr = "href")
    public List<String> beauty ;   // no size

    @Override
    public String toString() {
        return "NetBrandPage{" +
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
