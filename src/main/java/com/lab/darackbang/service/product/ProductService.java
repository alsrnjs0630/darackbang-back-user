package com.lab.darackbang.service.product;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    PageDTO<ProductDTO,ProductSearchDTO> findAll(ProductSearchDTO searchDTO, Pageable pageable);

    ProductDTO findOne(Long id);

    ResponseEntity<Resource> getFile(String fileName);
}
