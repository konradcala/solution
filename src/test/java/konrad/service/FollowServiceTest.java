package konrad.service;


import konrad.dao.UserDao;
import konrad.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FollowServiceTest {
    @Autowired
    private FollowService followService;
    @Autowired
    private UserDao userDao;

    @Test
    public void shouldAddFollowee() {
        //given
        User first = new User("first");
        User second = new User("second");
        userDao.save(first);
        userDao.save(second);

        //when
        followService.follow("first", "second");

        //then
        User user = userDao.findByName("first");
        assertThat(user.getFollowees()).hasSize(1);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionWhenFollowerNotFound() {
        //when
        followService.follow("first" , "second");
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionWhenFolloweeNotFound() {
        //given
        User first = new User("first");
        userDao.save(first);

        //when
        followService.follow("first" , "second");
    }
}