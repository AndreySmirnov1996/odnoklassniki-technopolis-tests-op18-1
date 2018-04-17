package core.wrappers.groupCard;

import org.openqa.selenium.WebElement;

/**
 * Абстрактная обертка для карточек групп
 */

public abstract class AbstractGroupCard {

    protected WebElement rootElement;

    public AbstractGroupCard(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    /**
     * Возвращает название группы
     *
     * @return текст имени
     */
    abstract public String getName();

    /**
     * Возвращает количество подписчиков
     *
     * @return текст имени
     */
    abstract public String getFollowersNumber();


    /**
     * Клик по карте
     */
    abstract public void clickToGroupCard();


}
