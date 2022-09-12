package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.stock.StockRequestDto;
import com.fevertime.coinvillage.dto.stock.StockResponseDto;
import com.fevertime.coinvillage.dto.stock.StockUpdateRequestDto;
import com.fevertime.coinvillage.dto.stock.nation.StockNationRequestDto;
import com.fevertime.coinvillage.dto.stock.nation.StockNationResponseDto;
import com.fevertime.coinvillage.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "주식")
@RequestMapping("stocks")
public class StockApiController {
    private final StockService stockService;

    @GetMapping("ruler")
    @ApiOperation(value = "주식 종목 전체보기(선생님)")
    @PreAuthorize("hasRole('ROLE_RULER')")
    public ResponseEntity<List<StockResponseDto>> showStocks(Authentication authentication) {
        return ResponseEntity.ok(stockService.showStocks(authentication.getName()));
    }

    @GetMapping("ruler/{stockId}")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "주식 종목 상세보기(선생님)")
    public ResponseEntity<StockResponseDto> showStock(@PathVariable Long stockId) {
        return ResponseEntity.ok(stockService.showStock(stockId));
    }

    @PostMapping("ruler")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "주식 종목 추가하기(선생님)")
    public ResponseEntity<StockResponseDto> makeStock(Authentication authentication, @RequestBody StockRequestDto stockRequestDto) {
        return ResponseEntity.ok(stockService.makeStocks(authentication.getName(), stockRequestDto));
    }

    @PutMapping("ruler/{stockId}")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "주식 종목 수정하기(선생님)")
    public ResponseEntity<StockResponseDto> changeStock(@PathVariable Long stockId, @RequestBody StockUpdateRequestDto stockUpdateRequestDto) {
        return ResponseEntity.ok(stockService.changeStocks(stockId, stockUpdateRequestDto));
    }

    @DeleteMapping("ruler/{stockId}")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "주식 종목 삭제하기(선생님)")
    public ResponseEntity delStock(@PathVariable Long stockId) {
        stockService.deleteStock(stockId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    @ApiOperation(value = "주식 종목 전체보기(학생)")
    public ResponseEntity<List<StockNationResponseDto>> showNationStocks(Authentication authentication) {
        return ResponseEntity.ok(stockService.showNationStocks(authentication.getName()));
    }

    @GetMapping("{stockId}")
    @ApiOperation(value = "주식 종목 상세보기(학생)")
    public ResponseEntity<StockResponseDto> showNationStock(@PathVariable Long stockId) {
        return ResponseEntity.ok(stockService.showNationStock(stockId));
    }

    @PostMapping("{stockId}")
    @ApiOperation(value = "주식 종목 구매하기(학생)")
    public ResponseEntity<StockResponseDto> buyNationStock(@PathVariable Long stockId, Authentication authentication, @RequestBody StockNationRequestDto stockNationRequestDto) {
        return ResponseEntity.ok(stockService.buyStock(stockId, authentication.getName(), stockNationRequestDto));
    }
}
