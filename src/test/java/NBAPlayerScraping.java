import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Optional;
import java.util.logging.Logger;


import static org.junit.Assert.assertTrue;

class NBAPlayerScraping {
    private Logger logger = Logger.getLogger(NBAPlayerScraping.class.getName());
    public WebDriver driver = null;
    private static final String scraped3PM = "//*[@id=\"__next\"]/div[2]/section/div[4]/section[2]/div/div/div/table/tbody/tr[1]/td[9]";

     @RepeatedTest(17)
     void mainTest_runner(){

         ChromeOptions options = new ChromeOptions();
         options.setHeadless(true);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);

        driver.get(GetPlayers.players.get(0));
        GetPlayers.players.remove(0);

        double sum3PM = 0.0;

        for (int i = 1; i <= 5; i++) {
            try {
                sum3PM += Integer.parseInt(
                        Optional.of(
                                driver.findElement(
                                        By.xpath(
                                                scraped3PM.replace("tr[i]", String.format("tr[%d]", i))
                                        )).getText()
                        ).orElse("0")
                );
            }catch (NoSuchElementException e){
                logger.info("Selenium.NoSuchElementException: " + scraped3PM.replace("tr[i]", String.format("tr[%d]", i)));
            }
        }

        assertTrue(String.format("Test failed, average %.2f is less than 1. ", sum3PM/5 ), sum3PM/5 >= 1);
        driver.quit();
    }


}