package crawler;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.*;
import java.util.*;

public class LoadProducts {

    public List<Product> load(File file) throws IOException {
        CsvParserSettings settings = new CsvParserSettings();
        //the file used in the example uses '\n' as the line separator sequence.
        //the line separator sequence is defined here to ensure systems such as MacOS and Windows
        //are able to process this file correctly (MacOS uses '\r'; and Windows uses '\r\n').
        settings.getFormat().setLineSeparator("\n");
        settings.getFormat().setDelimiter('\t');

        // creates a CSV parser
        CsvParser parser = new CsvParser(settings);

        // parses all rows in one go.
        List<Product> allProducts = new ArrayList<>() ;
        List<String[]> allRows = parser.parseAll(getReader(file));
        Iterator<String[]> allRowsIter = allRows.iterator() ;
        // skip the kead
        allRowsIter.next() ;
        while( allRowsIter.hasNext()) {
            String[] row = allRowsIter.next() ;
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
        product.title = row[1] ;
        product.details = row[2] ;
        product.setLister__item__price(row[3]) ;
        product.sizes = toList(row[4]) ;
        product.product_Broken_Size = toList(row[5]) ;
        product.productUrl = row[6] ;
        product.product_Live = row[7] ;
        product.product_Live_Date = row[8];
        product.product_Soldout_Date = row[9];
        product.product_Last_Broken_Size = toList(row[10]) ;
        product.product_Broken_Size_Date = row[11] ;
        product.sale_off_rate = row[12] != null && !row[12].isEmpty()?Double.parseDouble(row[12]):null;
        product.sale_off_rate_date = row[13] ;
        product.product_restock = toList(row[14]) ;
        product.product_restock_Date = row[15] ;
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
