package org.example.myselectshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.myselectshop.entity.Product;
import org.example.myselectshop.entity.ProductFolder;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String title;
    private String link;
    private String image;
    private int lprice;
    private int myprice;

    private List<FolderResponseDto> productFolderList = new ArrayList<>();

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.link = product.getLink();
        this.image = product.getImage();
        this.lprice = product.getLprice();
        this.myprice = product.getMyprice();
        for (ProductFolder productFolder : product.getProductFoldersList()) {
            productFolderList.add(new FolderResponseDto(productFolder.getFolder()));
        }
    }
}