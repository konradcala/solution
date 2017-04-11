package konrad.dao;

import konrad.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestEntityManager entityManager;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void userNameIsUnique() {
        //given
        User first = new User("test");
        User second = new User("test");
        userDao.save(first);

        //when then
        expectedException.expect(DataIntegrityViolationException.class);
        userDao.save(second);
    }

    @Test
    public void addFollowees() {
        //given
        User first = new User("first");
        User second = new User("second");
        User third = new User("third");

        third.getFollowees().add(first);
        third.getFollowees().add(second);

        userDao.save(first);
        userDao.save(second);
        userDao.save(third);
        entityManager.flush();
        entityManager.detach(third);

        //when
        User user = userDao.findByName(third.getName());

        //then
        assertThat(user.getFollowees()).contains(first, second);
    }
}