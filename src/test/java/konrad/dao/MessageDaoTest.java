package konrad.dao;

import konrad.model.Message;
import konrad.model.User;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageDaoTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MessageDao messageDao;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void unableToSaveTooLongMessage() {
        //given
        User user = new User("testUser");

        //when t
        expectedException.expect(DataIntegrityViolationException.class);
        messageDao.save(new Message("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", user));
    }

}