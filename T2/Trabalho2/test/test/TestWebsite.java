/*
    Classe para teste do site: https://www.uni-hamburg.de/
 */
package test;

import static org.junit.Assert.*;

import org.openqa.selenium.JavascriptExecutor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestWebsite {
    // constants
    public static String SITE_URL = "https://www.uni-hamburg.de/";
    public static String PATH_DRIVE = "C:\\Users\\augus\\Documents\\Eclipse\\qualidade\\assets\\Drivers\\geckodriver.exe";
    //public static String PATH_DRIVE = "/home/flan/Documents/UFSM/5º Semestre/Qualidade/selenium-java-3.141.59/geckodriver";
    private static WebDriver driver; // //objeto que contem os metodos que controlam o navegador web
    WebElement element;

    @BeforeClass
    public static void openBrowser () {
        System.setProperty("webdriver.gecko.driver", PATH_DRIVE);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(SITE_URL);
    }

    // Metodo que finaliza o teste, fechando a instancia do WebDriver.    
    @AfterClass
    public static void tearDownTest(){
        driver.quit();
    }
    
    public static boolean verificaConexao(String urlString) {
        boolean isValid = false;
        try {
            URL u = new URL(urlString);
            HttpURLConnection h = (HttpURLConnection) u.openConnection();
            h.setRequestMethod("GET");
            h.connect();
            if (h.getResponseCode() != HttpURLConnection.HTTP_NOT_FOUND) { //404 HTTP_OK
                isValid = true;
            }
        } catch (Exception e) {

        }
        return isValid;
    }
    
    // testa se um video existe -> component 4: <video>
    @Test
    @Ignore
    public void testVideo () throws InterruptedException {
        element = driver.findElement(By.tagName("video"));
        assertTrue(element != null);
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByTagName(\"video\")[1].play();");
        Thread.sleep(40000);
        js.executeScript("document.getElementsByTagName(\"video\")[1].pause();");
        Thread.sleep(4000);
        js.executeScript("document.getElementsByTagName(\"video\")[1].load();");
    }
    
    // Testa se o título da página é o esperado -> component 1: <title>
    @Test
    @Ignore
    public void testPageTitle () {
        assertEquals("Universität Hamburg", driver.getTitle());
    }

    // testa todas as opções disponíveis em um comboBox -> component 2: <select>
    @Test
    @Ignore
    public void testSelectButton () {
        // vai para a página de contato (em ingles)
        driver.navigate().to(SITE_URL + "en/newsroom/presse/kontakt.html");
        Select select = new Select(driver.findElement(By.id("institute")));
        List<WebElement> op = select.getOptions();
        List response = new LinkedList();		
        op.stream().forEach((content) -> {
            response.add(content.getText());
        });
        List expected = new LinkedList();
        expected.add("Structure");
        expected.add("Department Head");
        expected.add("Front Office");
        expected.add("Section 21: Media and Public Relations");
        expected.add("Section 22: Digital Communication and Design");
        expected.add("Communications Trainee");
        assertEquals(expected, response);
        driver.navigate().to(SITE_URL); // volta para a home page
    }

    // testa se a quantidade de elementos do select é o esperado
    @Test
    @Ignore
    public void testSelectSize () {
        driver.navigate().to(SITE_URL + "en/newsroom/presse/kontakt.html");
        Select select = new Select(driver.findElement(By.id("institute")));
        int size = select.getOptions().size();
        assertEquals(6, size);
        driver.navigate().to(SITE_URL); // volta para a home page
    }

    // testa se o botão submit da busca existe -> component 3: <input>
    @Test
    @Ignore
    public void testSubmitSearch () {
        element = driver.findElement(By.cssSelector("input[type='submit']"));
        assertTrue(element != null);
    }
    
    @Test
    @Ignore
    public void testField(){
        element = driver.findElement(By.name("q"));		 
        assertEquals("", element.getText());
    }
    
    // component 6: <a>
    @Test
    @Ignore
    public void testLinkTwitter () {
        element = driver.findElement(By.className("twitter"));
        assertTrue(element.isDisplayed());
    }
    
    @Test
    @Ignore
    public void testLinkTwitter2(){
        element = driver.findElement(By.className("twitter"));
        String link = element.getAttribute("href");
        boolean isValid = verificaConexao(link);
        assertTrue(isValid);
    }
    
    // testa os links da pagina
    @Test
    @Ignore
    public void testAllLinks () {
        List<WebElement> list = new ArrayList();
        boolean isValid = true;
        
        list = driver.findElements(By.tagName("a"));
        for (WebElement e : list) {
            String link = e.getAttribute("href");
            System.out.print(link);
            if (verificaConexao(link) == false) {
            	isValid = false;
            	System.out.print(" FAIL\n");
            } else {
            	System.out.print(" OK\n");
            }
        }
        
        assertTrue(isValid);
    }
    
    // component 7: <p>
    @Test
    @Ignore
    public void testCopyright() {
    	element = driver.findElement(By.className("copyright"));
    	assertEquals(element.getText(), "© 2020 Universität Hamburg. Alle Rechte vorbehalten");
    }
    
    // component 8: <footer>
    @Test
    @Ignore
    public void testFooter () {
    	element = driver.findElement(By.id("fuss"));
    	assertTrue(element.isDisplayed());
    }
    
 // testa se o botão de scroll é exibido na página -> component 5: <button>
    @Test
    @Ignore
    public void testScrollToTopButton () throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(10000);
        
        element = driver.findElement(By.id("scrollTopButton2"));
        assertTrue(element.isDisplayed());
    }
    
    @Test
    @Ignore
    public void testScrollToTopButtonAfterClick () throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(10000);
        
        element = driver.findElement(By.id("scrollTopButton2"));
        element.click();
        Thread.sleep(5000);
        
        assertTrue(!(element.isDisplayed()));
    }
    
    @Test
    public void testButtonRollUp () throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        
        Thread.sleep(10000);
        element = driver.findElement(By.id("scrollTopButton2"));
        element.click();
        
        Thread.sleep(5000);
        long offset = (long) js.executeScript("return window.scrollY");
        System.out.println("Offset: " + offset);
        
        assertEquals((long) 0, offset);
    }
}
