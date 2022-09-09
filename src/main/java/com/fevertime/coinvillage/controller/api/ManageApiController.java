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
@RequestMapping("managements")
@Api(tags = "국민관리")
public class ManageApiController {
    private final ManageService manageService;

    @GetMapping
    @ApiOperation(value = "국민관리 전체보기")
    public ResponseEntity<List<ManageResponseDto>> showMembers() {
        return ResponseEntity.ok(manageService.showMembers());
    }

    @PutMapping("{memberId}")
    @ApiOperation(value = "국민관리 직업설정")
    public ResponseEntity<ManageResponseDto> modMembers(@PathVariable Long memberId, @RequestBody ManageUpdateRequestDto manageUpdateRequestDto) {
        return ResponseEntity.ok(manageService.modMembers(memberId, manageUpdateRequestDto));
    }

    @DeleteMapping("{memberId}")
    @ApiOperation(value = "국민관리 회원삭제")
    public ResponseEntity delMember(@PathVariable Long memberId) {
        manageService.delMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("search")
    @ApiOperation(value = "국민관리 회원검색")
    public ResponseEntity<List<ManageResponseDto>> searchMembers(@RequestParam String searchWord) {
        return ResponseEntity.ok(manageService.searchMembers(searchWord));
    }

    @PutMapping("pay/{memberId}")
    @ApiOperation(value = "국민관리 월급지급")
    public ResponseEntity<ManageResponseDto> payMembers(@PathVariable Long memberId) {
        return ResponseEntity.ok(manageService.payMembers(memberId));
    }
}
