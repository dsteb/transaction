package it.dsteb.transaction.http;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TransactionRestControllerTest {

  private static final MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;

  private static final String PAYLOAD0 = "{" +
    "\"amount\": %d," +
    "\"type\": \"%s\"" +
  "}";

  private static final String PAYLOAD1 = "{" +
      "\"amount\": %d," +
      "\"type\": \"%s\"," +
      "\"parent_id\": %d" +
    "}";

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }


  @Test
  public void transactionNotFound() throws Exception {
    mockMvc.perform(get("/transactionservice/transaction/1"))
      .andExpect(status().isNotFound());
  }

  @Test
  public void transactionFound() throws Exception {
    String payload = String.format(PAYLOAD0, 5000, "children");
    mockMvc.perform(put("/transactionservice/transaction/2")
        .content(payload)
        .contentType(CONTENT_TYPE))
      .andExpect(status().isOk());
    mockMvc.perform(get("/transactionservice/transaction/2"))
      .andExpect(status().isOk());
  }

  @Test
  public void unknownParent() throws Exception {
    String payload = String.format(PAYLOAD1, 5000, "children", 1000);
    mockMvc.perform(put("/transactionservice/transaction/3")
        .content(payload)
        .contentType(CONTENT_TYPE))
      .andExpect(status().isNotFound());
  }

  @Test
  public void duplicateTransaction() throws Exception {
    String payload = String.format(PAYLOAD0, 5000, "children");
    mockMvc.perform(put("/transactionservice/transaction/4")
        .content(payload)
        .contentType(CONTENT_TYPE))
      .andExpect(status().isOk());
    mockMvc.perform(put("/transactionservice/transaction/4")
        .content(payload)
        .contentType(CONTENT_TYPE))
      .andExpect(status().isConflict());
  }

  @Test
  public void getEmptyType() throws Exception {
    mockMvc.perform(get("/transactionservice/types/unknown"))
      .andExpect(status().isOk())
      .andExpect(content().string("[]"));
  }

  @Test
  public void getByType() throws Exception {
    String payload = String.format(PAYLOAD0, 5000, "cars");
    System.out.println(payload);
    mockMvc.perform(put("/transactionservice/transaction/5")
        .content(payload)
        .contentType(CONTENT_TYPE))
      .andExpect(status().isOk());
    mockMvc.perform(get("/transactionservice/types/cars"))
      .andExpect(status().isOk())
      .andExpect(content().string("[5]"));

  }

  @Test
  public void getSumNotFound() throws Exception {
    mockMvc.perform(get("/transactionservice/sum/1000"))
    .andExpect(status().isNotFound());
  }

  @Test
  public void getSum() throws Exception {
    String payload = String.format(PAYLOAD0, 5000, "shopping");
    mockMvc.perform(put("/transactionservice/transaction/6")
        .content(payload)
        .contentType(CONTENT_TYPE))
      .andExpect(status().isOk());
    payload = String.format(PAYLOAD1, 10000, "shopping", 6);
    mockMvc.perform(put("/transactionservice/transaction/7")
        .content(payload)
        .contentType(CONTENT_TYPE))
      .andExpect(status().isOk());

    MvcResult result = mockMvc.perform(get("/transactionservice/sum/6"))
      .andExpect(status().isOk())
      .andReturn();
    String data = result.getResponse().getContentAsString();
    Assert.assertEquals("{\"sum\":15000.0}", data.replaceAll("\\s+", ""));

    result = mockMvc.perform(get("/transactionservice/sum/7"))
        .andExpect(status().isOk())
        .andReturn();
    data = result.getResponse().getContentAsString();
    Assert.assertEquals("{\"sum\":10000.0}", data.replaceAll("\\s+", ""));
  }
}
