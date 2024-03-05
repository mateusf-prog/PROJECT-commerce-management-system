package br.com.mateus.commercemanagementsystem.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.mapping.Any;

import static br.com.mateus.commercemanagementsystem.common.ProductConstants.PRODUCT_DTO;
import static br.com.mateus.commercemanagementsystem.common.ProductConstants.INVALID_PRODUCT_DTO;
import static br.com.mateus.commercemanagementsystem.common.ProductConstants.PRODUCT;
import static br.com.mateus.commercemanagementsystem.common.ProductConstants.INVALID_PRODUCT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.services.CategoryService;
import br.com.mateus.commercemanagementsystem.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

     @InjectMocks
     private ProductService productService;

     @Mock
     private ProductRepository productRepository;
     @Mock
     private CategoryService categoryService;
     
     @Test
     public void createProduct_ByUnexisting_ReturnProduct() {
          Category category = new Category();

          when(categoryService.findByName(any())).thenReturn(category);
          when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);

          ProductDTO sut = productService.createProduct(PRODUCT);

          assertThat(sut).isNotNull();
          assertThat(sut.getName()).isEqualTo(PRODUCT.getName());
          assertThat(sut.getPrice()).isEqualTo(PRODUCT.getPrice());
     }
}
