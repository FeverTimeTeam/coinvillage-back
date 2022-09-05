package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.manage.ManageResponseDto;
import com.fevertime.coinvillage.dto.manage.ManageUpdateRequestDto;
import com.fevertime.coinvillage.service.ManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("manage")
@Api(tags = "국민관리")
public class ManageApiController {
    private final ManageService manageService;

    @GetMapping
    @ApiOperation(value = "국민관리 전체보기")
    public ResponseEntity<List<ManageResponseDto>> showMembers() {
        return ResponseEntity.ok(manageService.showMembers());
    }

    @PutMapping
    @ApiOperation(value = "국민 관리 직업설정")
    public ResponseEntity<ManageResponseDto> modMembers(String nickname, @RequestBody ManageUpdateRequestDto manageUpdateRequestDto) {
        return ResponseEntity.ok(manageService.modMembers(nickname, manageUpdateRequestDto));
    }
}
