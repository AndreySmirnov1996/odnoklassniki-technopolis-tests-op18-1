package core.wrappers.feed;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Абстрактная обертка для постов в ленте от групп и пользователей
 */
public abstract class AbstractFeedPost {

  enum FeedPostType {GROUP, USER}

  protected WebElement rootElement;
  private static final By POST_TEXT = By.xpath("//*[contains(@class, 'textWrap')]");

  public AbstractFeedPost(WebElement rootElement) {
    this.rootElement = rootElement;
  }

  /**
   * Возвращает имя автора поста
   *
   * @return текст имени
   */
  abstract public String getAuthor();

  /**
   * Возвращает тип поста
   *
   * @return enum с типом поста
   */
  abstract public FeedPostType getType();

  /**
   * @return Число комментариев на посте
   */
  public Integer getCommentsCounter() {
    return null;
  }

  /**
   * @return число классов на посте
   */
  public Integer getKlassCounter() {
    return null;
  }

  /**
   * @return Число шар на посте
   */
  public Integer getShareCounter() {
    return null;
  }

  /**
   * @return текст поста
   */
  public String getText() {
    return rootElement.findElement(POST_TEXT).getText();
  }

  //TODO заменить на промисы где будут

  /**
   * Открыть комменты на посте
   */
  public void openComments() {

  }

  /**
   * Решарнуть пост
   */
  public void reshare() {

  }

  /**
   * Поставить класс
   */
  public void doKlass() {

  }
}
