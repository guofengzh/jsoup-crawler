package crawler;

import com.google.common.base.Joiner;
import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;
import crawler.persistence.Dao;

import java.io.*;
import java.util.List;

public class PersistProductsToDb {

    public void persist(List<Product> products) {
        for (Product product : products) {
            Dao.save(product);
        }
    }
}
