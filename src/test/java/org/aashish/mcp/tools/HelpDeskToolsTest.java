package org.aashish.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.aashish.mcp.dto.HelpDeskTicket;
import org.aashish.mcp.dto.TicketRequest;
import org.aashish.mcp.dto.TicketRequestWithUsername;
import org.aashish.mcp.service.HelpDeskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.model.ToolContext;


@ExtendWith(MockitoExtension.class)
class HelpDeskToolsTest {

    @Mock
    private HelpDeskService helpDeskService;

    @Mock
    private ToolContext toolContext;

    @InjectMocks
    private HelpDeskTools helpDeskTools;

    @Test
    void createTicket_CreatesTicketSuccessfully() {
        // Arrange
        String username = "testuser";
        String issue = "My laptop is broken";
        TicketRequest request = new TicketRequest(issue);
        
        when(toolContext.getContext()).thenReturn(Map.of("username", username));

        HelpDeskTicket mockTicket = new HelpDeskTicket();
        mockTicket.setId(101L);
        mockTicket.setUsername(username);
        mockTicket.setIssue(issue);
        
        when(helpDeskService.createTicket(any(TicketRequest.class), eq(username))).thenReturn(mockTicket);

        // Act
        String result = helpDeskTools.createTicket(request, toolContext);

        // Assert
        assertEquals("Ticket #101 created successfully for user: testuser", result);
    }

    @Test
    void getUserTickets_ReturnsListOfTickets() {
        // Arrange
        String username = "testuser";
        when(toolContext.getContext()).thenReturn(Map.of("username", username));

        HelpDeskTicket ticket1 = new HelpDeskTicket();
        ticket1.setId(101L);
        ticket1.setUsername(username);
        ticket1.setIssue("Issue 1");

        HelpDeskTicket ticket2 = new HelpDeskTicket();
        ticket2.setId(102L);
        ticket2.setUsername(username);
        ticket2.setIssue("Issue 2");

        List<HelpDeskTicket> mockTickets = List.of(ticket1, ticket2);

        when(helpDeskService.getTicketsByUsername(username)).thenReturn(mockTickets);

        // Act
        List<HelpDeskTicket> result = helpDeskTools.getUserTickets(toolContext);

        // Assert
        assertEquals(2, result.size());
        assertEquals(101L, result.get(0).getId());
        assertEquals("Issue 1", result.get(0).getIssue());
    }
}
