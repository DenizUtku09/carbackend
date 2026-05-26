package ashina.carrental.renter.dto;

public record SendMessageRequest(
        String fromName,
        String fromEmail,
        String fromPhone,
        String content
) {}
