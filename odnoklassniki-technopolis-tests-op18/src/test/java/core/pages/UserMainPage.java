package core.pages;

import core.transformers.FeedPostListTransformer;
import core.wrappers.feed.AbstractFeedPost;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Представляет главную страницу пользователя
 */

public class UserMainPage extends PageBase {

  private static final By FEED_CATEGORY_GROUPS = By
      .xpath("//*[contains(@data-l, 'feedTargetFilterId,205')]");
  private static final By FEED = By.xpath("//*[@class='feed-w']");
  private List<AbstractFeedPost> feedPosts;


  public UserMainPage(WebDriver driver) {
    super(driver);
    new WebDriverWait(driver, 10).until(ExpectedConditions
        .visibilityOfElementLocated(By.xpath("//*[contains(@class, 'nav-side')]")));
  }

  protected void check() {

  }


  /**
   * Открывает страницу "Группы" в левом навбаре
   */
  public void openGroupsByToolbar() {
    click(By.xpath(".//*[contains(@hrefattrs,'NavMenu_User_AltGroups')]"));
  }

  /**
   * Выбирает вкладку "Группы" в ленте
   */
  public void openGroupsFeedCategory() {
    click(FEED_CATEGORY_GROUPS);
    new WebDriverWait(driver, 10).until(ExpectedConditions
        .visibilityOfElementLocated(By.xpath("//*[contains(@class, 'stub-group')]")));
  }

  /**
   * Определяет, есть ли на странице пост с таким текстом
   *
   * @param s текст
   */
  public boolean isTopicPresentInFeedByText(String s) {
    return isElementPresent(
        By.xpath("//*[contains(@class, 'media-text') and contains(text(), \'" + s + "\')]"));
  }

  /**
   * Собирает список всех отображаемых на странице постов ленты
   *
   * @return true - есть посты, false - нет
   */
  public boolean collectFeedPosts() {
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(FEED));
    List<AbstractFeedPost> feedPosts = FeedPostListTransformer.collectPosts(driver);
    if (feedPosts.size() == 0) {
      return false;
    }

    this.feedPosts = feedPosts;
    return true;
  }

  /**
   * Возвращает первый пост из списка, собранного collectFeedPosts()
   *
   * @return враппер поста
   */
  public AbstractFeedPost getFirstPostInFeed() {
    return feedPosts.get(0);
  }
}
