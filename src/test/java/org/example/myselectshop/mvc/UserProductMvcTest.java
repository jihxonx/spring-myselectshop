package org.example.myselectshop.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.myselectshop.config.WebSecurityConfig;
import org.example.myselectshop.controller.ProductController;
import org.example.myselectshop.controller.UserController;
import org.example.myselectshop.dto.ProductRequestDto;
import org.example.myselectshop.entity.User;
import org.example.myselectshop.entity.UserRoleEnum;
import org.example.myselectshop.security.UserDetailsImpl;
import org.example.myselectshop.service.FolderService;
import org.example.myselectshop.service.KakaoService;
import org.example.myselectshop.service.ProductService;
import org.example.myselectshop.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@WebMvcTest(
        controllers = {UserController.class, ProductController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
@AutoConfigureMockMvc(addFilters = false)
class UserProductMvcTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private KakaoService kakaoService;

    @MockBean
    private ProductService productService;

    @MockBean
    private FolderService folderService;

    private UsernamePasswordAuthenticationToken createAuthentication() {
        User testUser = new User(
                "sollertia4351",
                "robbie1234",
                "sollertia@sparta.com",
                UserRoleEnum.USER
        );

        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);

        return new UsernamePasswordAuthenticationToken(
                testUserDetails,
                null,
                testUserDetails.getAuthorities()
        );
    }

    private void setAuthentication() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(createAuthentication());
        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("로그인 Page")
    void test1() throws Exception {
        mvc.perform(get("/api/user/login-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 가입 요청 처리")
    void test2() throws Exception {
        MultiValueMap<String, String> signupRequestForm = new LinkedMultiValueMap<>();
        signupRequestForm.add("username", "sollertia4351");
        signupRequestForm.add("password", "robbie1234");
        signupRequestForm.add("email", "sollertia@sparta.com");
        signupRequestForm.add("admin", "false");

        mvc.perform(post("/api/user/signup")
                        .params(signupRequestForm))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/user/login-page"))
                .andDo(print());
    }

    @Test
    @DisplayName("신규 관심상품 등록")
    void test3() throws Exception {
        setAuthentication();

        ProductRequestDto requestDto = new ProductRequestDto(
                "Apple <b>아이폰</b> 14 프로 256GB [자급제]",
                "https://shopping-phinf.pstatic.net/main_3456175/34561756621.20220929142551.jpg",
                "https://search.shopping.naver.com/gate.nhn?id=34561756621",
                959000
        );

        String postInfo = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/api/products")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}