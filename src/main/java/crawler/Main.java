package crawler;

import crawler.config.AppConfig;
import crawler.mat.service.MatCrawling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        MatCrawling matService = ctx.getBean(MatCrawling.class) ;

        try {
            logger.info("Starting crawling products") ;
            matService.run() ;
            logger.info("Starting crawling products - Done") ;
        } catch (Throwable t ) {
            logger.error(t.getMessage(), t);
            System.exit(-1);
        }
        System.exit(0);
    }

}
