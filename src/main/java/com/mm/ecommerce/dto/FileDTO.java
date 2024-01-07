package com.mm.ecommerce.dto;

import com.mm.ecommerce.enums.MerchantFileType;
import lombok.Data;

@Data
public class FileDTO {

    private int id;

    private MerchantFileType merchantFileType;

    private String fileName;
    private String filePath;

}
