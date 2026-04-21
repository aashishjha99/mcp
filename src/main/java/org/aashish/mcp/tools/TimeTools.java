package org.aashish.mcp.tools;

import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TimeTools {

  /**
   * @return
   */
  @Tool(name = "getCurrentLocalDateTime", description = "Get the current time in user's timezone")
  public String getCurrentLocalDateTime() {
    log.info("Getting current time");
    return "Current time is: " + LocalDateTime.now();
  }

  @Tool(name = "getCurrentTimeZone", description = "get the current time in the specific time zone")
  public String getCurrentTime(
      @ToolParam(description = "value representing the time zone") String timezone) {
    log.info("Getting current time for timezone:{}", timezone);
    return LocalDateTime.now(ZoneId.of(timezone)).toString();
  }
}
