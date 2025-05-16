package com.sb02.guestbookprj;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<GuestbookEntity, Long> {

}
