package ch.zkk0.football.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response object for sending a simple message.
 */
@Setter
@Getter
@AllArgsConstructor
public class MessageResponse {
    /**
     * The message content.
     */
    private String message;
}
