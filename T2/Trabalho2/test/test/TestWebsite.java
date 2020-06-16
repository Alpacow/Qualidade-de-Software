/*
    Classe para teste do site: https://www.uni-hamburg.de/
 */
package qualidade;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;



import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestWebsite {
    // constants
    public static String SITE_URL = "https://www.uni-hamburg.de/";
    public static String PATH_DRIVE = "C:\\Users\\augus\\Documents\\Eclipse\\qualidade\\assets\\Drivers\\geckodriver.exe";
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
    
    // Testa se o tÌtulo da p·gina È o esperado -> component 1: <title>
    @Test
    public void testPageTitle () {
        assertEquals("Universit‰t Hamburg", driver.getTitle());
    }
    
    // testa todas as opÁıes disponÌveis em um comboBox -> component 2: <select>
    @Test
    public void testSelectButton () {
        // vai para a p·gina de contato (em ingles)
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
    
    // testa se a quantidade de elementos do select È o esperado
    @Test
    public void testSelectSize () {
        driver.navigate().to(SITE_URL + "en/newsroom/presse/kontakt.html");
        Select select = new Select(driver.findElement(By.id("institute")));
        int size = select.getOptions().size();
        assertEquals(6, size);
        driver.navigate().to(SITE_URL); // volta para a home page
    }
    
    // testa se o bot„o submit da busca existe -> component 3: <input>
    @Test
    public void testSubmitSearch () {
        WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
        assertTrue(submit != null);
    }
    
    // testa se um video existe -> component 4: <video>
    @Test
    public void testVideoTag () {
        WebElement video = driver.findElement(By.tagName("video"));
        assertTrue(video != null);
    }
    
    // testa se o bot„o de scroll È exibido na p·gina -> component 5: <button>
    @Test
    public void testButtonRollUp () {
        WebElement button = driver.findElement(By.id("scrollTopButton2"));
        assertTrue(button.isDisplayed());
    }
    
    @Test
    public void testLinkTwitter () {
        element = driver.findElement(By.className("twitter"));
        assertTrue(element.isDisplayed());
    }
    
    @Test
    public void testLinkTwitter2(){
        element = driver.findElement(By.className("twitter"));
        String link = element.getAttribute("href");
        boolean isValid = verificaConexao(link);
        assertTrue(isValid);
    }
    
    @Test
    public void testLinkFacebook () {
        element = driver.findElement(By.className("facebook"));
        assertTrue(element.isDisplayed());
    }
    
    @Test
    public void testLinkFacebook2(){
        element = driver.findElement(By.className("facebook"));
        String link = element.getAttribute("href");
        boolean isValid = verificaConexao(link);
        assertTrue(isValid);
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
    
    @Test
    public void testCopyright() {
    	element = driver.findElement(By.className("copyright"));
    	assertEquals(element.getText(), "© 2020 Universit‰t Hamburg. Alle Rechte vorbehalten");
    }
    
    @Test
    public void testField(){
        element = driver.findElement(By.name("q"));		 
        assertEquals("", element.getText());
    }

    /*
    @Test
    public void testaComboBox(){
        Select combo = new Select(driver.findElement(By.id("selecttype")));
        String str = combo.getFirstSelectedOption().getText();
        assertEquals("Selenium IDE",str);
    }

    //exercicio
    @Test
    public void testaComboBox2(){
        Select combo = new Select(driver.findElement(By.id("selecttype")));
        int tam = combo.getOptions().size();		 
        assertEquals(4,tam);
    }

    //exercicio
    @Test
    public void testaComboBoxElements(){
        Select combo = new Select(driver.findElement(By.id("selecttype")));
        List<WebElement> opcoes = combo.getOptions();
        List retornada = new LinkedList();		
        for (WebElement conteudo : opcoes)
        {
                retornada.add(conteudo.getText());
        }

        List esperada = new LinkedList();
        esperada.add("Selenium IDE");
        esperada.add("Selenium Core");
        esperada.add("Selenium RC");
        esperada.add("Selenium Grid");

        assertEquals(esperada,retornada);
    }

    //se colocar um espaÔøΩo jÔøΩ da erro
    @Test
    public void testaField(){
        element = driver.findElement(By.id("storeinput"));		 
        assertEquals("",element.getText());
    }

    @Test
    public void testaLink1 () {
        element = driver.findElement(By.linkText("Home Page"));
        assertTrue(element.isDisplayed());
    }

    //ver se esta quebrado
    @Test
    public void testaLink2(){
        element = driver.findElement(By.linkText("Home Page"));
        String link = element.getAttribute("href");
        boolean isValid = verificaConexao(link);
        assertTrue(isValid);
    }

    //ver se link leva para a pagina que se gostaria
    @Test
    public void testaLink3(){
        element = driver.findElement(By.linkText("Home Page"));
        String link = element.getAttribute("href");
        //System.out.println(link);
        assertTrue(link.equals("http://book.theautomatedtester.co.uk/"));		
    }


    @Test
    public void testaAJAX(){
        driver.findElement(By.id("loadajax")).click();			

        String x = "The following text has been loaded from another page on this site." 
                        +   " It has been loaded in an asynchronous fashion so that"
                        +   " we can work through the AJAX section of this chapter";

        //primeira forma funcional
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //WebElement element = driver.findElement(By.xpath("//div[@id='ajaxdiv']/p"));
        WebDriverWait wda = new WebDriverWait(driver,20);
        element = wda.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='ajaxdiv']/p")));

        System.out.println("testaAJAX: "+element.getText());

        assertEquals(x, element.getText());
    }


    //@Ignore
    @Test
    public void testaAJAX2() throws Exception{	  
        driver.findElement(By.id("loadajax")).click();

        Thread.sleep(5000);
        WebElement divResultados = driver.findElement(By.id("ajaxdiv"));

        System.out.println(divResultados.getText());
        String x = "The following text has been loaded from another page on this site." 
                        +   " It has been loaded in an asynchronous fashion so that"
                        +   " we can work through the AJAX section of this chapter";

        assertEquals(x, divResultados.getText());
    }



    public static boolean verificaConexao(String urlString) {
            boolean isValid = false;
            try {
                    URL u = new URL(urlString);
                    HttpURLConnection h = (HttpURLConnection) u.openConnection();
                    h.setRequestMethod("GET");
                    h.connect();
                    System.out.println(h.getResponseCode());
                    if (h.getResponseCode() != h.HTTP_NOT_FOUND) { //404 HTTP_OK
                            isValid = true;
                    }
            } catch (Exception e) {

            }
            return isValid;
    }

    @Test
    public void testaYellowSquare(){
            element = driver.findElement(By.id("html5div"));
            boolean retorno = element.isDisplayed();
            if (retorno){		
                    System.out.println("yellow :"+element.getText());
                    String aux = "To be used after the AJAX section of the book";
                    assertEquals(aux,element.getText());
            }
    }



    @Test
    public void testaSecondAjaxButton(){
            String aux = "To be used after the AJAX section of the book";
            String auxQ="";
            for(int i=0; i<=2;i++){
                    driver.findElement(By.id("secondajaxbutton")).click();
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    element = driver.findElement(By.xpath("//div[@id='html5div']"));
                    auxQ=element.getText();
                    aux = aux.concat("\nI have been added with a timeout");
                    System.out.println("auxQ: "+auxQ);
                    System.out.println("aux: "+aux);
            }		 
            assertEquals(aux,auxQ);			 
    }

    @Test
    public void testaPopup(){

            element = driver.findElement(By.className("multiplewindow"));
            element.click();
            //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);




            String windows[] = getMaeFilha();
            String mae = windows[0];
            String filha = windows[1];


            // switch to popup window
            driver.switchTo().window(filha); 
            // perform operations on popup		  
            element = driver.findElement(By.id("popuptext"));
            System.out.println("texto da popup: "+element.getText());		

            assertEquals("Text within the pop up window",element.getText());

            driver.switchTo().window(mae);  // switch back to parent window

            //testando a segunda popup
            element = driver.findElement(By.className("multiplewindow2"));
            element.click();

            windows = getMaeFilha();
            mae = windows[0];
            filha = windows[1];


            // switch to popup window
            driver.switchTo().window(filha); 
            // perform operations on popup		  
            element = driver.findElement(By.id("popuptext"));
            System.out.println("texto da popup: "+element.getText());		

            assertEquals("Text within the pop up window",element.getText());

            driver.switchTo().window(mae);

    }

    public String[] getMaeFilha(){

            Set <String> set1=driver.getWindowHandles();
            Iterator <String> win1=set1.iterator();
            String mae=win1.next();
            String filha=win1.next();		 
            return new String[]{mae, filha};

    }

    @Test
    public void testaBotaoVerify(){
        element = driver.findElement(By.id("verifybutton"));
        assertEquals("",element.getText());
        if (element.isDisplayed()) {
            System.out.println("Bot√£o de verifica√ß√£o est√° na p√°gina.");
        }
        else {
            System.out.println("Bot√£o de verifica√ß√£o n√£o encontrado!!!");
        }
    }
    */
}
