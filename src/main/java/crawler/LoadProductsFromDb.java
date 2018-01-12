package crawler;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import crawler.persistence.Dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LoadProductsFromDb {

    public List<Product> load(File file) throws IOException {
        return Dao.loadAll();
    }
}
