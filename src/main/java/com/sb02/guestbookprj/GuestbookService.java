package com.sb02.guestbookprj;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestbookService {

  private final FileService fileService;
  private final GuestbookRepository guestbookRepository;

  public GuestbookResponseDto register(String name, String title, String content, MultipartFile image) {
    String imageUrl = null;

    if (image != null && !image.isEmpty()) {
      imageUrl = fileService.upload(image);
    }

    GuestbookEntity entity = GuestbookEntity.builder()
        .name(name)
        .title(title)
        .content(content)
        .imageUrl(imageUrl)
        .createdAt(Instant.now())
        .build();

    GuestbookEntity saved = guestbookRepository.save(entity);

    return new GuestbookResponseDto(
        saved.getId(),
        saved.getName(),
        saved.getTitle(),
        saved.getContent(),
        saved.getImageUrl(),
        saved.getCreatedAt()
    );
  }

  public PageResponse<GuestbookResponseDto> getGuestbooks(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    Page<GuestbookEntity> guestbooks = guestbookRepository.findAll(pageable);

    List<GuestbookResponseDto> guestbookResponseDtos = guestbooks.stream()
        .map(guestbook -> new GuestbookResponseDto(
            guestbook.getId(),
            guestbook.getName(),
            guestbook.getTitle(),
            guestbook.getContent(),
            guestbook.getImageUrl(),
            guestbook.getCreatedAt()
        ))
        .toList();

    return new PageResponse<>(
        guestbookResponseDtos,
        guestbooks.getTotalPages(),
        guestbooks.getTotalElements(),
        guestbooks.getSize(),
        guestbooks.getNumber()
    );
  }

  public GuestbookResponseDto getGuestbookById(Long id) {
    GuestbookEntity guestbook = guestbookRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Guestbook with id " + id + " not found"));

    return new GuestbookResponseDto(
        guestbook.getId(),
        guestbook.getName(),
        guestbook.getTitle(),
        guestbook.getContent(),
        guestbook.getImageUrl(),
        guestbook.getCreatedAt()
    );
  }

}
