package com.example.barcodeservice.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "barcodes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Barcode implements Serializable {

    /** 바코드 아이디 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 사용자 아이디 */
    @Column(name="user_id", length = 9, unique = true)
    private Long userId;

    /** 바코드 */
    @Column(nullable = false, length = 10, unique = true)
    private String barcode;

    @Builder
    public Barcode(Long id, Long userId, String barcode) {
        this.id = id;
        this.userId = userId;
        this.barcode = barcode;
    }
}
