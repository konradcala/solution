package konrad.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FollowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldAddFollowee() throws Exception {
        //given
        mockMvc.perform(post("/messages/first").content("first message"));
        mockMvc.perform(post("/messages/second").content("message of the second user"));

        //when
        mockMvc.perform(post("/follow/first").content("second")).andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNotFoundStatusForNonExistingFollower() throws Exception {
        //when then
        mockMvc.perform(post("/follow/first").content("second")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(2)));
        mockMvc.perform(get("/follow/first")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(2)));
    }

    @Test
    public void shouldReturnNotFoundStatusForNonExistingFollowee() throws Exception {
        //given
        mockMvc.perform(post("/messages/first").content("first message"));
        //when then
        mockMvc.perform(post("/follow/first").content("second")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(2)));
    }

    @Test
    public void shouldReturnFolloweesMessages() throws Exception {
        //given
        mockMvc.perform(post("/messages/first").content("first message"));
        mockMvc.perform(post("/messages/second").content("second user message 1"));
        mockMvc.perform(post("/messages/third").content("third user message 1"));
        mockMvc.perform(post("/messages/fourth").content("fourth user message 1"));
        mockMvc.perform(post("/messages/second").content("second user message 2"));

        mockMvc.perform(post("/follow/first").content("second"));
        mockMvc.perform(post("/follow/first").content("third"));

        //when then
        mockMvc.perform(get("/follow/first")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].content", is("second user message 2")))
                .andExpect(jsonPath("$[1].content", is("third user message 1")))
                .andExpect(jsonPath("$[2].content", is("second user message 1")));
    }
}
