package org.aashish.mcp.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aashish.mcp.dto.HelpDeskTicket;
import org.aashish.mcp.dto.TicketRequest;
import org.aashish.mcp.repository.HelpDeskRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpDeskService {
  private final HelpDeskRepository helpDeskTicketRepository;

  public HelpDeskTicket createTicket(TicketRequest ticketInput, String username) {
    HelpDeskTicket ticket =
        HelpDeskTicket.builder()
            .issue(ticketInput.issues())
            .username(username)
            .status("OPEN")
            .createdAt(LocalDateTime.now())
            .eta(LocalDateTime.now().plusDays(7))
            .build();
    return helpDeskTicketRepository.save(ticket);
  }

  public List<HelpDeskTicket> getTicketsByUsername(String username) {
    return helpDeskTicketRepository.findByUsername(username);
  }
}
