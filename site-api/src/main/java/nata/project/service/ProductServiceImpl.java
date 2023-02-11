package nata.project.service;

import lombok.RequiredArgsConstructor;
import nata.project.dtos.converters.ProductCardToDtoConverter;
import nata.project.dtos.converters.ProductToDtoConverter;
import nata.project.dtos.response.ProductCardDto;
import nata.project.dtos.response.ProductDto;
import nata.project.entity.Product;
import nata.project.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductToDtoConverter productConverter;
    private final CategoryService categoryService;
    private final ProductCardToDtoConverter productCardConverter;

    @Override
    @Transactional(readOnly = true)
    public ProductCardDto findById(long productId) {
        return productCardConverter.convert(productRepository.fetchProductPriceByProductIdInnerJoin(productId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> findAllByCategoryId(Integer parentId, Pageable pageable) {
        List<Integer> categoryIds = categoryService.getListChildCategoryIds(parentId);
        List<Product> productsList = productRepository.findAllByCategoryIdIn(categoryIds);
        Page<Product> products = new PageImpl<>(productsList, pageable, productsList.size());
        List<ProductDto> productDtoList = products.getContent().stream()
                .map(productConverter::convert)
                .collect(Collectors.toList());
        return new PageImpl<>(productDtoList, pageable, products.getTotalElements());
    }
}
