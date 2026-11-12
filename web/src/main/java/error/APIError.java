package error;

import java.time.Instant;

public record APIError(
        int status,
        String message,
        Instant timestamp
) {
}