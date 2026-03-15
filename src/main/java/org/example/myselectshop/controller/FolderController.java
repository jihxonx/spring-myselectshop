package org.example.myselectshop.controller;

import lombok.RequiredArgsConstructor;
import org.example.myselectshop.dto.FolderRequestDto;
import org.example.myselectshop.security.UserDetailsImpl;
import org.example.myselectshop.service.FolderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/folders")
    public void addFolders(@RequestBody FolderRequestDto folderRequestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<String> folderNames = folderRequestDto.getFolderNames();
        folderService.addFolders(folderNames, userDetails.getUser());
    }
}
