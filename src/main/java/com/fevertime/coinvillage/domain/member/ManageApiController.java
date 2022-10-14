package com.fevertime.coinvillage.domain.member;

import com.fevertime.coinvillage.domain.member.dto.manage.ManageResponseDto;
import com.fevertime.coinvillage.domain.member.dto.manage.ManageUpdateRequestDto;
import com.fevertime.coinvillage.domain.member.ManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<ManageResponseDto>> showMembers(Authentication authentication) {
        return ResponseEntity.ok(manageService.showMembers(authentication.getName()));
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
    public ResponseEntity<List<ManageResponseDto>> searchMembers(@RequestParam String searchWord, Authentication authentication) {
        return ResponseEntity.ok(manageService.searchMembers(searchWord, authentication.getName()));
    }

    @PutMapping("pay/{memberId}")
    @ApiOperation(value = "국민관리 월급지급")
    public ResponseEntity<ManageResponseDto> payMembers(@PathVariable Long memberId) {
        return ResponseEntity.ok(manageService.payMembers(memberId));
    }
}
