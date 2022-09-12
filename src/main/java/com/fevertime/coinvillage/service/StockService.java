package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Stock;
import com.fevertime.coinvillage.domain.account.StockBuy;
import com.fevertime.coinvillage.domain.account.StockHistory;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.dto.stock.StockRequestDto;
import com.fevertime.coinvillage.dto.stock.StockResponseDto;
import com.fevertime.coinvillage.dto.stock.StockUpdateRequestDto;
import com.fevertime.coinvillage.dto.stock.nation.StockNationRequestDto;
import com.fevertime.coinvillage.dto.stock.nation.StockNationResponseDto;
import com.fevertime.coinvillage.repository.MemberRepository;
import com.fevertime.coinvillage.repository.StockHistoryRepository;
import com.fevertime.coinvillage.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final StockBuyRepository stockBuyRepository;

    // 주식 종목 생성(선생님)
    public StockResponseDto makeStocks(String email, StockRequestDto stockRequestDto) {
        Member member = memberRepository.findByEmail(email);

        stockRequestDto.setStockTotal(member.getStockTotal());
        stockRequestDto.setMember(member);
        Stock stock = stockRequestDto.toEntity();
        stockRepository.save(stock);

        return new StockResponseDto(stock);
    }

    // 주식 종목 전체보기(선생님)
    public List<StockResponseDto> showStocks(String email) {
        List<Stock> stockList = stockRepository.findAllByMember_Email(email);
        return stockList.stream()
                .map(StockResponseDto::new).collect(Collectors.toList());
    }
    
    // 주식 종목 상세보기(선생님)
    public StockResponseDto showStock(Long stockId) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        return new StockResponseDto(stock);
    }
    
    // 주식 종목 수정(선생님)
    public StockResponseDto changeStocks(Long stockId, StockUpdateRequestDto stockUpdateRequestDto) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));

        StockHistory stockHistory = StockHistory.builder()
                .content(stock.getContent())
                .price(stock.getPrice())
                .stock(stock)
                .build();
        stockHistoryRepository.save(stockHistory);
        stock.update(stockUpdateRequestDto.getContent(), stockUpdateRequestDto.getDescription(), stockUpdateRequestDto.getPrice());
        stockRepository.save(stock);

        return new StockResponseDto(stock);
    }

    // 주식 종목 삭제(선생님)
    public void deleteStock(Long stockId) {
        stockRepository.deleteById(stockId);
    }

    // 주식 종목 전체보기(학생)
    public List<StockNationResponseDto> showNationStocks(String email) {
        List<Stock> stockList = stockRepository.findAllByMember_Country_CountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        return stockList.stream()
                .map(StockNationResponseDto::new).collect(Collectors.toList());
    }

    // 주식 종목 상세보기(학생)
    public StockResponseDto showNationStock(Long stockId) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        return new StockResponseDto(stock);
    }

    // 주식 구매하기(학생)
    public StockResponseDto buyStock(Long stockId, String email, StockNationRequestDto stockNationRequestDto) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        Member member = memberRepository.findByEmail(email);

        StockBuy stockBuy = StockBuy.builder()
                .content(stock.getContent())
                .stateName(StateName.WITHDRAWL)
                .count(stockNationRequestDto.getCount())
                .total(stockNationRequestDto.getTotal())
                .stock(stock)
                .build();
        stockBuyRepository.save(stockBuy);

        member.changeStockTotal(member.getStockTotal() - stock.getPrice());
        member.changeProperty(member.getAccountTotal() + member.getSavingsTotal() + member.getStockTotal());
        memberRepository.save(member);

        return new StockResponseDto(stock);
    }
}
