package konrad.rest;

import konrad.service.FollowService;
import konrad.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow/{follower}")
public class FollowController {
    @Autowired
    private FollowService followService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createMessage(@PathVariable String follower, @RequestBody String followee) {
        followService.follow(follower, followee);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<MessageDTO> getMessagesForUser(@PathVariable String follower) {
        return followService.getMessagesForFollower(follower);

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error userDoesNotExist(UserNotFoundException exception) {
        return new Error(2, exception.getMessage());
    }
}
