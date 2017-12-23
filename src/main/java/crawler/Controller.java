package crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by Srinivas on 11/14/2016.
 */
public class Controller {

    public static void main(String[] args) throws Exception {

        String fmt = "https://www.matchesfashion.com/intl/womens/shop?page=%d&noOfRecordsPerPage=120&sort=\n" ;
        int totalPages = 1 ;

        try {

            String crawlFolder = "data/crawler";
            int numberOfCrawlers = 1;

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlFolder);
            config.setMaxPagesToFetch(totalPages);
            config.setPolitenessDelay(1000);
            // To only crawl the pages which you added as a seed, set the MaxDepthOfCrawling to 0.
            config.setMaxDepthOfCrawling(0);

            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(
                    robotstxtConfig, pageFetcher);

            CrawlController controller = new CrawlController(config,
                    pageFetcher, robotstxtServer);

            for (int i = 1 ; i < totalPages + 1 ; i++ ) {
                String searchUrl = String.format(fmt, i) ;
                controller.addSeed(searchUrl);
            }

            controller.start(Crawler4j.class, numberOfCrawlers);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
