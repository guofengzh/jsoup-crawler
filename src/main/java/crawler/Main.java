package crawler;

import crawler.config.AppConfig;
import crawler.mat.crawling.MatCrawling;
import crawler.net.crawling.NetCrawling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String stringDate = dateFormat.format(new Date());
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args ) {
        if (args.length != 1 ) {
            System.out.println("Usage: java -jar jafile.jar tablename" ) ;
            System.exit(-1);
        }
        TableNameUtils.setTableNamePostfix(args[0]);

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        MatCrawling matCrawling = ctx.getBean(MatCrawling.class) ;
        NetCrawling netCrawling = ctx.getBean(NetCrawling.class) ;

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(()->{
            try {
                logger.info("Starting crawling Mat products") ;
                matCrawling.run() ;
                logger.info("Starting crawling Mat products - Done") ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }) ;
        es.submit(()->{
            try {
                logger.info("Starting crawling Net products") ;
                netCrawling.run() ;
                logger.info("Starting crawling Net products - Done") ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }) ;
        es.shutdown();
        // boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);
        // all tasks have finished or the time has been reached.        es.shutdown();
    }
}
