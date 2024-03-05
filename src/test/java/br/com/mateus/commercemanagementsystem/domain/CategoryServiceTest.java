package br.com.mateus.commercemanagementsystem.domain;

import static br.com.mateus.commercemanagementsystem.common.CategoryConstants.INVALID_CATEGORY_DTO;
import static br.com.mateus.commercemanagementsystem.common.CategoryConstants.CATEGORY_DTO;
import static br.com.mateus.commercemanagementsystem.common.CategoryConstants.CATEGORY;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.repository.CategoryRepository;
import br.com.mateus.commercemanagementsystem.services.CategoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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

        CategoryDTO sut = categoryService.createCategory(CATEGORY_DTO);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(CATEGORY_DTO);

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).saveAndFlush(categoryCaptor.capture());
        Category savedCategory = categoryCaptor.getValue();

        assertEquals(CATEGORY_DTO.getName(), savedCategory.getName());
    }

    @Test
    public void createCategory_WithInvalidData_ThrowsException() {

        Category category = new Category("");
        when(categoryRepository.saveAndFlush(any(Category.class))).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> categoryService.createCategory(INVALID_CATEGORY_DTO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getByName_ByExistsName_ReturnsCategory() {
        
        when(categoryRepository.findByName(CATEGORY.getName())).thenReturn(Optional.of(CATEGORY));

        Category sut = categoryService.findByName(CATEGORY.getName());

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(CATEGORY);
    }

    @Test
    public void getByName_ByUnexistingName_ThrowsException() {
        final String name = "Unexisting name";

        when(categoryRepository.findByName(name)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> categoryService.findByName(name)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void removeCategory_ByExistingCategory_doesNotThrowAnyException() {
        when(categoryRepository.findByName("Eletronics")).thenReturn(Optional.of(new Category("Eletronics")));

        CategoryService categoryService = new CategoryService(categoryRepository);
    
        assertThatCode(() -> categoryService.deleteByName("Eletronics")).doesNotThrowAnyException();
    }

    @Test
    public void removeCategory_ByUnexistingCategory_ThrowsException() {

        doThrow(new RuntimeException()).when(categoryRepository).findByName("Electronis");

        assertThatCode(() -> categoryService.deleteByName("Eletronics")).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getAll_ReturnsListOfCategories() {

        List<Category> expectedCategories = Arrays.asList(new Category("Electronics"));
        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        List<CategoryDTO> listDtos = expectedCategories.stream().map(x -> new CategoryDTO(x.getName())).toList();
        List<CategoryDTO> returnedCategories = categoryService.findAll();

        assertThat(returnedCategories.get(0).getName()).isEqualTo(listDtos.get(0).getName());
    }

    @Test
    public void getAll_ReturnsNoCategories() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatCode(() -> categoryService.findAll()).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void updateCategory_ByExistsId_ReturnsCategory() throws Exception {
        Field field = Category.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(CATEGORY, 1L); // Set the ID of CATEGORY
    
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY));
    
        Category sut = categoryService.updateCategory(CATEGORY);
    
        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo("Smartphones");
    }

    @Test
    public void updateCategory_ByUnexistingId_ThrowsException() {
        try {
            Field field = Category.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(CATEGORY, 99L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        doThrow(new RuntimeException()).when(categoryRepository).findById(99L); 

        assertThatCode(() -> categoryService.updateCategory(CATEGORY)).isInstanceOf(RuntimeException.class);
    }
}