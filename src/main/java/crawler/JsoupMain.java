package crawler;

import com.google.common.base.Joiner;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsoupMain {
    public static int totalPage = 5 ;
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
        List<String> noStockSizes = getNoStockSizes(sizesElement) ;
        String productUrl = product.getProductUrl() ;
        product.setCode(productUrl.substring(productUrl.lastIndexOf("-") + 1));
        product.setSizes(sizes);
        product.setNoStock(noStockSizes);
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

    public static List<String> getNoStockSizes(Element element) {
        Elements liElements = element.getElementsByTag("li") ;
        List<String> sizes = new ArrayList<>() ;
        for ( Element oneElement : liElements ) {
            String noStock = oneElement.attr("class"); // "noStock"
            Element anchorElement = oneElement.getElementsByTag("a").first() ;
            String sizeText = anchorElement.text() ;
            if ( noStock != null && !noStock.isEmpty())
                sizes.add(sizeText) ;
        }
        return sizes ;
    }

    public static List<String> getSizes(Element element) {
        Elements liElements = element.getElementsByTag("li") ;
        List<String> sizes = new ArrayList<>() ;
        for ( Element oneElement : liElements ) {
            String noStock = oneElement.attr("class"); // "noStock"
            Element anchorElement = oneElement.getElementsByTag("a").first() ;
            String sizeText = anchorElement.text() ;
            if ( noStock == null  || noStock.isEmpty())
                sizes.add(sizeText) ;
        }
        return sizes ;
    }

    public static List<Product> load() throws IOException {
        // List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
        // creates a CSV parser
        CsvParserSettings settings = new CsvParserSettings();
        //the file used in the example uses '\n' as the line separator sequence.
        //the line separator sequence is defined here to ensure systems such as MacOS and Windows
        //are able to process this file correctly (MacOS uses '\r'; and Windows uses '\r\n').
        settings.getFormat().setLineSeparator("\n");
        CsvParser parser = new CsvParser(settings);

        // call beginParsing to read records one by one, iterator-style.
        parser.beginParsing(getReader("/examples/example.csv"));

        String[] row;
        while ((row = parser.parseNext()) != null) {
            println(out, Arrays.toString(row));
        }

        // The resources are closed automatically when the end of the input is reached,
        // or when an error happens, but you can call stopParsing() at any time.

        // You only need to use this if you are not parsing the entire content.
        // But it doesn't hurt if you call it anyway.
        parser.stopParsing();

    }

    public static Reader getReader(String relativePath) throws IOException {
	    return new InputStreamReader(new FileInputStream(relativePath), "UTF-8");
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
            writer.writeHeaders(Product.header);
            for(Product product : products) {
                writer.addValue(product.getCode());
                writer.addValue(product.getTitle());
                writer.addValue(product.getLister__item__details());
                writer.addValue(product.getLister__item__price());
                writer.addValue(Joiner.on(",")
                        .skipNulls()
                        .join(product.getSizes()));
                writer.addValue(Joiner.on(",")
                        .skipNulls()
                        .join(product.getNoStock()));
                writer.addValue(product.getProductUrl());
                writer.addValue(product.getLister__item__slug());
                writer.addValue(product.getOn_shelf());
                writer.addValue(product.getOff_shelf());
                writer.addValue(product.getNew_product());
                writer.addValue(product.getSale_off());
                writer.addValue(product.getShort_in_size());
                writer.addValue(product.getComplement());
                //flushes all values to the output, creating a row.
                writer.writeValuesToRow();
            }
        }
    }
}
