package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.Country;
import com.fevertime.coinvillage.domain.account.Stock;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.dto.stock.StockRequestDto;
import com.fevertime.coinvillage.dto.stock.StockResponseDto;
import com.fevertime.coinvillage.dto.stock.StockUpdateRequestDto;
import com.fevertime.coinvillage.repository.CountryRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
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

    // 주식 종목 생성
    public StockResponseDto makeStocks(String email, StockRequestDto stockRequestDto) {
        Member member = memberRepository.findByEmail(email);

        stockRequestDto.setMember(member);
        Stock stock = stockRequestDto.toEntity();
        stockRepository.save(stock);

        return new StockResponseDto(stock);
    }

    // 주식 종목 보기
    public List<StockResponseDto> showStocks(String email) {
        List<Stock> stockList = stockRepository.findAllByMember_Email(email);
        return stockList.stream()
                .map(StockResponseDto::new).collect(Collectors.toList());
    }
    
    // 주식 종목 상세보기
    public StockResponseDto showStock(Long stockId) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        return new StockResponseDto(stock);
    }
    
    // 주식 종목 수정
    public StockResponseDto changeStocks(Long stockId, StockUpdateRequestDto stockUpdateRequestDto) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));

        stock.update(stockUpdateRequestDto.getContent(), stockUpdateRequestDto.getDescription(), stockUpdateRequestDto.getPrice());

        stockRepository.save(stock);

        return new StockResponseDto(stock);
    }

    // 주식 종목 삭제
    public void deleteStock(Long stockId) {
        stockRepository.deleteById(stockId);
    }
}
