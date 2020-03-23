package com.thesis.onlinemall.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thesis.onlinemall.product.domain.command.CommandConstants.COMMAND_TYPE;
import com.thesis.onlinemall.product.domain.command.CreateCatalogCommand;
import java.io.StringWriter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class ProductApplicationTests {

  private ObjectMapper mapper = new ObjectMapper();

  @Before
  void setUp() throws Exception {
  }

  @Test
  void contextLoads() throws Exception {
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    CreateCatalogCommand createCatalogCommand = new CreateCatalogCommand(StringUtils.EMPTY,
        COMMAND_TYPE.CREATE,
        "保养II类",
        "适用于三年以上车龄GLC车型",
        0,
        "",
        "Neo Lee");
    StringWriter str = new StringWriter();
    mapper.writeValue(str, createCatalogCommand);
    System.out.println(str.toString());

  }

}
