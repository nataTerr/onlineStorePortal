package nata.project.controller;

import lombok.RequiredArgsConstructor;
import nata.project.dtos.response.ProductCardDto;
import nata.project.dtos.response.ProductDto;
import nata.project.service.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping("/products/categories/{categoryId}")
    public Page<ProductDto> findAllByCategoryId(@PathVariable Integer categoryId, Pageable pageable) {
        return productService.findAllByCategoryId(categoryId, pageable);
    }

    @GetMapping("/products/{productId}/detail/info")
    public ProductCardDto findById(@PathVariable long productId) {
        return productService.findById(productId);
    }

}