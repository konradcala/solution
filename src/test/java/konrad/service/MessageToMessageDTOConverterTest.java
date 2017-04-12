package konrad.service;

import konrad.model.Message;
import konrad.model.User;
import konrad.rest.MessageDTO;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageToMessageDTOConverterTest {
    private MessageToMessageDTOConverter messageConverter = new MessageToMessageDTOConverter();
    private String username = "konrad";
    private String content = "some content";
    private long dateTimestamp = 12345L;

    @Test
    public void testConvert() {
        //given
        Message input = createMessage(content, username, dateTimestamp);
        MessageDTO expected = new MessageDTO(content, username, dateTimestamp);

        //when
        MessageDTO result = messageConverter.convertToMessageJson(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testConvertList() {
        //given
        Message first = createMessage(content, username, dateTimestamp);
        long secondDateTimestamp = new Date().getTime();
        String secondUsername = "another user";
        String secondContent = "other content";
        Message second = createMessage(secondContent, secondUsername, secondDateTimestamp);

        MessageDTO firstExpected = new MessageDTO(this.content, this.username, this.dateTimestamp);
        MessageDTO secondExpected = new MessageDTO(secondContent, secondUsername, secondDateTimestamp);

        //when
        List<MessageDTO> result = messageConverter.convertToMessageJson(Arrays.asList(first, second));

        //then
        assertThat(result).containsExactly(firstExpected, secondExpected);

    }

    private Message createMessage(String content, String username, long dateTimestamp) {
        User user = new User(username);
        return new Message(content, user, dateTimestamp);
    }
}