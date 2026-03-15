package org.example.myselectshop.dto;

import lombok.Getter;
import org.example.myselectshop.entity.Folder;

@Getter
public class FolderResponseDto {
    private Long id;
    private String name;

    public FolderResponseDto(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
    }
}