package com.trnd.trndapi.commons.service;

import com.trnd.trndapi.commons.mapper.TimezoneRefMapper;
import com.trnd.trndapi.commons.dto.TimezoneRefDto;
import com.trnd.trndapi.commons.repository.TimezoneRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimezoneRefServiceImpl implements TimezoneRefService{

    private final TimezoneRefRepository timezoneRefRepository;

    @Override
    public TimezoneRefDto addTimezone(TimezoneRefDto timezoneRefDto) {
        return TimezoneRefMapper.INSTANCE.convertToDto(timezoneRefRepository.save(TimezoneRefMapper.INSTANCE.convertToEntity(timezoneRefDto)));
    }

    @Override
    public List<TimezoneRefDto> addTimezone(List<TimezoneRefDto> timezoneRefDtoList) {
        return TimezoneRefMapper.INSTANCE.convertToDto(timezoneRefRepository.saveAll(TimezoneRefMapper.INSTANCE.convertToEntity(timezoneRefDtoList)));
    }

    @Override
    public List<TimezoneRefDto> viewTimezone() {
        return TimezoneRefMapper.INSTANCE.convertToDto(timezoneRefRepository.findAll());
    }
}
