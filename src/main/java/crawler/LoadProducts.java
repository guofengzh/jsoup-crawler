package crawler;

import com.google.common.base.Joiner;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LoadProducts {

    public List<Product> load(File file) throws IOException {
        CsvParserSettings settings = new CsvParserSettings();
        //the file used in the example uses '\n' as the line separator sequence.
        //the line separator sequence is defined here to ensure systems such as MacOS and Windows
        //are able to process this file correctly (MacOS uses '\r'; and Windows uses '\r\n').
        settings.getFormat().setLineSeparator("\n");

        // creates a CSV parser
        CsvParser parser = new CsvParser(settings);

        // parses all rows in one go.
        List<Product> allProducts = new ArrayList<>() ;
        List<String[]> allRows = parser.parseAll(getReader(file));
        for (String[] row : allRows ) {
            Product product = parseOneRow(row) ;
            allProducts.add(product) ;
        }

        // You only need to use this if you are not parsing the entire content.
        // But it doesn't hurt if you call it anyway.
        parser.stopParsing();

        return allProducts ;
    }

    private Product parseOneRow(String[] row) {
        Product product = new Product() ;
        product.code = row[0];
        product.title = row[2] ;
        product.lister__item__details = row[3] ;
        product.lister__item__price = row[4] ;
        product.sizes = toList(row[5]) ;
        product.productUrl = row[6] ;
        product.on_off_shelf = row[7] ;
        product.on_shelf_date = row[8];
        product.off_shelf_date = row[9];
        product.sizes_in_short = toList(row[10]) ;
        product.sale_off_rate = Double.parseDouble(row[11]);
        product.complements = toList(row[12]) ;
        product.complement_date = row[13] ;
        return product ;
    }

    private List<String> toList(String s ) {
        if ( s == null )
            return new ArrayList<>() ;

        String[] sizesInShort = s.split(",") ;
        return Arrays.asList(sizesInShort) ;
    }

    public Reader getReader(File file) throws IOException {
        return new InputStreamReader(new FileInputStream((file)), "UTF-8");
    }

}
