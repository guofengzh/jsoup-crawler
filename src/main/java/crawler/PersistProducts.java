package crawler;

import com.google.common.base.Joiner;
import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;

import java.io.*;
import java.util.List;

public class PersistProducts {

    public void persist(List<Product> products, File file) throws IOException {
        try(
                OutputStream outputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8"))
        {
            TsvWriter writer = new TsvWriter(outputStreamWriter, new TsvWriterSettings()) ;
            writer.writeHeaders(Product.header);
            for(Product product : products) {
                writer.addValue(product.code);
                writer.addValue(product.title);
                writer.addValue(product.lister__item__details);
                writer.addValue(product.getPrice());
                writer.addValue(listToString(product.sizes)) ;
                writer.addValue(listToString(product.product_Broken_Size));
                writer.addValue(product.productUrl);
                writer.addValue(product.product_Live);
                writer.addValue(product.product_Live_Date);
                writer.addValue(product.product_Soldout_Date);
                writer.addValue(listToString(product.product_Last_Broken_Size));
                writer.addValue(product.product_Broken_Size_Date);
                writer.addValue(product.sale_off_rate);
                writer.addValue(product.sale_off_rate_date) ;
                writer.addValue(listToString(product.product_restock));
                writer.addValue(product.product_restock_Date);
                //flushes all values to the output, creating a row.
                writer.writeValuesToRow();
            }
        }
    }

    private String listToString(List<String> s) {
        if (s == null || s.isEmpty()) return null ;

        return Joiner.on(",")
                .skipNulls()
                .join(s);
    }
}
