package konrad.dao;

import konrad.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

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
}