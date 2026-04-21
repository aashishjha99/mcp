package org.aashish.mcp.repository;

import java.util.List;
import org.aashish.mcp.dto.HelpDeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpDeskRepository extends JpaRepository<HelpDeskTicket, Long> {

  List<HelpDeskTicket> findByUsername(String username);
}
