package crawler;

import pl.droidsonroids.jspoon.annotation.Selector;

import java.util.List;

public class PageDetail {
    @Selector("ul.pdp-viewall:first-child .underline a")
    List<String> brands;
}
