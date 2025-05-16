package com.sb02.guestbookprj;

import java.util.List;

public record PageResponse<GuestbookResponseDto>(
    List<GuestbookResponseDto> content,
    int totalPages,
    long totalElements,
    int size,
    int number
) {

}
