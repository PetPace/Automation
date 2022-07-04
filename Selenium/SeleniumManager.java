package Selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumManager {

    private static WebDriver driver = null; 

    //#region Init

    private static void init(){
        String workingDir = System.getProperty("user.dir");
        String pathToChromeDriver = workingDir + "\\bin\\jar_files\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver); 
    }

    public static void openBrowserInstance(String url) throws InterruptedException {     

        init();
        
        if(driver == null){
            driver = new ChromeDriver();
        }
        else{
            driver.quit();  
        }

        driver.manage().window().maximize();

        driver.get(url);   

        Thread.sleep(2000);
    } 
    //#endregion

    //#region Navigation and Refresh
    public static void navigateTo(String navigateToUrl) throws InterruptedException{
        if(driver == null){
            openBrowserInstance(navigateToUrl);
        }
        else{
            driver.navigate().to(navigateToUrl);
            Thread.sleep(2000);
        }
    }

    public static void back() throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }
        driver.navigate().back();
    }

    public static void forward() throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }
        driver.navigate().forward();
    }

    public static void refresh() throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }
        driver.navigate().refresh();
    }
    //#endregion

    //#region Get URL or Text
    public static String getCurrentURL() throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }
        return driver.getCurrentUrl();
    }

    public static String getTitle() throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }
        return driver.getTitle();
    }

    public static String getTextByElementID(String elementID) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.id(elementID));
        if(webElement == null){
            throw new Exception("Unable to find element by ID: " + elementID + ". URL: " + driver.getCurrentUrl());
        }

        String ret = webElement.getText();

        return ret;
    }

    public static String getTextByTagName(String tagName) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.tagName(tagName));
        if(webElement == null){
            throw new Exception("Unable to find element by Tag name: " + tagName + ". URL: " + driver.getCurrentUrl());
        }

        String ret = webElement.getText();

        return ret;
    }


    //#endregion

    //#region Set Text
    public static void setInputTextByElementID(String elementID, String text) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.id(elementID));
        if(webElement == null){
            throw new Exception("Unable to find element by ID: " + elementID + ". URL: " + driver.getCurrentUrl());
        }

        webElement.sendKeys(text);
    }

    public static void setInputTextByXPath(String xpath, String text) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.xpath(xpath));
        if(webElement == null){
            throw new Exception("Unable to find element by xpath: " + xpath + ". URL: " + driver.getCurrentUrl());
        }

        webElement.sendKeys(text);
    }

    public static void setInputTextByName(String name, String text) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.name(name));
        if(webElement == null){
            throw new Exception("Unable to find name: " + name + ". URL: " + driver.getCurrentUrl());
        }

        webElement.sendKeys(text);
    }

    public static void setInputTextByClassName(String className, String text) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.className(className));
        if(webElement == null){
            throw new Exception("Unable to find class name: " + className + ". URL: " + driver.getCurrentUrl());
        }

        webElement.sendKeys(text);
    }
    //#endregion

    //#region Click
    
    public static void clickByElementID(String elementID) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.id(elementID));
        if(webElement == null){
            throw new Exception("Unable to find element by ID: " + elementID + ". URL: " + driver.getCurrentUrl());
        }

        webElement.click();
    }

    public static void clickByClassName(String className) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.className(className));
        if(webElement == null){
            throw new Exception("Unable to find Class Name by ID: " + className + ". URL: " + driver.getCurrentUrl());
        }

        webElement.click();
    }

    public static void clickByElementXPath(String xpath) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.xpath(xpath));
        if(webElement == null){
            throw new Exception("Unable to find element by XPath: " + xpath + ". URL: " + driver.getCurrentUrl());
        }

        webElement.click();
    }

    public static void clickByLinkText(String linkText) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.linkText(linkText));
        if(webElement == null){
            throw new Exception("Unable to find element by Link text: " + linkText + ". URL: " + driver.getCurrentUrl());
        }

        webElement.click();
    }

    //#endregion

    //#region Check if Exist

    public static boolean isLinkTextExist(String linkText) throws Exception{
        boolean result = false;
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.linkText(linkText));
        if(webElement != null){
            result = true;
        }

        return result;
    }

    public static boolean isElementExistByText(String text) throws Exception{
        boolean result = false;
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        String formated = String.format("//*[text()='%s']", text);  

        WebElement webElement = driver.findElement(By.xpath(formated));
        
        if(webElement != null && webElement.getText().equals(text)){
            result = true;
        }

        return result;
    }

    public static boolean isElementExistByElementID(String className) throws Exception{
        boolean result = false;
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.id(className));
        
        if(webElement != null){
            result = true;
        }

        return result;
    }

    public static boolean isElementExistByXPath(String xpath) throws Exception{
        boolean result = false;
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.xpath(xpath));
        
        if(webElement != null){
            result = true;
        }

        return result;
    }

    public static boolean isElementExistByXPathAndText(String xpath, String text) throws Exception{
        boolean result = false;
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.xpath(xpath));
        
        if(webElement != null){
            String textFromElement = webElement.getText();
            if(textFromElement.equals(text)){
                result = true;
            }
        }

        return result;
    }

    public static boolean isElementExistByClassName(String className) throws Exception{
        boolean result = false;
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }

        WebElement webElement = driver.findElement(By.className(className));
                
        if(webElement != null){
            result = true;
        }

        return result;
    }
    //#endregion

    //#region Wait
    public static void wait(int miliseconds) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }
        System.out.println("Start Waiting for: " + miliseconds + " milisec.");
        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(miliseconds));
        TimeUnit.MILLISECONDS.sleep(miliseconds);
        System.out.println("End Waiting for: " + miliseconds + " milisec.");
    }
    //#endregion

    //#region Operations

    public static String loginToGmailAndClickVerificationLinkByText(String userName, String password, String linkText) throws Exception{

        String mailURL = "https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";

        openBrowserInstance(mailURL);

        setInputTextByElementID("identifierId", userName);

        clickByElementXPath("//*[@id='identifierNext']/div/button");  

        wait(5000);

        setInputTextByXPath("//*[@id='password']/div[1]/div/div[1]/input", password); 

        clickByElementXPath("//*[@id='passwordNext']");  

        wait(20000);

        clickEmailLink(linkText);

        wait(2000);

        SwithToTab(1);

        String resultText = getTextByTagName("body");

        return resultText;
    } 
    
    public static void SwithToTab(int zeroBasedTabID) throws Exception{
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(zeroBasedTabID));
    }

    private static void clickEmailLink(String linkText) throws Exception{
        
        if(driver == null){
            throw new Exception("Error: Driver is not initialized");
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
        List<WebElement> inboxEmails = null;

        // read new emails
        try {
            inboxEmails = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@class='zA zE']"))));  
        } catch (Exception e) {
            System.out.println(e);
        }

        // read others
        if(inboxEmails == null){
            try {
                inboxEmails = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@class='zA yO']"))));  
            } catch (Exception e) {
                System.out.println(e);
            }
        }
                   
        if (inboxEmails == null){
            throw new Exception("unable to find emails");
        }

        for(WebElement email : inboxEmails){        

            email.click();                                                                                                                                         
            wait(1000);    
            if(isLinkTextExist(linkText) == true){
                clickByLinkText(linkText);
                break;    
            }          
        }  
    }

    // private static void ReadEmails() throws Exception{
        
    //     if(driver == null){
    //         throw new Exception("Error: Driver is not initialized");
    //     }
    //     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 
    //     List<WebElement> inboxEmails = null;

    //     // read new emails
    //     try {
    //         inboxEmails = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@class='zA zE']"))));  
    //     } catch (Exception e) {
    //         System.out.println(e);
    //     }

    //     // read others
    //     if(inboxEmails == null){
    //         try {
    //             inboxEmails = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@class='zA yO']"))));  
    //         } catch (Exception e) {
    //             System.out.println(e);
    //         }
    //     }
                          
       
    //     if (inboxEmails == null){
    //         throw new Exception("unable to find emails");
    //     }
    //     for(WebElement email : inboxEmails){        

    //         boolean i = email.isDisplayed();
    //         String tex = email.getText();

    //         email.click();                                                                                                                                         

    //             WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@title,'with label Inbox')]")));                    
    //             WebElement subject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Subject of this message')]")));          
    //             WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Single line body of this message')]")));   

    //             if(label != null){
    //                 String lab = label.getText();
    //                 System.out.println(lab);
    //             }
    //             if(subject != null){
    //                 String subj = subject.getText();
    //                 System.out.println(subj);
    //             }
    //             if(body != null){
    //                 String bd = body.getText();
    //                 System.out.println(bd);
    //             }                                                                                                                                                  
    //     }  
    // }
    //#endregion
}


