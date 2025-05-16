package com.sb02.guestbookprj;

import java.time.Instant;

public record GuestbookResponseDto(
    Long id,
    String name,
    String title,
    String content,
    String imageUrl,
    Instant createdAt
) {

}
