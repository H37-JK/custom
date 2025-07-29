package com.hjk.custom;

import com.hjk.custom.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@AutoConfigureRestDocs
class CustomApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test() throws Exception {
        this.mockMvc.perform(get("/test/{name}", "hjk").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andDo(document("test-api",
                        pathParameters(
                                parameterWithName("name").description("The name to return")
                        ),
                        responseBody()
                ));
    }

    @Test
    void tes2t() throws Exception {
        this.mockMvc.perform(get("/test2/{name}", "hjk").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andDo(document("test-api2",
                        pathParameters(
                                parameterWithName("name").description("The name to return")
                        ),
                        responseBody()
                ));
    }

}
