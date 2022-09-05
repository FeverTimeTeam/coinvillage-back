package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.manage.ManageResponseDto;
import com.fevertime.coinvillage.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("manage")
@Api(tags = "국민관리")
public class ManageApiController {
    private final MemberService memberService;

    @GetMapping
    @ApiOperation(value = "국민 전체보기")
    public ResponseEntity<List<ManageResponseDto>> showMembers() {
        return ResponseEntity.ok(memberService.showMembers());
    }
}
