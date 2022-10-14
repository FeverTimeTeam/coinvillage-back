package com.fevertime.coinvillage.domain.stock;

import com.fevertime.coinvillage.domain.account.repository.AccountHistoryRepository;
import com.fevertime.coinvillage.domain.account.entity.AccountHistory;
import com.fevertime.coinvillage.domain.member.repository.MemberRepository;
import com.fevertime.coinvillage.domain.stock.dto.nation.StockHistoryResponseDto;
import com.fevertime.coinvillage.domain.stock.dto.nation.StockNationMypageResponseDto;
import com.fevertime.coinvillage.domain.stock.dto.nation.StockNationRequestDto;
import com.fevertime.coinvillage.domain.stock.dto.nation.StockNationResponseDto;
import com.fevertime.coinvillage.domain.stock.entity.CurrentStock;
import com.fevertime.coinvillage.domain.stock.entity.StockBuy;
import com.fevertime.coinvillage.domain.stock.entity.StockHistory;
import com.fevertime.coinvillage.domain.member.entity.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.account.dto.AccountHistoryResponseDto;
import com.fevertime.coinvillage.domain.stock.dto.StockBuyRequestDto;
import com.fevertime.coinvillage.domain.stock.dto.StockBuyResponseDto;
import com.fevertime.coinvillage.domain.stock.dto.StockBuyUpdateRequestDto;
import com.fevertime.coinvillage.domain.stock.repository.CurrentStockRepository;
import com.fevertime.coinvillage.domain.stock.repository.StockBuyRepository;
import com.fevertime.coinvillage.domain.stock.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final MemberRepository memberRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final StockBuyRepository stockBuyRepository;
    private final CurrentStockRepository currentStockRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    // 주식 종목 생성(선생님)
    @Transactional
    public StockBuyResponseDto makeStocks(String email, StockBuyRequestDto stockBuyRequestDto) {
        Member member = memberRepository.findByEmail(email);

        stockBuyRequestDto.setStockTotal(member.getStock().getStockTotal());
        stockBuyRequestDto.setStock(member.getStock());
        StockBuy stockBuy = stockBuyRequestDto.toEntity();
        stockBuyRepository.save(stockBuy);

        return new StockBuyResponseDto(stockBuy);
    }

    // 주식 종목 전체보기(선생님)
    @Transactional(readOnly = true)
    public List<StockBuyResponseDto> showStocks(String email) {
        List<StockBuy> stockBuyList = stockBuyRepository.findAllByStock_Member_Country_CountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        return stockBuyList.stream()
                .map(StockBuyResponseDto::new).collect(Collectors.toList());
    }
    
    // 주식 종목 상세보기(선생님)
    @Transactional(readOnly = true)
    public StockBuyResponseDto showStock(Long stockId) {
        StockBuy stockBuy = stockBuyRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        return new StockBuyResponseDto(stockBuy);
    }
    
    // 주식 종목 수정(선생님)
    @Transactional
    public StockBuyResponseDto changeStocks(Long stockBuyId, StockBuyUpdateRequestDto stockBuyUpdateRequestDto) {
        StockBuy stockBuy = stockBuyRepository.findById(stockBuyId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));

        // 주식 변동 퍼센트용 로그 남기기
        StockHistory stockHistory = StockHistory.builder()
                .content(stockBuy.getContent())
                .price(stockBuy.getPrice())
                .stockBuy(stockBuy)
                .build();
        stockHistoryRepository.save(stockHistory);

        // 만약 사놓은 주식이 있다면 그 주식목록에 수정사항 적용
        if (currentStockRepository.existsByContentAndStock_Member_Country_CountryName(stockBuy.getContent(),
                memberRepository.findByEmail(stockBuy.getStock().getMember().getEmail()).getCountry().getCountryName())) {
            List<CurrentStock> currentStockList = currentStockRepository.findAllByContentAndStock_Member_Country_CountryName(stockBuy.getContent(),
                    memberRepository.findByEmail(stockBuy.getStock().getMember().getEmail()).getCountry().getCountryName());
            currentStockList.forEach(a -> a.change(stockBuyUpdateRequestDto.getContent(), stockBuyUpdateRequestDto.getDescription(), stockBuyUpdateRequestDto.getPrice()));
            currentStockRepository.saveAll(currentStockList);
        }

        // 주식 변경 사항 적용
        stockBuy.update(stockBuyUpdateRequestDto.getContent(), stockBuyUpdateRequestDto.getDescription(), stockBuyUpdateRequestDto.getPrice());
        stockBuyRepository.save(stockBuy);

        return new StockBuyResponseDto(stockBuy);
    }

    // 주식 종목 삭제(선생님)
    @Transactional
    public void deleteStock(Long stockId) {
        stockBuyRepository.deleteById(stockId);
    }

    // 주식 종목 전체보기(학생)
    @Transactional(readOnly = true)
    public List<StockNationResponseDto> showNationStocks(String email) {
        List<StockBuy> stockList = stockBuyRepository.findAllByStock_Member_Country_CountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        return stockList.stream()
                .map(StockNationResponseDto::new)
                .collect(Collectors.toList());
    }

    // 주식 종목 상세보기(학생)
    @Transactional(readOnly = true)
    public StockBuyResponseDto showNationStock(Long stockId) {
        StockBuy stockBuy = stockBuyRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        return new StockBuyResponseDto(stockBuy);
    }

    // 주식 구매하기(학생)
    @Transactional
    public StockBuyResponseDto buyStock(Long stockBuyId, String email, StockNationRequestDto stockNationRequestDto) {
        StockBuy stockBuy = stockBuyRepository.findById(stockBuyId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        Member member = memberRepository.findByEmail(email);

        // 주식 구매 기록 남기기(학생)
        StockHistory stockHistory = StockHistory.builder()
                .content(stockBuy.getContent())
                .count(stockNationRequestDto.getCount())
                .price(stockBuy.getPrice())
                .stateName(StateName.WITHDRAWL)
                .member(member)
                .build();
        stockHistoryRepository.save(stockHistory);

        // 현재 보유 주식
        if (currentStockRepository.existsByStock_Member_Email(email)) {
            CurrentStock currentStock = currentStockRepository.findByStock_Member_Email(email);
            currentStock.buyCount(stockNationRequestDto.getCount());
            currentStockRepository.save(currentStock);
        } else {
            CurrentStock currentStock = CurrentStock.builder()
                    .content(stockBuy.getContent())
                    .description(stockBuy.getDescription())
                    .count(stockNationRequestDto.getCount())
                    .price(stockBuy.getPrice())
                    .stock(member.getStock())
                    .build();
            currentStockRepository.save(currentStock);
        }

        // 재산 변경
        member.getStock().changeStockTotal(member.getStock().getStockTotal()
                - stockNationRequestDto.getCount() * stockBuy.getPrice());
        member.changeProperty(member.getAccount().getAccountTotal()
                + member.getSavings().getSavingsTotal()
                + member.getStock().getStockTotal());
        memberRepository.save(member);

        return new StockBuyResponseDto(stockBuy);
    }

    // 주식 마이페이지 전체보기(학생)
    @Transactional(readOnly = true)
    public List<StockNationMypageResponseDto> showMypages(String email) {
        List<CurrentStock> currentStockList = currentStockRepository.findAllByStock_Member_Email(email);
        List<StockNationMypageResponseDto> stockNationMypageResponseDtos = currentStockList.stream()
                .map(StockNationMypageResponseDto::new)
                .collect(Collectors.toList());
        stockNationMypageResponseDtos.forEach(a -> a.setStockTotal(memberRepository.findByEmail(email).getStock().getStockTotal()));
        return stockNationMypageResponseDtos;
    }

    // 주식 마이페이지 상세보기(학생)
    @Transactional(readOnly = true)
    public StockNationMypageResponseDto showMypage(Long currentStockId) {
        CurrentStock currentStock = currentStockRepository.findById(currentStockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        return new StockNationMypageResponseDto(currentStock);
    }

    // 주식 마이페이지 판매하기(학생)
    @Transactional
    public StockNationMypageResponseDto sellStocks(Long currentStockId, String email, StockNationRequestDto stockNationRequestDto) {
        CurrentStock currentStock = currentStockRepository.findById(currentStockId).orElseThrow(() -> new IllegalArgumentException("해당 종목 없음"));
        Member member = memberRepository.findByEmail(email);

        // 주식 판매 기록
        StockHistory stockHistory = StockHistory.builder()
                .content(currentStock.getContent())
                .stateName(StateName.DEPOSIT)
                .count(stockNationRequestDto.getCount())
                .price(currentStock.getPrice())
                .member(member)
                .build();
        stockHistoryRepository.save(stockHistory);

        // 몇 주 파는지
        currentStock.changeCount(stockNationRequestDto.getCount());
        currentStockRepository.save(currentStock);

        // 주식통장 잔액 구하기
        member.getStock().changeStockTotal(member.getStock().getStockTotal() + stockNationRequestDto.getCount() * currentStock.getPrice());
        member.changeProperty(member.getAccount().getAccountTotal() + member.getSavings().getSavingsTotal() + member.getStock().getStockTotal());
        memberRepository.save(member);

        return new StockNationMypageResponseDto(currentStock);
    }

    // 주식 거래기록 확인
    @Transactional(readOnly = true)
    public List<StockHistoryResponseDto> showStockBuys(String email) {
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAllByMember_Email(email);
        return stockHistoryList.stream()
                .map(StockHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 주식통장에서 입출금통장으로 입금
    @Transactional
    public AccountHistoryResponseDto stockTransfer(String email, StockBuyRequestDto stockBuyRequestDto) {
        Member member = memberRepository.findByEmail(email);

        AccountHistory accountHistory = AccountHistory.builder()
                .total(stockBuyRequestDto.getPrice())
                .stateName(StateName.DEPOSIT)
                .content("주식통장에서 입금")
                .account(member.getAccount())
                .build();
        accountHistoryRepository.save(accountHistory);

        member.getAccount().changeAccountTotal(member.getAccount().getAccountTotal() + stockBuyRequestDto.getPrice());
        member.getStock().changeStockTotal(member.getStock().getStockTotal() - stockBuyRequestDto.getPrice());
        memberRepository.save(member);

        return new AccountHistoryResponseDto(accountHistory);
    }
}
