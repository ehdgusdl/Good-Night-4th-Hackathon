package dev.ticketseller.ticket_seller.domain.common;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

public class BaseEntity {

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedBy
    private LocalDateTime updateAt;

    private boolean is_deleted;
}
