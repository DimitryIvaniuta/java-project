package com.javaproject.server.controller;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    /**
     * Session tracking & management capabilities.
     */
    private final SessionRegistry sessionRegistry;

    /**
     * SessionController session controller constructor.
     *
     * @param sessionRegistry session tracking registry
     */
    public SessionController(final SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    /**
     * Fetch active users list.
     *
     * @return a list of users
     */
    @GetMapping("/active-users")
    public List<String> getActiveUsers() {
        return sessionRegistry.getAllPrincipals().stream()
                .filter(UserDetails.class::isInstance)
                .map(principal -> ((UserDetails) principal).getUsername())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Fetch active sessions number.
     *
     * @return a sessions number
     */
    @GetMapping("/active-sessions")
    public int getActiveSessionCount() {
        return sessionRegistry.getAllPrincipals().stream()
                .mapToInt(principal -> sessionRegistry.getAllSessions(principal, false).size())
                .sum();
    }

    /**
     * Fetch sessions info.
     *
     * @return a list of sessions info
     */
    @GetMapping("/sessions")
    public List<String> getSessionsInfo() {
        return sessionRegistry.getAllPrincipals().stream()
                .flatMap(principal -> sessionRegistry.getAllSessions(principal, false).stream())
                .map(sessionInformation -> "Session ID: " + sessionInformation.getSessionId()
                        + ", Username: " + ((UserDetails) sessionInformation.getPrincipal()).getUsername()
                        + ", Last Request: " + sessionInformation.getLastRequest())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Invalidate specific session by ID.
     *
     * @param sessionId a session ID
     * @return a message
     */
    @GetMapping("/invalidate-session")
    public String invalidateSession(final String sessionId) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(principal, false)) {
                if (sessionInformation.getSessionId().equals(sessionId)) {
                    sessionInformation.expireNow();
                    return "Session invalidated: " + sessionId;
                }
            }
        }
        return "Session not found: " + sessionId;
    }

    /**
     * Fetch user sessions list.
     *
     * @return a list of sessions
     */
    @GetMapping("/user-sessions")
    public List<String> userSessions() {
        return sessionRegistry.getAllPrincipals().stream()
                .filter(UserDetails.class::isInstance)
                .map(principal -> {
                    String username = ((UserDetails) principal).getUsername();
                    int sessionCount = sessionRegistry.getAllSessions(principal, false).size();
                    return "User: " + username + ", Active Sessions: " + sessionCount;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
