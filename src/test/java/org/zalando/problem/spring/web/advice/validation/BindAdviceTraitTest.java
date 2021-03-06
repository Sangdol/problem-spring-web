package org.zalando.problem.spring.web.advice.validation;

import org.junit.Test;
import org.zalando.problem.spring.web.advice.AdviceTraitTesting;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BindAdviceTraitTest implements AdviceTraitTesting {
    @Test
    public void invalidRequestQueryParams() throws Exception {
        mvc().perform(request(GET, "http://localhost/api/handler-invalid-query-strings?page=-1&size=0"))
             .andExpect(status().isBadRequest())
             .andExpect(header().string("Content-Type", is("application/problem+json")))
             .andExpect(jsonPath("$.type", is("https://zalando.github.io/problem/constraint-violation")))
             .andExpect(jsonPath("$.title", is("Constraint Violation")))
             .andExpect(jsonPath("$.status", is(400)))
             .andExpect(jsonPath("$.violations", hasSize(2)))
             .andExpect(jsonPath("$.violations[0].field", is("page")))
             .andExpect(jsonPath("$.violations[0].message", is("must be greater than or equal to 0")))
             .andExpect(jsonPath("$.violations[1].field", is("size")))
             .andExpect(jsonPath("$.violations[1].message", is("must be greater than or equal to 1")));
    }
}
