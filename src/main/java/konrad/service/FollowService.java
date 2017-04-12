package konrad.service;

import konrad.rest.MessageDTO;

import java.util.List;

public interface FollowService {
    void follow(String follower, String followee);

    List<MessageDTO> getMessagesForFollower(String follower);
}
