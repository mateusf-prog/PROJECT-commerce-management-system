package br.com.mateus.commercemanagementsystem.services.serviceImpl;

import br.com.mateus.commercemanagementsystem.dto.ProductDTO;
import br.com.mateus.commercemanagementsystem.exceptions.EntityAlreadyExistsException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityInvalidDataException;
import br.com.mateus.commercemanagementsystem.exceptions.EntityNotFoundException;
import br.com.mateus.commercemanagementsystem.model.Category;
import br.com.mateus.commercemanagementsystem.model.OrderItem;
import br.com.mateus.commercemanagementsystem.model.Product;
import br.com.mateus.commercemanagementsystem.repository.ProductRepository;
import br.com.mateus.commercemanagementsystem.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryServiceImpl categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryServiceImpl categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(Product product) {

        Category category = categoryService.findByName(product.getCategory().getName());
        Optional<Product> queryProduct = productRepository.findByName(product.getName());

        if (queryProduct.isPresent()) {
            throw new EntityAlreadyExistsException("Produto já existe!");
        }

        product.setCategory(category);
        Product productSaved = productRepository.save(product);
        ProductDTO productDTO = convertProductToProductDTO(product);
        productDTO.setId(productSaved.getId());
        return productDTO;
    }

    @Override
    public ProductDTO findById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado!");
        }

        ProductDTO productDTO = convertProductToProductDTO(product.get());
        productDTO.setId(product.get().getId());
        return productDTO;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Product product) {

        checkProductExistsById(product.getId());

        Category category = categoryService.findByName(product.getCategory().getName());
        if (category == null) {
            throw new EntityNotFoundException("Categoria não existe!");
        }
        product.setCategory(category);

        product = productRepository.save(product);
        return convertProductToProductDTO(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        checkProductExistsById(id);
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findByName(String name) {

        ProductDTO dto = convertProductToProductDTO(checkProductExistsByName(name));
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {

        List<Product> products = productRepository.findAll();

        if(products.isEmpty()) {
            throw new EntityNotFoundException("Lista vazia!");
        }

        List<ProductDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = convertProductToProductDTO(product);
            productDTO.setId(product.getId());
            listDTO.add(productDTO);
        }
        return listDTO;
    }

    @Override
    @Transactional
    public String adjustStockQuantity(String name, int newQuantity) {

        Product product = checkProductExistsByName(name);
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
                Product product = checkProductExistsByName(item.getProduct().getName());
                product.setQuantity(product.getQuantity() + item.getQuantity());
                updateProduct(product);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int checkQuantityStockAvailability(String name) {

        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto no estoque com o nome: " + name);
        }

        return product.get().getQuantity();
    }

    @Override
    @Transactional
    public String setPrice(Long id, BigDecimal newPrice) {

        Product product = checkProductExistsById(id);
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EntityInvalidDataException("Novo preço inválido!");
        }

        product.setPrice(newPrice);
        updateProduct(product);
        return "Preço atualizado!";
    }

    @Transactional(readOnly = true)
    public Product checkProductExistsByName(String name) {
        Optional<Product> query = productRepository.findByName(name);
        if (query.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado. Nome: " + name);
        }
        return query.get();
    }

    @Transactional(readOnly = true)
    public Product checkProductExistsById(Long id) {
        Optional<Product> query = productRepository.findById(id);
        if (query.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado. ID: " + id);
        }
        return query.get();
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
