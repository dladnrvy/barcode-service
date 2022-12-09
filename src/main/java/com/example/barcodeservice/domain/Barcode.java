package com.example.barcodeservice.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "barcodes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Barcode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", length = 9, unique = true)
    private Long userId;

    @Column(nullable = false, length = 10, unique = true)
    private String barcode;

    @Builder
    public Barcode(Long id, Long userId, String barcode) {
        this.id = id;
        this.userId = userId;
        this.barcode = barcode;
    }
}
