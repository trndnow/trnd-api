package com.trnd.trndapi.commons.service;

import com.trnd.trndapi.commons.dto.TimezoneRefDto;

import java.util.List;

public interface TimezoneRefService {
    TimezoneRefDto addTimezone(TimezoneRefDto timezoneRefDto);

    List<TimezoneRefDto> addTimezone(List<TimezoneRefDto> timezoneRefDto);

    List<TimezoneRefDto> viewTimezone();
}
