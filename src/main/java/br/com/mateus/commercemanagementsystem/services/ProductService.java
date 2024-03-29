package br.com.mateus.commercemanagementsystem.services;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.ResourceNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public ProductDTO createProduct(Product product) {

        Category category = categoryService.findByName(product.getCategory().getName());
        Optional<Product> entity = productRepository.findByName(product.getName());

        if (entity.isPresent()) {
            throw new EntityAlreadyExistsException("Produto já existe!");
        }

        product.setCategory(category);
        product = productRepository.save(product);
        ProductDTO dto = convertProductToProductDTO(product);
        dto.setId(product.getId());
        return dto;
    }

    public ProductDTO findById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado!");
        }

        ProductDTO productDTO = convertProductToProductDTO(product.get());
        productDTO.setId(product.get().getId());
        return productDTO;
    }

    @Transactional
    public ProductDTO updateProduct(Product product) {

        checkProductExistsById(product.getId());

        Category category = categoryService.findByName(product.getCategory().getName());
        if (category == null) {
            throw new ResourceNotFoundException("Categoria não existe!");
        }
        product.setCategory(category);

        product = productRepository.save(product);
        return convertProductToProductDTO(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO findByName(String name) {

        Product product = productRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado. Verifique o nome digitado. NOME: " + name));

        return convertProductToProductDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {

        List<Product> products = productRepository.findAll();

        if(products.isEmpty()) {
            throw new ResourceNotFoundException("Lista de produtos vazia!");
        }

        List<ProductDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = convertProductToProductDTO(product);
            productDTO.setId(product.getId());
            listDTO.add(productDTO);
        }
        return listDTO;
    }

    @Transactional
    public String adjustStockQuantity(Long id, int newQuantity) {

        Product product = checkProductExistsById(id);
        if (newQuantity < 0) {
            throw new EntityInvalidDataException("Quantidade inválida!");
        }

        product.setQuantity(newQuantity);
        updateProduct(product);
        return "Quantidade em estoque atualizada!";
    }

    @Transactional
    public void returnQuantityInStockAfterCanceledOrder(List<OrderItem> list) {

         for(OrderItem item : list) {
                Product product = checkProductExistsById(item.getProduct().getId());
                product.setQuantity(product.getQuantity() + item.getQuantity());
                updateProduct(product);
        }
    }

    @Transactional(readOnly = true)
    public int checkQuantityStockAvailability(Long id) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Nenhum produto no estoque com o ID: " + id));

        return product.getQuantity();
    }

    @Transactional(readOnly = true)
    protected Product checkProductExistsById(Long id) {

        if (id == null) {
            throw new EntityInvalidDataException("O ID não pode ser nulo");
        }

        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado. ID: " + id));
    }

    public ProductDTO convertProductToProductDTO(Product product) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory().getName());
        return productDTO;
    }
}
