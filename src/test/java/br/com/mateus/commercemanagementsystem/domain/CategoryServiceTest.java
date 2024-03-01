package br.com.mateus.commercemanagementsystem.domain;

import static br.com.mateus.commercemanagementsystem.common.CategoryConstants.INVALID_CATEGORY;
import static br.com.mateus.commercemanagementsystem.common.CategoryConstants.CATEGORY;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.repository.CategoryRepository;
import br.com.mateus.commercemanagementsystem.services.CategoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void createCategory_WithValidData_ReturnsCategory() {

        Category category = new Category("Electronics");
        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(category);

        CategoryDTO sut = categoryService.createCategory(CATEGORY);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(CATEGORY);

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).saveAndFlush(categoryCaptor.capture());
        Category savedCategory = categoryCaptor.getValue();

        assertEquals(CATEGORY.getName(), savedCategory.getName());
    }

    @Test
    public void createCategory_WithInvalidData_ThrowsException() {

        Category category = new Category("");
        when(categoryRepository.saveAndFlush(any(Category.class))).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> categoryService.createCategory(INVALID_CATEGORY)).isInstanceOf(RuntimeException.class);
    }

}