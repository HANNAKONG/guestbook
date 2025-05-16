package com.sb02.guestbookprj;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/guestbooks")
@Slf4j
@RequiredArgsConstructor
public class GuestbookController {

  private final GuestbookService guestbookService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<GuestbookResponseDto> register(
      @RequestParam("name") String name,
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestPart(required = false) MultipartFile image
  ) {
    GuestbookResponseDto guestbookResponseDto = guestbookService.register(name, title, content, image);
    return ResponseEntity.ok(guestbookResponseDto);
  }

  @GetMapping
  public ResponseEntity<PageResponse<GuestbookResponseDto>> getGuestbooks(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size
  ) {
    PageResponse<GuestbookResponseDto> pageResponse = guestbookService.getGuestbooks(page, size);
    return ResponseEntity.ok(pageResponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GuestbookResponseDto> getGuestbook(@PathVariable Long id) {
    GuestbookResponseDto guestbookResponseDto = guestbookService.getGuestbookById(id);
    return ResponseEntity.ok(guestbookResponseDto);
  }

}