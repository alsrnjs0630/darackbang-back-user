package com.lab.darackbang.controller;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.product.ProductDTO;
import com.lab.darackbang.dto.product.ProductSearchDTO;
import com.lab.darackbang.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("api/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public PageDTO<ProductDTO,ProductSearchDTO> list(@ModelAttribute ProductSearchDTO productSearchDTO,
                                    @PageableDefault(size = 12, sort = "pno", direction = Sort.Direction.DESC) Pageable pageable) {
        return productService.findAll(productSearchDTO, pageable);
    }

    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id) {
        return productService.findOne(id);
    }

    /**
     * 상품이미지 보기
     * @param filename
     * @return
     * @throws IOException
     */
    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) throws IOException {
        return productService.getFile(filename);
    }

}
