package org.example.myselectshop.service;

import org.example.myselectshop.dto.ProductMypriceRequestDto;
import org.example.myselectshop.dto.ProductRequestDto;
import org.example.myselectshop.dto.ProductResponseDto;
import org.example.myselectshop.entity.User;
import org.example.myselectshop.entity.UserRoleEnum;
import org.example.myselectshop.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceIntegrationTest {

    @Autowired
    ProductService productService;

    @Autowired
    UserRepository userRepository;

    private User user;
    private ProductResponseDto createdProduct;
    private int updatedMyPrice;

    @BeforeAll
    void setUp() {
        user = userRepository.save(
                new User(
                        "testuser",
                        "testpassword",
                        "testuser@test.com",
                        UserRoleEnum.USER
                )
        );
    }

    @Test
    @Order(1)
    @DisplayName("신규 관심상품 등록")
    void test1() {
        // given
        String title = "Apple <b>에어팟</b> 2세대 유선충전 모델 (MV7N2KH/A)";
        String imageUrl = "https://shopping-phinf.pstatic.net/main_1862208/18622086330.20200831140839.jpg";
        String linkUrl = "https://search.shopping.naver.com/gate.nhn?id=18622086330";
        int lPrice = 173900;

        ProductRequestDto requestDto = new ProductRequestDto(
                title,
                imageUrl,
                linkUrl,
                lPrice
        );

        // when
        ProductResponseDto product = productService.createProduct(requestDto, user);

        // then
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(title, product.getTitle());
        assertEquals(imageUrl, product.getImage());
        assertEquals(linkUrl, product.getLink());
        assertEquals(lPrice, product.getLprice());
        assertEquals(0, product.getMyprice());

        createdProduct = product;
    }

    @Test
    @Order(2)
    @DisplayName("신규 등록된 관심상품의 희망 최저가 변경")
    void test2() {
        // given
        Long productId = createdProduct.getId();
        int myPrice = 173000;

        ProductMypriceRequestDto requestDto = new ProductMypriceRequestDto();
        requestDto.setMyprice(myPrice);

        // when
        ProductResponseDto product = productService.updateProduct(productId, requestDto);

        // then
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(createdProduct.getTitle(), product.getTitle());
        assertEquals(createdProduct.getImage(), product.getImage());
        assertEquals(createdProduct.getLink(), product.getLink());
        assertEquals(createdProduct.getLprice(), product.getLprice());
        assertEquals(myPrice, product.getMyprice());

        updatedMyPrice = myPrice;
    }

    @Test
    @Order(3)
    @DisplayName("회원이 등록한 모든 관심상품 조회")
    void test3() {
        // when
        Page<ProductResponseDto> productList = productService.getProducts(
                user, 0, 10, "id", false
        );

        // then
        Long createdProductId = createdProduct.getId();

        ProductResponseDto foundProduct = productList.stream()
                .filter(product -> product.getId().equals(createdProductId))
                .findFirst()
                .orElse(null);

        assertNotNull(foundProduct);
        assertEquals(createdProduct.getId(), foundProduct.getId());
        assertEquals(createdProduct.getTitle(), foundProduct.getTitle());
        assertEquals(createdProduct.getImage(), foundProduct.getImage());
        assertEquals(createdProduct.getLink(), foundProduct.getLink());
        assertEquals(createdProduct.getLprice(), foundProduct.getLprice());
        assertEquals(updatedMyPrice, foundProduct.getMyprice());
    }
}