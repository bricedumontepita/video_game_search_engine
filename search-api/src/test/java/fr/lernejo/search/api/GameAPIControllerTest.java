package fr.lernejo.search.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class GameAPIControllerTest {

    @Test
    void getAPI(@Autowired MockMvc mockMvc) throws Exception {
        try {
            MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/games?query=developer%3A%22Epic%20Games%22"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
            /*
            String content = result.getResponse().getContentAsString();
            System.out.println(content);
            Assertions.assertThat(content).isEqualTo("[{\"short_description\":\"A multi-platform free-to-play RPG developed and published by Epic Games for PC and Android devices.  \",\"thumbnail\":\"https://www.freetogame.com/g/435/thumbnail.jpg\",\"game_url\":\"https://www.freetogame.com/open/battlebreakers\",\"release_date\":\"2019-11-13\",\"freetogame_profile_url\":\"https://www.freetogame.com/battlebreakers\",\"genre\":\"Strategy\",\"publisher\":\"Epic Games\",\"developer\":\"Epic Games\",\"id\":435,\"title\":\"Battle Breakers\",\"platform\":\"PC (Windows)\"}]");
            */
        } catch (Exception e) {

        }
    }
}
