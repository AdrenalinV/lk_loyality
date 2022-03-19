package ru.gb.lk_loyality.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.CounterResponseDto;
import ru.gb.lk_loyality.entities.Counter;
import ru.gb.lk_loyality.services.DocumentService;

@Service
@RequiredArgsConstructor
public class CounterMapper {
    private final DocumentService documentService;

    public Counter mapToCounter(CounterResponseDto counterResponseDto) {
        Counter counter = new Counter();
        counter.setCardId(documentService.getDocumentByNumber(counterResponseDto.getDocumentNumber()).get().getCardId());
        counter.setDelta(counterResponseDto.getDelta());
        counter.setDeltaDateTime(counterResponseDto.getDeltaDateTime());
        counter.setActiveDate(counterResponseDto.getActiveDate());
        counter.setDocumentId(documentService.getDocumentByNumber(counterResponseDto.getDocumentNumber()).get().getId());
        return counter;
    }
}
