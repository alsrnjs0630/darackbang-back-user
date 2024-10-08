package com.lab.darackbang.service;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface ProductService {
    PageDTO<ProductDTO,ProductSearchDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable);

    ResponseEntity<Resource> getFile(String fileName);
}
