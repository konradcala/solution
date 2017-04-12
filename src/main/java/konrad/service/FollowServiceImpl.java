package konrad.service;

import konrad.dao.MessageDao;
import konrad.dao.UserDao;
import konrad.model.Message;
import konrad.model.User;
import konrad.rest.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private MessageToMessageDTOConverter messageConverter;

    @Override
    public void follow(String followerUsername, String followeeUsername) {
        User follower = userDao.findByName(followerUsername);
        if(follower == null) {
            throw new UserNotFoundException(followerUsername);
        }
        User followee = userDao.findByName(followeeUsername);
        if (followee == null) {
            throw new UserNotFoundException(followeeUsername);
        }
        follower.getFollowees().add(followee);
        userDao.save(follower);
    }

    @Override
    public List<MessageDTO> getMessagesForFollower(String followerUsername) {
        User follower = userDao.findByName(followerUsername);
        if(follower == null) {
            throw new UserNotFoundException(followerUsername);
        }

        List<Message> messagesOfFollowees = messageDao.findMessagesByAuthorInOrderByCreationDateTimestampDesc(follower.getFollowees());
        return messageConverter.convertToMessageJson(messagesOfFollowees);
    }
}
