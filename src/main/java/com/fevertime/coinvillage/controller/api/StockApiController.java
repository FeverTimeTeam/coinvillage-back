package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.account.AccountHistoryResponseDto;
import com.fevertime.coinvillage.dto.stock.StockBuyRequestDto;
import com.fevertime.coinvillage.dto.stock.StockBuyResponseDto;
import com.fevertime.coinvillage.dto.stock.StockBuyUpdateRequestDto;
import com.fevertime.coinvillage.dto.stock.nation.*;
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
    public ResponseEntity<List<StockBuyResponseDto>> showStocks(Authentication authentication) {
        return ResponseEntity.ok(stockService.showStocks(authentication.getName()));
    }

    @GetMapping("ruler/{stockBuyId}")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "주식 종목 상세보기(선생님)")
    public ResponseEntity<StockBuyResponseDto> showStock(@PathVariable Long stockBuyId) {
        return ResponseEntity.ok(stockService.showStock(stockBuyId));
    }

    @PostMapping("ruler")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "주식 종목 추가하기(선생님)")
    public ResponseEntity<StockBuyResponseDto> makeStock(Authentication authentication, @RequestBody StockBuyRequestDto stockBuyRequestDto) {
        return ResponseEntity.ok(stockService.makeStocks(authentication.getName(), stockBuyRequestDto));
    }

    @PutMapping("ruler/{stockBuyId}")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "주식 종목 수정하기(선생님)")
    public ResponseEntity<StockBuyResponseDto> changeStock(@PathVariable Long stockBuyId, @RequestBody StockBuyUpdateRequestDto stockBuyUpdateRequestDto) {
        return ResponseEntity.ok(stockService.changeStocks(stockBuyId, stockBuyUpdateRequestDto));
    }

    @DeleteMapping("ruler/{stockBuyId}")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "주식 종목 삭제하기(선생님)")
    public ResponseEntity<?> delStock(@PathVariable Long stockBuyId) {
        stockService.deleteStock(stockBuyId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    @ApiOperation(value = "주식 종목 전체보기(학생)")
    public ResponseEntity<List<StockNationResponseDto>> showNationStocks(Authentication authentication) {
        return ResponseEntity.ok(stockService.showNationStocks(authentication.getName()));
    }

    @GetMapping("{stockBuyId}")
    @ApiOperation(value = "주식 종목 상세보기(학생)")
    public ResponseEntity<StockBuyResponseDto> showNationStock(@PathVariable Long stockBuyId) {
        return ResponseEntity.ok(stockService.showNationStock(stockBuyId));
    }

    @PostMapping("{stockBuyId}")
    @ApiOperation(value = "주식 종목 구매하기(학생)")
    public ResponseEntity<StockBuyResponseDto> buyNationStock(@PathVariable Long stockBuyId, Authentication authentication, @RequestBody StockNationRequestDto stockNationRequestDto) {
        return ResponseEntity.ok(stockService.buyStock(stockBuyId, authentication.getName(), stockNationRequestDto));
    }

    @GetMapping("mypage")
    @ApiOperation(value = "주식 종목 마이페이지(학생)")
    public ResponseEntity<List<StockNationMypageResponseDto>> nationMypages(Authentication authentication) {
        return ResponseEntity.ok(stockService.showMypages(authentication.getName()));
    }

    @GetMapping("mypage/{stockBuyId}")
    @ApiOperation(value = "주식 종목 마이페이지 상세보기(학생)")
    public ResponseEntity<StockNationMypageResponseDto> nationMypage(@PathVariable Long stockBuyId) {
        return ResponseEntity.ok(stockService.showMypage(stockBuyId));
    }

    @PostMapping("mypage/{currentStockId}")
    @ApiOperation(value = "주식 종목 마이페이지 판매하기(학생)")
    public ResponseEntity<StockNationMypageResponseDto> sellStocks(@PathVariable Long currentStockId, Authentication authentication, @RequestBody StockNationRequestDto stockNationRequestDto) {
        return ResponseEntity.ok(stockService.sellStocks(currentStockId, authentication.getName(), stockNationRequestDto));
    }

    @GetMapping("history")
    @ApiOperation(value = "주식 종목 거래내역(학생)")
    public ResponseEntity<List<StockHistoryResponseDto>> showStockBuys(Authentication authentication) {
        return ResponseEntity.ok(stockService.showStockBuys(authentication.getName()));
    }

    @PostMapping("transfer")
    @ApiOperation(value = "주식통장에서 입출금통장으로 이체")
    public ResponseEntity<AccountHistoryResponseDto> stockTransfer(Authentication authentication, @RequestBody StockBuyRequestDto stockBuyRequestDto) {
        return ResponseEntity.ok(stockService.stockTransfer(authentication.getName(), stockBuyRequestDto));
    }
}
