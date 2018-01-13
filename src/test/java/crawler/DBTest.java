package crawler;

import crawler.persistence.Dao;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DBTest {
    @Test
    public void dbSave() {
        PersistProductsToDb db = new PersistProductsToDb() ;
        List<Product> products = prepareProducts() ;
        db.persist(products);
    }

    @Test
    public void dbLoad() {
        LoadProductsFromDb db = new LoadProductsFromDb() ;
        List<Product> products = db.load() ;
        Assert.assertEquals(products.size(), 1);
    }

    @Test
    public void dbLoadSave() {
        Dao.deleteAllProducts();
        PersistProductsToDb dbSave = new PersistProductsToDb() ;
        List<Product> products = prepareProducts() ;
        dbSave.persist(products) ;
        LoadProductsFromDb dbLoad = new LoadProductsFromDb() ;
        List<Product> saveProducts = dbLoad.load() ;
        Assert.assertTrue(saveProducts.iterator().next().id != null);
        dbSave.persist(saveProducts);
        List<Product> pLast = dbLoad.load() ;
        Assert.assertEquals(pLast.size(), 1);
    }

    private List<Product> prepareProducts() {
        List<Product> products = new ArrayList<>() ;

        Product product = new Product() ;
        product.code = "1234" ;

        product.title = "title1";
        product.details = "detail2";
        product.lister__item__price_full  = "$ 1234";
        product.price  = 1234.5;
        product.sizes = Arrays.asList("12", "34", "56");
        product.noStockSize = Arrays.asList("99", "88");
        product.productUrl = "/bin/product/1234";
        product.product_Live = "Live" ;
        product.product_Live_Date = new Date();
        product.product_Soldout_Date = new Date() ;
        product.product_Broken_Size = Arrays.asList("97", "88");
        product.product_Last_Broken_Size = Arrays.asList("97");
        product.product_Broken_Size_Date = new Date();
        product.sale_off_rate = 25.0 ;
        product.sale_off_rate_date  = new Date();
        product.product_restock = Arrays.asList("56");
        product.product_restock_Date = new Date();
        products.add(product) ;
        return products ;
    }
}
