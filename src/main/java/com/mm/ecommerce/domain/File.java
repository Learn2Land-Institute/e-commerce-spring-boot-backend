package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.MerchantFileType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private MerchantFileType merchantFile;

    private String fileName;
    private String filePath;

}
