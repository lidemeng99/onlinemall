package com.thesis.onlinemall.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thesis.onlinemall.product.domain.command.AddChildCatalogCommand;
import com.thesis.onlinemall.product.domain.command.CommandConstants.COMMAND_TYPE;
import com.thesis.onlinemall.product.domain.command.CreateCatalogCommand;
import com.thesis.onlinemall.product.domain.command.UpdateCatalogCommand;
import com.thesis.onlinemall.product.domain.entity.CatalogEntity;
import java.io.StringWriter;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.JVM)
public class CatalogRestControllerTest {

  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper mapper = new ObjectMapper();
  private String catalogID = UUID.randomUUID().toString();

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  @Test
  public void create() throws Exception {

    CreateCatalogCommand createCatalogCommand = new CreateCatalogCommand(StringUtils.EMPTY,
        COMMAND_TYPE.CREATE,
        "保养II类",
        "适用于三年以上车龄GLC车型",
        0,
        "",
        "Neo Lee");
    StringWriter str = new StringWriter();
    mapper.writeValue(str, createCatalogCommand);
    String content = str.toString();
    System.out.println(content);
    String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/catalog")
        //请求编码和数据格式为json和UTF8
        .contentType(MediaType.APPLICATION_JSON)
        //请求的参数，为json的格式
        .content(content))
        //期望的返回值 或者返回状态码
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        //返回请求的字符串信息
        .andReturn().getResponse().getContentAsString();
    System.out.println(result);
  }

  @Test
  public void findall() throws Exception {
    String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/catalogs")
        .contentType(MediaType.APPLICATION_JSON).param("pid", ""))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andReturn().getResponse().getContentAsString();
    System.out.println(result);
  }

//  @Test
//  public void submit() throws Exception {
//    CatalogEntity catalogEntity = new CatalogEntity();
//    catalogEntity.setCatalogId(catalogID);
//    catalogEntity.setModifier("Jane,Jiang");
//    UpdateCatalogCommand command = new UpdateCatalogCommand(catalogEntity, COMMAND_TYPE.SUBMIT);
//    System.out.println(mapper.writeValueAsString(command));
//    String result = mockMvc
//        .perform(
//            MockMvcRequestBuilders.put("/api/catalog/" + catalogEntity.getCatalogId() + "/status")
//                //请求编码和数据格式为json和UTF8
//                .contentType(MediaType.APPLICATION_JSON)
//                //请求的参数，为json的格式
//                .content(mapper.writeValueAsString(command)))
//        //期望的返回值 或者返回状态码
//        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//        //返回请求的字符串信息
//        .andReturn().getResponse().getContentAsString();
//    System.out.println(result);
//  }

//  @Test
//  public void addChild() throws Exception {
//    AddChildCatalogCommand addChildCatalogCommand = new AddChildCatalogCommand(
//        "化学品", "FOR GLC400 SPORT", 0, catalogID, "Duck,Chen"
//    );
//    System.out.println(mapper.writeValueAsString(addChildCatalogCommand));
//    String result = mockMvc
//        .perform(
//            MockMvcRequestBuilders
//                .post("/api/catalog/" + addChildCatalogCommand.getParentId() + "/child")
//                //请求编码和数据格式为json和UTF8
//                .contentType(MediaType.APPLICATION_JSON)
//                //请求的参数，为json的格式
//                .content(mapper.writeValueAsString(addChildCatalogCommand)))
//        //期望的返回值 或者返回状态码
//        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//        //返回请求的字符串信息
//        .andReturn().getResponse().getContentAsString();
//    System.out.println(result);
//  }

}