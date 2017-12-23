package crawler;

import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JsoupMain {
    public static int totalPage = 1000 ;
    public static void main(String[] args) throws IOException {
        int i ;
        List<Product> allProducts = new ArrayList<>() ;
        for (i = 1 ; i <= totalPage ; i++ ) {
            try {
                List<Product> products = getPage(i);
                if ( products.isEmpty())
                    break ;
                allProducts.addAll(products) ;
                try {
                    Thread.sleep( 5 * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Page " + i) ;
                    e.printStackTrace();
                }
            } catch (Throwable t ) {
                System.out.println("Error when scrawl page " + i ) ;
                t.printStackTrace();
            }
        }
        System.out.println("Total products " + allProducts.size() + " on " + (i-1) + " pages") ;
        persist(allProducts);
    }

    public static final String fmt = "https://www.matchesfashion.com/us/womens/shop?page=%d&noOfRecordsPerPage=120&sort=" ;
    public static List<Product> getPage(int i ) throws IOException {
        System.out.println("Extracting page " + i);
        String url = String.format(fmt, i) ;
        Connection.Response response = null;
        response = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(10000)
                .execute();
        int statusCode = response.statusCode();
        List<Product> products = new ArrayList<>() ;
        if ( statusCode != 200 ) {
            System.out.println("Status Code: " + statusCode + " at page " + i) ;
            return products ;
        }
        Document doc = response.parse();
        Elements lister__item__innerElements = doc.getElementsByClass("lister__item__inner") ;
        for (Element element : lister__item__innerElements ) {
            Product product = makeProduct(element) ;
            System.out.println(product);
            products.add(product) ;
        }

        return products ;
    }

    public static Product makeProduct(Element element) {
        Element productMainLink = element.getElementsByClass("productMainLink").first() ;
        Product product = processProductMainLink(productMainLink) ;
        Element sizesElement = element.getElementsByClass("sizes").first() ;
        List<String> sizes = getSizes(sizesElement) ;
        String productUrl = product.getProductUrl() ;
        product.setCode(productUrl.substring(productUrl.lastIndexOf("-") + 1));
        product.setSizes(sizes);
        return product ;
    }

    public static Product processProductMainLink(Element content) {
        String url = content.attr("href") ;
        String title = content.getElementsByClass("lister__item__title").text() ;
        String lister__item__details = content.getElementsByClass("lister__item__details").text() ;
        String lister__item__price = content.getElementsByClass("lister__item__price").text() ;
        String lister__item__slug = content.getElementsByClass("lister__item__slug").text() ;
        Product product = new Product(title, lister__item__details, lister__item__price, lister__item__slug) ;
        product.setProductUrl(url) ;
        return product ;
    }

    public static List<String> getSizes(Element element) {
        Elements liElements = element.getElementsByTag("li") ;
        List<String> sizes = new ArrayList<>() ;
        for ( Element oneElement : liElements ) {
            String noStock = oneElement.attr("class"); // "noStock"
            Element anchorElement = oneElement.getElementsByTag("a").first() ;
            String href = anchorElement.attr("href") ;
            String sizeText = anchorElement.text() ;
            if ( noStock != null && !noStock.isEmpty())
                sizeText = sizeText + "(" + noStock + ")";
            sizes.add(sizeText) ;
        }
        return sizes ;
    }

    public static void persist(List<Product> products) throws IOException {
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = dateFormat.format(date);
        try(
                OutputStream outputStream = new FileOutputStream("matchesfashion-" + stringDate + ".csv");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8"))
        {
            TsvWriter writer = new TsvWriter(outputStreamWriter, new TsvWriterSettings()) ;
            writer.writeHeaders("code", "title", "details", "price", "sizes", "product_url", "slug");
            for(Product product : products) {
                writer.addValue(product.getCode());
                writer.addValue(product.getTitle());
                writer.addValue(product.getLister__item__details());
                writer.addValue(product.getLister__item__price());
                writer.addValue(product.getSizes());
                writer.addValue(product.getProductUrl());
                writer.addValue(product.getLister__item__slug());
                //flushes all values to the output, creating a row.
                writer.writeValuesToRow();
            }
        }
    }
}
