package com.example.microservice.card.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor(){
		return Optional.of("CARDS_MS");
	}
}
