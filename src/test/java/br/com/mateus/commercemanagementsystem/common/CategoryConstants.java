package br.com.mateus.commercemanagementsystem.common;

import br.com.mateus.commercemanagementsystem.dto.CategoryDTO;
import br.com.mateus.commercemanagementsystem.model.Category;

public class CategoryConstants {
     public static final CategoryDTO INVALID_CATEGORY_DTO = new CategoryDTO("");
     public static final CategoryDTO CATEGORY_DTO = new CategoryDTO("Electronics");

     public static final Category CATEGORY = new Category("Smartphones");
}
