package com.example.barcodeservice.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "barcodes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BarcodeEntity implements Serializable {

    /** 바코드 아이디 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="barcode_id")
    private Long barcodeId;

    /** 사용자 아이디 */
    @Column(name="user_id", unique = true, nullable = false)
    private Long userId;

    /** 바코드 */
    @Column(length = 16, unique = true, nullable = false)
    private String barcode;

    /** 생성일 */
    @Column(name="barcode_approved_dt")
    @CreatedDate
    private LocalDateTime barcodeApprovedDt;

    @Builder
    public BarcodeEntity(Long barcodeId, Long userId, String barcode) {
        this.barcodeId = barcodeId;
        this.userId = userId;
        this.barcode = barcode;
    }
}
