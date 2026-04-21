package org.aashish.mcp.tools;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aashish.mcp.dto.HelpDeskTicket;
import org.aashish.mcp.dto.TicketRequest;
import org.aashish.mcp.service.HelpDeskService;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * A Spring component that defines a set of tools for an AI model to interact with a help desk
 * system. These tools allow the AI to create and retrieve support tickets on behalf of a user. The
 * methods in this class are annotated with {@link Tool} to be discoverable by the Spring AI
 * framework.
 */
@Component
@AllArgsConstructor
@Log4j2
public class HelpDeskTools {

  private final HelpDeskService helpDeskService;

  /**
   * Creates a support ticket in the help desk system. This tool is designed to be called by the AI
   * model when a user requests to create a ticket. It extracts the username from the {@link
   * ToolContext} and uses the {@link HelpDeskService} to persist the ticket.
   *
   * @param ticketRequest The details of the ticket to be created, provided by the AI model.
   * @param toolContext The context of the tool call, containing user information.
   * @return A confirmation message with the newly created ticket ID.
   */

  @Tool(
      name = "createSupportTicket",
      description = "Create a support ticket for the user",
      returnDirect = true)
  String createTicket(
      @ToolParam(description = "details to create a support ticket") TicketRequest ticketRequest,
      ToolContext toolContext) {
    String username = toolContext.getContext().get("username").toString();
    log.info("Creating ticket for user: {}, with issue: {}", username, ticketRequest.issues());
    HelpDeskTicket helpDeskTicket = helpDeskService.createTicket(ticketRequest, username);
    return "Ticket #" + helpDeskTicket.getId() + " created successfully for user: " + username;
  }

  /**
   * Retrieves all support tickets associated with the current user. This tool extracts the username
   * from the {@link ToolContext} and queries the {@link HelpDeskService} for a list of tickets.
   *
   * @param toolContext The context of the tool call, containing user information.
   * @return A list of {@link HelpDeskTicket} objects belonging to the user.
   */
  @Tool(name = "getUserTickets", description = "Get all support tickets for the user")
  List<HelpDeskTicket> getUserTickets(ToolContext toolContext) {
    String username = toolContext.getContext().get("username").toString();
    log.info("Getting tickets for user: {}", username);
    return helpDeskService.getTicketsByUsername(username);
  }

}
