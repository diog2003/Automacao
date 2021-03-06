package automacao;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Automacao {

    private static WebDriver browser;
    private static String loginInstagram = null;
    private static String senhaInstagram = null;
    private static String loginDizu = null;
    private static String senhaDizu = null;

    public static void main(String[] args) throws AWTException, InterruptedException {

        System.setProperty("webdriver.Chrome.driver", "/usr/bin/chromedriver");
        browser = new ChromeDriver();
        
        // ----- Tela Login do Instagram ----- //
        Instagram inst = new Instagram();
        inst.setLoginInstagram(loginInstagram);
        inst.setSenhaInstagram(senhaInstagram);
        inst.setBrowser(browser);
        inst.login();

        // abri uma nova aba no navegador;
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_T);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_T);

        // ----- Nova Aba ------ //
        Dizu dizu = new Dizu();
        dizu.setLoginDizu(loginDizu);
        dizu.setSenhaDizu(senhaDizu);
        dizu.setBrowser(browser);
        dizu.login();
        dizu.paginaConectarGanhar();

        for (int i = 0; i < 1; i++) {
            browser.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);// espera carregar os elementos da pagina;
            WebElement comboBox = browser.findElement(By.id("conta_id")); // cria o objeto comboBox;
            for (WebElement usuarios : comboBox.findElements(By.tagName("option"))) {
                if (usuarios.getText().contains(inst.getLoginInstagram())) { //se o usuario contem na comboBox ele é selecionado; 
                    usuarios.click();
                    System.out.println("usuario selecionado!");
                    inst.seguirUsuario();
                    break;
                }
            }

        }

        browser.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);// espera carregar todos os elementos da pagina;
        Thread.sleep(100);
//        browser.findElement(By.xpath("//*[contains(text(), 'Follow')]")).click();

    }

}
