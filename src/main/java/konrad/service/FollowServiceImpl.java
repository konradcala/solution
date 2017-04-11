package konrad.service;

import konrad.dao.UserDao;
import konrad.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {
    @Autowired
    private UserDao userDao;

    @Override
    public void follow(String followerUsername, String followeeUsername) {
        User follower = userDao.findByName(followerUsername);
        User followee = userDao.findByName(followeeUsername);
        follower.getFollowees().add(followee);
        userDao.save(follower);
    }
}
