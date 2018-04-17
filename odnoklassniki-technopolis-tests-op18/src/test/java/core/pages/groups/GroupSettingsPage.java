package core.pages.groups;

import core.pages.PageBase;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Представляет страницу настроек группы
 */
public class GroupSettingsPage extends PageBase {

  private static final By NAV_PANEL = By.xpath("//*[contains(@id, 'GroupSettingsCatalog')]");
  private static final By CHANGE_TYPE = By.xpath("//*[contains(@hrefattrs, 'GroupChangeType')]");
  private static final By CHANGE_TYPE_CONFIRM_POPUP = By.xpath("//*[contains(@id, 'GroupChangeType')]");
  private static final By FORM_CONFIRM = By.xpath("//*[@data-l='t,confirm']");
  private static final By PRIVACY_DROPDOWN = By.xpath("//*[@id='field_privacy']");
  private static final By SAVE_SETTINGS = By.xpath("//*[@name='button_save_settings']");
  private static final By TIP = By.xpath("//*[@data-module='NonBlockingTip']//*[@class='tip_cnt']//*");
  private String lastTipText = "NO TIP";

  public GroupSettingsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void check() {
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(NAV_PANEL));
  }


  /**
   * Меняет тип группы на страницу и обратно, подтверждая в попапе, обновляет уведомление
   */
  public void changeType() {
    click(CHANGE_TYPE);
    new WebDriverWait(driver, 5)
        .until(ExpectedConditions.visibilityOfElementLocated(CHANGE_TYPE_CONFIRM_POPUP));
    click(FORM_CONFIRM);
    updateLastTip();
  }

  /**
   * Выбирает в списке типов группы (не страницы) тип, отвечающий за доступ
   *
   * @param type тип группы
   */
  public void changePrivacy(GroupPrivacy type) {
    Select select = new Select(driver.findElement(PRIVACY_DROPDOWN));
    switch (type) {
      case OPEN:
        select.selectByValue("OPEN");
        break;
      case PRIVATE:
        select.selectByValue("BY_MEMBER_INVITATION_AND_REQUEST");
        break;
      case SECRET:
        select.selectByValue("BY_MEMBER_INVITATION");
        break;
    }
  }


  /**
   * Нажимает подтверждение внесенных изменений и обновляет последнее уведомление
   */
  public void confirmSettings() {
    click(SAVE_SETTINGS);
    updateLastTip();
  }

  /**
   * Обновляет последнее всплывающее уведомление (используйте getLastTipText() чтобы его получить)
   */
  private void updateLastTip() {
    try {
      new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(TIP));
      lastTipText = driver.findElement(TIP).getText();
    } catch (TimeoutException te) {
      lastTipText = "NO TIP";
    }

    //ждем, пока исчезнет уведомление, иначе новые уведомления не будут появляться
    try {
      new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(TIP));
    } catch (TimeoutException te) {
      Assert.fail("Settings tip is not disappearing");
    }
  }

  /**
   * Возвращает текст последнего всплывающего уведомления
   *
   * @return текст уведомления
   */
  public String getLastTipText() {
    return lastTipText;
  }


}
