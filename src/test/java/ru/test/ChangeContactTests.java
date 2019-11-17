package ru.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.model.*;

import java.util.Comparator;
import java.util.List;

public class ChangeContactTests extends TestBase {

    @BeforeClass
    public void setUp() {
        app.init();
        app.getContactHelper().isThereAContact();
    }

    @Test
    public void changeContact() {
        app.getNavigationHelper().goToHomePage();
        List<ContactUser> before = app.getContactHelper().getContactList();
        ContactUser user = new ContactUser(before.get(before.size() - 1).getId(), "test9", "test8", "test3");
        app.getChangesHelper().clickEditContact();
        app.getContactHelper().fillContactName(user);
        app.getChangesHelper().pressUpdateButton();
        app.getContactHelper().returnToContactPage();
        app.getChangesHelper().clickModifyContact();
        app.getContactHelper().fillPhone(new ContactPhones("test12", "test1", "test4"));
        app.getChangesHelper().pressUpdateButton();
        app.getContactHelper().returnToContactPage();
        List<ContactUser> after = app.getContactHelper().getContactList();
        Comparator<? super ContactUser> byId = Comparator.comparingInt(ContactUser::getId);
        before.sort(byId);
        after.sort(byId);
        if (!before.get(before.size() - 1).getFirstName().equals(user.getFirstName())) {
            Assert.assertNotEquals(before.get(before.size() - 1), after.get(after.size() - 1));
            before.remove(before.size() - 1);
            after.remove(after.size() - 1);
        }
        Assert.assertEquals(before, after);
    }
}
