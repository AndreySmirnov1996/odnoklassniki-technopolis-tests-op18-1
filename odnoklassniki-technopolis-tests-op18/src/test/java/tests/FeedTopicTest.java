package tests;

import core.pages.groups.GroupMainPage;
import core.pages.SessionPage;
import core.pages.UserGroupsPage;
import core.pages.UserMainPage;
import core.wrappers.feed.AbstractFeedPost;
import model.TestBot;
import org.junit.Assert;
import org.junit.Test;

/**
 * Проверяется создание поста в группе и его появление на в ленте групп пользователя
 */
public class FeedTopicTest extends TestBase {

  @Test
  public void testCase() throws Exception {

    //preconditions
    new SessionPage(driver).loginAuth(new TestBot("QA18testbot78", "QA18testbot"));
    new UserMainPage(driver).openGroupsByToolbar();
    UserGroupsPage userGroupsPage = new UserGroupsPage(driver);
    userGroupsPage.createGroupByToolbar();
    userGroupsPage.selectPublicPage();
    userGroupsPage.inputGroupName("AddTopicTestGroup");
    userGroupsPage.selectCategoryComputers();
    userGroupsPage.confirmGroupCreation();

    //test
    GroupMainPage groupMainPage = new GroupMainPage(driver);
    groupMainPage.clickCreateNewTopic();
    groupMainPage.typeTextInNewTopic("Тест доставки ленты");
    groupMainPage.confirmTopicPublication();
    groupMainPage.returnToUserFeed();

    UserMainPage userMainPage = new UserMainPage(driver);
    userMainPage.openGroupsFeedCategory();

    //conditions
    Assert.assertTrue(userMainPage.collectFeedPosts()); //проверяем, что лента не пустая
    AbstractFeedPost firstFeedPost = userMainPage.getFirstPostInFeed();
    //проверяем, что пост соответствует ожиданиям
    Assert.assertEquals("AddTopicTestGroup", firstFeedPost.getAuthor());
    Assert.assertEquals("Тест доставки ленты", firstFeedPost.getText());

  }
}
