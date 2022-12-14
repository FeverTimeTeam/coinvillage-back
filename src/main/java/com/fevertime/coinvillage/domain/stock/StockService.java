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

    // ?????? ?????? ??????(?????????)
    @Transactional
    public StockBuyResponseDto makeStocks(String email, StockBuyRequestDto stockBuyRequestDto) {
        Member member = memberRepository.findByEmail(email);

        stockBuyRequestDto.setStockTotal(member.getStock().getStockTotal());
        stockBuyRequestDto.setStock(member.getStock());
        StockBuy stockBuy = stockBuyRequestDto.toEntity();
        stockBuyRepository.save(stockBuy);

        return new StockBuyResponseDto(stockBuy);
    }

    // ?????? ?????? ????????????(?????????)
    @Transactional(readOnly = true)
    public List<StockBuyResponseDto> showStocks(String email) {
        List<StockBuy> stockBuyList = stockBuyRepository.findAllByStock_Member_Country_CountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        return stockBuyList.stream()
                .map(StockBuyResponseDto::new).collect(Collectors.toList());
    }
    
    // ?????? ?????? ????????????(?????????)
    @Transactional(readOnly = true)
    public StockBuyResponseDto showStock(Long stockId) {
        StockBuy stockBuy = stockBuyRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("?????? ?????? ??????"));
        return new StockBuyResponseDto(stockBuy);
    }
    
    // ?????? ?????? ??????(?????????)
    @Transactional
    public StockBuyResponseDto changeStocks(Long stockBuyId, StockBuyUpdateRequestDto stockBuyUpdateRequestDto) {
        StockBuy stockBuy = stockBuyRepository.findById(stockBuyId).orElseThrow(() -> new IllegalArgumentException("?????? ?????? ??????"));

        // ?????? ?????? ???????????? ?????? ?????????
        StockHistory stockHistory = StockHistory.builder()
                .content(stockBuy.getContent())
                .price(stockBuy.getPrice())
                .stockBuy(stockBuy)
                .build();
        stockHistoryRepository.save(stockHistory);

        // ?????? ????????? ????????? ????????? ??? ??????????????? ???????????? ??????
        if (currentStockRepository.existsByContentAndStock_Member_Country_CountryName(stockBuy.getContent(),
                memberRepository.findByEmail(stockBuy.getStock().getMember().getEmail()).getCountry().getCountryName())) {
            List<CurrentStock> currentStockList = currentStockRepository.findAllByContentAndStock_Member_Country_CountryName(stockBuy.getContent(),
                    memberRepository.findByEmail(stockBuy.getStock().getMember().getEmail()).getCountry().getCountryName());
            currentStockList.forEach(a -> a.change(stockBuyUpdateRequestDto.getContent(), stockBuyUpdateRequestDto.getDescription(), stockBuyUpdateRequestDto.getPrice()));
            currentStockRepository.saveAll(currentStockList);
        }

        // ?????? ?????? ?????? ??????
        stockBuy.update(stockBuyUpdateRequestDto.getContent(), stockBuyUpdateRequestDto.getDescription(), stockBuyUpdateRequestDto.getPrice());
        stockBuyRepository.save(stockBuy);

        return new StockBuyResponseDto(stockBuy);
    }

    // ?????? ?????? ??????(?????????)
    @Transactional
    public void deleteStock(Long stockId) {
        stockBuyRepository.deleteById(stockId);
    }

    // ?????? ?????? ????????????(??????)
    @Transactional(readOnly = true)
    public List<StockNationResponseDto> showNationStocks(String email) {
        List<StockBuy> stockList = stockBuyRepository.findAllByStock_Member_Country_CountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        return stockList.stream()
                .map(StockNationResponseDto::new)
                .collect(Collectors.toList());
    }

    // ?????? ?????? ????????????(??????)
    @Transactional(readOnly = true)
    public StockBuyResponseDto showNationStock(Long stockId) {
        StockBuy stockBuy = stockBuyRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("?????? ?????? ??????"));
        return new StockBuyResponseDto(stockBuy);
    }

    // ?????? ????????????(??????)
    @Transactional
    public StockBuyResponseDto buyStock(Long stockBuyId, String email, StockNationRequestDto stockNationRequestDto) {
        StockBuy stockBuy = stockBuyRepository.findById(stockBuyId).orElseThrow(() -> new IllegalArgumentException("?????? ?????? ??????"));
        Member member = memberRepository.findByEmail(email);

        // ?????? ?????? ?????? ?????????(??????)
        StockHistory stockHistory = StockHistory.builder()
                .content(stockBuy.getContent())
                .count(stockNationRequestDto.getCount())
                .price(stockBuy.getPrice())
                .stateName(StateName.WITHDRAWL)
                .member(member)
                .build();
        stockHistoryRepository.save(stockHistory);

        // ?????? ?????? ??????
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

        // ?????? ??????
        member.getStock().changeStockTotal(member.getStock().getStockTotal()
                - stockNationRequestDto.getCount() * stockBuy.getPrice());
        member.changeProperty(member.getAccount().getAccountTotal()
                + member.getSavings().getSavingsTotal()
                + member.getStock().getStockTotal());
        memberRepository.save(member);

        return new StockBuyResponseDto(stockBuy);
    }

    // ?????? ??????????????? ????????????(??????)
    @Transactional(readOnly = true)
    public List<StockNationMypageResponseDto> showMypages(String email) {
        List<CurrentStock> currentStockList = currentStockRepository.findAllByStock_Member_Email(email);
        List<StockNationMypageResponseDto> stockNationMypageResponseDtos = currentStockList.stream()
                .map(StockNationMypageResponseDto::new)
                .collect(Collectors.toList());
        stockNationMypageResponseDtos.forEach(a -> a.setStockTotal(memberRepository.findByEmail(email).getStock().getStockTotal()));
        return stockNationMypageResponseDtos;
    }

    // ?????? ??????????????? ????????????(??????)
    @Transactional(readOnly = true)
    public StockNationMypageResponseDto showMypage(Long currentStockId) {
        CurrentStock currentStock = currentStockRepository.findById(currentStockId).orElseThrow(() -> new IllegalArgumentException("?????? ?????? ??????"));
        return new StockNationMypageResponseDto(currentStock);
    }

    // ?????? ??????????????? ????????????(??????)
    @Transactional
    public StockNationMypageResponseDto sellStocks(Long currentStockId, String email, StockNationRequestDto stockNationRequestDto) {
        CurrentStock currentStock = currentStockRepository.findById(currentStockId).orElseThrow(() -> new IllegalArgumentException("?????? ?????? ??????"));
        Member member = memberRepository.findByEmail(email);

        // ?????? ?????? ??????
        StockHistory stockHistory = StockHistory.builder()
                .content(currentStock.getContent())
                .stateName(StateName.DEPOSIT)
                .count(stockNationRequestDto.getCount())
                .price(currentStock.getPrice())
                .member(member)
                .build();
        stockHistoryRepository.save(stockHistory);

        // ??? ??? ?????????
        currentStock.changeCount(stockNationRequestDto.getCount());
        currentStockRepository.save(currentStock);

        // ???????????? ?????? ?????????
        member.getStock().changeStockTotal(member.getStock().getStockTotal() + stockNationRequestDto.getCount() * currentStock.getPrice());
        member.changeProperty(member.getAccount().getAccountTotal() + member.getSavings().getSavingsTotal() + member.getStock().getStockTotal());
        memberRepository.save(member);

        return new StockNationMypageResponseDto(currentStock);
    }

    // ?????? ???????????? ??????
    @Transactional(readOnly = true)
    public List<StockHistoryResponseDto> showStockBuys(String email) {
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAllByMember_Email(email);
        return stockHistoryList.stream()
                .map(StockHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    // ?????????????????? ????????????????????? ??????
    @Transactional
    public AccountHistoryResponseDto stockTransfer(String email, StockBuyRequestDto stockBuyRequestDto) {
        Member member = memberRepository.findByEmail(email);

        AccountHistory accountHistory = AccountHistory.builder()
                .total(stockBuyRequestDto.getPrice())
                .stateName(StateName.DEPOSIT)
                .content("?????????????????? ??????")
                .account(member.getAccount())
                .build();
        accountHistoryRepository.save(accountHistory);

        member.getAccount().changeAccountTotal(member.getAccount().getAccountTotal() + stockBuyRequestDto.getPrice());
        member.getStock().changeStockTotal(member.getStock().getStockTotal() - stockBuyRequestDto.getPrice());
        memberRepository.save(member);

        return new AccountHistoryResponseDto(accountHistory);
    }
}
